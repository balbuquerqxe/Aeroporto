package visual;

import java.awt.*;
import javax.swing.*;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        // Adiciona a imagem do avião
        setTitle("Sistema de Aeroporto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Painel central com fundo azul claro
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        // Título estilizado
        JLabel titulo = new JLabel("Bem-vindo ao Sistema de Aeroporto!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Botões
        JButton botaoFuncionario = new JButton("Funcionário");
        JButton botaoPassageiro = new JButton("Passageiro");

        estilizarBotao(botaoFuncionario);
        estilizarBotao(botaoPassageiro);

        // Ações
        botaoFuncionario.addActionListener(e -> {
            new LoginFuncionario().setVisible(true);
            dispose();
        });

        botaoPassageiro.addActionListener(e -> {
            new LoginPassageiro().setVisible(true);
            dispose();
        });

        // Adiciona a imagem do avião
        ImageIcon iconeAvião = new ImageIcon("dados/aviao.png");
        Image imagemEscalada = iconeAvião.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel imagemLabel = new JLabel(new ImageIcon(imagemEscalada));
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(imagemLabel);

        // Adiciona botões e título
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(botaoFuncionario);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(botaoPassageiro);

        add(painelCentral, BorderLayout.CENTER);
    }

    private void estilizarBotao(JButton botao) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setBackground(Color.decode("#0052cc"));
        botao.setForeground(Color.decode("#003366"));
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(150, 40));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
