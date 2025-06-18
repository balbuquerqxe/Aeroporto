package pessoas;

import comunicacao.Comunicavel;
import enums.ClassePassagem;

public class Passageiro extends Pessoa implements Comunicavel {
    private ClassePassagem classePassagem;

    public Passageiro(String id, String nome, String cpf, String dataNascimento, String senha,
            ClassePassagem classePassagem) {
        super(id, nome, cpf, dataNascimento, senha);
        this.classePassagem = classePassagem;
    }

    @Override
    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem para Passageiro " + nome + ": " + mensagem);
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    public String getCPF() {
        return this.cpf;
    }

    public String getId() {
        return this.id;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public String getSenha() {
        return this.senha;
    }

    public ClassePassagem getClassePassagem() { 
        return classePassagem; 
    }

    // Métodos específicos:
    public void consultarVoos() {
        /* ... */ }

    public void reservarPoltrona() {
        /* ... */ }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        destinatario.receberMensagem("Mensagem de " + nome + ": " + mensagem);
    }

}
