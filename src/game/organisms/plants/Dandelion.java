package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Dandelion extends Plant {

    public Dandelion(World _world) {
        super(_world);
        name = "Dandelion";
        strength = 0;
        sign = "d";
    }

    public Dandelion(World _world, String[] arr) {
        super(_world, arr);
        name = "Dandelion";
        sign = "d";
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
    public void draw(Graphics g) {
        drawOrg(g, new Color(245,187,0));
    }
}