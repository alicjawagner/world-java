package game.world;

import game.organisms.Organism;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

    private final World world;

    public MyMouseListener(World _world) {
        world = _world;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point field = getMouseField(new Point(e.getPoint()));
        if (field == null)
            return;

        if (world.findOnField(field) == null) {
            showMenuAndAddOrg(field);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private Point getMouseField(Point mouseLocation) {
        Point mouseField = new Point();
        mouseField.x = (mouseLocation.x / World.FIELD_SIZE);
        mouseField.y = (mouseLocation.y / World.FIELD_SIZE);
        if (mouseField.x >= World.FIELDS_NUMBER || mouseField.y >= World.FIELDS_NUMBER)
            return null;
        return mouseField;
    }

    private void showMenuAndAddOrg(Point field) {
        JFrame f = new JFrame();

        OrganismsNames[] orgArr = new OrganismsNames[10];
        int i = 0;
        for (OrganismsNames name : OrganismsNames.values()) {
            if (name == OrganismsNames.HUMAN)
                continue;
            orgArr[i] = name;
            i++;
        }

        JComboBox<OrganismsNames> comboBox = new JComboBox<>(orgArr);
        comboBox.addActionListener(e -> {
            if(e.getSource() == comboBox) {
                if (comboBox.getSelectedItem() != null) {
                    Organism o = world.createOrganism((OrganismsNames) comboBox.getSelectedItem());
                    world.addOrganism(o);
                    o.moveToField(field);
                    f.setVisible(false);
                }
            }
        });

        f.add(comboBox);
        f.setSize(200, 75);
        f.setLocationRelativeTo(null);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
    }

}
