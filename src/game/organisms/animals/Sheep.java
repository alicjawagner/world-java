package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Sheep extends Animal {

    public Sheep(World _world) {
        super(_world);
        name = "Sheep";
        strength = 4;
        initiative = 4;
        sign = "S";
    }

    public Sheep(World _world, String[] arr) {
        super(_world, arr);
        name = "Sheep";
        initiative = 4;
        sign = "S";
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.SHEEP;
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(211,211,211));
    }
}