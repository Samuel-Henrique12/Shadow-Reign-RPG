package fundo;

import tela.Navegacao;
import tela.PainelEscalavel;
import tela.Recursos;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.util.ArrayList;
import tela.Player;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import tela.Hud;
import tela.StatsMenu;

public class Mapa extends PainelEscalavel implements ActionListener {

    private final Image mapaPrincipal;
    private ArrayList<Rectangle> colisoes = new ArrayList<>();
    private final Player player;
    Timer timer;
    private int cameraX, cameraY;
    int telaLargura = 800;
    int telaAltura = 600;
    int mapaLargura = 1536;
    int mapaAltura = 1024;
    private Font fontePixel;
    private final Rectangle areaParaCasa = new Rectangle(220, 200, 40, 10);
    boolean mostrarMensagemCasa = false;
    boolean podeEntrarCasa = false;
    private final Rectangle areaParaDojo = new Rectangle(1140, 615, 140, 10);
    boolean mostrarMensagemDojo = false;
    boolean podeEntrarDojo = false;
    boolean podeLutarInimigoBarbaro = false;
    boolean podeLutarInimigoArqueiro = false;
    private Hud hud = new Hud();
    private StatsMenu statsMenuController;
    private Image Barbaro;
    private Image Arqueiro;
    private int BarbaroX = 790;
    private int BarbaroY= 370;
    private int ArqueiroX = 1080;
    private int ArqueiroY= 205;;
    private boolean mostrarDialogoBarbaro = false;
    private boolean mostrarDialogoArqueiro = false;
    private Rectangle areaBarbaro = new Rectangle(770, 390, 120, 30);
    private Rectangle areaArqueiro = new Rectangle(1090, 200, 90, 103);
    private Rectangle areaEscola = new Rectangle(880,1000,100,10);
    boolean podeEntrarEscola = false;
    boolean mostrarMensagemEscola = false;

    private void trocarParaCasa() {
        Casa voltouCasa = new Casa(hud, statsMenuController);
        voltouCasa.getPlayer().setX(170);
        voltouCasa.getPlayer().setY(425);
        Navegacao.trocarTela(this, voltouCasa);
    }

    private void trocarParaDojo() {
        Dojo dojo = new Dojo(hud, statsMenuController);
        dojo.getPlayer().setX(750);
        dojo.getPlayer().setY(470);
        Navegacao.trocarTela(this, dojo);
    }

    private void trocarParaInimigoBarbaro() {
        MapaInimigoBarbaro mapaInimigoBarbaro = new MapaInimigoBarbaro(hud, statsMenuController);
        Navegacao.trocarTela(this, mapaInimigoBarbaro);
    }

    private void trocarParaInimigoArqueiro() {
        MapaInimigoArqueiro mapaInimigoArqueiro = new MapaInimigoArqueiro(hud, statsMenuController);
        Navegacao.trocarTela(this, mapaInimigoArqueiro);
    }

    private void trocarParaEscola() {
        Escola escola = new Escola(hud, statsMenuController);
        Navegacao.trocarTela(this, escola);
    }


    public Player getPlayer() {
        return player;
    }

