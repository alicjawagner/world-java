package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

public class Wolf extends Animal {

    public Wolf(World _world) {
        super(_world);
        name = "Wolf";
        strength = 9;
        initiative = 5;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.WOLF;
    }

    @Override
    public void draw() {
    ////////////////////////////////////////////////////////////////////////////
    }
}