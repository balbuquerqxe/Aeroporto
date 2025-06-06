package visual;

import dados.LeitorUsuarios;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginPassageiro extends JFrame {

    public LoginPassageiro() {
        setTitle("Login - Passageiro");
        setSize(400, 330);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Acesso de Passageiro");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        // CPF
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField cpfField = new JTextField(15);
        estilizarCampoTexto(cpfField);

        JPanel painelCpf = new JPanel();
        painelCpf.setLayout(new BoxLayout(painelCpf, BoxLayout.Y_AXIS));
        painelCpf.setBackground(Color.decode("#e6f0ff"));
        painelCpf.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpfLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cpfField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCpf.add(cpfLabel);
        painelCpf.add(Box.createRigidArea(new Dimension(0, 5)));
        painelCpf.add(cpfField);

        // Senha
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPasswordField senhaField = new JPasswordField(15);
        estilizarCampoTexto(senhaField);

        JPanel painelSenha = new JPanel();
        painelSenha.setLayout(new BoxLayout(painelSenha, BoxLayout.Y_AXIS));
        painelSenha.setBackground(Color.decode("#e6f0ff"));
        painelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        senhaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        senhaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelSenha.add(senhaLabel);
        painelSenha.add(Box.createRigidArea(new Dimension(0, 5)));
        painelSenha.add(senhaField);

        // Botão entrar
        JButton entrar = new JButton("Entrar");
        estilizarBotao(entrar);

        entrar.addActionListener(e -> {
            String cpf = cpfField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            Map<String, String> passageiros = LeitorUsuarios.carregarUsuarios("dados/passageiros.csv");

            if (passageiros.containsKey(cpf) && passageiros.get(cpf).equals(senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, passageiro.");
                // abrir próxima tela
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha inválidos.");
            }
        });

        // Botão voltar
        JButton voltar = new JButton("Voltar");
        estilizarBotaoVoltar(voltar);
        voltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        // Montando layout
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(painelCpf);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        painelCentral.add(painelSenha);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(entrar);

        // Painel inferior com botão voltar
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInferior.setBackground(Color.decode("#e6f0ff"));
        painelInferior.add(voltar);

        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setMaximumSize(new Dimension(250, 30));
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.decode("#003366"), 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campo.setBackground(Color.WHITE);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private void estilizarBotao(JButton botao) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#003366"));
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(150, 40));
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2));
    }

    private void estilizarBotaoVoltar(JButton botao) {
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#003366"));
        botao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
    }
}