package pessoas;

import aviao.GerenciadorDeVoos;
import aviao.Voo;
import comunicacao.Comunicavel;
import enums.TipoFuncionario;
import java.util.ArrayList;
import java.util.List;

public class Administrativo extends Funcionario implements Comunicavel, GerenciadorDeVoos {

    private List<Voo> voosCadastrados = new ArrayList<>();

    public Administrativo(String id, String nome, String cpf, String dataNascimento, String senha, String matricula) {
        super(id, nome, cpf, dataNascimento, matricula, senha, TipoFuncionario.ADMINISTRATIVO);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Funcionário Administrativo: " + nome);
    }

    @Override
    public void executarFuncao() {
        // tarefas administrativas
    }

    // Métodos da interface Comunicavel
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        destinatario.receberMensagem("Mensagem de " + nome + ": " + mensagem);
    }

    @Override
    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem recebida por Administrativo " + nome + ": " + mensagem);
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    // Implementação da interface GerenciadorDeVoos
    @Override
    public void cadastrarVoo(Voo voo) {
        voosCadastrados.add(voo);
        System.out.println("Voo cadastrado com sucesso: " + voo);
        // Aqui você pode adicionar código para salvar no arquivo ou registrar na Central
    }

    public List<Voo> getVoosCadastrados() {
        return voosCadastrados;
    }

    // Funções específicas
    public void gerenciarAvioes() {
        /* ... */ }

    public void alocarTripulacao() {
        /* ... */ }

    public void gerarRelatorios() {
        /* ... */ }

}

