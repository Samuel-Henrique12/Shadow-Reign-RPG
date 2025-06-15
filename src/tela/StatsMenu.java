package tela;

import java.awt.*;
import javax.swing.ImageIcon;
import tela.Hud;
import java.awt.Font;
import java.awt.Color;
import java.io.InputStream;

public class StatsMenu {

    private Player player;
    private Image statsMenu;
    private Image chaveDojo;
    private Font fontePixel;
    private boolean mostrarStatsMenu = false;
    private Rectangle statsBotao;
    private Rectangle botaoFechar;

    public StatsMenu(Hud hud, Player player) {
        statsMenu = new ImageIcon(getClass().getResource("/res/statsMenu.png")).getImage();
        chaveDojo = new ImageIcon(getClass().getResource("/res/chaveDojo.png")).getImage();
        statsBotao = new Rectangle(
                hud.getStatsIconX(),
                hud.getStatsIconY(),
                hud.getStatsIconWidth(),
                hud.getStatsIconHeight()
        );
        this.player = player;

        botaoFechar = new Rectangle(447,125,49,47);

        carregarFonte();
    }

    private void carregarFonte() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/Press_Start_2P/PressStart2P-Regular.ttf");
            fontePixel = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(17f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fontePixel);
        } catch (Exception e) {
            e.printStackTrace();
            fontePixel = new Font("Arial", Font.PLAIN, 18);
        }
    }

    public void clicar(int mouseX, int mouseY) {
        if (statsBotao.contains(mouseX, mouseY)) {
            mostrarStatsMenu = !mostrarStatsMenu;
        }
        if (botaoFechar.contains(mouseX, mouseY)) {
            mostrarStatsMenu = false;
            return;
        }
    }

    public void render(Graphics g) {
        if (mostrarStatsMenu) {
            g.drawImage(statsMenu, 30, 80,500, 700, null);
            g.drawImage(chaveDojo, 95, 520,35, 35, null);

            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.WHITE);
            g.setFont(fontePixel);

            String nome = player.getNome();
            int nomeX = 400 - fm.stringWidth(nome);
            g.drawString(nome, nomeX, 215);
            String classe = player.getClasse();
            int classeX = 410 - fm.stringWidth(classe);

            g.drawString(nome, nomeX, 215);
            g.drawString(classe, classeX, 253);
            g.drawString(String.valueOf(player.getLevel()), 440, 290);
            g.drawString(String.valueOf(player.getAtaque()), 440, 330);
            g.drawString(String.valueOf(player.getDefesa()), 440, 370);
            g.drawString(String.valueOf(player.getVida()), 430, 409);
            g.drawString(String.valueOf(player.getPontosMelhoria()), 460, 448);
        }
    }

    public Player getPlayer() {
        return player;
    }
}
