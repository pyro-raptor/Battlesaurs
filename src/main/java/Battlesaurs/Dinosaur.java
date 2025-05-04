package Battlesaurs;

public abstract class Dinosaur {
    protected String name;
    protected int health;
    protected int maxHealth; // Account for resting...

    public Dinosaur(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health; // Account for resting...
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0; // He dead...
        }
    }

    public void recover() {
        health += 10;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    // Determine damage dealt...
    public int attack(String action, Dinosaur target) {
        switch (action) {
            case "charge":
                return 15;
            case "stomp":
                return 5;
            case "kick":
                return 10;
            case "bite":
                return 15;
            default:
                return 0; // For resting...
        }
    }

    public boolean isAlive() {
        return (health > 0);
    }

    public String flavorText(String action) {
        return (name + " used " + action + "!");
    }

    // For raptor + compy's dodge modifier...
    protected boolean dodge = false;
    public boolean didHeDodge() {
        return dodge;
    }

    // For rex + trike's stun modifier...
    protected boolean stunned = false;
    public boolean isStunned() {
        return stunned;
    }

    public void recoveredStun() {
        this.stunned = false;
    }

    // For raptor's bleed mechanic... bleed for 3 turns
    protected boolean bleeding = false;
    private int bleedTurns = 0;

    public void bleed(int turns) {
        bleeding = true;
        bleedTurns = turns;
    }

    public void bleedCheck() {
        if (bleeding) {
            this.health -= 2;
            bleedTurns--;
            if (bleedTurns <= 0) {
                bleeding = false;
            }
        }
    }


}