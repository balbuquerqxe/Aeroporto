package pessoas;

import enums.ClassePassagem;

// Passageiro.java
public class Passageiro extends Pessoa {
    private ClassePassagem classePassagem;

    public Passageiro(String id, String nome, String cpf, String dataNascimento, ClassePassagem classePassagem) {
        super(id, nome, cpf, dataNascimento);
        this.classePassagem = classePassagem;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Passageiro: " + nome + " | Classe: " + classePassagem);
    }

    // Métodos específicos:
    public void consultarVoos() { /* ... */ }
    public void reservarPoltrona() { /* ... */ }
    public void enviarMensagem() { /* ... */ }
}
