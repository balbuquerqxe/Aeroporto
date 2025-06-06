package comunicacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import pessoas.Passageiro;

public class CentralComunicacao {
    public static final List<Comunicavel> usuarios = new ArrayList<>();
    public static JTextArea areaMensagensCompartilhada;
    private static final String ARQUIVO_MENSAGENS = "dados/mensagens.txt";

    static {
        carregarMensagensDoArquivo();
    }

    public static void registrar(Comunicavel c) {
        usuarios.add(c);
    }

    public static List<Comunicavel> getUsuarios() {
        return usuarios;
    }

    public static void enviarMensagem(Comunicavel remetente, Comunicavel destinatario, String mensagem) {
        String formatada = remetente.getNome() + " → " + destinatario.getNome() + ": " + mensagem;

        destinatario.receberMensagem(formatada);
        if (areaMensagensCompartilhada != null) {
            areaMensagensCompartilhada.append(formatada + "\n");
        }
        salvarMensagem(formatada);
    }

    private static void salvarMensagem(String msg) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_MENSAGENS, true))) {
            bw.write(msg);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar mensagem: " + e.getMessage());
        }
    }

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

    public static void salvarMensagem(String remetente, String destinatario, String mensagem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados/mensagens.txt", true))) {
            writer.write("\n" + remetente + " -> " + destinatario + " | Mensagem: " + mensagem);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar mensagem no histórico: " + e.getMessage());
        }
    }

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
