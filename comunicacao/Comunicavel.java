package comunicacao;

public interface Comunicavel {
    void enviarMensagem(Comunicavel destinatario, String mensagem);
    void receberMensagem(String mensagem);
    String getNome();
}

