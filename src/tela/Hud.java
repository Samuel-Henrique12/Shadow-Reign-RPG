package tela;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Hud {

    private Image statsIcon;
    private final int statsIconX = -17;
    private final int statsIconY = 665;
    private final int statsIconWidth = 290;
    private final int statsIconHeight = 160;

    public Hud() {
        statsIcon = new ImageIcon(getClass().getResource("/res/statsIcon.png")).getImage();
    }

    public void render(Graphics g) {
        g.drawImage(statsIcon, statsIconX, statsIconY, statsIconWidth, statsIconHeight, null);
    }

    public int getStatsIconX() {
        return statsIconX;
    }

    public int getStatsIconY() {
        return statsIconY;
    }

    public int getStatsIconWidth() {
        return statsIconWidth;
    }

    public int getStatsIconHeight() {
        return statsIconHeight;
    }
}