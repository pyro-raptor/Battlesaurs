package Battlesaurs;

import java.util.Random;

public class Triceratops extends Dinosaur {
    /*
    * MODIFIERS:
    * TRICERATOPS
    * Charge VS ALL = +1-5 dmg to flat rate (16-20 total);
    * SPECIAL: 15% chance to stun opponent. Opponent's next turn will be skipped.
    */

    public Triceratops() {
        super("Triceratops", 100);
    }

    @Override
    public int attack(String action, Dinosaur target) {
        int damage = super.attack(action, target);
        Random random = new Random();
        if (action.equals("charge")) {
            damage += random.nextInt(5) + 1;
        }
        if (random.nextDouble() < 0.15) {
            target.stunned = true;
        }
        return damage;
    }

    @Override
    public String flavorText(String action) {
        if (action.equals("charge")) {
            return "A thundering can be heard... brace for a triceratops's charge!";
        }
        return super.flavorText(action);
    }
}
