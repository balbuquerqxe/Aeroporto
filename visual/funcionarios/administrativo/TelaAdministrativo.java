package visual.funcionarios.administrativo;

import comunicacao.CentralComunicacao;
import java.awt.*;
import javax.swing.*;
import pessoas.Administrativo;

public class TelaAdministrativo extends JFrame {

    public TelaAdministrativo(Administrativo funcionario) {
        setTitle("Painel Administrativo");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + funcionario.getNome() + "!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel botoes = new JPanel(new GridLayout(3, 2, 15, 15));
        botoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        botoes.setBackground(Color.decode("#e6f0ff"));

        String[] opcoes = {
                "Cadastrar Voo", "Gerenciar Aviões",
                "Alocar Tripulação", "Gerar Relatórios",
                "Responder Perguntas", "Voltar"
        };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            botao.setFont(new Font("SansSerif", Font.PLAIN, 14));
            botao.setBackground(Color.decode("#0052cc"));
            botao.setForeground(Color.decode("#003366"));
            botoes.add(botao);

            switch (texto) {
                case "Responder Perguntas":
                    botao.addActionListener(e -> {
                        new TelaChatDuvidasAdministrativo(funcionario).setVisible(true);
                    });
                    break;
                case "Voltar":
                    botao.addActionListener(e -> {
                        dispose();
                        JOptionPane.showMessageDialog(this, "Voltando para o login.");
                        new visual.funcionarios.LoginFuncionario().setVisible(true);
                    });
                    break;
                // Adicione os demais listeners aqui se quiser.
            }
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);

        // Registra o funcionário na central de comunicação ao abrir a tela
        CentralComunicacao.registrar(funcionario);
    }
}