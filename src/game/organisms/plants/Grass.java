package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Grass extends Plant {

    public Grass(World _world) {
        super(_world);
        name = "Grass";
        strength = 0;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.GRASS;
    }

    @Override
    public void draw(Graphics g) {
        ///////////////////////////////////////////////////////////////////////////////////////////////
    }
}