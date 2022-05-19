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
    public void action() {
        //kills all the nearest animals
        Point toKill;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                toKill = new Point(point.x + i, point.y + j);
                if (!world.isFieldInBoard(toKill) || world.isFieldUnoccupied(toKill))
                    continue;

                if (world.whatIsOnBoard(toKill).isAnimal()) {
                    world.text += this + " has no mercy: ";
                    world.findOnField(toKill).die();
                }
            }
        }
        super.action();
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(204,235,68), "B");
    }
}