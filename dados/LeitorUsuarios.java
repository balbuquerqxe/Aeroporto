package dados;

import java.io.*;
import java.util.*;

public class LeitorUsuarios {

    public static Map<String, String> carregarMatriculasESenhas(String caminhoArquivo) {
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

                if (partes.length >= 7) {
                    String senha = partes[4].trim(); // 5ª coluna: senha
                    String matricula = partes[5].trim(); // 6ª coluna: matrícula
                    usuarios.put(matricula, senha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return usuarios;
    }

    public static Map<String, String> carregarCpfsESenhas(String caminhoArquivo) {
        Map<String, String> passageiros = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(",");

                if (partes.length >= 6) {
                    String cpf = partes[2].trim(); // 3ª coluna
                    String senha = partes[4].trim(); // 5ª coluna
                    passageiros.put(cpf, senha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler passageiros.csv: " + e.getMessage());
        }

        return passageiros;
    }

    public static String[] buscarFuncionarioCompleto(String caminhoArquivo, String matriculaBuscada) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula o cabeçalho
                    continue;
                }

                String[] partes = linha.split(",");

                if (partes.length >= 7) {
                    String matricula = partes[5].trim(); // 6ª coluna
                    if (matricula.equals(matriculaBuscada)) {
                        return partes;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar funcionário completo: " + e.getMessage());
        }

        return null;
    }

    public static String buscarNomePorMatricula(String caminhoArquivo, String matricula) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    primeira = false;
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length >= 6 && partes[5].trim().equals(matricula)) {
                    return partes[1].trim(); // nome do funcionário (coluna 2)
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar nome do funcionário: " + e.getMessage());
        }

        return "Funcionário";
    }

}