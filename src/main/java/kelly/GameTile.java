package kelly;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameTile {
    private static final Color[] colors = {
            new Color(204, 204, 204)
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
            , new Color(255, 153, 255)
            , new Color(102, 102, 255)
    };
    private int num;
    private Color color;

    public GameTile(int num) {
        this.num = (int)Math.pow(2, num);
        color = colors[num];
    }

    public static Color[] getColors() {
        return colors;
    }

    public Color getColor() {
        return color;
    }

    private BufferedImage tileImage = null;
    public BufferedImage drawTile(int scale, int offset, Color background, String fontName) {
        if(tileImage == null) {
            int length = scale - offset * 2;

            tileImage = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);

            Graphics g = tileImage.getGraphics();
            g.setColor(background);

            g.fillRect(0,0, length, length);

            g.setColor(getColor());

            int round = scale/5;
            g.fillRoundRect(0, 0, length, length, round, round);

            if(num > 1) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints. VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

                int fontSize = num < 1000 ? 38
                        : num < 10000 ? 36
                        : 32;
                Font font = new Font(fontName, Font.BOLD, fontSize);
                g.setFont(font);
                String text = Integer.toString(num);
                g.setColor(Color.DARK_GRAY);
                FontMetrics fm = g.getFontMetrics(font);
                int fw = fm.stringWidth(text);
                int fh = fm.getHeight();

                g.drawString(text, (length-fw)/2, length/2 + fh/3);
            }
        }
        return tileImage;
    }

    public static GameTile[] gameTiles() {
        GameTile[] result = new GameTile[colors.length];
        for(int i = 0; i < colors.length; i++) {
            result[i] = new GameTile(i);
        }

        return result;
    }
}
