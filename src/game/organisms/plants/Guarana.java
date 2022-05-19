package game.organisms.plants;

import game.organisms.Organism;
import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

import java.awt.*;

public class Guarana extends Plant {

    public Guarana(World _world) {
        super(_world);
        name = "Guarana";
        strength = 0;
        sign = "u";
    }

    @Override
    public boolean ifILostTheFight(Organism attacker) {
        if (super.ifILostTheFight(attacker)) {
            attacker.setStrength(attacker.getStrength() + 3);
            return true;
        }
        return false;
    }

    @Override
    public void writeIDie() {
        world.text += name + " is dead :( (+3)\n";
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.GUARANA;
    }

    @Override
    public void draw(Graphics g) {
        drawOrg(g, new Color(126,200,80));
    }
}