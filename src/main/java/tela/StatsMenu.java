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
        statsMenu = Recursos.imagem("statsMenu.png");
        chaveDojo = Recursos.imagem("chaveDojo.png");
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
        fontePixel = Recursos.fonte(17f);
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
