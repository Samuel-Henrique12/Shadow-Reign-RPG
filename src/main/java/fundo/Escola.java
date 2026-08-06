package fundo;

import tela.Navegacao;
import tela.PainelEscalavel;
import tela.Recursos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import tela.Player;
import tela.StatsMenu;
import tela.Hud;

public class Escola extends PainelEscalavel implements ActionListener, KeyListener {

    private final Image imagemFundo;
    private final Player player;
    private final Hud hud;
    private Font fontePixel;
    private final StatsMenu statsMenuController;
    private Timer timer;
    private ArrayList<String> dialogos = new ArrayList<>();
    private int dialogoIndex = 0;
    private boolean mostrandoDialogo = true;
    private String textoBalão = null;
    private boolean mostrandoMenuUpgrade = false;
    private boolean esperandoConfirmacao = false;

    public Escola(Hud hud, StatsMenu statsMenuController) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.hud = hud;
        this.statsMenuController = statsMenuController;
        this.player = statsMenuController.getPlayer();

        setFocusable(true);
        setDoubleBuffered(true);
        requestFocusInWindow();

        imagemFundo = Recursos.imagem("sala.png");

        dialogos.add("PRESTA ATENÇÃO AQUI NO CABEÇUDINHO");
        dialogos.add("Ah, só tem você aí");
        dialogos.add("Você quer upar seus atributos?");
        dialogos.add("Quer saber?... F@#!$-se, eu não ligo");

        timer = new Timer(PainelEscalavel.INTERVALO_MS, this);
        timer.start();

