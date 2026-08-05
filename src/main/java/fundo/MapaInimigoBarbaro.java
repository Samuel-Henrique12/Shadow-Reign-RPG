package fundo;

import tela.Navegacao;
import tela.PainelEscalavel;
import tela.Recursos;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import tela.Player;
import java.awt.*;
import javax.swing.*;
import tela.Hud;
import tela.StatsMenu;
import tela.StatsIcon;
import fundo.Dojo;

public class MapaInimigoBarbaro extends PainelEscalavel implements ActionListener {

    private Timer timer;
    private Player player;
    private Hud hud;
    private StatsMenu statsMenuController;
    private Font fontePixel;
    private Image fundoBatalhaImg;
    private Image playerBatalhaImg1;
    private Image playerBatalhaImg2;
    private Image inimigoBarbaroImg1;
    private Image inimigoBarbaroImg2;
    private boolean alternarSprite = false;
    private int frameCount = 0;
    private final int trocarCada = 30;
    private int vidaInimigo = 130;
    private int vidaMaximaInimigo = 130;
    private double vidaAnimada = 130;
    private double vidaAnimadaPlayer;
    private final double velocidadeAnimacao = 1.5;
    private java.util.List<String> mensagensLog = new java.util.ArrayList<>();
    private final int maxMensagens = 5;
    private enum EstadoBatalha { INTRO, ESCOLHENDO_ACAO, TURNO_EM_ANDAMENTO }
    private EstadoBatalha estadoAtual = EstadoBatalha.INTRO;

    public MapaInimigoBarbaro( Hud hud, StatsMenu statsMenuController) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.hud = hud;
        this.statsMenuController = statsMenuController;
        this.player = statsMenuController.getPlayer();
        int vida = player.getVida();
        int vidaMaxima = player.getVidaMaxima();
        int defesa = player.getDefesa();
        int ataque = player.getAtaque();
        int level = player.getLevel();
        int pontosMelhoria = player.getPontosMelhoria();
        String classe = player.getClasse();
        String nome = player.getNome();

        vidaAnimadaPlayer = player.getVida();

        fundoBatalhaImg = Recursos.imagem("Batalha.png");

        playerBatalhaImg1 = Recursos.imagem("PlayerBatalha1.png");
        playerBatalhaImg2 = Recursos.imagem("PlayerBatalha2.png");

        inimigoBarbaroImg1 = Recursos.imagem("InimigoBarbaro1.png");
        inimigoBarbaroImg2 = Recursos.imagem("InimigoBarbaro2.png");

        setFocusable(true);
        setDoubleBuffered(true);

        timer = new Timer(17, this);
        timer.start();

        fontePixel = Recursos.fonte(18f);

        mostrarMensagensIniciais();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();

                if (tecla == KeyEvent.VK_ESCAPE) {
                    mensagensLog.clear();
                    adicionarMensagemLog("Você fugiu da batalha...");
                    Timer delay = new Timer(1500, evt -> trocarParaMapa());
                    delay.setRepeats(false);
                    delay.start();
                    return;
                }

                switch (estadoAtual) {
                    case INTRO:
                        mostrarOpcoesAtaque();
                        break;
                    case ESCOLHENDO_ACAO:
                        if (tecla == KeyEvent.VK_1) {
                            executarAtaqueRapido();
                        } else if (tecla == KeyEvent.VK_2) {
                            executarInvestida();
                        }
                        break;
                    default:
                        break;
                }

