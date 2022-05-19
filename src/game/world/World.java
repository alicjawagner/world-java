package game.world;

import game.organisms.Organism;
import game.organisms.animals.*;
import game.organisms.plants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class World extends JPanel implements ActionListener {

    public static final int TEXT_FIELD_WIDTH = 303;
    public static final int BOARD_SIZE = 700;
    public static final int FIELD_SIZE = 35;
    public static final int FIELDS_NUMBER = (BOARD_SIZE / FIELD_SIZE); //20 - how many fields each dimension
    public static final int SCREEN_WIDTH = BOARD_SIZE + TEXT_FIELD_WIDTH;
    public static final int SCREEN_HEIGHT = BOARD_SIZE;
    private static final int INITIAL_NUMBER_OF_ORGANISMS_OF_SPECIES = 3;
    private static final String PATH_TO_SAVES = ".\\src\\game\\saves\\";
    private static final String INSTRUCTIONS = "MOVEMENT:                                    arrows\n" +
                                                "MAGIC POTION (strength +5):      P\n" +
                                                "NEW ROUND:                                 N\n" +
                                                "SAVE:                                                S\n" +
                                                "LOAD:                                                L\n\n";

    public String text = INSTRUCTIONS;
    public Organism[][] board = new Organism[FIELDS_NUMBER][FIELDS_NUMBER];
    private int numberOfBornOrganisms = 0;
    private Human human = null;
    public ArrayList<Organism> organisms = new ArrayList<>();
    public ArrayList<Organism> toAdd = new ArrayList<>();

    public World() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new World.MyKeyAdapter());
        startGame();
    }

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

    public Organism findOnField(final Point point) {
        return board[point.x][point.y];
    }

    private void removeDead() {
        if (organisms.size() != 0 )
            organisms.removeIf(o -> (!o.getIsAlive()));
        if (toAdd.size() != 0)
            toAdd.removeIf(o -> (!o.getIsAlive()));
        if (human != null && !human.getIsAlive())
            human = null;
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

    private void startGame() {
        for (OrganismsNames org : OrganismsNames.values()) {
            for (int i = 0; i < INITIAL_NUMBER_OF_ORGANISMS_OF_SPECIES; i++) {
                if (org == OrganismsNames.HUMAN) {
                    createAndAddOrganism(OrganismsNames.HUMAN);
                    break;
                }
                createAndAddOrganism(org);
            }
        }
        repaint();
    }

    private void nextRound() {
        text = INSTRUCTIONS;
        if (human != null)
            human.resetPotionText();

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
    }

    private void drawWorld(Graphics g) {

        for(int i = 0; i < SCREEN_HEIGHT/FIELD_SIZE ; i++) {
            g.drawLine(i * FIELD_SIZE, 0, i * FIELD_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * FIELD_SIZE, SCREEN_WIDTH, i * FIELD_SIZE);
        }

        for (Organism o : organisms) {
            o.draw(g);
        }

        drawComments(g);

        if(human == null) {
            gameOver(g);
        }
        repaint();
    }

    private void drawTextWithNewLines(Graphics g, String text, int x, int y) {
        g.setColor(Color.white);
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    private void drawComments(Graphics g) {
        g.setColor(new Color(13,15,17));
        int x = SCREEN_WIDTH - TEXT_FIELD_WIDTH;
        g.fillRect(x, 0, TEXT_FIELD_WIDTH, SCREEN_HEIGHT);

        g.setFont(new Font("Helvetica", Font.PLAIN, 12));
        drawTextWithNewLines(g, text, x + 5, 3);

        if (human != null) {
            String humanText = human.getPotionText();
            if (!Objects.equals(humanText, ""))
                g.drawString(humanText, x + 5, SCREEN_HEIGHT - 20);
        }
    }

    public void gameOver(Graphics g) {
        String gameOver = "Game Over";
        g.setFont( new Font("Times New Roman",Font.BOLD, 55));
        FontMetrics metrics2 = getFontMetrics(g.getFont());

        g.setColor(new Color(0,0,0, 170));
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g.setColor(Color.red);
        g.drawString(gameOver, (SCREEN_WIDTH - metrics2.stringWidth(gameOver))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (human != null) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_P:
                        human.startElixir();
                        human.setNextMove(Human.NextMove.STAY);
                        break;
                    case KeyEvent.VK_LEFT:
                        human.setNextMove(Human.NextMove.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        human.setNextMove(Human.NextMove.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        human.setNextMove(Human.NextMove.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        human.setNextMove(Human.NextMove.DOWN);
                        break;
                    case KeyEvent.VK_N:
                        nextRound();
                        break;
                    case KeyEvent.VK_S:
                        saveGameState();
                        break;
                    case KeyEvent.VK_L:
                        loadGameState();
                }
            }
        }
    }

    private void saveGameState() {
        JFrame input = new JFrame();
        JTextField textField  = new JTextField("Saving: Enter file name and hit \"enter\"", 30);
        input.add(textField);
        input.setSize(300,80);
        input.setLocationRelativeTo(null);
        input.setVisible(true);

        textField.addActionListener(e -> {
            String fileName = textField.getText();

            try {
                saveToFile(fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            input.setVisible(false);
            text = "Game state saved";
        });
    }

    private void saveToFile(final String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_SAVES + fileName));

        writer.write(numberOfBornOrganisms + Organism.DELIMITER + organisms.size() + "\n");
        for (Organism o : organisms)
            o.writeMeToFile(writer);

        writer.close();
    }

    private void loadGameState() {
        JFrame input = new JFrame();
        JTextField textField  = new JTextField("Loading: Enter file name and hit \"enter\"", 30);
        input.add(textField);
        input.setSize(300,80);
        input.setLocationRelativeTo(null);
        input.setVisible(true);

        textField.addActionListener(e -> {
            String fileName = textField.getText();

            try {
                loadFromFile(fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            input.setVisible(false);
            text = "Game state loaded";
        });
    }

    private void loadFromFile(String fileName) throws IOException {
        File file = new File(PATH_TO_SAVES + fileName);
        if(file.exists()) {
            board = new Organism[FIELDS_NUMBER][FIELDS_NUMBER];
            human = null;
            organisms.clear();
            toAdd.clear();
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();
            String[] elements = line.split(Organism.DELIMITER);
            numberOfBornOrganisms = Integer.parseInt(elements[0]);
            int orgSize = Integer.parseInt(elements[1]);

            for (int i = 0; i < orgSize; i++) {
                line = reader.readLine();
                elements = line.split(Organism.DELIMITER);
                Organism o = null;
                switch(elements[0]) {
                    case "F":
                        o = new Fox(this, elements);
                        break;
                    case "W":
                        o = new Wolf(this, elements);
                        break;
                    case "S":
                        o = new Sheep(this, elements);
                        break;
                    case "A":
                        o = new Antelope(this, elements);
                        break;
                    case "H":
                        o = new Human(this);
                        human = (Human) o;
                        break;
                    case "T":
                        o = new Turtle(this, elements);
                        break;
                    case "g":
                        o = new Grass(this, elements);
                        break;
                    case "d":
                        o = new Dandelion(this, elements);
                        break;
                    case "u":
                        o = new Guarana(this, elements);
                        break;
                    case "n":
                        o = new DeadlyNightshade(this, elements);
                        break;
                    case "b":
                        o = new PineBorscht(this, elements);
                        break;
                }

                organisms.add(o);
                if (o != null) {
                    o.putOnBoard();
                }

            }
        }
    }

}