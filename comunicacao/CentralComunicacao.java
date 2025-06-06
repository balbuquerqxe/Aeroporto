package comunicacao;

import java.util.ArrayList;
import java.util.List;

public class CentralComunicacao {
    private static final List<Comunicavel> usuarios = new ArrayList<>();

    public static void registrar(Comunicavel c) {
        if (c != null && !usuarios.contains(c)) {
            usuarios.add(c);
        }
    }

    public static void enviarMensagem(String mensagem, Comunicavel remetente) {
        for (Comunicavel c : usuarios) {
            if (c != null && c != remetente) {
                c.receberMensagem("De " + remetente.getNome() + ": " + mensagem);
            }
        }
    }
}
