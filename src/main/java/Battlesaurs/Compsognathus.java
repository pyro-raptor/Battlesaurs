package Battlesaurs;

import java.util.Random;

public class Compsognathus extends Dinosaur {
    /*
    * MODFIIERS:
    * COMPSOGNATHUS PACK
    * Charge, Stomp, Kick VS ALL = Deal 5 dmg to opponent.
    * Bite VS ALL = Deal 25 flat dmg to opponent.
    * SPECIAL: Dodge Rate = 50%
    */

    public Compsognathus () {
        super ("Compy Pack", 60);
    }

    @Override
    public int attack(String action, Dinosaur target) {
        if (action.equals("bite")) {
            return 25;
        }
        return 5; // All other attacks deal 5 flat, as noted
    }

    @Override
    public void takeDamage(int damage) {
        dodge = false;
        Random random = new Random();
        if (random.nextDouble() > 0.5) {
            dodge = true;
            return; // Thou hath dodged!
        }
        super.takeDamage(damage); // Thou hath not dodged well...
    }

    @Override
    public String flavorText(String action) {
        if (action.equals("charge") || action.equals("stomp") || action.equals("kick")) {
            return "Compies are weak...";
        }
        return "It's like a million zaps!";
    }
}
