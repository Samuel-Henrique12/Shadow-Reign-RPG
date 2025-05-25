package fundo;

import java.awt.Rectangle;
import java.util.ArrayList;

public class ColisoesMapaPrincipal {

    public static ArrayList<Rectangle> carregarColisoes() {
        ArrayList<Rectangle> colisoes = new ArrayList<>();

        //Casa Protagonista
        for (int x = 70; x <= 300; x += 47) {
            for (int y = 10; y <= 200; y += 50) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        //Grama Casa Protagonista
        for (int x = 300; x <= 400; x += 47) {
            for (int y = 10; y <= 110; y += 44) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        for (int x= 380; x <= 380; x+= 47) {
            for (int y = 180; y <= 180; y += 44) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        for (int x= 180; x <= 249; x+= 100) {
            for (int y = 200; y <= 200; y += 44) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        for (int x= 10; x <= 20; x+= 100) {
            for (int y = 200; y <= 200; y += 44) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        for (int x= 370; x <= 370; x+= 100) {
            for (int y = 200; y <= 200; y += 44) {
                colisoes.add(new Rectangle(x, y, 50, 50));
            }
        }
        // Arvore
        for (int x= 1030; x <= 1100; x ++) {
            for (int y = 280; y == 280; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1030; x <= 1100; x ++) {
            for (int y = 260; y == 260; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1030; x <= 1120; x ++) {
            for (int y = 220; y == 220; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1000; x <= 1150; x ++) {
            for (int y = 200; y == 200; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 980; x <= 1170; x ++) {
            for (int y = 180; y == 180; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 980; x <= 1150; x ++) {
            for (int y = 160; y == 160; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 965; x <= 1150; x ++) {
            for (int y = 150; y == 150; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 950; x <= 1150; x ++) {
            for (int y = 130; y == 130; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 980; x <= 1120; x ++) {
            for (int y = 120; y == 120; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1000; x <= 1120; x ++) {
            for (int y = 80; y == 80; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 980; x <= 1120; x ++) {
            for (int y = 280; y == 280; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 950; x <= 1150; x ++) {
            for (int y = 300; y == 300; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        //Campo de Treino
        for (int x= 1550; x <= 1620; x ++) {
            for (int y = 80; y <= 100; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1550; x == 1550; x ++) {
            for (int y = 80; y <= 215; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1880; x == 1880; x ++) {
            for (int y = 80; y <= 115; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        //Lagoa
        for (int x= 290; x <= 1070; x ++) {
            for (int y = 1000; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 270; x <= 1090; x ++) {
            for (int y = 1030; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 250; x <= 1120; x ++) {
            for (int y = 1050; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 290; x <= 1070; x ++) {
            for (int y = 985; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 330; x <= 1050; x ++) {
            for (int y = 960; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 380; x <= 970; x ++) {
            for (int y = 940; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 430; x <= 950; x ++) {
            for (int y = 900; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 480; x <= 870; x ++) {
            for (int y = 880; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 520; x <= 850; x ++) {
            for (int y = 860; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 600; x <= 800; x ++) {
            for (int y = 840; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        // DOJO
        for (int x= 1770; x == 1770; x ++) {
            for (int y = 660; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1500; x <= 1770; x ++) {
            for (int y = 660; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }
        for (int x= 1380; x <= 1770; x ++) {
            for (int y = 690; y <= 1080; y ++) {
                colisoes.add(new Rectangle(x, y, 1, 1));
            }
        }

        return colisoes;
    }
}