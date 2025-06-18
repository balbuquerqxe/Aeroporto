package pessoas;

import aviao.GerenciadorDeVoos;
import aviao.Voo;
import comunicacao.Comunicavel;
import enums.TipoFuncionario;

public class Piloto extends Funcionario implements Comunicavel, GerenciadorDeVoos{

    public Piloto(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.PILOTO);
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public void executarFuncao() {
        verEscala();
    }

    public void verEscala() {
        // lógica de consulta do próximo voo
    }

    @Override
    public String toString() {
        return getNome() + " - Matrícula: " + getMatricula();
    }

    public String getId() {
        return id;
    }

    // Corrigido para getCpf() para corresponder à sua chamada na TelaPiloto
    // e convenção Java de nomes de métodos.
    public String getCpf() { 
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public String getMatricula() {
        return matricula;
    }


    @Override
    public void cadastrarVoo(Voo voo) {
        throw new UnsupportedOperationException("Unimplemented method 'cadastrarVoo'");
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        throw new UnsupportedOperationException("Unimplemented method 'enviarMensagem'");
    }

    @Override
    public void receberMensagem(String mensagem) {
        throw new UnsupportedOperationException("Unimplemented method 'receberMensagem'");
    }

    

}
