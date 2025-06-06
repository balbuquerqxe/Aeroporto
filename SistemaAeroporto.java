import comunicacao.CentralComunicacao;
import enums.ClassePassagem;
import enums.TipoFuncionario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import pessoas.Administrativo;
import pessoas.Passageiro;
import visual.TelaInicial;

public class SistemaAeroporto {

    public static void main(String[] args) {
        inicializarChatCompartilhado();
        carregarMensagensIniciais();  
        carregarFuncionarios();
        carregarPassageiros();

        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }

    private static void carregarFuncionarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados/funcionarios.csv"))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pula o cabeçalho
                }

                String[] partes = linha.split(",");
                if (partes.length >= 7) {
                    String id = partes[0].trim();
                    String nome = partes[1].trim();
                    String cpf = partes[2].trim();
                    String nascimento = partes[3].trim();
                    String senha = partes[4].trim();
                    String matricula = partes[5].trim();
                    String tipoStr = partes[6].trim().toUpperCase();

                    try {
                        TipoFuncionario tipo = TipoFuncionario.valueOf(tipoStr);

                        if (tipo == TipoFuncionario.ADMINISTRATIVO) {
                            Administrativo adm = new Administrativo(id, nome, cpf, nascimento, senha, matricula);
                            CentralComunicacao.registrar(adm);
                        }

                        // Adicione aqui outros tipos (ex: PILOTO, COMISSARIO), se necessário

                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de funcionário inválido: " + tipoStr);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários: " + e.getMessage());
        }
    }

    private static void carregarPassageiros() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados/passageiros.csv"))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // pula o cabeçalho
                }

                String[] partes = linha.split(",");
                if (partes.length >= 6) {
                    String id = partes[0].trim();
                    String nome = partes[1].trim();
                    String cpf = partes[2].trim();
                    String nascimento = partes[3].trim();
                    String senha = partes[4].trim();
                    String classeStr = partes[5].trim().toUpperCase();

                    try {
                        ClassePassagem classe = ClassePassagem.valueOf(classeStr);
                        Passageiro p = new Passageiro(id, nome, cpf, nascimento, senha, classe);
                        CentralComunicacao.registrar(p);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Classe de passagem inválida: " + classeStr);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar passageiros: " + e.getMessage());
        }
    }

    private static void inicializarChatCompartilhado() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        CentralComunicacao.areaMensagensCompartilhada = area;
    }

    private static void carregarMensagensIniciais() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados/mensagens.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                CentralComunicacao.areaMensagensCompartilhada.append(linha + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar mensagens iniciais: " + e.getMessage());
        }
    }

}