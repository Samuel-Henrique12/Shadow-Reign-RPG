package tela;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Collections;
import java.util.List;

// Base de Todas as Telas do Jogo
public abstract class PainelEscalavel extends JPanel {

    // Base Vs Coordenadas do Jogo Foram Calibradas
    public static final int LARGURA_PADRAO = 1540;
    public static final int ALTURA_PADRAO = 845;

    // Intervalo do Loop de Render, Unico para Todas as Telas
    // OBSERVAÇÃO: O Relogio do Windows Tem Granularidade de 15,625ms
    public static final int INTERVALO_MS = 14;

    // Passo Fixo da Logica: o Movimento Nao Depende da Taxa Real do Timer
    public static final int PASSO_LOGICO_MS = 16;

    // Teto de Compensacao: Evita Salto Enorme Depois de Uma Travada Longa
    private static final int MAXIMO_PASSOS = 5;

    private final int larguraBase;
    private final int alturaBase;

    private long marcoLogica = 0;

    private double fator = 1.0;
    private int deslocX = 0;
    private int deslocY = 0;

    protected PainelEscalavel(int larguraBase, int alturaBase) {
        this.larguraBase = larguraBase;
        this.alturaBase = alturaBase;

        setPreferredSize(new Dimension(larguraBase, alturaBase));
        setBackground(Color.BLACK);
        setOpaque(true);
    }

    protected final int larguraLogica() {
        return larguraBase;
    }

    protected final int alturaLogica() {
        return alturaBase;
    }

    // Calcula o Fator de Escala e as Barras Pretas
    private void recalcularEscala() {
        int w = getWidth();
        int h = getHeight();

        if (w <= 0 || h <= 0) {
            fator = 1.0;
            deslocX = 0;
            deslocY = 0;
            return;
        }

        fator = Math.min(w / (double) larguraBase, h / (double) alturaBase);
        deslocX = (int) Math.round((w - larguraBase * fator) / 2.0);
        deslocY = (int) Math.round((h - alturaBase * fator) / 2.0);
    }

    @Override
    protected final void paintComponent(Graphics g) {
        super.paintComponent(g);
        recalcularEscala();

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.translate(deslocX, deslocY);
            g2.scale(fator, fator);
            g2.clipRect(0, 0, larguraBase, alturaBase);

            // Pixel Art: Vizinho Mais Proximo e Mais Rapido e Nao Borra
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            desenhar(g2);

            if (Depuracao.hitboxes) {
                desenharDepuracao(g2);
            }
        } finally {
            g2.dispose();
        }
    }

    // Corpo do Antigo paintComponent de Cada Tela
    protected abstract void desenhar(Graphics2D g);

    // Quantos Passos de Logica Cabem no Tempo Decorrido Desde o Ultimo Quadro
    protected final int passosDeLogica() {
        long agora = System.currentTimeMillis();

        if (marcoLogica == 0) {
            marcoLogica = agora;
            return 1;
        }

        int passos = (int) ((agora - marcoLogica) / PASSO_LOGICO_MS);

        if (passos > MAXIMO_PASSOS) {
            marcoLogica = agora;
            return MAXIMO_PASSOS;
        }

        marcoLogica += (long) passos * PASSO_LOGICO_MS;
        return passos;
    }

    // Chamado Pelo Container Antes de Mostrar a Tela
    public void aoEntrar() {
    }

    // Chamado Pelo Container Antes de Descartar a Tela
    public void aoSair() {
    }

    // Areas Clicaveis da Tela, Para o Modo de Depuracao
    protected List<Rectangle> hitboxesDepuracao() {
        return Collections.emptyList();
    }

    private void desenharDepuracao(Graphics2D g) {
        g.setColor(new Color(255, 0, 255, 200));
        for (Rectangle r : hitboxesDepuracao()) {
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    // Converte Coordenada da Janela Para Coordenada Logica do Jogo
    public Point paraLogico(int telaX, int telaY) {
        recalcularEscala();

        if (fator <= 0) {
            return new Point(telaX, telaY);
        }

        return new Point(
                (int) Math.round((telaX - deslocX) / fator),
                (int) Math.round((telaY - deslocY) / fator));
    }

    public Point paraLogico(Point p) {
        return paraLogico(p.x, p.y);
    }
}
