package game.organisms;

import game.world.World;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Organism {
    protected String name;
    protected int strength;
    protected int initiative;
    protected Point point;
    protected int birthTime;
    protected boolean isAlive;
    protected int stepRange;
    protected World world;

    Organism(World _world) {
        world = _world;
        strength = -1;
        initiative = -1;
        isAlive = true;
        stepRange = 1;
        birthTime = world.getNumberOfBornOrganisms() + 1;

        Random rand = new Random();
        int x, y;
        int fields = World.FIELDS_NUMBER;
        do {
            x = rand.nextInt(fields);
            y = rand.nextInt(fields);
        } while (world.board[x][y] != null);

        point.x = x;
        point.y = y;

    }

    public abstract int whoAmI();
    public abstract void action();
    public abstract void draw();

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public Point getPoint() {
        return point;
    }

    public int getBirthTime() {
        return birthTime;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public World getWorld() {
        return world;
    }

    public void setStrength(final int newStrength) {
        strength = newStrength;
    }

    public void setBirthTime(final int _birthTime) {
        birthTime = _birthTime;
    }

    protected enum Strength {
        STRONGER,
        EQUAL,
        WEAKER
    }

    protected Strength amIStronger(final Organism other) {
        if (strength > other.strength) return Strength.STRONGER;
	else if (strength == other.strength) return Strength.EQUAL;
	else return Strength.WEAKER;
    }

    public boolean ifIRepelledTheAttack(final Organism attacker) {
        return false;
    }

    public boolean ifIEscaped(Animal attacker) {
        return false;
    }

    public boolean ifIWonTheFight(Organism attacker) {
        return amIStronger(attacker) == Strength.STRONGER;
    }

    public ArrayList<Point> findFieldsToMove() {
        Point current, possibleMove;
        current = point;

        ArrayList<Point> possibleMoves = new ArrayList<>();
        for (int i = -1 * stepRange; i <= stepRange; i += stepRange) {
            for (int j = -1 * stepRange; j <= stepRange; j += stepRange) {
                if (i == 0 && j == 0)
                    continue;

                possibleMove = new Point(current.x + i, current.y + j);
                if (world.isFieldInBoard(possibleMove))
                    possibleMoves.add(possibleMove);
            }
        }

        return possibleMoves;
    }

    public void removeOccupiedFields(ArrayList<Point> possibleMoves) {
        possibleMoves.removeIf(field -> (!world.isFieldUnoccupied(field)));
    }

    public void putOnBoard() {
        world.board[point.x][point.y] = this;
    }

    public void moveToField(Point newPoint) {
        world.clearTheField(point);
        point = newPoint;
        putOnBoard();
    }

    public void writeIWon() {
        /////////////////////////////////////////////////////////////////////////////////////////////
        // change to some string to be shown, not print line
        //System.out.println(name + " (" + point.x + "," + point.y + ") won the fight: ");
    }

    public void writeIDie() {
        //////////////////////////////////////////////////////////////////////////
        // not print line
        //System.out.println(name + " is dead :(\n");
    }
}

/*
* class Organizm {
public:

void Organizm::umrzyj() {
	czyZyje = false;
	swiat.zwolnijPoleNaPlanszy(this->polozenie);
	piszUmieram();
}

void Organizm::stworzDziecko(std::vector<Polozenie>& pola) const {
	Organizm* dziecko = swiat.stworzOrganizm(this->kimJestem());

	int ktore = rand() % pola.size();
	dziecko->przesunNaPole(pola[ktore]);
	swiat.dodajDoVectoraDoDodania(dziecko);
}
* */