package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

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
        /////////////////////////////////////////////////////////////////////////////////////////////
        // name + " (" + point.x + "," + point.y + ") are the cause of poisoning: "
    }

    @Override
    public void draw() {
    //////////////////////////////////////////////////////////////////////////////////////////
    }
}