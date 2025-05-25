package telaMenu;

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
        colisoes.add(new Rectangle(10, 50, 1200, 40)); // Parede cima
        colisoes.add(new Rectangle(10, 690, 1200, 40)); // Parede Baixo
        colisoes.add(new Rectangle(1005, 50, 100, 1200)); // Parede Direita
        colisoes.add(new Rectangle(450, 690, 70, 40)); // Porta
        colisoes.add(new Rectangle(670, 170, 260, 10)); // Moveis
        colisoes.add(new Rectangle(630, 460, 90, 5)); // Pia
        colisoes.add(new Rectangle(680, 465, 10, 25)); // Pia baixo
        colisoes.add(new Rectangle(910, 450, 50, 60)); // Privada
        colisoes.add(new Rectangle(850, 500, 50, 20)); // Privada baixo
        colisoes.add(new Rectangle(260, 100, 80, 30)); // Travesseiro Cama
        colisoes.add(new Rectangle(250, 200, 110, 100)); // Cama

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
