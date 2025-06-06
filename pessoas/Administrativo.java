package pessoas;

import aviao.GerenciadorDeVoos;
import aviao.Voo;
import comunicacao.Comunicavel;
import enums.TipoFuncionario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Administrativo extends Funcionario implements Comunicavel, GerenciadorDeVoos {

    private List<Voo> voosCadastrados = new ArrayList<>();

    public Administrativo(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.ADMINISTRATIVO);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Funcionário Administrativo: " + nome);
    }

    @Override
    public void executarFuncao() {
        // tarefas administrativas
    }

    // Métodos da interface Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        destinatario.receberMensagem("Mensagem de " + nome + ": " + mensagem);
    }

    @Override
    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem recebida por Administrativo " + nome + ": " + mensagem);
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    // Implementação da interface GerenciadorDeVoos
    @Override
    public void cadastrarVoo(Voo voo) {
        voosCadastrados.add(voo);
        System.out.println("Voo cadastrado com sucesso: " + voo);
        // Aqui você pode adicionar código para salvar no arquivo ou registrar na
        // Central
    }

    public List<Voo> getVoosCadastrados() {
        return voosCadastrados;
    }

    public void salvarVoosEmArquivo() {
        File arquivo = new File("dados/voos.csv");
        boolean arquivoExiste = arquivo.exists();
        boolean arquivoVazio = arquivo.length() == 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, true))) { // append = true
            // Escreve cabeçalho apenas se o arquivo estiver vazio
            if (!arquivoExiste || arquivoVazio) {
                writer.println("id,avião,destino,dataSaida,horaSaida,dataChegada,horaChegada,piloto");
            }

            DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

            for (Voo voo : voosCadastrados) {
                String idAviao = voo.getAviao().getIdentificador();
                String modeloAviao = voo.getAviao().getTipo().getModelo();
                String destino = voo.getDestino().name();
                String dataSaida = voo.getHorarioSaida().toLocalDate().format(dataFormat);
                String horaSaida = voo.getHorarioSaida().toLocalTime().format(horaFormat);
                String dataChegada = voo.getHorarioChegada().toLocalDate().format(dataFormat);
                String horaChegada = voo.getHorarioChegada().toLocalTime().format(horaFormat);
                String piloto = voo.getPiloto().getNome();

                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        idAviao,
                        modeloAviao,
                        destino,
                        dataSaida,
                        horaSaida,
                        dataChegada,
                        horaChegada,
                        piloto);
            }

            System.out.println("Voos salvos em 'dados/voos.csv'.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar voos: " + e.getMessage());
        }
    }

    // Funções específicas
    public void gerenciarAvioes() {
        /* ... */ }

    public void alocarTripulacao() {
        /* ... */ }

    public void gerarRelatorios() {
        /* ... */ }

}
