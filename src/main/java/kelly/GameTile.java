package kelly;

import java.awt.*;

public class GameTile {
    private static final Color[] colors = {
            Color.WHITE
            , new Color(255, 204, 204)
            , new Color(255, 229, 204)
            , new Color(255, 240, 204)
            , new Color(255, 255, 204)
            , new Color(229, 255, 204)
            , new Color(204, 255, 204)
            , new Color(204, 255, 214)
            , new Color(204, 255, 229)
            , new Color(204, 255, 255)
            , new Color(204, 229, 255)
            , new Color(204, 204, 255)
            , new Color(229, 204, 255)
            , new Color(255, 204, 255)
            , new Color(255, 204, 229)
            , new Color(255, 153, 204)
            , new Color(255, 153, 204)
            , new Color(255, 102, 255)
            , new Color(102, 102, 255)
    };
    private String displayText;
    private Color color;

    public GameTile(int num) {
        displayText = Integer.toString((int)Math.pow(2, num));
        color = colors[num];
    }

    public static Color[] getColors() {
        return colors;
    }

    public String getDisplayText() {
        return displayText;
    }

    public Color getColor() {
        return color;
    }

    public static GameTile[] gameTiles() {
        GameTile[] result = new GameTile[colors.length];
        for(int i = 0; i < colors.length; i++) {
            result[i] = new GameTile(i);
        }

        return result;
    }
}
