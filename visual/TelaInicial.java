package visual;

import java.awt.*;
import javax.swing.*;
import visual.funcionarios.LoginFuncionario;
import visual.passageiros.LoginPassageiro;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        setTitle("Sistema de Aeroporto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // AJUSTE AQUI: Novo tamanho do JFrame
        setSize(600, 450); // Ajustado para o novo tamanho
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
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25)); // Aumentado para 25
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0)); // Ajustado padding

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
        ImageIcon iconeAviao = null;
        try {
            // Carregando a imagem do classpath para robustez (mantendo o caminho /dados/ como antes)
            iconeAviao = new ImageIcon(getClass().getResource("/dados/aviao.png"));
            if (iconeAviao == null || iconeAviao.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Atenção: Imagem 'aviao.png' não foi carregada corretamente. Verifique o caminho ou se existe.");
                iconeAviao = null; // Força o uso de um fallback ou evita NullPointerException
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem do avião: " + e.getMessage());
            iconeAviao = null;
        }
        
        JLabel imagemLabel = new JLabel();
        if (iconeAviao != null) {
            Image imagemEscalada = iconeAviao.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Aumentado para 100x100
            imagemLabel.setIcon(new ImageIcon(imagemEscalada));
        } else {
            imagemLabel.setText("[AVIÃO]"); 
            imagemLabel.setForeground(Color.GRAY);
        }
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); // Mais espaço antes da imagem
        painelCentral.add(imagemLabel);

        // Adiciona botões e título
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); // Mais espaço entre título e botões
        painelCentral.add(botaoFuncionario);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15))); // Mais espaço entre botões
        painelCentral.add(botaoPassageiro);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 30))); // Mais espaço no final


        add(painelCentral, BorderLayout.CENTER);
    }

    private void estilizarBotao(JButton botao) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setBackground(Color.decode("#0052cc")); // Fundo azul
        botao.setForeground(Color.WHITE); // Texto branco
        botao.setFont(new Font("SansSerif", Font.BOLD, 20)); // Fonte um pouco maior
        botao.setFocusPainted(false);
        // AJUSTE AQUI: Aumentando a largura e altura para o novo JFrame
        botao.setPreferredSize(new Dimension(300, 60)); // Aumentado para 300 de largura e 60 de altura
        botao.setMaximumSize(new Dimension(300, 60)); // Define o tamanho máximo
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); // Adiciona uma borda
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
