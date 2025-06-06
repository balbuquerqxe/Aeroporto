package pessoas;

import comunicacao.Comunicavel;
import enums.TipoFuncionario;

public class Administrativo extends Funcionario implements Comunicavel {

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

    // Funções específicas
    public void cadastrarVoo() {
        /* ... */ }

    public void gerenciarAvioes() {
        /* ... */ }

    public void alocarTripulacao() {
        /* ... */ }

    public void gerarRelatorios() {
        /* ... */ }

    public void responderPerguntas() {
        /* ... */ }
}
