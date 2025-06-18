package visual.funcionarios.administrativo;

import comunicacao.CentralComunicacao;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder; // Importa para LineBorder
import pessoas.Administrativo;

public class TelaChatDuvidasAdministrativo extends JFrame {
    private JTextArea areaMensagens;
    private JTextField campoResposta;
    private JTextField campoRemetente; // Campo para digitar o nome do destinatário

    public TelaChatDuvidasAdministrativo(Administrativo funcionario) {

        setTitle("Chat de Dúvidas - Administrativo"); // Título mais descritivo
        // *** AJUSTE AQUI: Novo tamanho do JFrame ***
        setSize(600, 450); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());
        // *** AJUSTE AQUI: Padding geral para afastar o conteúdo das bordas ***
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Área de mensagens (compartilhada)
        this.areaMensagens = CentralComunicacao.areaMensagensCompartilhada;
        areaMensagens.setEditable(false);
        // *** AJUSTE AQUI: Fonte para a área de mensagens ***
        areaMensagens.setFont(new Font("SansSerif", Font.PLAIN, 14));
        areaMensagens.setBackground(Color.WHITE); // Fundo branco para a área de texto
        areaMensagens.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1)); // Borda
        JScrollPane scrollPane = new JScrollPane(areaMensagens);
        add(scrollPane, BorderLayout.CENTER);

        // Painel inferior para entrada de texto e botão de enviar
        JPanel painelInferior = new JPanel(new BorderLayout(10, 0)); // Espaçamento horizontal de 10px

        // Campo para digitar o nome do remetente (quem vai receber a resposta, no caso, o passageiro)
        campoRemetente = new JTextField();
        campoRemetente.setToolTipText("CPF do Passageiro/Nome do destinatário"); // Dica de ferramenta melhorada
        // *** AJUSTE AQUI: Tamanho e fonte do campo remetente ***
        campoRemetente.setPreferredSize(new Dimension(150, 40)); 
        campoRemetente.setMaximumSize(new Dimension(200, 40)); // Limita largura
        campoRemetente.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoRemetente.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.decode("#003366"), 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding interno
        ));
        painelInferior.add(campoRemetente, BorderLayout.WEST);

        // Campo de resposta
        campoResposta = new JTextField();
        // *** AJUSTE AQUI: Tamanho e fonte do campo de resposta ***
        campoResposta.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40)); // Preenche largura, altura fixa
        campoResposta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoResposta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.decode("#003366"), 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding interno
        ));
        painelInferior.add(campoResposta, BorderLayout.CENTER);

        // Botão responder
        JButton botaoResponder = new JButton("Responder");
        // *** AJUSTE AQUI: Estilo do botão responder ***
        botaoResponder.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoResponder.setBackground(Color.decode("#0052cc")); // Azul escuro
        botaoResponder.setForeground(Color.WHITE); // Texto branco
        botaoResponder.setFocusPainted(false);
        botaoResponder.setPreferredSize(new Dimension(120, 40)); // Tamanho do botão
        botaoResponder.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); // Borda

        botaoResponder.addActionListener((ActionEvent e) -> {
            String resposta = campoResposta.getText().trim();
            String destinatario = campoRemetente.getText().trim();

            if (!resposta.isEmpty() && !destinatario.isEmpty()) {
                // Formata a mensagem para exibição na área de chat
                // É útil incluir quem está respondendo para clareza no histórico
                String linha = "Administrativo (" + funcionario.getNome() + ") -> " + destinatario + " | Resposta: " + resposta;

                // Atualiza a área de mensagens compartilhada
                areaMensagens.append(linha + "\n");

                // Salva a mensagem no arquivo (usando o nome do admin logado como remetente)
                CentralComunicacao.salvarMensagem(funcionario.getNome(), destinatario, resposta);

                campoResposta.setText(""); // Limpa o campo de resposta
                // campoRemetente.setText(""); // Opcional: limpar também o campo do destinatário se for uma conversa única
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, digite a resposta e o nome/CPF do destinatário.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            }
        });

        painelInferior.add(botaoResponder, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);
    }

    // Este método é chamado pela CentralComunicacao quando uma mensagem é recebida
    public void receberMensagem(String msg) {
        areaMensagens.append(msg + "\n");
        // Scrolla para o final da área de mensagens
        areaMensagens.setCaretPosition(areaMensagens.getDocument().getLength());
    }
}
