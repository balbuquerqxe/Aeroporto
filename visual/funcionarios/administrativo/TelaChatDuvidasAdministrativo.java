package visual.administrativo;

import comunicacao.CentralComunicacao;
import comunicacao.Comunicavel;
import pessoas.Administrativo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaChatDuvidasAdministrativo extends JFrame {
    private JTextArea areaMensagens;
    private JTextField campoResposta;
    private Administrativo funcionario;

    public TelaChatDuvidasAdministrativo(Administrativo funcionario) {
        this.funcionario = funcionario;

        setTitle("Chat de Dúvidas");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Área para visualizar mensagens
        areaMensagens = new JTextArea();
        areaMensagens.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        add(scrollPane, BorderLayout.CENTER);

        // Campo de resposta
        JPanel painelInferior = new JPanel(new BorderLayout());
        campoResposta = new JTextField();
        JButton botaoResponder = new JButton("Responder");

        botaoResponder.addActionListener((ActionEvent e) -> {
            String resposta = campoResposta.getText().trim();
            if (!resposta.isEmpty()) {
                CentralComunicacao.enviarMensagem(resposta, funcionario);
                campoResposta.setText("");
                areaMensagens.append("Você: " + resposta + "\n");
            }
        });

        painelInferior.add(campoResposta, BorderLayout.CENTER);
        painelInferior.add(botaoResponder, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);
    }

    public void receberMensagem(String msg) {
        areaMensagens.append(msg + "\n");
    }
}
