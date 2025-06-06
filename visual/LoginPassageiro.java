package visual;

import dados.LeitorUsuarios;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class LoginPassageiro extends JFrame {

    public LoginPassageiro() {
        setTitle("Login - Passageiro");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();

        JButton entrar = new JButton("Entrar");

        add(cpfLabel);
        add(cpfField);
        add(senhaLabel);
        add(senhaField);
        add(new JLabel()); // espaçamento
        add(entrar);

        entrar.addActionListener(e -> {
            String cpf = cpfField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            Map<String, String> passageiros = LeitorUsuarios.carregarUsuarios("dados/passageiros.csv");

            if (passageiros.containsKey(cpf) &&
                passageiros.get(cpf).equals(senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, passageiro.");
                // Ir para a tela do passageiro
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha inválidos.");
            }
        });
    }
}
