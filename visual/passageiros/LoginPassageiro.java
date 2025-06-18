package visual.passageiros;

import dados.LeitorUsuarios;
import pessoas.Passageiro;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

import comunicacao.CentralComunicacao;
import visual.TelaInicial;

public class LoginPassageiro extends JFrame {

    public LoginPassageiro() {
        setTitle("Login - Passageiro");
        // *** AJUSTE AQUI: Novo tamanho do JFrame para alinhar com outras telas ***
        setSize(600, 450); 
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Acesso de Passageiro");
        // *** AJUSTE AQUI: Fonte maior para o título ***
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26)); 
        titulo.setForeground(Color.decode("#003366"));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // *** AJUSTE AQUI: Mais padding no título ***
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); 

        // CPF
        JLabel cpfLabel = new JLabel("CPF:");
        // *** AJUSTE AQUI: Fonte maior para labels ***
        cpfLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        JTextField cpfField = new JTextField(15);
        estilizarCampoTexto(cpfField);

        JPanel painelCpf = new JPanel();
        painelCpf.setLayout(new BoxLayout(painelCpf, BoxLayout.Y_AXIS));
        painelCpf.setBackground(Color.decode("#e6f0ff"));
        painelCpf.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpfLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cpfField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCpf.add(cpfLabel);
        painelCpf.add(Box.createRigidArea(new Dimension(0, 5)));
        painelCpf.add(cpfField);

        // Senha
        JLabel senhaLabel = new JLabel("Senha:");
        // *** AJUSTE AQUI: Fonte maior para labels ***
        senhaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        JPasswordField senhaField = new JPasswordField(15);
        estilizarCampoTexto(senhaField);

        JPanel painelSenha = new JPanel();
        painelSenha.setLayout(new BoxLayout(painelSenha, BoxLayout.Y_AXIS));
        painelSenha.setBackground(Color.decode("#e6f0ff"));
        painelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        senhaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        senhaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelSenha.add(senhaLabel);
        painelSenha.add(Box.createRigidArea(new Dimension(0, 5)));
        painelSenha.add(senhaField);

        // Botão entrar
        JButton entrar = new JButton("Entrar");
        estilizarBotao(entrar);

        entrar.addActionListener(e -> {
            String cpf = cpfField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            Map<String, String> passageiros = LeitorUsuarios.carregarCpfsESenhas("dados/passageiros.csv");

            if (passageiros.containsKey(cpf) && passageiros.get(cpf).equals(senha)) {
                Passageiro passageiro = CentralComunicacao.getPassageiroPorCpf(cpf);

                if (passageiro != null) {
                    JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, " + passageiro.getNome() + ".", "Login Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    new TelaPassageiro(passageiro).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar dados do passageiro. Por favor, tente novamente ou entre em contato com o suporte.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botão voltar
        JButton voltar = new JButton("Voltar");
        estilizarBotaoVoltar(voltar);
        voltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        // Botão "Não possui cadastro?"
        JButton cadastrar = new JButton("Não possui cadastro?");
        estilizarBotaoLink(cadastrar);
        cadastrar.addActionListener(e -> {
            new TelaCadastroPassageiro().setVisible(true);
            // Ao abrir a tela de cadastro, não fechar a de login para que o usuário possa voltar
            // Se você quiser fechar, adicione dispose() aqui: dispose();
        });

        // Montando layout
        painelCentral.add(titulo);
        // *** AJUSTE AQUI: Mais espaço entre o título e os campos ***
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); 
        painelCentral.add(painelCpf);
        // *** AJUSTE AQUI: Mais espaço entre os campos ***
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); 
        painelCentral.add(painelSenha);
        // *** AJUSTE AQUI: Mais espaço antes do botão de entrar ***
        painelCentral.add(Box.createRigidArea(new Dimension(0, 30))); 
        painelCentral.add(entrar);

        // Painel inferior com layout BorderLayout
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(Color.decode("#e6f0ff"));

        // Subpainel esquerdo com botão Voltar
        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEsquerdo.setBackground(Color.decode("#e6f0ff"));
        painelEsquerdo.add(voltar);

        // Subpainel direito com botão de cadastro
        JPanel painelDireito = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDireito.setBackground(Color.decode("#e6f0ff"));
        painelDireito.add(cadastrar);

        // Monta os dois lados no painel inferior
        painelInferior.add(painelEsquerdo, BorderLayout.WEST);
        painelInferior.add(painelDireito, BorderLayout.EAST);

        // Adiciona à tela
        add(painelCentral, BorderLayout.CENTER); 
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void estilizarCampoTexto(JTextField campo) {
        // *** AJUSTE AQUI: Aumentando o tamanho dos campos de texto ***
        campo.setMaximumSize(new Dimension(350, 40)); 
        campo.setPreferredSize(new Dimension(350, 40)); 
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.decode("#003366"), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        campo.setBackground(Color.WHITE);
        // *** AJUSTE AQUI: Fonte maior para o texto dos campos ***
        campo.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
    }

    private void estilizarBotao(JButton botao) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        // *** AJUSTE AQUI: Fundo azul para o botão principal de ação (Entrar) ***
        botao.setBackground(Color.decode("#0052cc")); 
        botao.setForeground(Color.WHITE); 
        // *** AJUSTE AQUI: Fonte maior para o botão Entrar ***
        botao.setFont(new Font("SansSerif", Font.BOLD, 20)); 
        botao.setFocusPainted(false);
        // *** AJUSTE AQUI: Tamanho maior para o botão Entrar ***
        botao.setPreferredSize(new Dimension(300, 60)); 
        botao.setMaximumSize(new Dimension(300, 60)); 
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); 
    }

    private void estilizarBotaoVoltar(JButton botao) {
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#003366"));
        // *** AJUSTE AQUI: Fonte maior para o botão Voltar ***
        botao.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        botao.setFocusPainted(false);
        // *** AJUSTE AQUI: Tamanho definido para o botão Voltar ***
        botao.setPreferredSize(new Dimension(120, 45)); 
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
    }

    private void estilizarBotaoLink(JButton botao) {
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#0052cc"));
        // *** AJUSTE AQUI: Fonte maior para o botão de link ***
        botao.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}
