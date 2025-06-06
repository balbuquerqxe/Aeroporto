package dados;

import java.io.*;
import java.util.*;

public class LeitorUsuarios {

    public static Map<String, String> carregarUsuarios(String caminhoArquivo) {
        Map<String, String> usuarios = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    primeira = false; // pula o cabeçalho
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    String usuario = partes[0].trim();
                    String senha = partes[1].trim();
                    usuarios.put(usuario, senha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return usuarios;
    }
}
