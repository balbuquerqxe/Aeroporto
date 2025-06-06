package pessoas;

// Piloto.java
public class Piloto extends Funcionario {

    public Piloto(String id, String nome, String cpf, String dataNascimento, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Piloto: " + nome);
    }

    @Override
    public void executarFuncao() {
        verEscala();
    }

    public void verEscala() { /* lógica de consulta do próximo voo */ }
}
