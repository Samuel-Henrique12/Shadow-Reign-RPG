package tela;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import telaMenu.Menu;
import fundo.Mapa;
import fundo.Casa;
import fundo.Dojo;
import fundo.MapaInimigoArqueiro;
import fundo.MapaInimigoBarbaro;
import fundo.MapaInimigoMago;
import telaMenu.Creditos;
import telaMenu.CriacaoNome;
import telaMenu.CriacaoClasse;
import fundo.Escola;

public class Container extends JFrame {

    private Player player;
    public static final String MENU = "MENU";
    public static final String CRIACAONOME = "CRIACAONOME";
    public static final String CRIACAOCLASSE = "CRIACAOCLASSE";
    public static final String CREDITOS = "CREDITOS";

    // Card Único Onde Vive a Tela de Gameplay Ativa (Uma de Cada Vez)
    private static final String DINAMICA = "DINAMICA";

    private Hud hud;
    private StatsMenu statsMenuController;


    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private PainelEscalavel telaDinamica;


    public Container() {
        player = new Player();
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        hud = new Hud();
        statsMenuController = new StatsMenu(hud, player);

        // Adiciona as Telas Fixas do Menu e Criação de Personagem
        painelPrincipal.add(new Menu(this), MENU);
        painelPrincipal.add(new CriacaoNome(this, player), CRIACAONOME);
        painelPrincipal.add(new CriacaoClasse(this, player), CRIACAOCLASSE);
        painelPrincipal.add(new Creditos(this), CREDITOS);


        // Configuracoes da Janela
        add(painelPrincipal);
        setTitle("Shadow Reign RPG");

        if (Ambiente.web()) {
            // Sem Decoracao: a Janela Ocupa Todo o Canvas do CheerpJ
            setUndecorated(true);
            // DISPOSE: EXIT_ON_CLOSE Mataria a JVM da Aba
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);
            pack();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(true);
            setMinimumSize(new Dimension(640, 360));
            pack();
            ajustarAoMonitor();
            setLocationRelativeTo(null);
        }

        instalarAtalhoDepuracao();

        setVisible(true);

        mostrarTela(MENU);
    }

    public Player getPlayer() {
        return player;
    }

    // Entrada do Gameplay, Vinda da Criacao de Classe
    public void iniciarJogo() {
        trocarTela(new Casa(hud, statsMenuController));
    }

    // Troca a Tela de Gameplay Ativa
    public void trocarTela(PainelEscalavel nova) {
        PainelEscalavel anterior = telaDinamica;

        if (anterior != null) {
            anterior.aoSair();
        }

        // Prepara o Primeiro Frame Antes de Aparecer, Senao a Tela Salta
        nova.setSize(painelPrincipal.getSize());
        nova.aoEntrar();

        telaDinamica = nova;
        painelPrincipal.add(nova, DINAMICA);
        cardLayout.show(painelPrincipal, DINAMICA);

        // Remove a Antiga So Depois: Evita um Quadro Sem Nenhuma Tela Visivel
        if (anterior != null) {
            painelPrincipal.remove(anterior);
        }

        painelPrincipal.revalidate();
        painelPrincipal.repaint();

        SwingUtilities.invokeLater(nova::requestFocusInWindow);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(painelPrincipal, nomeTela);

        // Foco praa TELA Visivel, Nunca pro Container do CardLayout
        SwingUtilities.invokeLater(() -> {
            for (java.awt.Component tela : painelPrincipal.getComponents()) {
                if (tela.isVisible()) {
                    tela.requestFocusInWindow();
                    return;
                }
            }
        });
    }

    // Ajusta a Janela ao Monitor Preservando a Proporcao do Conteudo
    private void ajustarAoMonitor() {
        Rectangle util = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Insets bordas = getInsets();

        int conteudoDisponivelW = util.width - bordas.left - bordas.right;
        int conteudoDisponivelH = util.height - bordas.top - bordas.bottom;

        // Dimensiona o Conteudo na Proporcao da Base e Soma os Insets
        double escala = Math.min(1.0, Math.min(
                conteudoDisponivelW / (double) PainelEscalavel.LARGURA_PADRAO,
                conteudoDisponivelH / (double) PainelEscalavel.ALTURA_PADRAO));

        int conteudoW = (int) Math.round(PainelEscalavel.LARGURA_PADRAO * escala);
        int conteudoH = (int) Math.round(PainelEscalavel.ALTURA_PADRAO * escala);

        setSize(conteudoW + bordas.left + bordas.right,
                conteudoH + bordas.top + bordas.bottom);
    }

    // Alterna o Overlay de Hitboxes com F3, Independente de Quem Tem Foco
    private void instalarAtalhoDepuracao() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(evento -> {
                    if (evento.getID() == java.awt.event.KeyEvent.KEY_PRESSED
                            && evento.getKeyCode() == java.awt.event.KeyEvent.VK_F3) {
                        Depuracao.hitboxes = !Depuracao.hitboxes;
                        repaint();
                    }
                    return false;
                });
    }

    public static void main(String[] args) {
        Ambiente.configurar(args);
        SwingUtilities.invokeLater(Container::new);
    }
}