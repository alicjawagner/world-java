package game.organisms;

import game.world.World;

import java.awt.*;
import java.util.ArrayList;

import static game.world.World.FIELD_SIZE;

public abstract class Plant extends Organism {

    private static final int PROBABILITY_OF_SPREADING = 15;

    public Plant(World _world) {
        super(_world);
        initiative = 0;
    }

    @Override
    public void action() {
        if (rand.nextInt(100) < PROBABILITY_OF_SPREADING) {
            ArrayList<Point> possibleMoves = findFieldsToMove();
            removeOccupiedFields(possibleMoves);

            if (possibleMoves.size() <= 0)
                return;

            makeChild(possibleMoves);
        }
    }

    @Override
    public void writeIDie() {
        world.text += name + " was eaten :(\n";
    }

    @Override
    protected void drawShapeOrg(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(point.x * FIELD_SIZE, point.y *FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
    }
}