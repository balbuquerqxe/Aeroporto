package visual.passageiros;

import comunicacao.CentralComunicacao;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import pessoas.Passageiro;

public class TelaChatPassageiro extends JFrame {
    private JTextArea areaMensagens;
    private JTextField campoMensagem;
    private Passageiro passageiro;

    public TelaChatPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;

        setTitle("Enviar Dúvida");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        areaMensagens = CentralComunicacao.areaMensagensCompartilhada;
        areaMensagens.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        add(scrollPane, BorderLayout.CENTER);

        campoMensagem = new JTextField();
        JButton botaoEnviar = new JButton("Enviar");

        botaoEnviar.addActionListener((ActionEvent e) -> {
            String texto = campoMensagem.getText().trim();
            if (!texto.isEmpty()) {
                String linha = passageiro.getNome() + " -> Administrativo | Mensagem: " + texto;
                areaMensagens.append(linha + "\n");
                CentralComunicacao.salvarMensagem(passageiro.getNome(), "Administrativo", texto);
                campoMensagem.setText("");
            }
        });

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(campoMensagem, BorderLayout.CENTER);
        painelInferior.add(botaoEnviar, BorderLayout.EAST);

        add(painelInferior, BorderLayout.SOUTH);
    }
}
