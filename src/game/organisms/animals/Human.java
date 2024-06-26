package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

import static game.world.World.FIELD_SIZE;

public class Human extends Animal {

    public static final int START_POTION = 5;
    public static final int COUNTDOWN_POTION = 10;

    private NextMove nextMove = NextMove.STAY;
    private int potionWorking = 0;
    private int potionCountdown = 0;
    private String potionText = "";

    public enum NextMove {
        UP,
        DOWN,
        RIGHT,
        LEFT,
        STAY
    }

    public Human(World _world) {
        super(_world);
        name = "Human";
        strength = 5;
        initiative = 4;
        sign = "H";
    }

    public Human(World _world, String[] arr) {
        super(_world, arr);
        name = "Human";
        initiative = 4;
        sign = "H";
        potionWorking = Integer.parseInt(arr[5]);
        potionCountdown = Integer.parseInt(arr[6]);
    }

    public void setNextMove(final NextMove _nextMove) {
        nextMove = _nextMove;
    }

    public void startElixir() {
        if (potionCountdown == 0) {
            potionText = "Magic potion increased your strength by 5!";
            potionWorking = START_POTION;
            potionCountdown = COUNTDOWN_POTION;
            strength += 5;
        }
        else
            potionText = "You can't drink the potion yet.";
    }

    private void countDownPotion() {
        if (potionWorking > 0) {
            potionWorking--;
            potionCountdown--;
            strength--;
        }
        else if (potionCountdown > 0)
            potionCountdown--;
    }

    @Override
    public void action() {
        if (!isAlive)
            return;

        if (potionCountdown > 0)
            countDownPotion();

        Point newPoint = new Point(point);
        if (nextMove == NextMove.UP)
            newPoint.y = point.y - 1;
        else if (nextMove == NextMove.DOWN)
            newPoint.y = point.y + 1;
        else if (nextMove == NextMove.LEFT)
            newPoint.x = point.x - 1;
        else if (nextMove == NextMove.RIGHT)
            newPoint.x = point.x + 1;
        else if (nextMove == NextMove.STAY)
            return;

        if (world.isFieldInBoard(newPoint))
            makeMoveOrCollision(newPoint);
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.HUMAN;
    }

    public String getPotionText() {
        return potionText;
    }

    public void resetPotionText() {
        potionText = "";
    }

    @Override
    public void draw(Graphics g) {
        drawShapeOrg(g, Color.red);

        g.setColor(Color.black);
        g.setFont( new Font("Helvetica", Font.BOLD, 31));
        FontMetrics metrics = world.getFontMetrics(g.getFont());
        double x = ((double)(2 * point.x + 1) * FIELD_SIZE) / 2;
        double y = ((double)(2 * point.y + 2) * FIELD_SIZE) / 2;
        g.drawString(String.valueOf(sign), (int)(x - (metrics.stringWidth(String.valueOf(sign)) / 2)) + 1, (int)(y - (g.getFont().getSize() / 2)) + 9);
    }

    @Override
    protected void myOwnFieldsToFile(BufferedWriter writer) throws IOException {
        writer.write(DELIMITER + potionWorking + DELIMITER + potionCountdown);
    }
}