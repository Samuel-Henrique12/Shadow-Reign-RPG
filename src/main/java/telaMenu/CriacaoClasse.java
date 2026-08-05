package telaMenu;



import tela.PainelEscalavel;
import tela.Recursos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import tela.*;
import telaMenu.*;
import fundo.*;

import tela.Container;

public class CriacaoClasse extends PainelEscalavel implements KeyListener, MouseListener {
    private Container container;
    private Player player;
    private String nomeDigitado = "";
    private Image imagemCriacaoClasse;
    private String classeSelecionada = null;
    private Font fontePixel;
    private final Rectangle areaRonin = new Rectangle(640, 252, 240, 90);
    private final Rectangle areaSamurai = new Rectangle(633, 370, 253, 93);
    private final Rectangle areaShogun = new Rectangle(635, 493, 251, 90);
    private Rectangle botaoAvancar = new Rectangle(931, 673, 97, 95);

    public CriacaoClasse(Container container, Player player) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.container = container;
        this.player = player;

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addMouseListener(this);

        imagemCriacaoClasse = Recursos.imagem("criacaoClasse.jpg");

        carregarFonte();
    }

    private void carregarFonte() {
        fontePixel = Recursos.fonte(18f);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
    @Override
    protected void desenhar(Graphics2D g) {
        g.drawImage(imagemCriacaoClasse, -10, -50, larguraLogica(), alturaLogica(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(fontePixel);

        if (classeSelecionada != null) {
            g2d.drawString("Classe: " + classeSelecionada.toUpperCase(), 100, 100);
        }

        // Borda destacando a seleção atual
        Rectangle selectedArea = null;

        if ("Ronin".equals(classeSelecionada)) {
            selectedArea = areaRonin;
        } else if ("Samurai".equals(classeSelecionada)) {
            selectedArea = areaSamurai;
        } else if ("Shogun".equals(classeSelecionada)) {
            selectedArea = areaShogun;
        }

        if (selectedArea != null) {
            GradientPaint gradiente = new GradientPaint(
                    selectedArea.x, selectedArea.y, new Color(255, 94, 77),   // laranja avermelhado
                    selectedArea.x + selectedArea.width, selectedArea.y + selectedArea.height, new Color(255, 204, 102) // amarelo pôr do sol
            );

            g2d.setPaint(gradiente);
            g2d.setStroke(new BasicStroke(4)); // borda mais grossa
            g2d.draw(selectedArea);
        }
        GradientPaint gradienteBotao = new GradientPaint(
                botaoAvancar.x, botaoAvancar.y, new Color(255, 94, 77),
                botaoAvancar.x + botaoAvancar.width, botaoAvancar.y + botaoAvancar.height, new Color(255, 195, 113)
        );

        g2d.setPaint(gradienteBotao);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(botaoAvancar);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = paraLogico(e.getPoint());

        if (areaRonin.contains(click)) {
            classeSelecionada = "Ronin";
            repaint();
        } else if (areaSamurai.contains(click)) {
            classeSelecionada = "Samurai";
            repaint();
        } else if (areaShogun.contains(click)) {
            classeSelecionada = "Shogun";
            repaint();
        } else if (botaoAvancar.contains(click) && classeSelecionada != null) {
            player.setClasse(classeSelecionada);
            container.mostrarTela(Container.CASA);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    protected java.util.List<Rectangle> hitboxesDepuracao() {
        return java.util.Arrays.asList(areaRonin, areaSamurai, areaShogun, botaoAvancar);
    }
}
