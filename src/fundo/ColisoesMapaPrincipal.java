package fundo;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ColisoesMapaPrincipal {

    public static ArrayList<Rectangle> carregarColisoes() {
        ArrayList<Rectangle> colisoes = new ArrayList<>();

        // Colisoes
        //X para os lados                               MAXIMO PRA CIMA == Y 50          MAXIMO PRA BAIXO == Y 690
        //Y para cima                                   MAXIMO PRO LADO == X 10          MAXIMO PRO LADO == X 1005
        //Width para <>
        //height para aumentar cima e baixo
        colisoes.add(new Rectangle(1000,100,1200,1200));

        return colisoes;
    }
}