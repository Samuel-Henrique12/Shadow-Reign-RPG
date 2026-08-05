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
    public static final String CASA = "CASA";
    public static final String CRIACAONOME = "CRIACAONOME";
    public static final String CRIACAOCLASSE = "CRIACAOCLASSE";
    public static final String MAPAINIMIGOARQUEIRO = "MAPAINIMIGOARQUEIRO";
    public static final String MAPAINIMIGOBARBARO = "MAPAINIMIGOBARBARO";
    public static final String MAPAINIMIGOMAGO = "MAPAINIMIGOMAGO";
    public static final String ESCOLA = "ESCOLA";
    public static final String MAPA = "MAPA";
    public static final String CREDITOS = "CREDITOS";
    public static final String DOJO = "DOJO";
    private Hud hud;
    private StatsMenu statsMenuController;


    private CardLayout cardLayout;
    private JPanel painelPrincipal;


    public Container() {
        player = new Player();
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        Menu painelMenu = new Menu(this);
        CriacaoNome painelCriacaoNome = new CriacaoNome(this, player);
        CriacaoClasse painelCriacaoClasse = new CriacaoClasse(this, player);
        hud = new Hud();
        statsMenuController = new StatsMenu(hud, player);
        Casa painelCasa = new Casa(hud, statsMenuController);
        Mapa painelMapa = new Mapa(hud,statsMenuController);
        Dojo painelDojo = new Dojo(hud,statsMenuController);
        Escola painelEscola = new Escola(hud, statsMenuController);
        MapaInimigoMago painelMapaInimigoMago = new MapaInimigoMago(hud, statsMenuController);
        MapaInimigoBarbaro painelMapaInimigoBarbaro = new MapaInimigoBarbaro(hud, statsMenuController);
        MapaInimigoArqueiro painelMapaInimigoArqueiro = new MapaInimigoArqueiro( hud, statsMenuController);
        Creditos painelCreditos = new Creditos(this);

        painelPrincipal.add(painelMenu, MENU);
        painelPrincipal.add(painelCriacaoNome, CRIACAONOME);
        painelPrincipal.add(painelCriacaoClasse, CRIACAOCLASSE);
        painelPrincipal.add(painelCreditos, CREDITOS);
        painelPrincipal.add(painelMapa, MAPA);
        painelPrincipal.add(painelDojo, DOJO);
        painelPrincipal.add(painelCasa, CASA);
        painelPrincipal.add(painelEscola, ESCOLA);
        painelPrincipal.add(painelMapaInimigoArqueiro, MAPAINIMIGOARQUEIRO);
        painelPrincipal.add(painelMapaInimigoBarbaro, MAPAINIMIGOBARBARO);
        painelPrincipal.add(painelMapaInimigoMago, MAPAINIMIGOMAGO);


        pack();

        // Configurações da janela
        add(painelPrincipal);
        setTitle("Shadow Reign RPG");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(true);
        setVisible(true);

        mostrarTela(MENU);
    }

    public Player getPlayer() {
        return player;
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(painelPrincipal, nomeTela);
    }

    public static void main(String[] args) {
        new Container();
    }
}