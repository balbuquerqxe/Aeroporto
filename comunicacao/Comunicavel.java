package comunicacao;

public interface Comunicavel {
    // Envia mensagem.
    void enviarMensagem(Comunicavel destinatario, String mensagem);
    // Recebe mensagem.
    void receberMensagem(String mensagem);
    // Nome de quem está recebendo a mensagem.
    String getNome();
}

