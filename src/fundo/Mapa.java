package fundo;
import java.awt.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.util.ArrayList;
import tela.Player;
import telaMenu.Casa;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.*;

public class Mapa extends JPanel implements ActionListener {

    private final Image mapaPrincipal;
    private ArrayList<Rectangle> colisoes = new ArrayList<>();
    private final Player player;
    Timer timer;
    private int cameraX, cameraY;
    int telaLargura = 800;
    int telaAltura = 600;
    int mapaLargura = 1920;
    int mapaAltura = 1115;
    private Font fontePixel;
    private final Rectangle areaParaCasa = new Rectangle(100, 180, 50, 50);
    boolean mostrarMensagemCasa = false;
    boolean podeEntrarCasa = false;

    private void trocarParaCasa() {
        timer.stop();
        JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
        Casa voltouCasa = new Casa();
        voltouCasa.getPlayer().setX(440);
        voltouCasa.getPlayer().setY(610);
        janela.remove(this);
        janela.add(voltouCasa);
        janela.revalidate();
        janela.repaint();
    }


    public Player getPlayer() {
        return player;
    }

    public Mapa() {
        setFocusable(true);
        setDoubleBuffered(true);
        colisoes = ColisoesMapaPrincipal.carregarColisoes();
        requestFocusInWindow();
        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });


        ImageIcon referencia = new ImageIcon("res\\mapa.png");
        mapaPrincipal = referencia.getImage();

        player = new Player();
        player.Load();

        addKeyListener(new TecladoAdapter());

        timer = new Timer(5, this);
        timer.start();

        try {
            fontePixel = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/res/Press_Start_2P/PressStart2P-Regular.ttf")
            ).deriveFont(18f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontePixel);  // opcional
        } catch (Exception ex) {
            ex.printStackTrace();
            fontePixel = new Font("Arial", Font.PLAIN, 18); // fallback
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D graficos = (Graphics2D) g;

        graficos.drawImage(mapaPrincipal, -cameraX, -cameraY, mapaLargura, mapaAltura, this);
        graficos.drawImage(player.getImagem(), player.getX() - cameraX, player.getY() - cameraY, 83, 83, this);
        if (mostrarMensagemCasa) {
            g2.setFont(fontePixel);

            int x = 430;
            int y = 10;
            int largura = 700;
            int altura = 230;

            // Sombra branca de fundo (mais suave, para dar profundidade)
            g2.setColor(new Color(255, 255, 255, 190)); // branco semi-transparente
            g2.fillRect(x + 5, y + 5, largura, altura);

            // Sombra cinza (mais próxima)
            g2.setColor(new Color(40, 40, 40));
            g2.fillRect(x + 3, y + 3, largura, altura);

            // Caixa principal (preta)
            g2.setColor(Color.BLACK);
            g2.fillRect(x, y, largura, altura);

            // Borda dupla branca (pixel art)
            g2.setColor(Color.WHITE);
            // cantos
            g2.fillRect(x, y, 5, 5);
            g2.fillRect(x + largura - 5, y, 5, 5);
            g2.fillRect(x, y + altura - 5, 5, 5);
            g2.fillRect(x + largura - 5, y + altura - 5, 5, 5);
            // bordas internas
            g2.fillRect(x + 5, y, largura - 10, 2); // topo
            g2.fillRect(x + 5, y + altura - 2, largura - 10, 2); // base
            g2.fillRect(x, y + 5, 2, altura - 10); // esquerda
            g2.fillRect(x + largura - 2, y + 5, 2, altura - 10); // direita

            // segunda camada de contorno (marrom claro para toque retrô)
            g2.setColor(new Color(255, 75, 8));
            g2.fillRect(x + 2, y + 2, largura - 4, 2); // topo
            g2.fillRect(x + 2, y + altura - 4, largura - 4, 2); // base
            g2.fillRect(x + 2, y + 4, 2, altura - 8); // esquerda
            g2.fillRect(x + largura - 4, y + 4, 2, altura - 8); // direita

            // Efeito "highlight" 8-bit no canto superior esquerdo
            g2.setColor(new Color(255, 255, 255, 255)); // laranja claro transparente
            g2.fillRect(x + 10, y + 10, 100, 6);
            g2.fillRect(x + 10, y + 16, 30, 2);

            // Efeito espelhado no canto inferior direito
            g2.fillRect(x + largura - 110, y + altura - 16, 100, 6);
            g2.fillRect(x + largura - 40, y + altura - 20, 30, 2);

            // Texto centralizado
            g2.setColor(Color.WHITE);
            String texto = "APERTE ENTER PARA ENTRAR EM CASA!";
            g2.drawString(texto, x + 65, y + 125);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int oldX = player.getX();
        int oldY = player.getY();
        player.Update();

        Rectangle jogador = player.getBounds();
        for (Rectangle obstaculo : colisoes) {
            if (jogador.intersects(obstaculo)) {
                player.setX(oldX);
                player.setY(oldY);
                break;
            }
        }
        if (areaParaCasa.intersects(jogador)) {
            mostrarMensagemCasa = true;
        } else {
            mostrarMensagemCasa = false;
            podeEntrarCasa = false;
        }
        if (mostrarMensagemCasa && podeEntrarCasa) {
            trocarParaCasa();
        }

        cameraX = player.getX() - telaLargura / 2;
        cameraY = player.getY() - telaAltura / 2;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > 390) cameraX = 390;
        if (cameraY > 280) cameraY = 280;
        repaint();
    }

    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagemCasa) {
                podeEntrarCasa = true;
            }

        }
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}