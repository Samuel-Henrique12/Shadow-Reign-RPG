package telaMenu;
import java.awt.*;
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
        colisoes.add(new Rectangle(10, -50, 1200, 40)); // Parede cima
        colisoes.add(new Rectangle(650, -50, 100, 2000)); // Parede Lado Direito
        colisoes.add(new Rectangle(10, -15, 70, 30)); // Lareira
        colisoes.add(new Rectangle(200, -15, 1200, 40)); // Armario
        colisoes.add(new Rectangle(540, 25, 300, 100)); // Baldes Superior Direita
        colisoes.add(new Rectangle(400, 260, 300, 1)); // Baldes Banheiro
        colisoes.add(new Rectangle(350, 200, 10, 300)); // Divisao banheiro
        colisoes.add(new Rectangle(310, 512, 1200, 300)); // Barreiras da Porta
        colisoes.add(new Rectangle(20, 512, 100, 300)); // Barreiras da Porta
        colisoes.add(new Rectangle(150, 525, 1200, 300)); // Porta
        colisoes.add(new Rectangle(-5, 275, 50, 95)); // Cama
        colisoes.add(new Rectangle(-5, 255, 147, 25)); // Divisão Cama
        colisoes.add(new Rectangle(-5, 127, 90, 25)); // Mesa
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
