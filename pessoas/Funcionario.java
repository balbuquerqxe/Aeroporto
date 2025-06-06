package pessoas;

// Funcionario.java
public abstract class Funcionario extends Pessoa {
    protected String matricula;

    public Funcionario(String id, String nome, String cpf, String dataNascimento, 
     String matricula, String senha) {
        super(id, nome, cpf, dataNascimento, senha);
        this.matricula = matricula;
    }

    public abstract void executarFuncao();
}
