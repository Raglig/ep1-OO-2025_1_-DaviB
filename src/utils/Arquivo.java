    package utils;

    import java.io.*;
    import java.util.ArrayList;
    import java.util.List;

    public class Arquivo {

        public static boolean existeArquivo(String caminho) {
            File file = new File(caminho);
            return file.exists() && file.isFile();
        }

        public static List<String> lerLinhas(String caminho) throws IOException {
            List<String> linhas = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    linhas.add(linha);
                }
            }
            return linhas;
        }

        public static void escreverLinhas(String caminho, List<String> linhas) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
                for (String linha : linhas) {
                    writer.write(linha);
                    writer.newLine();
                }
            }
        }
    }
