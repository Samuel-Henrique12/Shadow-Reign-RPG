package fundo;

import tela.Navegacao;
import tela.PainelEscalavel;
import tela.Recursos;
import tela.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import tela.*;
import tela.StatsMenu;

public class Casa extends PainelEscalavel implements ActionListener, KeyListener {
    private Image imagemCasa;
    private final Player player;
    private final Timer timer;
    private final ColisoesCasa colisoesCasa;
    private final Rectangle areaDaPorta = new Rectangle(150, 498, 1200, 300);
    private Rectangle areaPai = new Rectangle(10, 50, 10, 30);
    private Rectangle areaMae = new Rectangle(10, 200, 10, 30);
    private Rectangle areaCama = new Rectangle(10, 250, 10, 30);
    boolean mostrarMensagem = false;
    boolean podeSair = false;
    private Image playerPai;
    private Image playerMae;
    private int paiX = 507;
    private int paiY= 270;
    private int maeX = 507;
    private int maeY= 505;
    private boolean dialogoPai = false;
    private boolean dialogoMae = false;
    public Player getPlayer() { return player; }
    private Font fontePixel;
    private Hud hud = new Hud();
    private StatsMenu statsMenuController;
    private StatsIcon statsIconController;


    private void trocarParaMapa() {
        if (mostrarMensagem && podeSair) {
            Mapa novoMapa = new Mapa(hud, statsMenuController);
            novoMapa.getPlayer().setX(190);
            novoMapa.getPlayer().setY(260);
            Navegacao.trocarTela(this, novoMapa);

        }
    }

    public Casa(Hud hud, StatsMenu statsMenu) {
        super(PainelEscalavel.LARGURA_PADRAO, PainelEscalavel.ALTURA_PADRAO);
        this.colisoesCasa = new ColisoesCasa();
        setFocusable(true);
        requestFocusInWindow();

        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });

        addKeyListener(this);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                java.awt.Point p = paraLogico(e.getX(), e.getY());
                statsMenuController.clicar(p.x, p.y);
            }
        });

        imagemCasa = Recursos.imagem("casa2.png");

        playerPai = Recursos.imagem("PlayerPai.png");
        playerMae = Recursos.imagem("PlayerMae.png");

        player = new Player();
        player.Load();
        player.setColisoesCasa(colisoesCasa);
        statsMenuController = new StatsMenu(hud, player);
        statsIconController = new StatsIcon(hud);
        this.hud = hud;
        this.statsMenuController = statsMenu;


        timer = new Timer (15,this);
        timer.start();

        fontePixel = Recursos.fonte(18f);
    }
    @Override
    protected void desenhar(Graphics2D g) {
        int cameraX = player.getX() - larguraLogica() / 2 + 32 / 2;
        int cameraY = player.getY() - alturaLogica() / 2 + 1050 / 2;

        cameraX = Math.max(0, Math.min(cameraX, imagemCasa.getWidth(this) - larguraLogica()));
        cameraY = Math.max(0, Math.min(cameraY, imagemCasa.getHeight(this) - alturaLogica()));
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(imagemCasa, -cameraX, -cameraY, this);
        int larguraCasaPlayer = 107;
        int alturaCasaPlayer = 107;

        int larguraPai = 100;
        int alturaPai = 100;
        g.drawImage(playerPai, paiX - cameraX, paiY - cameraY, larguraPai, alturaPai, this);

        int larguraMae = 120;
        int alturaMae = 130;
        g.drawImage(playerMae, maeX - cameraX, maeY - cameraY, larguraMae, alturaMae, this);


        g.drawImage(player.getImagem(),590 + player.getX(), 100 + player.getY(), larguraCasaPlayer ,alturaCasaPlayer, this);
        hud.render(g);
        if (statsMenuController != null) {
            statsMenuController.render(g);
        }
        if (dialogoPai) {
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
            String texto1 = "- Ja vai sair pra lutar, filho?";
            String texto2 = "- Aproveita e passa no Dojo para mim!";
            String texto3 = "- Pega 3 Shurikens";
            String texto4 = "* Entrega Chave do Dojo *";
            g2.drawString(texto1, x + 10, y + 48);
            g2.drawString(texto2, x + 10, y + 78);
            g2.drawString(texto3, x + 10, y + 113);
            g2.drawString(texto4, x + 10, y + 145);
        }
        if (dialogoMae) {
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
            String texto1 = "-Bom dia filho!";
            String texto2 = "-Tome cuidado quando for sair.";
            String texto3 = "-Tem muitos saqueadores essa época!";
            String texto4 = "* Abraço da Mamãe *";
            g2.drawString(texto1, x + 10, y + 48);
            g2.drawString(texto2, x + 10, y + 78);
            g2.drawString(texto3, x + 10, y + 113);
            g2.drawString(texto4, x + 10, y + 145);
        }
        if (mostrarMensagem) {
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
            String texto = "APERTE ENTER PARA SAIR DE CASA!";
            g2.drawString(texto, x + 65, y + 125);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.Update();

        if (colisoesCasa.temColisao(player.getBounds())) {
            player.cancelarMovimento();
        }
        if (areaDaPorta.intersects(player.getBounds())) {
            trocarParaMapa();
        }
        if (areaDaPorta.intersects(player.getBounds())) {
            mostrarMensagem = true;
        } else {
            mostrarMensagem = false;
            podeSair = false;
        }
        if (player.getBounds().intersects(areaPai)) {
            dialogoPai = true;
        } else {
            dialogoPai = false;
        }
        if (player.getBounds().intersects(areaMae)) {
            dialogoMae = true;
        } else {
            dialogoMae = false;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER && mostrarMensagem) {
            podeSair = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void aoSair() {
        timer.stop();
    }
}