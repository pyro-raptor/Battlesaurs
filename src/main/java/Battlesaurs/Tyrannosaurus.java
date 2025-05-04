package Battlesaurs;

import java.util.Random;

public class Tyrannosaurus extends Dinosaur {
    /*
    * MODIFIERS:
    * TYRANNOSAURUS REX
    * Stomp VS ALL = Deal 20 flat dmg.
    * Bite VS ALL = Deal 25 flat dmg to opponent.
    * SPECIAL: 30% chance to stun opponent. Opponent's next turn will be skipped.
    * */
    public Tyrannosaurus () {
        super("T-Rex", 100);
    }

    @Override
    public int attack(String action, Dinosaur target) {
        int damage = super.attack(action, target);
        if (action.equals("stomp")) {
            damage = 20;
        }
        if (action.equals("bite")) {
            damage = 25;
        }

        Random random = new Random();
        if (random.nextDouble() < 0.3) {
            target.stunned = true;
        }
        return damage;
    }
}
