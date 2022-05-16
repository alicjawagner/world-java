package game.organisms;

import game.world.World;

import java.awt.*;
import java.util.ArrayList;

public abstract class Animal extends Organism {

    public Animal(World _world) {
        super(_world);
    }

    public boolean isTheSameSpecies(Organism other) {
        return this.name.equals(other.name);
    }

    @Override
    public void action() {
        moveToRandField(findFieldsToMove());
    }

    public void moveToRandField(ArrayList<Point> possibleMoves) {
        if (possibleMoves.size() <= 0)
            return;

        makeMoveOrCollision(possibleMoves.get(rand.nextInt(possibleMoves.size())));
    }

    public void makeMoveOrCollision(Point field) {
        if (world.isFieldUnoccupied(field))
            moveToField(field);
        else
            collision(world.findOnField(field));
    }

    public void collision(Organism attacked) {
        if (isTheSameSpecies(attacked)) {
            reproduce(attacked);
        }
        else if (attacked.ifIRepelledTheAttack(this)) {
            ///////////////////////////////////////////////////////////////////////////////
            // name + " returned to the previous place :/\n"
        }
        else if (attacked.ifIEscaped(this)) {
            return;
        }
        else if (!attacked.ifIWonTheFight(this)) {
            world.clearTheField(point);
            point = attacked.getPoint();
            writeIWon();
            attacked.die();
            putOnBoard();
        }
        else {
            attacked.writeIWon();
            die();
        }
    }

    public void reproduce(Organism attacked) {
        ArrayList<Point> forChild = findFieldsToMove();
        forChild.addAll(attacked.findFieldsToMove());

        removeOccupiedFields(forChild);
        if (forChild.size() <= 0)
            return;

        makeChild(forChild);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // "child - parents: " + name + " x2 on fields (" + point.x + "," + point.y + ") & (" + attacked.getPoint().x + "," + attacked.getPoint().y + ")\n"
    }

}