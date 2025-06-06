package pessoas;

import enums.TipoFuncionario;

public abstract class Funcionario extends Pessoa {
    protected String matricula;
    protected TipoFuncionario tipo;

    public Funcionario(String id, String nome, String cpf, String dataNascimento, 
                       String matricula, String senha, TipoFuncionario tipo) {
        super(id, nome, cpf, dataNascimento, senha);
        this.matricula = matricula;
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public TipoFuncionario getTipo() {
        return tipo;
    }

    public abstract void executarFuncao();
}
