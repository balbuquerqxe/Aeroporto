package visual.funcionarios.administrativo;

import comunicacao.CentralComunicacao;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import pessoas.Administrativo;

public class TelaChatDuvidasAdministrativo extends JFrame {
    private JTextArea areaMensagens;
    private JTextField campoResposta;
    private JTextField campoRemetente;

    public TelaChatDuvidasAdministrativo(Administrativo funcionario) {

        setTitle("Chat de Dúvidas");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Área de mensagens (compartilhada)
        this.areaMensagens = CentralComunicacao.areaMensagensCompartilhada;
        areaMensagens.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        add(scrollPane, BorderLayout.CENTER);

        // Painel inferior
        JPanel painelInferior = new JPanel(new BorderLayout());

        // Campo para digitar o nome do destinatário
        campoRemetente = new JTextField();
        campoRemetente.setToolTipText("Nome do destinatário");
        campoRemetente.setPreferredSize(new Dimension(150, 30));
        painelInferior.add(campoRemetente, BorderLayout.WEST);

        // Campo de resposta
        campoResposta = new JTextField();
        painelInferior.add(campoResposta, BorderLayout.CENTER);

        // Botão responder
        JButton botaoResponder = new JButton("Responder");
        botaoResponder.addActionListener((ActionEvent e) -> {
            String resposta = campoResposta.getText().trim();
            String destinatario = campoRemetente.getText().trim();

            if (!resposta.isEmpty() && !destinatario.isEmpty()) {
                String linha = "Administrativo -> " + destinatario + " | Mensagem: " + resposta;

                // Atualiza a área de mensagens compartilhada
                areaMensagens.append(linha + "\n");

                // Salva a mensagem no arquivo
                CentralComunicacao.salvarMensagem("Administrativo", destinatario, resposta);

                campoResposta.setText("");
            }
        });

        painelInferior.add(botaoResponder, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);
    }

    public void receberMensagem(String msg) {
        areaMensagens.append(msg + "\n");
    }
}
