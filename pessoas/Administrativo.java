package pessoas;

import aviao.GerenciadorDeVoos;
import aviao.Voo;
import comunicacao.Comunicavel;
import enums.TipoFuncionario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Administrativo extends Funcionario implements Comunicavel, GerenciadorDeVoos {

    // Lista de voos cadastrados pelo administrativo.
    private List<Voo> voosCadastrados = new ArrayList<>();

    // Inicialização do funcionário administrativo.
    public Administrativo(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.ADMINISTRATIVO);
    }

    @Override
    public void executarFuncao() {
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

public String gerarConteudoRelatorio(List<Voo> voosParaRelatorio) {
    if (voosParaRelatorio == null || voosParaRelatorio.isEmpty()) {
        return null; // Retorna nulo se a lista estiver vazia.
    }

    // A lógica do StringBuilder é a mesma de antes.
    StringBuilder relatorioConteudo = new StringBuilder();
    relatorioConteudo.append("=========================================================\n");
    relatorioConteudo.append("              RELATÓRIO DE VOOS SOLICITADOS\n");
    relatorioConteudo.append("=========================================================\n");
    relatorioConteudo.append("Gerado em: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
    relatorioConteudo.append("Total de voos no relatório: ").append(voosParaRelatorio.size()).append("\n\n");

    for (Voo voo : voosParaRelatorio) {
        relatorioConteudo.append("---------------------------------------------------------\n");
        relatorioConteudo.append(">>> DADOS DO VOO: ").append(voo.getId()).append("\n");
        // ... (resto da sua lógica de formatação do voo) ...
        relatorioConteudo.append("  Piloto: ").append(voo.getPiloto().getNome()).append("\n\n");
    }

    return relatorioConteudo.toString();
}

/**
 * MÉTODO 2: O "Salvador".
 * Apenas recebe um conteúdo de texto e o SALVA em um arquivo.
 * Não sabe como o conteúdo foi criado.
 * @param conteudo O texto do relatório a ser salvo.
 * @return true se salvou com sucesso, false se ocorreu um erro.
 */
public boolean salvarRelatorioEmArquivo(String conteudo) {
    String nomeArquivo = "RelatorioVoos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";
    
    try (PrintWriter writer = new PrintWriter(new FileWriter("dados/" + nomeArquivo))) {
        writer.print(conteudo);
        System.out.println("Arquivo de relatório salvo em: dados/" + nomeArquivo);
        return true; // Sucesso!
    } catch (IOException e) {
        System.err.println("Ocorreu um erro ao salvar o relatório em arquivo: " + e.getMessage());
        e.printStackTrace();
        return false; // Falha!
    }
}
}
