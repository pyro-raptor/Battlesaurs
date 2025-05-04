package Battlesaurs;

public class Ankylosaurus extends Dinosaur {
    /*
    * MODIFIERS:
    * ANKYLOSAURUS
    * Kick becomes Whip; Whip VS ALL = Deal 20 flat dmg.
    */

    public Ankylosaurus () {
        super("Ankylosaurus", 120);
    }

    @Override
    public int attack(String action, Dinosaur target) {
        int damage = super.attack(action, target);
        if (action.equals("kick")) { // Kick becomes Whip! Use flavor text in log...
            damage = 20;
        }
        return damage;
    }

    @Override
    public String flavorText(String action) {
        if (action.equals("kick")) {
            return "Since an ankylosaurus sucks at kicking, a tail whip occurs instead!";
        }
        return super.flavorText(action);
    }
}
