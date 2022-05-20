package game.world;

import game.organisms.Organism;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Saving {

    private final World world;

    public Saving(World _world) {
        world = _world;
    }

    public void saveGameState() {
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
        });
    }

    private void saveToFile(final String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(World.PATH_TO_SAVES + fileName));

        writer.write(world.getNumberOfBornOrganisms() + Organism.DELIMITER + world.organisms.size() + "\n");
        for (Organism o : world.organisms)
            o.writeMeToFile(writer);

        writer.close();
        world.text = "Game state saved";
    }

}
