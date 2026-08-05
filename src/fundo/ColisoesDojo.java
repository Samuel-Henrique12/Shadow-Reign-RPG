package fundo;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ColisoesDojo extends ColisoesCasa {

    private final List<Rectangle> colisoesDojo;

    public ColisoesDojo() {
        colisoesDojo = new ArrayList<>();

        // Colisoes
        //X para os lados                               MINIMO PRA CIMA == Y 45          MAXIMO PRA BAIXO == Y 593
        //Y para cima                                   MINIMO PRO LADO == X 248          MAXIMO PRO LADO == X 1250
        //Width para <>
        //height para aumentar cima e baixo

        colisoesDojo.add(new Rectangle(248, 125, 100, 1000)); // Parede esquerda
        colisoesDojo.add(new Rectangle(1260, 125, 10, 1000)); // Parede Direita
        colisoesDojo.add(new Rectangle(248, 593, 1000, 10)); // Parede Baixo
        colisoesDojo.add(new Rectangle(248, 45, 1000, 10)); // Parede Cima
        colisoesDojo.add(new Rectangle(255, 60, 100, 10)); // Quina Cima Esquerda
        colisoesDojo.add(new Rectangle(1258, 55, 100, 15)); // Quina Cima Direita
        colisoesDojo.add(new Rectangle(780, 593, 45, 10)); // Porta Entrada
        colisoesDojo.add(new Rectangle(780, 45, 45, 10)); // Porta Saida
        colisoesDojo.add(new Rectangle(470, 243, 30, 1)); // Barril Esquerda
        colisoesDojo.add(new Rectangle(1100, 243, 35, 1)); // Barril Esquerda
        colisoesDojo.add(new Rectangle(550, 543, 40, 10)); // Mesa Entrada Esquerda
        colisoesDojo.add(new Rectangle(1010, 543, 40, 10)); // Mesa Entrada Direita
        colisoesDojo.add(new Rectangle(775, 144, 67, 8)); // Mago
    }

    @Override
    public boolean temColisao(Rectangle area) {
        for (Rectangle colisao : colisoesDojo) {
            if (colisao.intersects(area)) {
                return true;
            }
        }
        return false;
    }
}
