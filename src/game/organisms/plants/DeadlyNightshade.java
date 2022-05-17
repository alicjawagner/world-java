package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class DeadlyNightshade extends Plant {

    public DeadlyNightshade(World _world) {
        super(_world);
        name = "Deadly Nightshade";
        strength = 99;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.DEADLY_NIGHTSHADE;
    }

    @Override
    public void writeIWon() {
        world.text += this + " poison: ";
    }

    @Override
    public void draw(Graphics g) {
    //////////////////////////////////////////////////////////////////////////////////////////
    }
}