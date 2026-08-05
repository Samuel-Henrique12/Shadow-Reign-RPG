package fundo;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import tela.Player;
import tela.Hud;
import tela.StatsMenu;

public class Dojo extends JPanel implements ActionListener {

    private Image imagemDojo;
    private int cameraX = 0;
    private int cameraY = 0;
    private Image mago;
    private final Player player;
    private Font fontePixel;
    Timer timer;
    private final Rectangle areaParaMapa = new Rectangle(710, 589, 190, 10);
    private boolean mostrarMensagemMapa = false;
    private boolean mostrarMensagemOni = false;
    private boolean podeVoltarMapa = false;
    private boolean trocarParaInimigoOni = false;
    private Hud hud = new Hud();
    private StatsMenu statsMenuController;
    private Image Mago;
    private Rectangle areaMago = new Rectangle(760, 150, 60, 8);
    private Rectangle areaOni = new Rectangle(735, 55, 130, 8);
    private int magoY= 283;
    private int magoX= 745;
    private boolean dialogoMago = false;

    private void trocarParaMapa() {
        timer.stop();
        JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
        Mapa mapa = new Mapa(hud, statsMenuController);
        mapa.getPlayer().setX(1160);
        mapa.getPlayer().setY(630);
        janela.remove(this);
        janela.add(mapa);
        janela.revalidate();
        janela.repaint();
        mapa.requestFocusInWindow();
    }

    private void trocarParaInimigoOni() {
        timer.stop();
        JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
        MapaInimigoMago mapaInimigoMago = new MapaInimigoMago( hud, statsMenuController);
        janela.remove(this);
        janela.add(mapaInimigoMago);
        janela.revalidate();
        janela.repaint();

        mapaInimigoMago.requestFocusInWindow();
    }

    public Dojo(Hud hud, StatsMenu statsMenu) {
        ColisoesDojo colisoesDojo = new ColisoesDojo();

        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon ref = new ImageIcon("res\\dojo.png");
        imagemDojo = ref.getImage();

        Mago = new ImageIcon(getClass().getResource("/res/Mago.png")).getImage();

        player = new Player();
        player.setColisoesCasa(colisoesDojo);
        this.hud = hud;
        this.statsMenuController = statsMenu;
        player.Load();

        player.setX(1000);
        player.setY(1200);

        addKeyListener(new TecladoAdapter());

        requestFocusInWindow();

        timer = new Timer(15, this);
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
            fontePixel = new Font("Arial", Font.PLAIN, 18);
        }
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D graficos = (Graphics2D) g;
        g.drawImage(imagemDojo, -cameraX, -cameraY, this);
        g.drawImage(player.getImagem(), player.getX(), player.getY(), 83, 83, this);

        int larguraMago = 100;
        int alturaMago = 100;
        g.drawImage(Mago, magoX - cameraX, magoY - cameraY, larguraMago, alturaMago, this);

        hud.render(g);
        statsMenuController.render(g);
        if (mostrarMensagemMapa) {
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
            String texto = "APERTE ENTER PARA VOLTAR AO MAPA!";
            g2.drawString(texto, x + 65, y + 125);
        }
        if (dialogoMago) {
            g2.setFont(fontePixel);

            int x = 430;
            int y = 540;
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
            String texto1 = "- Olha, vai ser dificil falar isso";
            String texto2 = "- Mas......";
            String texto3 = "* Silencio Constrangedor *";
            String texto4 = "- Eu libertei um Oni ali atrás";
            String texto5 = "- Boa sorte, guerreiro!";
            g2.drawString(texto1, x + 10, y + 48);
            g2.drawString(texto2, x + 10, y + 78);
            g2.drawString(texto3, x + 10, y + 113);
            g2.drawString(texto4, x + 10, y + 145);
            g2.drawString(texto5, x + 10, y + 175);
        }
        if (mostrarMensagemOni) {
            g2.setFont(fontePixel);

            int x = 430;
            int y = 540;
            int largura = 700;
            int altura = 230;

            // Sombra branca de fundo (mais suave, para dar profundidade)
            g2.setColor(Color.RED); // branco semi-transparente
            g2.fillRect(x + 5, y + 5, largura, altura);

            // Sombra cinza (mais próxima)
            g2.setColor(Color.RED);
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
            g2.setColor(Color.RED); // laranja claro transparente
            g2.fillRect(x + 10, y + 10, 100, 6);
            g2.fillRect(x + 10, y + 16, 30, 2);

            // Efeito espelhado no canto inferior direito
            g2.fillRect(x + largura - 110, y + altura - 16, 100, 6);
            g2.fillRect(x + largura - 40, y + altura - 20, 30, 2);

            // Texto centralizado
            g2.setColor(Color.RED);
            String texto = "APERTE ENTER PARA ENFRENTAR O ONI!";
            g2.drawString(texto, x + 50, y + 125);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cameraX = player.getX() - getWidth() / 2 + 40 / 2;
        cameraY = player.getY() - getHeight() / 2 + 500;
        player.Update();

        Rectangle jogador = new Rectangle(player.getX(), player.getY(), 83, 83);
        if (areaParaMapa.intersects(jogador)) {
            mostrarMensagemMapa = true;
        } else {
            mostrarMensagemMapa = false;
            podeVoltarMapa = false;
        }

        if (mostrarMensagemMapa && podeVoltarMapa) {
            trocarParaMapa();
        }

        if (player.getBounds().intersects(areaMago)) {
            dialogoMago = true;
        } else {
            dialogoMago = false;
        }
        if (areaOni.intersects(jogador)) {
            mostrarMensagemOni = true;
        } else {
            mostrarMensagemOni = false;
            trocarParaInimigoOni = false;
        }
        if (mostrarMensagemOni && trocarParaInimigoOni) {
            trocarParaInimigoOni();
        }

        if (cameraX > 0) cameraX = 0;
        if (cameraY > 555) cameraY = 555;
        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;

        repaint();
    }

    private class TecladoAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagemMapa) {
                podeVoltarMapa = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagemOni) {
                trocarParaInimigoOni = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
