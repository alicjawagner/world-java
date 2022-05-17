package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;
import java.util.ArrayList;

public class Fox extends Animal {

    public Fox(World _world) {
        super(_world);
        name = "Fox";
        strength = 3;
        initiative = 7;
    }

    @Override
    public ArrayList<Point> findFieldsToMove() {
        ArrayList<Point> possibleMoves = super.findFieldsToMove();

        if (possibleMoves.size() <= 0)
            return possibleMoves;

        possibleMoves.removeIf(field -> (world.findOnField(field) != null && (world.findOnField(field).getStrength() > strength)));

        return possibleMoves;
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.FOX;
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(255,124,5), "F");
    }
}