                setFocusable(true);
                requestFocusInWindow();
            }
        });
    }

    private void mostrarMensagensIniciais() {
        mensagensLog.clear();
        adicionarMensagemLog("Saqueador: Vai me enfrentar por causa de uma maçâ?");
        adicionarMensagemLog("Saqueador: Esse foi seu último erro, prepare-se pro seu fim!");
        adicionarMensagemLog("");
        adicionarMensagemLog("Aperte ENTER para Continuar a batalha / Aperte ESC para Fugir");
        estadoAtual = EstadoBatalha.INTRO;
    }

    public void adicionarMensagemLog(String mensagem) {
        if (mensagensLog.size() >= maxMensagens) {
            mensagensLog.clear();
        }
        mensagensLog.add(mensagem);
    }
    @Override
    protected void desenhar(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(fundoBatalhaImg, 0, 0, null);

        Image inimigoAtual = alternarSprite ? inimigoBarbaroImg1 : inimigoBarbaroImg2;
        g.drawImage(inimigoAtual, 1080, 180, 210, 210, null);

        Image playerAtual = alternarSprite ? playerBatalhaImg1 : playerBatalhaImg2;
        g.drawImage(playerAtual, 300, 370, 210, 210, null);

        //Log de Mensagens

        g.setColor(new Color(0, 0, 0, 180)); // preto semi-transparente
        g.fillRoundRect(45, 615, 1470, 162, 15, 15);
        g.setColor(Color.ORANGE);
        g.drawRoundRect(45, 615, 1470, 162, 15, 15);

        // Mensagens
        g.setFont(fontePixel);
        g.setColor(Color.WHITE);
        int y = 650;
        for (String mensagem : mensagensLog) {
            g.drawString(mensagem, 65, y);
            y += 20;
        }

        // Barras de Vida Inimigo
        g.setColor(Color.RED);
        g.fillRect(1080, 150, 210, 10); // fundo da barra
        g.setColor(Color.GREEN);
        int barra = (int) ((vidaAnimada / (double) vidaMaximaInimigo) * 210);
        g.fillRect(1080, 150, barra, 10);
        if (vidaAnimada > vidaInimigo) {
            vidaAnimada -= velocidadeAnimacao;
            if (vidaAnimada < vidaInimigo) vidaAnimada = vidaInimigo;
        } else if (vidaAnimada < vidaInimigo) {
            vidaAnimada += velocidadeAnimacao;
            if (vidaAnimada > vidaInimigo) vidaAnimada = vidaInimigo;
        }

        // Barra de Vida Player
        g.setColor(Color.RED);
        g.fillRect(340, 340, 210, 10); // fundo da barra
        g.setColor(Color.GREEN);
        int barraPlayer = (int) ((vidaAnimadaPlayer / (double) player.getVidaMaxima()) * 210);
        g.fillRect(340, 340, barraPlayer, 10);
        if (vidaAnimadaPlayer > player.getVida()) {
            vidaAnimadaPlayer -= velocidadeAnimacao;
            if (vidaAnimadaPlayer < player.getVida()) vidaAnimadaPlayer = player.getVida();
        } else if (vidaAnimadaPlayer < player.getVida()) {
            vidaAnimadaPlayer += velocidadeAnimacao;
            if (vidaAnimadaPlayer > player.getVida()) vidaAnimadaPlayer = player.getVida();
        }

        g.setColor(new Color(90, 90, 100));
        g2.setFont(fontePixel);
        g.drawString("HP: " + vidaInimigo + "/" + vidaMaximaInimigo, 1084, 145);

        g.setColor(new Color(90, 90, 100));
        g2.setFont(fontePixel);
        g.drawString("HP: " + player.getVida() + "/" + player.getVidaMaxima(), 344, 335);

        g.setColor(new Color(0, 0, 0));
        g2.setFont(fontePixel);
        g.drawString("Inimigo: Saqueador", 50, 107);
        g.drawString("HP: " + vidaInimigo + "/" + vidaMaximaInimigo, 53, 129);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frameCount++;
        if (frameCount >= trocarCada) {
            alternarSprite = !alternarSprite;
            frameCount = 0;
        }

        player.Update();
        repaint();
    }

    private void trocarParaMapa() {
            Mapa novoMapa = new Mapa(hud, statsMenuController);
            novoMapa.getPlayer().setX(650);
            novoMapa.getPlayer().setY(400);
            Navegacao.trocarTela(this, novoMapa);
    }

    private int calcularDanoPlayer() {
        int ataqueBase = player.getAtaque();
        int level = player.getLevel();
        double variacao = Math.random() * 0.2 + 0.9;

        int danoCalculado = (int) ((ataqueBase + level * 0.5) * variacao);
        return Math.max(danoCalculado, 1);
    }

    private int calcularDanoPlayerInvestida() {
        int ataqueBase = player.getAtaque();
        int level = player.getLevel();
        double variacao = Math.random() * 0.3 + 1.0;

        int dano = (int) ((ataqueBase * 1.5 + level * 2) * variacao);
        return Math.max(dano, 5);
    }

    private void atacarInimigo() {
        int dano = (int) (Math.random() * 15 + 5);
        diminuirVida(dano);
        if (vidaInimigo > 0) {
            sofrerDanoDoInimigo();
        }
    }

    private void mostrarOpcoesAtaque() {
        mensagensLog.clear();
        adicionarMensagemLog("Aperte [1] para Utilizar Ataque Rápido");
        adicionarMensagemLog("Aperte [2] para usar Investida (" + player.getClasse() + ")");
        adicionarMensagemLog("");
        adicionarMensagemLog("Aperte ESC para Fugir");
        estadoAtual = EstadoBatalha.ESCOLHENDO_ACAO;
    }

    private void executarAtaqueRapido() {
        mensagensLog.clear();
        adicionarMensagemLog("Você usou Ataque Rápido!");
        atacarInimigoComDano(calcularDanoPlayer());
        estadoAtual = EstadoBatalha.INTRO;
    }

    private void executarInvestida() {
        mensagensLog.clear();
        adicionarMensagemLog("Você usou Investida!");
        atacarInimigoComDano(calcularDanoPlayerInvestida());
        estadoAtual = EstadoBatalha.INTRO;
    }

    private void atacarInimigoComDano(int dano) {
        diminuirVida(dano);
        if (vidaInimigo > 0) {
            sofrerDanoDoInimigo();
        }
    }

    public void diminuirVida(int dano) {
        if (vidaInimigo <= 0) return;

        vidaInimigo -= dano;
        if (vidaInimigo < 0) vidaInimigo = 0;

        adicionarMensagemLog("Você causou " + dano + " de dano!");

        if (vidaInimigo == 0) {
            adicionarMensagemLog("O inimigo foi derrotado!");
            Timer delay = new Timer(1500, e -> trocarParaMapa());
            delay.setRepeats(false);
            delay.start();
            aumentarAtributosJogador();
        }
    }

    private void sofrerDanoDoInimigo() {
        int dano = calcularDanoInimigoContraPlayer();
        int novaVida = player.getVida() - dano;
        if (novaVida < 0) novaVida = 0;

        player.setVida(novaVida);
        adicionarMensagemLog("O inimigo causou " + dano + " de dano!");
        adicionarMensagemLog("");
        adicionarMensagemLog("Aperte ENTER para continuar ou ESC para fugir");

        if (novaVida == 0) {
            adicionarMensagemLog("Você foi derrotado...");
            Timer delay = new Timer(1500, e -> trocarParaMapa());
            delay.setRepeats(false);
            delay.start();
            player.setVida(player.getVidaMaxima());
        }
    }

    private int calcularDanoInimigoContraPlayer() {
        int ataqueInimigo = 20;
        int defesaPlayer = player.getDefesa();
        int dano = ataqueInimigo - (defesaPlayer / 2);
        return Math.max(dano, 1);
    }

    private void aumentarAtributosJogador() {
        player.setLevel(player.getLevel() + 1);
        player.setAtaque(player.getAtaque() + 5);
        player.setDefesa(player.getDefesa() + 5);
        player.setVidaMaxima(player.getVidaMaxima() + 20);
        player.setVida(player.getVidaMaxima());
        player.setPontosMelhoria(player.getPontosMelhoria() + 1);

        adicionarMensagemLog("Parabéns! Você subiu para o nível " + player.getLevel());
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void aoSair() {
        timer.stop();
    }
}

