package visual.funcionarios.administrativo;

import comunicacao.CentralComunicacao;
import java.awt.*;
import javax.swing.*;
import pessoas.Administrativo;
import sistema.SistemaAeroporto;

public class TelaAdministrativo extends JFrame {

    public TelaAdministrativo(Administrativo funcionario) {
        setTitle("Painel Administrativo");
        setSize(600, 450);
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
                "Cancelar Voo", "Gerar Relatórios",
                "Responder Perguntas", "Voltar"
        };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            botao.setFont(new Font("SansSerif", Font.PLAIN, 18));
            botao.setBackground(Color.decode("#0052cc"));
            botao.setForeground(Color.WHITE);
            botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2));
            botoes.add(botao);

            switch (texto) {
                case "Cadastrar Voo":
                    botao.addActionListener(e -> {
                        Administrativo adm = (Administrativo) funcionario;
                        new TelaCadastrarVoos(adm, SistemaAeroporto.avioes, SistemaAeroporto.pilotosDisponiveis)
                                .setVisible(true);
                    });
                    break;
                case "Responder Perguntas":
                    botao.addActionListener(e -> {
                        new TelaChatDuvidasAdministrativo(funcionario).setVisible(true);
                    });
                    break;
                case "Gerar Relatórios":
                    botao.addActionListener(e -> {
                        new TelaGerarRelatorios(funcionario, this).setVisible(true);
                        this.setVisible(false); 
                    });
                    break;
                case "Voltar":
                    botao.addActionListener(e -> {
                        dispose();
                        new visual.TelaInicial().setVisible(true);
                    });
                case "Cancelar Voo":
                    botao.addActionListener(e -> {
                        dispose();
                        new TelaCancelarVoos(funcionario, SistemaAeroporto.avioes, SistemaAeroporto.pilotosDisponiveis).setVisible(true);
                    });
                    break;
            }
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);

        CentralComunicacao.registrar(funcionario);
    }
}