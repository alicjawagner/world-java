package game.organisms.animals;

import game.organisms.Animal;
import game.organisms.Organism;
import game.world.OrganismsNames;
import game.world.World;

public class Turtle extends Animal {

    static final int PROBABILITY_NOT_MOVING = 75;

    public Turtle(World _world) {
        super(_world);
        name = "Turtle";
        strength = 2;
        initiative = 1;
    }

    @Override
    public boolean ifIRepelledTheAttack(final Organism attacker) {
        if (attacker.getStrength() < 5) {
            ////////////////////////////////////////////////////////////////////////////////////////
            // name + " (" + point.x + "," + point.y + ") repelled the attack: "
            return true;
        }
        return false;
    }

    @Override
    public void action() {
        if (rand.nextInt(100) < PROBABILITY_NOT_MOVING)
            return;
        super.action();
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.TURTLE;
    }

    @Override
    public void draw() {
    ///////////////////////////////////////////////////////////////////////////
    }
}