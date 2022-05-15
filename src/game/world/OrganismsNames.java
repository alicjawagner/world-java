package game.world;

//from the greatest initiative
public enum OrganismsNames {
    FOX,
    WOLF,
    SHEEP,
    ANTELOPE,
    HUMAN,
    TURTLE,
    GRASS,
    DANDELION,
    GUARANA,
    DEADLY_NIGHTSHADE,
    PINE_BORSCHT;

    public boolean isAnimal() {
        switch(this) {
            case FOX:
            case WOLF:
            case SHEEP:
            case ANTELOPE:
            case HUMAN:
            case TURTLE:
                return true;
            default:
                return false;
        }
    }
}
