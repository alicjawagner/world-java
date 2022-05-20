package game.world;

import game.organisms.Organism;
import game.organisms.animals.*;
import game.organisms.plants.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loading {

    private final World world;

    public Loading(World _world) {
        world = _world;
    }

    public void loadGameState() {
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
        });
    }

    private void loadFromFile(String fileName) throws IOException {
        File file = new File(World.PATH_TO_SAVES + fileName);
        if(file.exists()) {
            world.board = new Organism[World.FIELDS_NUMBER][World.FIELDS_NUMBER];
            world.setHuman(null);
            world.organisms.clear();
            world.toAdd.clear();
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();
            String[] elements = line.split(Organism.DELIMITER);
            world.setNumberOfBornOrganisms(Integer.parseInt(elements[0]));
            int orgSize = Integer.parseInt(elements[1]);

            for (int i = 0; i < orgSize; i++) {
                line = reader.readLine();
                elements = line.split(Organism.DELIMITER);
                Organism o = null;
                switch(elements[0]) {
                    case "F":
                        o = new Fox(world, elements);
                        break;
                    case "W":
                        o = new Wolf(world, elements);
                        break;
                    case "S":
                        o = new Sheep(world, elements);
                        break;
                    case "A":
                        o = new Antelope(world, elements);
                        break;
                    case "H":
                        o = new Human(world, elements);
                        world.setHuman((Human) o);
                        break;
                    case "T":
                        o = new Turtle(world, elements);
                        break;
                    case "g":
                        o = new Grass(world, elements);
                        break;
                    case "d":
                        o = new Dandelion(world, elements);
                        break;
                    case "u":
                        o = new Guarana(world, elements);
                        break;
                    case "n":
                        o = new DeadlyNightshade(world, elements);
                        break;
                    case "b":
                        o = new PineBorscht(world, elements);
                        break;
                }

                world.organisms.add(o);
                if (o != null) {
                    o.putOnBoard();
                }

            }
            world.text = "Game state loaded";
        }
        else
            world.text = "File not found";
    }

}
