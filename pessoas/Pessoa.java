package pessoas;

// Pessoa.java
public abstract class Pessoa {
    protected String id;
    protected String nome;
    protected String cpf;
    protected String dataNascimento;
    protected String senha;

    public Pessoa(String id, String nome, String cpf, String dataNascimento, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    public abstract void exibirInformacoes();

    // getters e setters podem ser adicionados conforme necessidade
}
