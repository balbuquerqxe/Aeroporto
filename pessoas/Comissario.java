package pessoas;

// Comissario.java
public class Comissario extends Funcionario {

    public Comissario(String id, String nome, String cpf, String dataNascimento, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Comissário: " + nome);
    }

    @Override
    public void executarFuncao() {
        verEscala();
    }

    public void verEscala() { /* lógica de consulta da equipe/voo */ }
}
