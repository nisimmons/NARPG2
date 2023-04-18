import java.awt.*;

public enum Faction {
    Spawn,
    Ally,
    Town,
    Forest,
    Ruins,
    Beach,
    Desert,
    Plains,
    Mountain,
    Wilderness,
    Dungeon,
    FinalDungeon;

    public Color getColor() {
        switch (this) {
            case Spawn:
                return Color.BLUE;
            case Town:
                return Color.CYAN;
            case Forest:
                return Color.GREEN;
            case Ruins:
                return Color.ORANGE;
            case Beach:
                return Color.YELLOW;
            case Desert:
                return Color.YELLOW;
            case Plains:
                return Color.LIGHT_GRAY;
            case Mountain:
                return Color.WHITE;
            case Wilderness:
                return Color.RED;
            case Dungeon:
                return Color.darkGray;
            case FinalDungeon:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }
}
