package fundo;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ColisoesCasa {

    private ArrayList<Rectangle> colisoes;

    public ColisoesCasa() {
        colisoes = new ArrayList<>();


        // Colisoes
        //X para os lados                               MAXIMO PRA CIMA == Y 50          MAXIMO PRA BAIXO == Y 690
        //Y para cima                                   MAXIMO PRO LADO == X 10          MAXIMO PRO LADO == X 1005
        //Width para <>
        //height para aumentar cima e baixo
        colisoes.add(new Rectangle(10, -70, 1200, 40)); // Parede cima
        colisoes.add(new Rectangle(645, -50, 100, 2000)); // Parede Lado Direito
        colisoes.add(new Rectangle(10, -22, 60, 30)); // Lareira
        colisoes.add(new Rectangle(230, -24, 500, 40)); // Armario
        colisoes.add(new Rectangle(185, -42, 15, 60)); // Quina Armario
        colisoes.add(new Rectangle(534, 25, 300, 90)); // Baldes Superior Direita
        colisoes.add(new Rectangle(400, 246, 62, 1)); // Cadeira Banheiro
        colisoes.add(new Rectangle(447, 412, 150, 1)); // Banheira
        colisoes.add(new Rectangle(337, 187, 11, 500)); // Divisao banheiro
        colisoes.add(new Rectangle(297, 500, 1200, 300)); // Barreiras da Porta
        colisoes.add(new Rectangle(20, 500, 100, 300)); // Barreiras da Porta
        colisoes.add(new Rectangle(150, 514, 1200, 300)); // Porta
        colisoes.add(new Rectangle(1, 275, 50, 87)); // Cama
        colisoes.add(new Rectangle(-1, 245, 147, 25)); // Divisão Cama
        colisoes.add(new Rectangle(-5, 115, 75, 15)); // Mesa
    }

    public ArrayList<Rectangle> getColisoes() {
        return colisoes;
    }

    public boolean temColisao(Rectangle jogador) {
        for (Rectangle r : colisoes) {
            if (jogador.intersects(r)) return true;
        }
        return false;
    }
}
