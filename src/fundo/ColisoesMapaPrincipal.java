package fundo;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ColisoesMapaPrincipal {

    public static ArrayList<Rectangle> carregarColisoes() {
        ArrayList<Rectangle> colisoes = new ArrayList<>();

        // Colisoes
        //X para os lados                               MAXIMO PRA CIMA == Y 50          MAXIMO PRA BAIXO == Y 690
        //Y para cima                                   MAXIMO PRO LADO == X 10          MAXIMO PRO LADO == X 1530
        //Width para <>
        //height para aumentar cima e baixo
        colisoes.add(new Rectangle(1530,50,1200,1200)); // Parede Direita
        colisoes.add(new Rectangle(10,1015,1600,10)); // Parede Baixo
        colisoes.add(new Rectangle(239,590,170,10)); // Lagoa
        colisoes.add(new Rectangle(203,606,235,10)); // Lagoa
        colisoes.add(new Rectangle(175,620,320,10)); // Lagoa
        colisoes.add(new Rectangle(145,638,400,10)); // Lagoa
        colisoes.add(new Rectangle(125,648,445,10)); // Lagoa
        colisoes.add(new Rectangle(135,658,460,10)); // Lagoa
        colisoes.add(new Rectangle(100,668,460,10)); // Lagoa
        colisoes.add(new Rectangle(145,688,400,1)); // Lagoa
        colisoes.add(new Rectangle(170,698,340,1)); // Lagoa
        colisoes.add(new Rectangle(195,709,300,1)); // Lagoa
        colisoes.add(new Rectangle(255,729,190,1)); // Lagoa
        colisoes.add(new Rectangle(155,89,365,10)); // Casa
        colisoes.add(new Rectangle(140,120,400,10)); // Casa
        colisoes.add(new Rectangle(120,140,445,50)); // Casa
        colisoes.add(new Rectangle(1120,100,269,90)); // Alvo
        colisoes.add(new Rectangle(740,210,112,10)); // Arvore
        colisoes.add(new Rectangle(710,230,170,10)); // Arvore
        colisoes.add(new Rectangle(685,237,230,10)); // Arvore
        colisoes.add(new Rectangle(660,249,280,10)); // Arvore
        colisoes.add(new Rectangle(635,269,335,10)); // Arvore
        colisoes.add(new Rectangle(699,279,205,10)); // Arvore
        colisoes.add(new Rectangle(739,319,100,10)); // Arvore
        colisoes.add(new Rectangle(709,390,160,1)); // Arvore
        colisoes.add(new Rectangle(1250,770,90,10)); // Arvore2
        colisoes.add(new Rectangle(1205,785,190,10)); // Arvore2
        colisoes.add(new Rectangle(1175,797,217,10)); // Arvore2
        colisoes.add(new Rectangle(1133,809,280,10)); // Arvore2
        colisoes.add(new Rectangle(1145,829,295,5)); // Arvore2
        colisoes.add(new Rectangle(1240,855,95,30)); // Arvore2
        colisoes.add(new Rectangle(1025,460,370,30)); // Dojo
        colisoes.add(new Rectangle(1005,495,405,30)); // Dojo
        colisoes.add(new Rectangle(975,515,455,105)); // Dojo
        colisoes.add(new Rectangle(807,400,75,10)); // Barbaro (Corpo)
        colisoes.add(new Rectangle(827,440,40,25)); // Barbaro (Pés)
        colisoes.add(new Rectangle(1100,210,70,30)); // Arqueiro (Corpo)
        colisoes.add(new Rectangle(1110,280,30,20)); // Arqueiro (Pés)

        return colisoes;
    }
}