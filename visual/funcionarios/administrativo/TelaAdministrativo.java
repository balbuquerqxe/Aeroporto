package visual.funcionarios.administrativo;

import java.awt.*;
import javax.swing.*;

public class TelaAdministrativo extends JFrame {

    public TelaAdministrativo(String nomeFuncionario) {
        setTitle("Painel Administrativo");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + nomeFuncionario + "!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel botoes = new JPanel(new GridLayout(3, 2, 15, 15));
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        botoes.setBackground(Color.decode("#e6f0ff"));

        String[] opcoes = {
            "Cadastrar Voo", "Gerenciar Aviões",
            "Alocar Tripulação", "Gerar Relatórios",
            "Responder Perguntas", "Sair"
        };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            botao.setFont(new Font("SansSerif", Font.PLAIN, 14));
            botao.setBackground(Color.decode("#0052cc"));
            botao.setForeground(Color.WHITE);
            botoes.add(botao);

            if (texto.equals("Sair")) {
                botao.addActionListener(e -> {
                    dispose();
                    JOptionPane.showMessageDialog(this, "Você saiu do sistema.");
                    // Voltar para o login, se quiser
                });
            }

            // Aqui você pode adicionar ações específicas para os outros botões
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAdministrativo("Funcionário X").setVisible(true));
    }
}
