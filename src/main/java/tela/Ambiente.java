package tela;

// Detecta Onde o Jogo Esta Rodando
public final class Ambiente {

    private static boolean web = Boolean.parseBoolean(System.getProperty("shadowreign.web", "false"));

    private Ambiente() {
    }

    public static boolean web() {
        return web;
    }

    // Le o Argumento de Linha de Comando
    public static void configurar(String[] args) {
        if (args != null) {
            for (String arg : args) {
                if ("web".equalsIgnoreCase(arg)) {
                    web = true;
                    return;
                }
            }
        }
    }
}
