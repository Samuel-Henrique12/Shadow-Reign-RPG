package tela;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import fundo.ColisoesCasa;

public class Player {

    private String nome;
    private String classe;
    private int level;
    private int ataque;
    private int defesa;
    private int vida;
    private int vidaMaxima;
    private int pontosMelhoria;
    private int x,y;
    private int dx, dy;
    private Image imagem;
    private String direcao = "Baixo";
    private String direcaoSimples = "Baixo";
    private String direcaoComposta = "BaixoDireita";
    private int animacaoDelay = 0;
    private final int velocidade = 3;
    private boolean andando = false;

    public void inicializarAtributosPorClasse() {
        switch (classe.toLowerCase()) {
            case "ronin":
                this.level = 1;
                this.ataque = 8;
                this.defesa = 12;
                this.vida = 100;
                this.vidaMaxima = 100;
                this.pontosMelhoria = 0;
                break;
            case "samurai":
                this.level = 1;
                this.ataque = 10;
                this.defesa = 8;
                this.vida = 170;
                this.vidaMaxima = 170;
                this.pontosMelhoria = 0;
                break;
            case "shogun":
                this.level = 1;
                this.ataque = 30;
                this.defesa = 7;
                this.vida = 100;
                this.vidaMaxima = 100;
                this.pontosMelhoria = 0;
                break;
            default:
                this.level = 1;
                this.ataque = 10;
                this.defesa = 5;
                this.vida = 100;
                this.pontosMelhoria = 0;
                break;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setClasse(String classe) {
        this.classe = classe;
        inicializarAtributosPorClasse();
    }
    public String getClasse() {
        return classe;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {return vidaMaxima; }

    public void setVidaMaxima(int vidaMaxima) {this.vidaMaxima = vidaMaxima;}

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getPontosMelhoria() {
        return pontosMelhoria;
    }

    public void setPontosMelhoria(int pontosMelhoria) {
        this.pontosMelhoria = pontosMelhoria;
    }

    private void alternarSpriteCaminhada() {
        andando = !andando;

        if (andando) {
            imagem = Recursos.imagem("PlayerAndando" + direcaoComposta + ".png");
        } else {
            imagem = Recursos.imagem("PlayerParado" + direcaoSimples + ".png");
        }
    }

    public Player() {
        this.x = 83;
        this.y = 290;

        this.level = 1;
        this.ataque = 10;
        this.defesa = 5;
        this.vida = 100;
        this.vidaMaxima = 100;
        this.pontosMelhoria = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 73, 73);
    }

    public void Load() {
        Recursos.preCarregarPlayer();
        imagem = Recursos.imagem("PlayerParadoBaixo.png");
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
            Rectangle futuroX = new Rectangle(novoX, y, 83, 83);
            if (colisoesCasa != null && colisoesCasa.temColisao(futuroX)){
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


        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 1920 - 83) x = 1920 - 83;
        if (y > 1080 - 83) y = 1080 - 83;


        if (dx != 0 || dy != 0) {
            animacaoDelay++;
            if (animacaoDelay >= 7) {
                alternarSpriteCaminhada();
                animacaoDelay = 0;
            }
        }
    }

    public void cancelarMovimento() {
        dx = 0;
        dy = 0;
    }

    // TECLAS DO PERSONAGEM

    public void keyPressed(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if (codigo == KeyEvent.VK_UP || codigo == KeyEvent.VK_W) {
            dy = -velocidade;
            direcaoSimples = "Cima";
            if (dx < 0) direcaoComposta = "CimaEsquerda";
            else if (dx > 0) direcaoComposta = "CimaDireita";
            else direcaoComposta = "CimaDireita"; // padrão
        }

        if (codigo == KeyEvent.VK_DOWN || codigo == KeyEvent.VK_S) {
            dy = velocidade;
            direcaoSimples = "Baixo";
            if (dx < 0) direcaoComposta = "BaixoEsquerda";
            else if (dx > 0) direcaoComposta = "BaixoDireita";
            else direcaoComposta = "BaixoDireita"; // padrão
        }

        if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
            dx = -velocidade;
            direcaoComposta = direcaoSimples + "Esquerda";
        }

        if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
            dx = velocidade;
            direcaoComposta = direcaoSimples + "Direita";
        }
    }

    public void keyReleased(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if (codigo == KeyEvent.VK_UP || codigo == KeyEvent.VK_W ||
                codigo == KeyEvent.VK_DOWN || codigo == KeyEvent.VK_S) {
            dy = 0;
        }

        if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A ||
                codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
            dx = 0;
        }

        andando = false;
        imagem = Recursos.imagem("PlayerParado" + direcaoSimples + ".png");
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

    // Fim da Teclas

    public Image getImagem() {
        return imagem;
    }
}