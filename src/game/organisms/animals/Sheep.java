package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

public class Sheep extends Animal {

    public Sheep(World _world) {
        super(_world);
        name = "Sheep";
        strength = 4;
        initiative = 4;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.SHEEP;
    }

    @Override
    public void draw() {
    ///////////////////////////////////////////////////////////////////////////////////
    }
}