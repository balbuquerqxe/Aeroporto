package pessoas;

import enums.TipoFuncionario;

public class Piloto extends Funcionario {

    public Piloto(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.PILOTO);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Piloto: " + nome);
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

}
