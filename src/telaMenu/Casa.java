package telaMenu;

import tela.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import fundo.Mapa;
import java.io.File;
import java.io.*;

public class Casa extends JPanel implements ActionListener, KeyListener {
    private Image imagemCasa;
    private final Player player;
    private final Timer timer;
    private final ColisoesCasa colisoesCasa;
    private final Rectangle areaDaPorta = new Rectangle(450, 670, 70, 40);
    boolean mostrarMensagem = false;
    boolean podeSair = false;
    public Player getPlayer() { return player; }
    private Font fontePixel;


    private void trocarParaMapa() {
        if (mostrarMensagem && podeSair) {
            timer.stop();
            JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
            Mapa novoMapa = new Mapa();
            novoMapa.getPlayer().setX(80);
            novoMapa.getPlayer().setY(270);
            janela.remove(this);
            janela.add(novoMapa);
            janela.revalidate();
            janela.repaint();
        }
    }

    public Casa() {
        this.colisoesCasa = new ColisoesCasa();
        setFocusable(true);
        requestFocusInWindow();
        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });
        addKeyListener(this);

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/casa.png"));
        imagemCasa = icon.getImage();

        player = new Player();
        player.Load();
        player.setColisoesCasa(colisoesCasa);

        timer = new Timer (15,this);
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(imagemCasa, 275, 0, 1000,800, this);
        int larguraCasaPlayer = 84;
        int alturaCasaPlayer = 84;
        g.drawImage(player.getImagem(), 275 + player.getX(), player.getY(), larguraCasaPlayer ,alturaCasaPlayer, this);
        if (mostrarMensagem) {
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
            String texto = "APERTE ENTER PARA SAIR DE CASA!";
            g2.drawString(texto, x + 65, y + 125);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.Update();

        if (colisoesCasa.temColisao(player.getBounds())) {
            player.cancelarMovimento();
        }
        if (areaDaPorta.intersects(player.getBounds())) {
            trocarParaMapa();
        }
        if (player.getX() >= 360 && player.getX() <= 520 && player.getY() >= 600) {
            mostrarMensagem = true;
        } else {
            mostrarMensagem = false;
            podeSair = false;
        }
        repaint();
        }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagem) {
            podeSair = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
