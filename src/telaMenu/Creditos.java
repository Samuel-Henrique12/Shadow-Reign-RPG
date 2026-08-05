package telaMenu;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import tela.Container;

public class Creditos extends JPanel {

    private Image creditos1;
    private Image creditos2;
    private boolean mostrandoSegundaImagem = false;
    private Rectangle setaCreditos1 = new Rectangle(1220, 627, 83, 76);
    private Rectangle setaCreditos2 = new Rectangle(1220, 627, 83, 76);
    private Container container;

    public Creditos(Container container) {
        this.container = container;
        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);

        creditos1 = new ImageIcon(getClass().getResource("/res/creditos1.png")).getImage();
        creditos2 = new ImageIcon(getClass().getResource("/res/creditos2.jpeg")).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (!mostrandoSegundaImagem && setaCreditos1.contains(x, y)) {
                    mostrandoSegundaImagem = true;
                    repaint();
                } else if (mostrandoSegundaImagem && setaCreditos2.contains(x, y)) {
                    mostrandoSegundaImagem = false;
                    container.mostrarTela(Container.MENU);
                }
            }
        });
    }

    public void reiniciar() {
        mostrandoSegundaImagem = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mostrandoSegundaImagem) {
            g.drawImage(creditos2, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.drawImage(creditos1, 0, 0, getWidth(), getHeight(), null);
        }
    }
}