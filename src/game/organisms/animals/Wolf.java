package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Wolf extends Animal {

    public Wolf(World _world) {
        super(_world);
        name = "Wolf";
        strength = 9;
        initiative = 5;
        sign = "W";
    }

    public Wolf(World _world, String[] arr) {
        super(_world, arr);
        name = "Wolf";
        initiative = 5;
        sign = "W";
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.WOLF;
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(43,45,47));
    }
}