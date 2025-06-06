package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        setTitle("Sistema de Aeroporto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel pergunta = new JLabel("Você é funcionário?", SwingConstants.CENTER);
        add(pergunta, BorderLayout.NORTH);

        JPanel botoes = new JPanel();
        JButton botaoSim = new JButton("Sim");
        JButton botaoNao = new JButton("Não");

        botoes.add(botaoSim);
        botoes.add(botaoNao);
        add(botoes, BorderLayout.CENTER);

        botaoSim.addActionListener(e -> {
            new LoginFuncionario().setVisible(true);
            dispose();
        });

        botaoNao.addActionListener(e -> {
            new LoginPassageiro().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
