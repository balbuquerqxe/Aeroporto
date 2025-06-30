package visual.funcionarios.piloto;

import java.awt.*;
import javax.swing.*;
import pessoas.Piloto; // Importa a classe Piloto


public class TelaPiloto extends JFrame {

    public TelaPiloto(Piloto piloto) {
        setTitle("Painel do Piloto");

        setSize(600, 450); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar tudo ao fechar a tela principal do piloto
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo, " + piloto.getNome() + "!", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28)); // Fonte grande para o título
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Padding

        // Painel para os botões com GridLayout
        JPanel botoes = new JPanel(new GridLayout(3, 1, 20, 20)); // 3 botões em 3 linhas, 1 coluna, com espaçamento
        botoes.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        botoes.setBackground(Color.decode("#e6f0ff"));

        String[] opcoes = { "Consultar Próximos Voos", "Ver Minhas Informações", "Voltar" };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setFocusPainted(false);
            botao.setFont(new Font("SansSerif", Font.BOLD, 18)); // Fonte dos botões
            botao.setBackground(Color.decode("#0052cc")); // Cor de fundo do botão
            botao.setForeground(Color.WHITE); // Cor do texto do botão
            botao.setPreferredSize(new Dimension(300, 60)); // Tamanho preferencial
            botao.setMaximumSize(new Dimension(350, 60)); // Tamanho máximo
            botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); // Borda do botão
            
            botoes.add(botao); // Adiciona o botão ao painel

            switch (texto) {
                case "Voltar":
                    botao.addActionListener(e -> {
                        dispose(); // Fecha a tela atual
                        new visual.TelaInicial().setVisible(true); // Retorna para a tela inicial
                    });
                    break;
                case "Consultar Próximos Voos":
                    botao.addActionListener(e -> new TelaVoosDoPiloto(piloto).setVisible(true));
                    break;
                case "Ver Minhas Informações":
                    botao.addActionListener(e -> {
                        // Constrói a mensagem com as informações do piloto
                        String info = "ID: " + piloto.getId() + "\n" +
                                      "Nome: " + piloto.getNome() + "\n" +
                                      "Data de Nascimento: " + piloto.getDataNascimento() + "\n" +
                                      "Matrícula: " + piloto.getMatricula(); // Assumindo que Piloto tem getMatricula()
                        
                        JOptionPane.showMessageDialog(this, info, "Minhas Informações", JOptionPane.INFORMATION_MESSAGE);
                    });
                    break;
            }
        }

        add(titulo, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
    }
}
