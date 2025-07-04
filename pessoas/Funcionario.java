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

    public String getId() { 
        return this.id; 
    }

    public String getNome() { 
        return this.nome; 
    }
    public String getCpf() { 
        return this.cpf; 
    }
    public String getDataNascimento() { 
        return this.dataNascimento; 
    }
    public String getSenha() { 
        return this.senha; 
    }
    public String getMatricula() {
        return matricula;
    }

    public TipoFuncionario getTipo() {
        return tipo;
    }

    public abstract void executarFuncao();
}
