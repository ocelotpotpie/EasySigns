package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PotionAction extends SignAction {


    private SignData sign;
    private PotionEffect effect;
    boolean valid = true;


    public PotionAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            String name = args[0];
            int amplifier = Integer.parseInt(args[1]) - 1;
            int duration = Integer.parseInt(args[2]) * 20;
            PotionEffectType type = PotionEffectType.getByName(name);
            if (amplifier < 0) {
                amplifier = 0;
            }
            if (type != null && duration > 0) {
                effect = new PotionEffect(type, duration, amplifier);
            } else {
                valid = false;
            }
        } catch (IndexOutOfBoundsException|IllegalArgumentException ex) {
            valid = false;
        }
    }


    public PotionAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.effect = (PotionEffect) attributes.get("effect");
    }


    public String getName() {
        return "potion";
    }


    public String getUsage() {
        return "<effect> <strength> <seconds>";
    }


    public String getHelpText() {
        return "Applies a potion effect to the player. <strength> must be at least 1 and " +
                "<duration> is in seconds.";
    }


    public String toString() {
        return String.format("%s %s %d %d",
                getName(),
                effect.getType().getName(),
                effect.getAmplifier() + 1,
                effect.getDuration() / 20
        );
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("effect", effect);
        return map;
    }


    public void action(Player player) {
        player.addPotionEffect(effect, true);
    }


}
