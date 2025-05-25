package tela;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import telaMenu.Menu;
import fundo.Mapa;
import telaMenu.Casa;

public class Container extends JFrame {

    public static final String MENU = "MENU";
    public static final String CASA = "CASA";
    public static final String CRIACAO = "CRIACAO";
    public static final String MAPA = "MAPA";
    public static final String CREDITOS = "CREDITOS";

    private CardLayout cardLayout;
    private JPanel painelPrincipal;


    public Container() {
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        Menu painelMenu = new Menu(this);
        Casa painelCasa = new Casa();
        Mapa painelMapa = new Mapa();
        JPanel painelCriacao = new JPanel();
        JPanel painelCreditos = new JPanel();

        painelCriacao.setBackground(Color.BLUE);
        painelCreditos.setBackground(Color.DARK_GRAY);

        painelPrincipal.add(painelMenu, MENU);
        painelPrincipal.add(painelCriacao, CRIACAO);
        painelPrincipal.add(painelMapa, MAPA);
        painelPrincipal.add(painelCreditos, CREDITOS);
        painelPrincipal.add(painelCasa, CASA);

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

    public void mostrarTela(String nomeTela) {
        cardLayout.show(painelPrincipal, nomeTela);
    }

    public static void main(String[] args) {
        new Container();
    }
}
