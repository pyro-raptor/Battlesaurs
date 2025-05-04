package Battlesaurs;

import java.util.Random;

public class Velociraptor extends Dinosaur {
    /*
    * MODIFIERS:
    * VELOCIRAPTOR
    * Kick VS ALL = Deal 15 flat dmg.
    * SPECIAL: Dodge Rate = 25%
    * SPECIAL: Bleed! Opponent loses 2 HP for 3 turns.
    */

    public Velociraptor () {
        super("Velociraptor", 75);
    }

    @Override
    public int attack(String action, Dinosaur target) {
        int damage = super.attack(action, target);
        if (action.equals("kick")) {
            damage = 15;
        }
        return damage;
    }

    @Override
    public void takeDamage(int damage) {
        dodge = false;
        Random random = new Random();
        if (random.nextDouble() < 0.25) {
            dodge = true;
            return; // He hath dodged!
        }
        super.takeDamage(damage); // He hath not dodged...
    }

    @Override
    public String flavorText(String action) {
        if (action.equals("kick")) {
            return "Velociraptor claws cut through the air...";
        } else if (action.equals("bite")) {
            return "Chomp! The bite of a velociraptor!";
        }
        return super.flavorText(action);
    }
}
