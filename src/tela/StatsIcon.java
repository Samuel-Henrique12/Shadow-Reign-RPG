package tela;

public class StatsIcon {
    private Hud hud;

    public StatsIcon(Hud hud) {
        this.hud = hud;
    }

    public boolean foiClicado(int mouseX, int mouseY) {
        return mouseX >= hud.getStatsIconX() &&
                mouseX <= hud.getStatsIconX() + hud.getStatsIconWidth() &&
                mouseY >= hud.getStatsIconY() &&
                mouseY <= hud.getStatsIconY() + hud.getStatsIconHeight();
    }
}
