package game.organisms.plants;

import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class PineBorscht extends Plant {

    public PineBorscht(World _world) {
        super(_world);
        name = "Pine Borscht";
        strength = 10;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.PINE_BORSCHT;
    }

    @Override
    public void draw() {
        ///////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void action() {
        //kills all the nearest animals
        Point toKill;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                toKill = new Point(point.x + i, point.y + j);
                if (!world.isFieldInBoard(toKill))
                    continue;

                if (world.whatIsOnBoard(toKill).isAnimal()) {
                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    //cout << "Pine Borscht (" + point.x << ", " << point.y << ") has no mercy: ";
                    world.findOnField(toKill).die();
                }
            }
        }
    }
}