package visual.passageiros;

import java.awt.*;
import javax.swing.*;
import pessoas.Passageiro;

public class TelaPassageiro extends JFrame {

    public TelaPassageiro(Passageiro passageiro) {
        setTitle("Painel do Passageiro");
        // *** AJUSTE AQUI: Novo tamanho do JFrame para alinhar com outras telas ***
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + passageiro.getNome() + "!", SwingConstants.CENTER);
        // *** AJUSTE AQUI: Fonte maior para o título ***
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28)); // Aumentado para 28
        titulo.setForeground(Color.decode("#003366"));
        // *** AJUSTE AQUI: Mais padding no título ***
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // *** AJUSTE AQUI: Mais espaço entre os botões no GridLayout ***
        JPanel botoes = new JPanel(new GridLayout(3, 1, 20, 20)); // 4 linhas para 4 botões, com mais espaçamento
        // *** AJUSTE AQUI: Mais padding no painel de botões ***
        botoes.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80)); // Aumentado o padding lateral
        botoes.setBackground(Color.decode("#e6f0ff"));

        String[] opcoes = { "Consultar Reserva", "Reservar Poltrona", "Enviar Dúvida", "Voltar" };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            // *** AJUSTE AQUI: Fonte maior para os botões ***
            botao.setFont(new Font("SansSerif", Font.BOLD, 18)); // Aumentado para 18
            botao.setBackground(Color.decode("#0052cc"));
            botao.setForeground(Color.WHITE); 
            botao.setPreferredSize(new Dimension(250, 60)); // Tamanho preferencial
            botao.setMaximumSize(new Dimension(350, 60)); // Tamanho máximo para o BoxLayout
            botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); // Adiciona borda
            
            botoes.add(botao);

            switch (texto) {
                case "Voltar":
                    botao.addActionListener(e -> {
                        dispose();
                        new visual.TelaInicial().setVisible(true);
                    });
                    break;
                case "Reservar Poltrona":
                    botao.addActionListener(e -> new TelaVoosDisponiveis(passageiro).setVisible(true));
                    break;

                case "Consultar Reserva":
                    botao.addActionListener(e -> new TelaCancelarReserva(passageiro).setVisible(true));
                    break;

                case "Enviar Dúvida":
                    botao.addActionListener(e -> {
                        new TelaChatPassageiro(passageiro).setVisible(true);
                    });
                    break;
            }
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
    }
}
