package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

public class Dandelion extends Plant {

    public Dandelion(World _world) {
        super(_world);
        name = "Dandelion";
        strength = 0;
    }

    @Override
    public void action() {
        for (int i = 0; i < 3; i++)
            super.action();
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.DANDELION;
    }

    @Override
    public void draw() {
        ///////////////////////////////////////////////////////////////////////////////////////////////
    }
}