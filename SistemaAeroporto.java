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
        carregarFuncionarios();
        carregarPassageiros();

        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }

    private static void carregarFuncionarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados/funcionarios.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 7) {
                    String id = partes[0];
                    String nome = partes[1];
                    String cpf = partes[2];
                    String nascimento = partes[3];
                    String matricula = partes[4];
                    String senha = partes[5];
                    TipoFuncionario tipo = TipoFuncionario.valueOf(partes[6]);

                    if (tipo == TipoFuncionario.ADMINISTRATIVO) {
                        Administrativo adm = new Administrativo(id, nome, cpf, nascimento, senha, matricula);
                        CentralComunicacao.registrar(adm);
                    }
                    // Adicione aqui os outros tipos de funcionários, se necessário
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários: " + e.getMessage());
        }
    }

    private static void carregarPassageiros() {
        try (BufferedReader br = new BufferedReader(new FileReader("dados/passageiros.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 6) {
                    String id = partes[0];
                    String nome = partes[1];
                    String cpf = partes[2];
                    String nascimento = partes[3];
                    String senha = partes[4];
                    ClassePassagem classe = ClassePassagem.valueOf(partes[5]);

                    Passageiro p = new Passageiro(id, nome, cpf, nascimento, senha, classe);
                    CentralComunicacao.registrar(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar passageiros: " + e.getMessage());
        }
    }
}