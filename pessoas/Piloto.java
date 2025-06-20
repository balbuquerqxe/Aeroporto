package pessoas;

import aviao.GerenciadorDeVoos;
import aviao.Voo;
import comunicacao.Comunicavel;
import enums.TipoFuncionario;

public class Piloto extends Funcionario implements Comunicavel, GerenciadorDeVoos {

    public Piloto(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.PILOTO);
    }

    @Override
    public String getNome() {
        return super.getNome(); // Chama o getter da superclasse Funcionario
    }

    @Override
    public void executarFuncao() {
        verEscala();
    }

    public void verEscala() {
        // lógica de consulta do próximo voo
        // Você pode chamar a tela de consulta de voos do piloto aqui,
        // mas essa lógica será executada a partir da TelaPiloto.
    }

    @Override
    public String toString() {
        // Agora, estes getters virão de Funcionario.
        return getNome() + " - Matrícula: " + getMatricula(); 
    }


    // --- MÉTODOS DE INTERFACE (MANUTENÇÃO) ---
    // Você mencionou anteriormente que o piloto não envia/recebe mensagens,
    // o que tornaria a interface Comunicavel opcional para Piloto.
    // Se você *ainda* deseja que Piloto não se comunique, mas Comunicavel
    // é necessário por outras razões (por exemplo, hierarquia),
    // estas implementações de throw UnsupportedOperationException são válidas,
    // mas podem indicar que Comunicavel talvez não seja a melhor interface para Piloto.
    // Deixei-os como estão porque eles não são a causa do erro atual.

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