    public Mapa(Hud hud, StatsMenu statsMenu) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        setFocusable(true);
        setDoubleBuffered(true);
        colisoes = ColisoesMapaPrincipal.carregarColisoes();
        requestFocusInWindow();

        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                java.awt.Point p = paraLogico(e.getX(), e.getY());
                statsMenuController.clicar(p.x, p.y);
            }
        });


        mapaPrincipal = Recursos.imagem("mapa2.jpg");

        Arqueiro = Recursos.imagem("Arqueiro.png");
        Barbaro = Recursos.imagem("Barbaro.png");

        player = new Player();
        this.hud = hud;
        this.statsMenuController = statsMenu;

        player.Load();

        addKeyListener(new TecladoAdapter());

        timer = new Timer(PainelEscalavel.INTERVALO_MS, this);
        timer.start();

        fontePixel = Recursos.fonte(18f);
    }
    @Override
    protected void desenhar(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D graficos = (Graphics2D) g;

        graficos.drawImage(mapaPrincipal, -cameraX, -cameraY, mapaLargura, mapaAltura, this);
        graficos.drawImage(player.getImagem(), player.getX() - cameraX, player.getY() - cameraY, 83, 83, this);
        hud.render(g);
        statsMenuController.render(g);

        int larguraBarbaro = 100;
        int alturaBarbaro = 100;
        g.drawImage(Barbaro, BarbaroX - cameraX, BarbaroY - cameraY, larguraBarbaro, alturaBarbaro, this);

        int larguraArqueiro = 92;
        int alturaArqueiro = 92;
        g.drawImage(Arqueiro, ArqueiroX - cameraX, ArqueiroY - cameraY, larguraArqueiro, alturaArqueiro, this);

        if (mostrarMensagemCasa) {
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
            String texto = "APERTE ENTER PARA ENTRAR EM CASA!";
            g2.drawString(texto, x + 65, y + 125);
        }
        if (mostrarMensagemDojo) {
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
            String texto = "APERTE ENTER PARA ENTRAR NO DOJO!";
            g2.drawString(texto, x + 65, y + 125);
        }
        if (mostrarMensagemEscola) {
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
            String texto = "APERTE ENTER PARA ENTRAR NA UNA!";
            g2.drawString(texto, x + 65, y + 125);
        }
        if (mostrarDialogoBarbaro) {
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
            String texto1 = "- GUERREIRO! QUE BOM QUE CHEGOU!";
            String texto2 = "- NO TOPO DA ARVORE TEM UM SAQUEADOR!";
            String texto3 = "- ELE ROUBOU MINHA MAÇA!!!";
            String texto4 = "- DÊ UM FIM NELE POR MIM!";
            String texto5 = "APERTE ENTER PARA BATALHAR";
            g2.drawString(texto1, x + 10, y + 48);
            g2.drawString(texto2, x + 10, y + 78);
            g2.drawString(texto3, x + 10, y + 113);
            g2.drawString(texto4, x + 10, y + 145);
            g2.drawString(texto5, x + 100, y + 204);
        }
        if (mostrarDialogoArqueiro) {
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
            String texto1 = "- Olá, nobre guerreiro!";
            String texto2 = "- Gostaria de uma missão?";
            String texto3 = "- Avistaram um bandido ao sul!";
            String texto4 = "- Derrote ele e ficara mais forte.";
            String texto5 = "APERTE ENTER PARA BATALHAR";
            g2.drawString(texto1, x + 10, y + 48);
            g2.drawString(texto2, x + 10, y + 78);
            g2.drawString(texto3, x + 10, y + 113);
            g2.drawString(texto4, x + 10, y + 145);
            g2.drawString(texto5, x + 100, y + 204);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int passo = passosDeLogica(); passo > 0; passo--) {
            moverJogador();
        }

        Rectangle jogador = player.getBounds();
        if (areaParaCasa.intersects(jogador)) {
            mostrarMensagemCasa = true;
        } else {
            mostrarMensagemCasa = false;
            podeEntrarCasa = false;
        }
        if (mostrarMensagemCasa && podeEntrarCasa) {
            trocarParaCasa();
        }


        if (areaParaDojo.intersects(jogador)) {
            mostrarMensagemDojo = true;
        } else {
            mostrarMensagemDojo = false;
            podeEntrarDojo = false;
        }

        if (mostrarMensagemDojo && podeEntrarDojo) {
            trocarParaDojo();
        }

        if (areaEscola.intersects(jogador)) {
            mostrarMensagemEscola = true;
        } else {
            mostrarMensagemEscola = false;
            podeEntrarEscola = false;
        }

        if (mostrarMensagemEscola && podeEntrarEscola) {
            trocarParaEscola();
        }

        if (player.getBounds().intersects(areaBarbaro)) {
            mostrarDialogoBarbaro = true;
        } else {
            mostrarDialogoBarbaro = false;
            podeLutarInimigoBarbaro = false;
        }

        if (player.getBounds().intersects(areaArqueiro)) {
            mostrarDialogoArqueiro = true;
        } else {
            mostrarDialogoArqueiro = false;
            podeLutarInimigoArqueiro = false;
        }

        if (mostrarDialogoArqueiro && podeLutarInimigoArqueiro) {
            trocarParaInimigoArqueiro();
        }

        if (mostrarDialogoBarbaro && podeLutarInimigoBarbaro) {
            trocarParaInimigoBarbaro();
        }

        atualizarCamera();
        repaint();
    }

    // Um Passo de Movimento: Anda e Desfaz se Bateu em Obstaculo
    private void moverJogador() {
        int oldX = player.getX();
        int oldY = player.getY();
        player.Update();

        Rectangle jogador = player.getBounds();
        for (Rectangle obstaculo : colisoes) {
            if (jogador.intersects(obstaculo)) {
                player.setX(oldX);
                player.setY(oldY);
                break;
            }
        }
    }

    // Precisa Rodar Antes do Primeiro Frame, Senao a Cena Salta ao Entrar
    private void atualizarCamera() {
        cameraX = player.getX() - telaLargura / 2;
        cameraY = player.getY() - telaAltura / 2;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > 0) cameraX = 0;
        if (cameraY > 230) cameraY = 230;
    }

    @Override
    public void aoEntrar() {
        atualizarCamera();
    }

    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagemCasa) {
                podeEntrarCasa = true;
            }
            if (mostrarMensagemDojo && e.getKeyCode() == KeyEvent.VK_ENTER) {
                podeEntrarDojo = true;
            }
            if (mostrarMensagemEscola && e.getKeyCode() == KeyEvent.VK_ENTER) {
                podeEntrarEscola = true;
            }
            if (mostrarDialogoArqueiro && e.getKeyCode() == KeyEvent.VK_ENTER) {
                podeLutarInimigoArqueiro = true;
            }
            if (mostrarDialogoBarbaro && e.getKeyCode() == KeyEvent.VK_ENTER) {
                podeLutarInimigoBarbaro = true;
            }
                }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }


    @Override
    public void aoSair() {
        timer.stop();
    }
}