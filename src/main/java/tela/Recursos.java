package tela;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// Classe Utilitária para Carregar Recursos (Imagens e Fontes) do ClassPath
public final class Recursos {

    private static final Map<String, Image> CACHE = new HashMap<>();
    private static Font fonteBase;

    private Recursos() {
    }

    // Carrega Imagem do Classpath Com Cache
    public static Image imagem(String nome) {
        Image cacheada = CACHE.get(nome);
        if (cacheada != null) {
            return cacheada;
        }

        URL url = Recursos.class.getResource("/res/" + nome);
        if (url == null) {
            throw new IllegalStateException("Recurso ausente no classpath: /res/" + nome);
        }

        try {
            BufferedImage lida = ImageIO.read(url);
            CACHE.put(nome, lida);
            return lida;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao ler /res/" + nome, e);
        }
    }

    // Registra a Fonte Uma Unica Vez e Deriva o Tamanho
    public static Font fonte(float tamanho) {
        if (fonteBase == null) {
            try (InputStream is = Recursos.class
                    .getResourceAsStream("/res/Press_Start_2P/PressStart2P-Regular.ttf")) {

                if (is == null) {
                    throw new IllegalStateException("Fonte ausente no classpath");
                }

                fonteBase = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonteBase);

            } catch (Exception e) {
                // Monospaced e Garantido pela JVM; Arial nao existe no CheerpJ
                fonteBase = new Font(Font.MONOSPACED, Font.PLAIN, 18);
            }
        }

        return fonteBase.deriveFont(tamanho);
    }

    // Pre-Carrega os Sprites do Player Para Evitar I/O no Loop de Animacao
    public static void preCarregarPlayer() {
        String[] paradas = { "Baixo", "Cima" };
        String[] caminhadas = {
                "BaixoDireita", "BaixoEsquerda", "CimaDireita", "CimaEsquerda"
        };

        for (String direcao : paradas) {
            imagem("PlayerParado" + direcao + ".png");
        }
        for (String direcao : caminhadas) {
            imagem("PlayerAndando" + direcao + ".png");
        }
    }
}
