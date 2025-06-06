package visual;

import dados.LeitorUsuarios;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class LoginFuncionario extends JFrame {

    public LoginFuncionario() {
        setTitle("Login - Funcionário");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        JLabel matriculaLabel = new JLabel("Matrícula:");
        JTextField matriculaField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();

        JButton entrar = new JButton("Entrar");

        add(matriculaLabel);
        add(matriculaField);
        add(senhaLabel);
        add(senhaField);
        add(new JLabel()); // espaçamento
        add(entrar);

        entrar.addActionListener(e -> {
            String matricula = matriculaField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            Map<String, String> funcionarios = LeitorUsuarios.carregarUsuarios("dados/funcionarios.csv");

            if (funcionarios.containsKey(matricula) &&
                funcionarios.get(matricula).equals(senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, funcionário.");
                // Ir para a tela do funcionário
            } else {
                JOptionPane.showMessageDialog(this, "Matrícula ou senha inválidos.");
            }
        });
    }
}
