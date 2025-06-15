package telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tela.Player;
import tela.Container;

public class CriacaoNome extends JPanel implements KeyListener, MouseListener {
    private Container container;
    private Player player;
    private String nomeDigitado = "";
    private Image imagemCriacaoNome;
    private Font fontePixel;
    private final Rectangle areaTexto = new Rectangle(585, 448, 356, 64);
    private Rectangle botaoAvancar = new Rectangle(945, 700, 85, 80);

    public CriacaoNome(Container container, Player player) {
        this.container = container;
        this.player = player;

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addMouseListener(this);

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/criacaoNome.jpg"));
        imagemCriacaoNome = icon.getImage();

        carregarFonte();
    }

    private void carregarFonte() {
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

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemCriacaoNome, -10, -50, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(fontePixel);

        if (!nomeDigitado.isEmpty()) {
            g2d.drawString("Nome: " + nomeDigitado, 100, 100);
        }

        // Nome
        g.setColor(new Color(40, 40, 40));
        g2d.setFont(fontePixel);
        FontMetrics fm = g2d.getFontMetrics(fontePixel);
        int larguraTexto = fm.stringWidth(nomeDigitado);

        // Empurrar o nome
        int centroCaixa = 580 + 356 / 2;
        int Texto = centroCaixa - larguraTexto / 2;
        int xTextoReflexo = centroCaixa - larguraTexto / 2;
        int yTextoReflexo = 490;

        if (!nomeDigitado.isEmpty()) {
            // Reflexo
            g2d.setColor(new Color(244, 164, 96));
            g2d.drawString(nomeDigitado, xTextoReflexo + 2, yTextoReflexo + 4);
        }

        // Texto principal
        g2d.setColor(new Color(40, 40, 40));
        g2d.drawString(nomeDigitado, xTextoReflexo, yTextoReflexo);

        g2d.drawString(nomeDigitado, Texto, 490);

        GradientPaint gradienteBotao = new GradientPaint(
                botaoAvancar.x, botaoAvancar.y, new Color(255, 94, 77),
                botaoAvancar.x + botaoAvancar.width, botaoAvancar.y + botaoAvancar.height, new Color(255, 195, 113)
        );

        g2d.setPaint(gradienteBotao);
        g2d.setStroke(new BasicStroke(3)); // contorno mais forte
        g2d.draw(botaoAvancar);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetterOrDigit(c) || c == ' ') {
            nomeDigitado += c;
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && nomeDigitado.length() > 0) {
            nomeDigitado = nomeDigitado.substring(0, nomeDigitado.length() - 1);
            repaint();
        }
    }
    @Override public void keyReleased(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (botaoAvancar.contains(e.getPoint()) && (!nomeDigitado.trim().isEmpty())) {
            player.setNome(nomeDigitado.trim());
            container.mostrarTela(Container.CRIACAOCLASSE);
        } else if (areaTexto.contains(e.getPoint())) {
            requestFocusInWindow(); // Clique dentro da caixa traz o foco
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
