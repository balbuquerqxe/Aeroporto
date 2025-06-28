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

    private List<Voo> voosCadastrados = new ArrayList<>();

    public Administrativo(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.ADMINISTRATIVO);
    }

    @Override
    public void executarFuncao() {
    }

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

    @Override
    public void cadastrarVoo(Voo voo) {
        voosCadastrados.add(voo);
        System.out.println("Voo cadastrado com sucesso: " + voo);
    }

    public List<Voo> getVoosCadastrados() {
        return voosCadastrados;
    }

    public boolean salvarVoosEmArquivo() {
        File arquivo = new File("dados/voos.csv");
        boolean arquivoExiste = arquivo.exists();
        boolean arquivoVazio = arquivo.length() == 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, true))) {
            if (!arquivoExiste || arquivoVazio) {
                writer.println("id,avião,destino,dataSaida,horaSaida,dataChegada,horaChegada,piloto");
            }

            DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

            for (Voo voo : voosCadastrados) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        voo.getAviao().getIdentificador(),
                        voo.getAviao().getTipo().getModelo(),
                        voo.getDestino().name(),
                        voo.getHorarioSaida().toLocalDate().format(dataFormat),
                        voo.getHorarioSaida().toLocalTime().format(horaFormat),
                        voo.getHorarioChegada().toLocalDate().format(dataFormat),
                        voo.getHorarioChegada().toLocalTime().format(horaFormat),
                        voo.getPiloto().getNome());
            }

            System.out.println("Voos salvos em 'dados/voos.csv'.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar voos: " + e.getMessage());
            return false;
        }
    }
    
    public String gerarConteudoRelatorio(List<Voo> voosParaRelatorio) {
        if (voosParaRelatorio == null || voosParaRelatorio.isEmpty()) {
            return "Nenhum voo selecionado para o relatório.";
        }

        StringBuilder relatorio = new StringBuilder();
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

        relatorio.append("======================================================================\n");
        relatorio.append("                 RELATÓRIO DE VOOS\n");
        relatorio.append("======================================================================\n\n");
        relatorio.append("Gerado por: ").append(this.getNome()).append("\n");
        relatorio.append("Data de Geração: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        relatorio.append("Total de Voos no Relatório: ").append(voosParaRelatorio.size()).append("\n\n");
        relatorio.append("----------------------------------------------------------------------\n");

        for (Voo voo : voosParaRelatorio) {
            relatorio.append("ID DO VOO:         ").append(voo.getId()).append("\n");
            relatorio.append("DESTINO:           ").append(voo.getDestino().getNome()).append("\n");
            relatorio.append("PILOTO:            ").append(voo.getPiloto().getNome()).append("\n\n");
            
            relatorio.append("AERONAVE:\n");
            relatorio.append("  - Modelo:        ").append(voo.getAviao().getTipo().getModelo()).append("\n");
            relatorio.append("  - Identificador: ").append(voo.getAviao().getIdentificador()).append("\n\n");

            relatorio.append("HORÁRIOS:\n");
            relatorio.append("  - Data de Saída: ").append(voo.getHorarioSaida().format(dataFormat)).append("\n");
            relatorio.append("  - Hora de Saída: ").append(voo.getHorarioSaida().format(horaFormat)).append("\n");
            relatorio.append("  - Data de Chegada: ").append(voo.getHorarioChegada().format(dataFormat)).append("\n");
            relatorio.append("  - Hora de Chegada: ").append(voo.getHorarioChegada().format(horaFormat)).append("\n\n");
            
            relatorio.append("----------------------------------------------------------------------\n");
        }
        
        relatorio.append("\nFIM DO RELATÓRIO.\n");

        return relatorio.toString();
    }

    public boolean salvarRelatorioEmArquivo(String conteudo) {
        String nomeArquivo = "RelatorioVoos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("dados/" + nomeArquivo))) {
            writer.print(conteudo);
            System.out.println("Arquivo de relatório salvo em: dados/" + nomeArquivo);
            return true;
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao salvar o relatório em arquivo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void gerenciarAvioes() { /* ... */ }
    public void alocarTripulacao() { /* ... */ }
}