
export('easysign.signs', get_value('easysign.signs'))
bind(player_interact, null, null, @e,
	if(array_index_exists(@e, 'location') && is_sign_at(@e['location'])){
		@signdata = import('easysign.signs')
		@key = json_encode(@e['location'])
		if(is_array(@signdata) && array_index_exists(@signdata, @key)){
			foreach(@signdata[@key], @cmd,
				@data = @cmd['data']
				switch(@cmd['type'],
					# Add new types here
					'sleep',
						set_pbed_location(ploc())
						msg(color(LIGHT_PURPLE).'Your bed has now been set!')
					,'warp',
						set_ploc(@data)
					,'ci',
						foreach(0..104,@i,
							@array = associative_array()
							@array[@i] = null
							set_pinv(@array)
						)
					,'give',
						@item = @data['item']
						@qty = @data['qty']
						@slot = @data['slot']
						if(@slot != null){
							@array = associative_array()
							@array[@slot] = array(type: @item, qty: @qty)
							console(@array)
							set_pinv(@array)
						} else {
							pgive_item(@item, @qty)
						}
					,'hunger',
						set_phunger(20)
						set_psaturation(20),
					,'announce',
						@message = @data['message']
						if(reg_count('%s', @data['message'])){
							@message = sprintf(@message, player())
						}
						@value = get_value('easysign.announce')
						@id = @data['id']
						if(!is_array(@value), @value = associative_array())
						if(!array_index_exists(@value, @id)){
							@value[@id] = array()
						}
						if(array_contains(@value[@id], player())){
							msg(color(GREEN).'[SIGN] '.color(WHITE).'You can only announce here once!')
						} else {
							array_push(@value[@id], player())
							store_value('easysign.announce', @value)
							broadcast(colorize(@message))
						}
				)
			)
			cancel()
		}
	}
)

# Every minute, check to see if all the locations are valid signs. If not,
# go ahead and delete it.
set_interval(1 * 60 * 1000, closure(
	@signdata = get_value('easysign.signs')
	if(!is_array(@signdata)){
		@signdata = array()
	}
	foreach(@signdata, @key, @data,
		if(!is_sign_at(json_decode(@key))){
			array_remove(@signdata, @key)
			store_value('easysign.signs', @signdata)
			export('easysign.signs', @signdata)
			console(color(RED).'The sign at '.@key.' is no longer a sign, so it has been deleted from easy signs')
			die()
		}
	)
))
