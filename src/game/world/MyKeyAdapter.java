package game.world;

import game.organisms.animals.Human;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {

    private final World world;
    private final Saving saving;
    private final Loading loading;

    public MyKeyAdapter(World _world) {
        world = _world;
        saving = new Saving(_world);
        loading = new Loading(_world);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (world.getHuman() != null) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    world.getHuman().startElixir();
                    world.getHuman().setNextMove(Human.NextMove.STAY);
                    break;
                case KeyEvent.VK_LEFT:
                    world.getHuman().setNextMove(Human.NextMove.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    world.getHuman().setNextMove(Human.NextMove.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    world.getHuman().setNextMove(Human.NextMove.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    world.getHuman().setNextMove(Human.NextMove.DOWN);
                    break;
                case KeyEvent.VK_N:
                    world.nextRound();
                    break;
                case KeyEvent.VK_S:
                    saving.saveGameState();
                    break;
                case KeyEvent.VK_L:
                    loading.loadGameState();
            }
        }
    }
}
