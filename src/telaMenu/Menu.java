package telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tela.Container;

public class Menu extends JPanel implements KeyListener, MouseListener {
    private Image imagemMenu;
    private int opcaoSelecionada = 0;
    private Container container;
    private Rectangle botaoJogar = new Rectangle(550, 260, 520, 200);
    private Rectangle botaoCreditos = new Rectangle(450, 525, 720, 200);

    public Menu(Container container) {
        this.container = container;

        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
        addKeyListener(this);

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/menu.jpeg"));
        imagemMenu = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemMenu, 0, 0, this);
    }
@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
        opcaoSelecionada = (opcaoSelecionada - 1 + 2) % 2;
        repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
        opcaoSelecionada = (opcaoSelecionada + 1) % 2;
        repaint();
    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (opcaoSelecionada == 0) {
            container.mostrarTela(Container.CRIACAONOME);
        } else {
            container.mostrarTela(Container.CREDITOS);
        }
    }
}

@Override
public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    if (botaoJogar.contains(x, y)) {
        container.mostrarTela(Container.CRIACAONOME);
    } else if (botaoCreditos.contains(x, y)) {
        container.mostrarTela(Container.CREDITOS);
    }
}

public void keyReleased(KeyEvent e) {}
public void keyTyped(KeyEvent e) {}
public void mouseClicked(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
}

