package game.organisms.animals;

import game.organisms.Animal;
import game.organisms.Organism;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Turtle extends Animal {

    static final int PROBABILITY_NOT_MOVING = 75;

    public Turtle(World _world) {
        super(_world);
        name = "Turtle";
        strength = 2;
        initiative = 1;
        sign = "T";
    }

    public Turtle(World _world, String[] arr) {
        super(_world, arr);
        name = "Turtle";
        initiative = 1;
        sign = "T";
    }

    @Override
    public boolean ifIRepelledTheAttack(final Organism attacker) {
        if (attacker.getStrength() < 5) {
            world.text += this + " repelled the attack: ";
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
    public void draw(Graphics g) {
        drawOrg(g, new Color(138,154,91));
    }
}