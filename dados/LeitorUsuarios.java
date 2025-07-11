package dados;

import java.io.*;
import java.util.*;
import pessoas.Piloto;

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

    public static String[] buscarFuncionarioCompleto(String caminhoArquivo, String matricula) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // pula o cabeçalho
                }

                String[] partes = linha.split(",");
                if (partes.length >= 7 && partes[5].trim().equals(matricula.trim())) {
                    return partes;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
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

    public static List<Piloto> carregarPilotos(String caminhoArquivo) {
        List<Piloto> pilotos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); // pula cabeçalho

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");

                if (partes.length < 7)
                    continue;

                String id = partes[0];
                String nome = partes[1];
                String cpf = partes[2];
                String dataNascimento = partes[3];
                String senha = partes[4];
                String matricula = partes[5];
                String tipo = partes[6];

                if (tipo.equalsIgnoreCase("PILOTO")) {
                    pilotos.add(new Piloto(id, nome, cpf, dataNascimento, senha, matricula));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler pilotos: " + e.getMessage());
        }

        return pilotos;
    }

}