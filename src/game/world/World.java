package game.world;

import game.organisms.Organism;
import game.organisms.animals.*;
import game.organisms.plants.*;

import java.awt.*;
import java.util.ArrayList;

public class World {

    public static final int BOARD_SIZE = 700;
    public static final int FIELD_SIZE = 35;
    public static final int FIELDS_NUMBER = (BOARD_SIZE / FIELD_SIZE); //20 - how many fields each dimension
    private static final int INITIAL_NUMBER_OF_ORGANISMS_OF_SPECIES = 3;

    public Organism[][] board = new Organism[FIELDS_NUMBER][FIELDS_NUMBER];
    private int numberOfBornOrganisms = 0;
    private Human human = null;
    public ArrayList<Organism> organisms = new ArrayList<>();
    public ArrayList<Organism> toAdd = new ArrayList<>();

    public World() {}

    public int getNumberOfBornOrganisms() {
        return numberOfBornOrganisms;
    }

    public boolean isFieldInBoard(final Point point) {
        return point.x >= 0 && point.x < FIELDS_NUMBER && point.y >= 0 && point.y < FIELDS_NUMBER;
    }

    public boolean isFieldUnoccupied(final Point point) {
        return board[point.x][point.y] == null;
    }

    public void clearTheField(final Point point) {
        board[point.x][point.y] = null;
    }

    public Organism searchArrayList(final Point point, final ArrayList<Organism> arr) {
        for (Organism o : arr) {
            if (o.getIsAlive() && o.getPoint() == point) {
                return o;
            }
        }
        return null;
    }

    public Organism findOnField(final Point point) {
        Organism org = searchArrayList(point, organisms);
        if (org != null)
            return org;

        org = searchArrayList(point, toAdd);
        return org;
    }

    private void removeDead() {
        organisms.removeIf(o -> (!o.getIsAlive()));
        toAdd.removeIf(o -> (!o.getIsAlive()));
    }

    /**
     * returns index, at which newOrg should be added; if at the end: -1
     */
    private int findPlaceInOrganisms(final Organism newOrg) {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getInitiative() < newOrg.getInitiative()) {
                return i;
            }
        }
        return -1;
    }

    public void insertIntoToAdd(Organism newOrg) {
        toAdd.add(newOrg);
    }

    public OrganismsNames whatIsOnBoard(final Point where) {
        Organism who = board[where.x][where.y];
        if (who == null)
            return null;

        return who.whoAmI();
    }

    public Organism createOrganism(final OrganismsNames which) {
        Organism o = null;
        switch (which) {
            case FOX:
                o = new Fox(this);
                break;
            case WOLF:
                o = new Wolf(this);
                break;
            case SHEEP:
                o = new Sheep(this);
                break;
            case ANTELOPE:
                o = new Antelope(this);
                break;
            case HUMAN:
                if (human == null) {
                    o = new Human(this);
                    human = (Human) o;
                }
                break;
            case TURTLE:
                o = new Turtle(this);
                break;
            case GRASS:
                o = new Grass(this);
                break;
            case DANDELION:
                o = new Dandelion(this);
                break;
            case GUARANA:
                o = new Guarana(this);
                break;
            case DEADLY_NIGHTSHADE:
                o = new DeadlyNightshade(this);
                break;
            case PINE_BORSCHT:
                o = new PineBorscht(this);
                break;
        }
        return o;
    }

    public void addOrganism(Organism newOrg) {
        if (newOrg == null) return;

        int beforeThat = findPlaceInOrganisms(newOrg);
        if (beforeThat == -1)
            organisms.add(newOrg);
        else
            organisms.add(beforeThat, newOrg);

        newOrg.putOnBoard();
        numberOfBornOrganisms++;
    }

    public void createAndAddOrganism(final OrganismsNames which) {
        addOrganism(createOrganism(which));
    }

    private void drawBoard() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void prepareGame() {
        for (OrganismsNames org : OrganismsNames.values()) {
            for (int i = 0; i < INITIAL_NUMBER_OF_ORGANISMS_OF_SPECIES; i++) {
                if (org == OrganismsNames.HUMAN) {
                    createAndAddOrganism(OrganismsNames.HUMAN);
                    break;
                }
                createAndAddOrganism(org);
            }
        }

        drawBoard();
    }

    private int makeRound() {
        /*
        int klawisz = human.wczytajStrzalki();

        if (klawisz == ESC)
            return ESC;
        if (klawisz == ELIKSIR)
            human.startElixir();
        */
        for (Organism o : organisms) {
            if (o.getIsAlive())
                o.action();
        }

        removeDead();

        // add waiting organisms to main ArrayList
        for (Organism o : toAdd) {
            addOrganism(o);
            o.setBirthTime(numberOfBornOrganisms);
        }
        toAdd.clear();

        drawBoard();

        return 1;
    }

    public void startGame() {
        prepareGame();
        int ch = 1;

        // "Let's start the game!\n"
        // jak uzywac: strzalkli, eliksir (+5 do Twojej sily)
        while (ch != -1) { //ch != ESC
            ch = makeRound();
            if (human == null) {
                // "Game over :C. Good luck next time!"
                break;
            }
        }
    }

}