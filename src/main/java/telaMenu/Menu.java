package telaMenu;

import tela.PainelEscalavel;
import tela.Recursos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tela.Container;

public class Menu extends PainelEscalavel implements KeyListener, MouseListener {

    // Dimensões da Arte do Menu
    private static final int LARGURA_ARTE = 1599;
    private static final int ALTURA_ARTE = 899;

    private Image imagemMenu;
    private int opcaoSelecionada = 0;
    private Container container;
    private Rectangle botaoJogar = new Rectangle(550, 260, 520, 200);
    private Rectangle botaoCreditos = new Rectangle(450, 525, 720, 200);

    public Menu(Container container) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.container = container;

        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
        addKeyListener(this);

        imagemMenu = Recursos.imagem("menu.jpeg");
    }
    @Override
    protected void desenhar(Graphics2D g) {
        g.drawImage(imagemMenu, 0, 0, LARGURA_ARTE, ALTURA_ARTE, this);
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
        java.awt.Point p = paraLogico(e.getX(), e.getY());
        int x = p.x;
        int y = p.y;

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

    @Override
    protected java.util.List<Rectangle> hitboxesDepuracao() {
        return java.util.Arrays.asList(botaoJogar, botaoCreditos);
    }
}

