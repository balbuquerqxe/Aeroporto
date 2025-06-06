package visual.funcionarios;

import dados.LeitorUsuarios;
import visual.TelaInicial;
import visual.funcionarios.administrativo.TelaAdministrativo;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginFuncionario extends JFrame {

    public LoginFuncionario() {
        setTitle("Login - Funcionário");
        setSize(400, 330);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Acesso de Funcionário");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        // Matrícula
        JLabel matriculaLabel = new JLabel("Matrícula:");
        matriculaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField matriculaField = new JTextField(15);
        estilizarCampoTexto(matriculaField);

        JPanel painelMatricula = new JPanel();
        painelMatricula.setLayout(new BoxLayout(painelMatricula, BoxLayout.Y_AXIS));
        painelMatricula.setBackground(Color.decode("#e6f0ff"));
        painelMatricula.setAlignmentX(Component.CENTER_ALIGNMENT);
        matriculaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        matriculaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelMatricula.add(matriculaLabel);
        painelMatricula.add(Box.createRigidArea(new Dimension(0, 5)));
        painelMatricula.add(matriculaField);

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
            String matricula = matriculaField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            Map<String, String> funcionarios = LeitorUsuarios.carregarMatriculasESenhas("dados/funcionarios.csv");

            String[] dados = LeitorUsuarios.buscarFuncionarioCompleto("dados/funcionarios.csv", matricula);

            if (dados != null && dados[4].equals(senha)) {
                String tipo = dados[6].toUpperCase(); // Supondo que tipo esteja na sétima coluna

                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");

                switch (tipo) {
                    case "PILOTO":
                        break;
                    case "COMISSARIO":
                        break;
                    case "ADMINISTRATIVO":
                        new TelaAdministrativo().setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Tipo de funcionário desconhecido.");
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Matrícula ou senha inválidos.");
            }

        });

        // Botão voltar
        JButton voltar = new JButton("Voltar");
        estilizarBotaoVoltar(voltar);
        voltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        // Montagem do layout
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(painelMatricula);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        painelCentral.add(painelSenha);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(entrar);

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
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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