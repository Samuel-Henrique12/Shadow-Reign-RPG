package tela;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import telaMenu.ColisoesCasa;

public class Player {

    private int x,y;
    private int dx, dy;
    private Image imagem;
    int altura,largura;
    private int frame = 1;
    private String direcao = "Baixo";
    private int animacaoDelay = 0;
    private final int velocidade = 4;

    public Player() {
        this.x = 83;
        this.y = 290;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 73, 73);
    }

    public void Load() {
        ImageIcon referencia = new ImageIcon("res\\PlayerParadoBaixo.png");
        imagem = referencia.getImage();
        altura = imagem.getHeight(null);
        largura = imagem.getWidth(null);
    }

    private ColisoesCasa colisoesCasa;

    public void setColisoesCasa(ColisoesCasa colisoesCasa) {
        this.colisoesCasa = colisoesCasa;
    }

    public void Update() {
        int novoX = x + dx;
        int novoY = y + dy;

        boolean podeMoverX = true;
        boolean podeMoverY = true;

        if (dx != 0) {
            Rectangle futuroX = new Rectangle(novoX, y , 83, 83);
            if (colisoesCasa != null && colisoesCasa.temColisao(futuroX)) {
                podeMoverX = false;
            }
        }

        if (dy != 0) {
            Rectangle futuroY = new Rectangle(x, novoY, 83, 83);
            if (colisoesCasa != null && colisoesCasa.temColisao(futuroY)) {
                podeMoverY = false;
            }
        }

        if (podeMoverX) x = novoX;
        if (podeMoverY) y = novoY;

        // Limites da tela
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 1920 - 83) x = 1920 - 83;
        if (y > 1080 - 83) y = 1080 - 83;

        // Animação
        if (dx != 0 || dy != 0) {
            animacaoDelay++;
            if (animacaoDelay >= 8) {
                alternarFrame();
                atualizarSprite();
                animacaoDelay = 0;
            }
        }
    }


    private void alternarFrame() {
        frame = (frame ==1) ? 2: 1;
    }

    private void atualizarSprite() {
        String caminho = "res\\PlayerAndando" + direcao + frame + ".png";
        imagem = new ImageIcon(caminho).getImage();
    }

    public void cancelarMovimento() {
        dx = 0;
        dy = 0;
    }

    // TECLAS DO PERSONAGEM

    public void keyPressed(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if(codigo == KeyEvent.VK_UP) {
            dy=-velocidade;
            direcao = "Cima";
        }
        if(codigo == KeyEvent.VK_DOWN) {
            dy=velocidade;
            direcao = "Baixo";
        }
        if(codigo == KeyEvent.VK_LEFT) {
            dx=-velocidade;
            direcao = "Baixo";
        }
        if(codigo == KeyEvent.VK_RIGHT) {
            dx=velocidade;
            direcao = "Baixo";
        }

        if(codigo == KeyEvent.VK_W) {
            dy=-velocidade;
            direcao = "Cima";
        }
        if(codigo == KeyEvent.VK_S) {
            dy=velocidade;
            direcao = "Baixo";
        }
        if(codigo == KeyEvent.VK_A) {
            dx=-velocidade;
            direcao = "Baixo";
        }
        if(codigo == KeyEvent.VK_D) {
            dx=velocidade;
            direcao = "Baixo";
        }
    }
    // FIM DAS TECLAS

    // PAROU DE APERTAR AS TECLAS ==


    public void keyReleased(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if(codigo == KeyEvent.VK_UP || codigo == KeyEvent.VK_DOWN) {
            dy=0;
        }
        if(codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_RIGHT) {
            dx=0;
        }

        if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_S) {
            dy=0;
        }
        if(codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_D) {
            dx=0;
        }

        imagem = new ImageIcon("res\\PlayerParado" + direcao + ".png").getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImagem() {
        return imagem;
    }
}