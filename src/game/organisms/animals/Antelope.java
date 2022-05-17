package game.organisms.animals;

import game.organisms.Animal;
import game.organisms.Organism;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;
import java.util.ArrayList;

public class Antelope extends Animal {

    static final int PROBABILITY_ESCAPING = 50;

    public Antelope(World _world) {
        super(_world);
        name = "Antelope";
        strength = 4;
        initiative = 4;
        stepRange = 2;
    }

    @Override
    public boolean ifIEscaped(Animal attacker) {
        if (rand.nextInt(100) < PROBABILITY_ESCAPING) {
            Point myOld = new Point(point);
            attacker.moveToField(myOld);

            stepRange = 1;
            ArrayList<Point> possibleMoves = findFieldsToMove();
            stepRange = 2;
            removeOccupiedFields(possibleMoves);

            int which = rand.nextInt(possibleMoves.size());
            Point newPoint = new Point(possibleMoves.get(which));
            putOnBoard();
            point = newPoint;

            world.text += this + " escaped the fight ;) on field (" + attacker.getPoint().x + "," + attacker.getPoint().y + ")\n";
            return true;
        }
        return false;
    }

    @Override
    public void collision(Organism attacked) {
        if (isTheSameSpecies(attacked)) {
            reproduce(attacked);
        }
        else if (rand.nextInt(100) < PROBABILITY_ESCAPING) {
            Point myOld = new Point(point);
            point = attacked.getPoint();
            stepRange = 1;
            ArrayList<Point> possibleMoves = findFieldsToMove();
            stepRange = 2;
            point = myOld;
            removeOccupiedFields(possibleMoves);
            moveToRandField(possibleMoves);
            world.text += this + " escaped the fight ;) on field (" + attacked.getPoint().x + "," + attacked.getPoint().y + ")\n";
        } else
            super.collision(attacked);
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.ANTELOPE;
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(158,121,104), "A");
    }
}