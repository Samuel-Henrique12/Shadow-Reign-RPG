import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Otimiza os Assets do Jogo pro Carregamento no Browser
public class OtimizarAssets {

    static final Path RES = Path.of("src", "main", "resources", "res");

    // Arquivos sem Nenhuma Referencia no Codigo
    static final String[] ORFAOS = {
        "casa.png", "mapa.png",
        "PlayerAndandoBaixo1Antigo.png", "PlayerAndandoBaixo2Antigo.png",
        "PlayerAndandoCima1Antigo.png", "PlayerAndandoCima2Antigo.png",
        "PlayerParadoBaixoAntigo.png", "PlayerParadoCimaAntigo.png"
    };

    // Origem, Destino, Largura Alvo, Altura Alvo (0 = manter) | Qualidade JPEG (0 = Manter PNG)
    record Alvo(String origem, String destino, int largura, int altura, float qualidade) { }

    static final List<Alvo> ALVOS = List.of(
        // Cenarios Opacos -> JPEG. Dimensoes = Tamanho de Desenho Ja Fixado no Codigo
        new Alvo("casa2.png",        "casa2.jpg",       1600, 1536, 0.85f),
        new Alvo("dojo.png",         "dojo.jpg",        1600, 1350, 0.85f),
        new Alvo("Batalha.png",      "Batalha.jpg",     1560,  800, 0.88f),
        // mapa2 e 1024x1536 mas Desenhado 1536x1024: Reamostrar Remove uma Reescala por Frame
        new Alvo("mapa2.png",        "mapa2.jpg",       1536, 1024, 0.85f),
        // Telas Cheias, Desenhadas Escaladas pra Base 1540x845
        new Alvo("creditos1.png",    "creditos1.jpg",   1540,  845, 0.85f),
        new Alvo("criacaoClasse.jpg","criacaoClasse.jpg",1540, 845, 0.82f),
        new Alvo("criacaoNome.jpg",  "criacaoNome.jpg", 1540,  845, 0.82f),
        new Alvo("statsMenu.png",    "statsMenu.png",    750, 1050, 0f),
        new Alvo("statsIcon.png",    "statsIcon.png",    435,  240, 0f)
    );

    public static void main(String[] args) throws Exception {
        boolean aplicar = args.length > 0 && args[0].equals("--aplicar");

        if (!Files.isDirectory(RES)) {
            System.out.println("Rode a partir da raiz do projeto. Nao achei " + RES);
            System.exit(1);
        }

        System.out.println(aplicar ? "=== APLICANDO ===" : "=== ANALISE (nenhum arquivo alterado) ===");
        System.out.println();

        long antes = tamanhoTotal();

        System.out.println("--- ORFAOS ---");
        long liberado = 0;
        for (String nome : ORFAOS) {
            Path p = RES.resolve(nome);
            if (!Files.exists(p)) { System.out.printf("  %-32s (ja removido)%n", nome); continue; }
            long tam = Files.size(p);
            liberado += tam;
            System.out.printf("  %-32s %,10d bytes%s%n", nome, tam, aplicar ? "  APAGADO" : "");
            if (aplicar) Files.delete(p);
        }
        System.out.printf("  %-32s %,10d bytes%n%n", "subtotal", liberado);

        System.out.println("--- IMAGENS ---");
        System.out.printf("  %-20s %-12s %-12s %8s %10s %10s  %s%n",
                "arquivo", "atual", "alvo", "alfa", "antes", "depois", "acao");

        long depoisImagens = 0;
        List<String> avisos = new ArrayList<>();

        for (Alvo alvo : ALVOS) {
            Path origem = RES.resolve(alvo.origem());
            if (!Files.exists(origem)) { avisos.add("ausente: " + alvo.origem()); continue; }

            BufferedImage img = ImageIO.read(origem.toFile());
            long tamAntes = Files.size(origem);

            boolean temAlfa = img.getColorModel().hasAlpha();
            boolean alfaUsado = temAlfa && alfaRealmenteUsado(img);
            boolean viraJpeg = alvo.qualidade() > 0f;

            // Guard de Seguranca: Nunca Converter pra JPEG Algo com Transparencia
            if (viraJpeg && alfaUsado) {
                avisos.add("BLOQUEADO " + alvo.origem()
                        + ": tem transparencia real, converter para JPEG deixaria o fundo preto");
                continue;
            }

            int largAlvo = alvo.largura() == 0 ? img.getWidth() : alvo.largura();
            int altAlvo = alvo.altura() == 0 ? img.getHeight() : alvo.altura();

            String descAlfa = !temAlfa ? "sem" : (alfaUsado ? "USADO" : "opaco");
            String acao = (viraJpeg ? "JPEG q" + (int) (alvo.qualidade() * 100) : "PNG")
                    + (largAlvo != img.getWidth() || altAlvo != img.getHeight() ? " + reamostra" : "");

            byte[] saida = converter(img, largAlvo, altAlvo, alvo.qualidade());
            depoisImagens += saida.length;

            System.out.printf("  %-20s %-12s %-12s %8s %,10d %,10d  %s%n",
                    alvo.origem(),
                    img.getWidth() + "x" + img.getHeight(),
                    largAlvo + "x" + altAlvo,
                    descAlfa, tamAntes, saida.length, acao);

            if (aplicar) {
                Files.write(RES.resolve(alvo.destino()), saida);
                if (!alvo.destino().equals(alvo.origem())) Files.delete(origem);
            }
        }

        if (!avisos.isEmpty()) {
            System.out.println();
            System.out.println("--- AVISOS ---");
            avisos.forEach(a -> System.out.println("  " + a));
        }

        System.out.println();
        if (aplicar) {
            System.out.printf("res/ : %,d -> %,d bytes%n", antes, tamanhoTotal());
        } else {
            long intocado = antes - liberado - somaOrigens();
            System.out.printf("res/ hoje      : %,10d bytes%n", antes);
            System.out.printf("projetado      : %,10d bytes%n", intocado + depoisImagens);
            System.out.println();
            System.out.println("Nada foi alterado. Rode com --aplicar para converter.");
        }
    }

    // Percorre os Pixels Procurando Algum Realmente Transparente
    static boolean alfaRealmenteUsado(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if ((img.getRGB(x, y) >>> 24) != 0xFF) return true;
            }
        }
        return false;
    }

    static byte[] converter(BufferedImage origem, int larg, int alt, float qualidade) throws Exception {
        boolean jpeg = qualidade > 0f;

        BufferedImage destino = new BufferedImage(larg, alt,
                jpeg ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = destino.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(origem, 0, 0, larg, alt, null);
        g.dispose();

        var saida = new java.io.ByteArrayOutputStream();

        if (jpeg) {
            Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpeg");
            ImageWriter escritor = it.next();
            ImageWriteParam param = escritor.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(qualidade);

            try (ImageOutputStream ios = ImageIO.createImageOutputStream(saida)) {
                escritor.setOutput(ios);
                escritor.write(null, new IIOImage(destino, null, null), param);
            }
            escritor.dispose();
        } else {
            ImageIO.write(destino, "png", saida);
        }

        return saida.toByteArray();
    }

    static long tamanhoTotal() throws Exception {
        try (var fluxo = Files.walk(RES)) {
            return fluxo.filter(Files::isRegularFile).mapToLong(p -> {
                try { return Files.size(p); } catch (Exception e) { return 0; }
            }).sum();
        }
    }

    static long somaOrigens() throws Exception {
        long total = 0;
        for (Alvo a : ALVOS) {
            Path p = RES.resolve(a.origem());
            if (Files.exists(p)) total += Files.size(p);
        }
        return total;
    }
}
