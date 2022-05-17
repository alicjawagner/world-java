package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Human extends Animal {

    public static final int START_POTION = 5;
    public static final int COUNTDOWN_POTION = 10;

    private NextMove nextMove = NextMove.STAY;
    private int potionWorking = 0;
    private int potionCountdown = 0;

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
    }

    public int getPotionCountdown() {
        return potionCountdown;
    }

    public void setNextMove(final NextMove _nextMove) {
        nextMove = _nextMove;
    }

    public void startElixir() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        // "Magic potion increased your strength by 5!\n"
        potionWorking = START_POTION;
        potionCountdown = COUNTDOWN_POTION;
        strength += 5;
    }

    private void countDownPotion() {
        if (potionWorking > 0) {
            potionWorking--;
            potionCountdown--;
            strength--;
        }
        else if (potionCountdown > 0)
            potionCountdown--;

        //////////////////////////////////////////////////////////////////////////////////////////////
        // "You can't drink the potion yet.\n"
    }

    @Override
    public void action() {
        if (!isAlive)
            return;

        if (potionCountdown > 0)
            countDownPotion();

        Point newPoint = new Point(point);
        if (nextMove == NextMove.UP)
            newPoint.x = point.x - 1;
        else if (nextMove == NextMove.DOWN)
            newPoint.x = point.x + 1;
        else if (nextMove == NextMove.LEFT)
            newPoint.y = point.y - 1;
        else if (nextMove == NextMove.RIGHT)
            newPoint.y = point.y + 1;
        else if (nextMove == NextMove.STAY)
            return;

        if (world.isFieldInBoard(newPoint))
            makeMoveOrCollision(newPoint);
    }

//    public void getKeyPressed(KeyEvent e) {
//        switch(e.getKeyCode()) {
//            case KeyEvent.VK_E:
//                if (potionCountdown == 0)
//                    startElixir();
//                nextMove = NextMove.STAY;
//                break;
//            case KeyEvent.VK_LEFT:
//                nextMove = NextMove.LEFT;
//                break;
//            case KeyEvent.VK_RIGHT:
//                nextMove = NextMove.RIGHT;
//                break;
//            case KeyEvent.VK_UP:
//                nextMove = NextMove.UP;
//                break;
//            case KeyEvent.VK_DOWN:
//                nextMove = NextMove.DOWN;
//                break;
//            default:
//                nextMove = NextMove.STAY;
//        }
//    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.HUMAN;
    }

    @Override
    public void draw() {
    /////////////////////////////////////////////////////////////////////////////////////////////
    }
}