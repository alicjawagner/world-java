package game.organisms.animals;

import game.organisms.Animal;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Human extends Animal {

    static final int START_POTION = 5;
    static final int COUNTDOWN_POTION = 10;

    private NextMove nextMove = NextMove.STAY;
    private int potionWorking = 0;
    private int potionCountdown = 0;

    private enum NextMove {
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

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.HUMAN;
    }

    @Override
    public void draw() {
    /////////////////////////////////////////////////////////////////////////////////////////////
    }
}

/*
int Czlowiek::wczytajStrzalki() {
    int ch = _getch();
    if (ch == ESC)
        return ESC;
    else if (ch == 0 || ch == 224) {
        switch (_getch()) {
        case GORA:
            nastepnyRuch = GORA;
            break;
        case DOL:
            nastepnyRuch = DOL;
            break;
        case LEWO:
            nastepnyRuch = LEWO;
            break;
        case PRAWO:
            nastepnyRuch = PRAWO;
            break;
        }
    }
    else if (ch == ELIKSIR) {
        if (odliczanieDoWlaczeniaEliksiru == 0) {
            nastepnyRuch = ZOSTAN;
            return ELIKSIR;
        }
        else
            nastepnyRuch = ZOSTAN;
    }
    else
        nastepnyRuch = ZOSTAN;

    return 1;
}
 */