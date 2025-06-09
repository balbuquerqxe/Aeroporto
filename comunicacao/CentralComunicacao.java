package comunicacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import pessoas.Passageiro;

public class CentralComunicacao {
    // Lista dos usuários comunicáveis.
    public static final List<Comunicavel> usuarios = new ArrayList<>();
    // Área das mensagens complartilhadas (aparece na parte gráfica).
    public static JTextArea areaMensagensCompartilhada;
    // Arquivo com todas as mensagens.
    private static final String ARQUIVO_MENSAGENS = "dados/mensagens.txt";

    // Carrega o arquivo CSV com as mensagens.
    static {
        carregarMensagensDoArquivo();
    }

    // Registra o usuário como comunicável.
    public static void registrar(Comunicavel c) {
        usuarios.add(c);
    }


    // Envia mensagem.
    public static void enviarMensagem(Comunicavel remetente, Comunicavel destinatario, String mensagem) {
        // Formata a mensagem.
        String formatada = remetente.getNome() + " → " + destinatario.getNome() + ": " + mensagem;

        // Quem irá receber a mensagem formatada.
        destinatario.receberMensagem(formatada);
        if (areaMensagensCompartilhada != null) {
            // Registra a mensagem na área.
            areaMensagensCompartilhada.append(formatada + "\n");
        }
        // Salva a mensagem.
        salvarMensagem(formatada);
    }

    // Salva a mensagem na tela.
    private static void salvarMensagem(String msg) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_MENSAGENS, true))) {
            bw.write(msg);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar mensagem: " + e.getMessage());
        }
    }

    // Carrega mensagem do arquivo CSV.
    private static void carregarMensagensDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_MENSAGENS))) {
            String linha;
            if (areaMensagensCompartilhada == null)
                areaMensagensCompartilhada = new JTextArea();
            while ((linha = br.readLine()) != null) {
                areaMensagensCompartilhada.append(linha + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar mensagens anteriores.");
        }
    }

    // Salva a mensagem no arquivo txt.
    public static void salvarMensagem(String remetente, String destinatario, String mensagem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados/mensagens.txt", true))) {
            writer.write("\n" + remetente + " -> " + destinatario + " | Mensagem: " + mensagem);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar mensagem no histórico: " + e.getMessage());
        }
    }

    // (NÃO SEI SE DEVIA DEIXAR ISSO AQUI OU DAR UM JEITO DE FICAR NO LEITOR USUARIO)
    public static Passageiro getPassageiroPorCpf(String cpf) {
        for (Comunicavel c : usuarios) {
            if (c instanceof Passageiro) {
                Passageiro p = (Passageiro) c;
                if (p.getCPF().equals(cpf)) {
                    return p;
                }
            }
        }
        return null;
    }

}
