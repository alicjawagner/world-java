package game.organisms.plants;

import game.organisms.Organism;
import game.organisms.Plant;
import game.world.OrganismsNames;
import game.world.World;

public class Guarana extends Plant {

    public Guarana(World _world) {
        super(_world);
        name = "Guarana";
        strength = 0;
    }

    @Override
    public boolean ifIWonTheFight(Organism attacker) {
        if (!super.ifIWonTheFight(attacker)) {
            attacker.setStrength(attacker.getStrength() + 3);
            return false;
        }
        return true;
    }

    @Override
    public void writeIDie() {
        //////////////////////////////////////////////////////////////////////////
        // not print line
        // name + " is dead :( (but the strength of the animal increased by 3)\n"
    }

    @Override
    public OrganismsNames whoAmI() {
        return OrganismsNames.GUARANA;
    }

    @Override
    public void draw() {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}