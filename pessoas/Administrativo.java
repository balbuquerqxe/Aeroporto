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

    public void gerarRelatorios() {
        if (voosCadastrados.isEmpty()) {
        System.out.println("Nenhum voo cadastrado. Não é possível gerar relatórios.");
        return; 
    } 

 // 2. Constrói o conteúdo do relatório com base na lista recebida.
    StringBuilder relatorioConteudo = new StringBuilder();
    relatorioConteudo.append("=========================================================\n");
    relatorioConteudo.append("              RELATÓRIO DE VOOS SOLICITADOS\n");
    relatorioConteudo.append("=========================================================\n");
    relatorioConteudo.append("Gerado em: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
    relatorioConteudo.append("Total de voos no relatório: ").append(voosParaRelatorio.size()).append("\n\n");

    // 3. Itera sobre a lista de voos que foi recebida como parâmetro.
    for (Voo voo : voosParaRelatorio) {
        relatorioConteudo.append("---------------------------------------------------------\n");
        relatorioConteudo.append(">>> DADOS DO VOO: ").append(voo.getId()).append("\n");
        relatorioConteudo.append("---------------------------------------------------------\n");
        relatorioConteudo.append("  Destino: ").append(voo.getDestino()).append("\n");
        relatorioConteudo.append("  Partida: ").append(voo.getHorarioSaida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"))).append("\n");
        relatorioConteudo.append("  Chegada: ").append(voo.getHorarioChegada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"))).append("\n");
        relatorioConteudo.append("  Aeronave: ").append(voo.getAviao().getTipo().getModelo()).append(" (ID: ").append(voo.getAviao().getIdentificador()).append(")\n");
        relatorioConteudo.append("  Piloto: ").append(voo.getPiloto().getNome()).append("\n\n");
    }

    // 4. Salva o relatório em um arquivo.
    String nomeArquivo = "RelatorioVoos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";
    
    try (PrintWriter writer = new PrintWriter(new FileWriter("dados/" + nomeArquivo))) {
        writer.print(relatorioConteudo.toString());
        System.out.println("\nRelatório salvo com sucesso no arquivo: dados/" + nomeArquivo);
    } catch (IOException e) {
        System.err.println("\nOcorreu um erro ao salvar o relatório: " + e.getMessage());
        e.printStackTrace(); 
    }
    }
}
