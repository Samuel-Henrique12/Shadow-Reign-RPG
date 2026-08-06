package telaMenu;

import tela.PainelEscalavel;
import tela.Recursos;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import tela.Container;

public class Creditos extends PainelEscalavel {

    private Image creditos1;
    private Image creditos2;
    private boolean mostrandoSegundaImagem = false;
    private Rectangle setaCreditos1 = new Rectangle(1220, 627, 83, 76);
    private Rectangle setaCreditos2 = new Rectangle(1220, 627, 83, 76);
    private Container container;

    public Creditos(Container container) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.container = container;
        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);

        creditos1 = Recursos.imagem("creditos1.jpg");
        creditos2 = Recursos.imagem("creditos2.jpeg");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                java.awt.Point p = paraLogico(e.getX(), e.getY());
                int x = p.x;
                int y = p.y;

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
    protected void desenhar(Graphics2D g) {
        if (mostrandoSegundaImagem) {
            g.drawImage(creditos2, 0, 0, larguraLogica(), alturaLogica(), null);
        } else {
            g.drawImage(creditos1, 0, 0, larguraLogica(), alturaLogica(), null);
        }
    }

    @Override
    protected java.util.List<Rectangle> hitboxesDepuracao() {
        return java.util.Arrays.asList(setaCreditos1, setaCreditos2);
    }
}