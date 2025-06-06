package visual.passageiros;

import comunicacao.CentralComunicacao;
import pessoas.Passageiro;

import javax.swing.*;
import java.awt.*;

public class TelaPassageiro extends JFrame {

    public TelaPassageiro(Passageiro passageiro) {
        setTitle("Painel do Passageiro");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + passageiro.getNome() + "!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel botoes = new JPanel(new GridLayout(3, 1, 15, 15));
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        botoes.setBackground(Color.decode("#e6f0ff"));

        String[] opcoes = {"Consultar Voos", "Reservar Poltrona", "Voltar"};

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            botao.setFont(new Font("SansSerif", Font.PLAIN, 14));
            botao.setBackground(Color.decode("#0052cc"));
            botao.setForeground(Color.WHITE);
            botoes.add(botao);

            switch (texto) {
                case "Voltar":
                    botao.addActionListener(e -> {
                        dispose();
                        new visual.TelaInicial().setVisible(true);
                    });
                    break;
                case "Consultar Voos":
                    botao.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, "Funcionalidade de consulta de voos ainda não implementada.");
                    });
                    break;
                case "Reservar Poltrona":
                    botao.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, "Funcionalidade de reserva de poltrona ainda não implementada.");
                    });
                    break;
            }
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
        CentralComunicacao.registrar(passageiro);
    }
}
