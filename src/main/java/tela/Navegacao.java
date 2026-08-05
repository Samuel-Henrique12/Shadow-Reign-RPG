package tela;

import javax.swing.SwingUtilities;
import java.awt.Component;

// Ponte Entre Tela e Container
public final class Navegacao {

    private Navegacao() {
    }

    // Troca a Tela de Gameplay a Partir de Qualquer Painel
    public static void trocarTela(Component origem, PainelEscalavel nova) {
        Container janela = (Container) SwingUtilities.getWindowAncestor(origem);
        janela.trocarTela(nova);
    }
}
