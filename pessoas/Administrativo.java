package pessoas;

import enums.TipoFuncionario;

public class Administrativo extends Funcionario {

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

    // Funções específicas
    public void cadastrarVoo() { /* ... */ }
    public void gerenciarAvioes() { /* ... */ }
    public void alocarTripulacao() { /* ... */ }
    public void gerarRelatorios() { /* ... */ }
    public void responderPerguntas() { /* ... */ }
}