        fontePixel = Recursos.fonte(18f);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    trocarParaMapa();
                    return;
                }
                if (mostrandoDialogo) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        dialogoIndex++;
                        if (dialogoIndex >= dialogos.size()) {
                            mostrandoDialogo = false;
                            mostrandoMenuUpgrade = true;
                            atualizarTextoMenu();
                        }
                        repaint();
                    }
                } else if (mostrandoMenuUpgrade) {
                    int key = e.getKeyCode();

                    if (key == KeyEvent.VK_1) {
                        if (player.getPontosMelhoria() >= 1) {
                            aumentarAtaque();
                            esperandoConfirmacao = true;
                            mostrandoMenuUpgrade = false;
                            atualizarTextoConfirmacao();
                        } else {
                            mostrarErroSemPontos();
                        }
                        repaint();
                    } else if (key == KeyEvent.VK_2) {
                        if (player.getPontosMelhoria() >= 1) {
                            aumentarDefesa();
                            esperandoConfirmacao = true;
                            mostrandoMenuUpgrade = false;
                            atualizarTextoConfirmacao();
                        } else {
                            mostrarErroSemPontos();
                        }
                        repaint();
                    } else if (key == KeyEvent.VK_3) {
                        if (player.getPontosMelhoria() >= 1) {
                            aumentarVida();
                            esperandoConfirmacao = true;
                            mostrandoMenuUpgrade = false;
                            atualizarTextoConfirmacao();
                        } else {
                            mostrarErroSemPontos();
                        }
                        repaint();
                    } else if (key == KeyEvent.VK_4) {
                        mostrandoMenuUpgrade = false;
                        trocarParaMapa();
                    }
                }
                else if (esperandoConfirmacao) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        esperandoConfirmacao = false;
                        if (player.getPontosMelhoria() > 0) {
                            mostrandoMenuUpgrade = true;
                            atualizarTextoMenu();
                        } else {
                            dialogos.clear();
                            dialogos.add("Pontos de melhoria insuficientes.");
                            dialogos.add("Aperte ESC para voltar para mapa");
                            mostrandoDialogo = true;
                            dialogoIndex = 0;
                        }
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        esperandoConfirmacao = false;
                        trocarParaMapa();
                    }
                }
            }
        });
    }

    private void mostrarErroSemPontos() {
        dialogos.clear();
        dialogos.add("Pontos de melhoria insuficientes.");
        mostrandoDialogo = true;
        mostrandoMenuUpgrade = false;
        esperandoConfirmacao = false;
        dialogoIndex = 0;
    }

    private void atualizarTextoMenu() {
        textoBalão = "[1] Para aumentar Ataque\n"
                + "[2] Para aumentar Defesa\n"
                + "[3] Para aumentar Vida\n"
                + "[4] Para voltar ao Mapa";
    }

    private void atualizarTextoConfirmacao() {
        textoBalão = "Quer upar novamente?\nENTER para continuar / ESC para sair";
    }

    private void aumentarAtaque() {
        player.setAtaque(player.getAtaque() + 5);
        System.out.println("Ataque aumentado para " + player.getAtaque());
        player.setPontosMelhoria(player.getPontosMelhoria() - 1);
    }
    private void aumentarDefesa() {
        player.setDefesa(player.getDefesa() + 5);
        System.out.println("Defesa aumentada para " + player.getDefesa());
        player.setPontosMelhoria(player.getPontosMelhoria() - 1);
    }
    private void aumentarVida() {
        player.setVida(player.getVidaMaxima() + 10);
        System.out.println("Vida aumentada para " + player.getVida());
        player.setPontosMelhoria(player.getPontosMelhoria() - 1);
    }

    private void trocarParaMapa() {
        Mapa novoMapa = new Mapa(hud, statsMenuController);
        novoMapa.getPlayer().setX(190);
        novoMapa.getPlayer().setY(260);
        Navegacao.trocarTela(this, novoMapa);
    }
    @Override
    protected void desenhar(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;

        hud.render(g);
        g2.drawImage(imagemFundo, 258, 0, 1000, 800, this);

        g2.setFont(fontePixel);
        g2.setColor(Color.WHITE);

        if (mostrandoDialogo && dialogoIndex < dialogos.size()) {
            desenharBalaoDeFala(g2, dialogos.get(dialogoIndex));
        } else if (mostrandoMenuUpgrade || esperandoConfirmacao) {
            // Mostra o balão com o texto do menu ou confirmação
            desenharBalaoDeFala(g2, textoBalão);
        }
    }

    private void desenharBalaoDeFala(Graphics2D g2, String texto) {
        g2.setFont(fontePixel);
        int x = 485;
        int y = 175;
        int largura = 700;
        int altura = 170;

        g2.setColor(new Color(0, 0, 0, 200)); // fundo preto translúcido
        g2.fillRoundRect(x, y, largura, altura, 20, 20);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, largura, altura, 20, 20);

        // Rabicho do balão
        Polygon rabicho = new Polygon();
        rabicho.addPoint(x + 200, y);
        rabicho.addPoint(x + 230, y);
        rabicho.addPoint(x + 215, y - 20);
        g2.fillPolygon(rabicho);

        g2.setColor(new Color(0, 0, 0, 200));
        Polygon rabichoFundo = new Polygon();
        rabichoFundo.addPoint(x + 201, y);
        rabichoFundo.addPoint(x + 229, y);
        rabichoFundo.addPoint(x + 215, y - 18);
        g2.fillPolygon(rabichoFundo);

        // Texto com múltiplas linhas
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();

        int linhaAltura = fm.getHeight();
        String[] linhas = texto.split("\n");

        int totalAlturaTexto = linhaAltura * linhas.length;
        int textoY = y + (altura - totalAlturaTexto) / 2 + fm.getAscent();

        for (String linha : linhas) {
            int textoLargura = fm.stringWidth(linha);
            int textoX = x + (largura - textoLargura) / 2;
            g2.drawString(linha, textoX, textoY);
            textoY += linhaAltura;
        }

        // Instrução fixa para pressionar tecla (se não estiver no menu com instruções explícitas)
        if (!texto.contains("ENTER para continuar") && !texto.contains("para voltar")) {
            String instrucao = "Aperte ENTER para continuar";
            int instrucaoLargura = fm.stringWidth(instrucao);
            int instrucaoX = x + (largura - instrucaoLargura) / 2;
            int instrucaoY = y + altura - 20;
            g2.drawString(instrucao, instrucaoX, instrucaoY);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (mostrandoDialogo && e.getKeyCode() == KeyEvent.VK_ENTER) {
            dialogoIndex++;
            if (dialogoIndex >= dialogos.size()) {
                mostrandoDialogo = false;
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void aoSair() {
        timer.stop();
    }
}