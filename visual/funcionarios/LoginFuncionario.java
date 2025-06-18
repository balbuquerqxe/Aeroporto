package visual.funcionarios;

import dados.LeitorUsuarios;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import pessoas.Administrativo;
import visual.TelaInicial;
import visual.funcionarios.administrativo.TelaAdministrativo;
import comunicacao.CentralComunicacao; // Importa CentralComunicacao

public class LoginFuncionario extends JFrame {

    public LoginFuncionario() {
        setTitle("Login - Funcionário");
        setSize(600, 450); // Mantém o novo tamanho do JFrame
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Acesso de Funcionário");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26)); // Aumentado para 26
        titulo.setForeground(Color.decode("#003366"));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Ajustado padding

        // Matrícula
        JLabel matriculaLabel = new JLabel("Matrícula:");
        matriculaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Aumentado para 16
        JTextField matriculaField = new JTextField(15);
        estilizarCampoTexto(matriculaField);

        JPanel painelMatricula = new JPanel();
        painelMatricula.setLayout(new BoxLayout(painelMatricula, BoxLayout.Y_AXIS));
        painelMatricula.setBackground(Color.decode("#e6f0ff"));
        painelMatricula.setAlignmentX(Component.CENTER_ALIGNMENT);
        matriculaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        matriculaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelMatricula.add(matriculaLabel);
        painelMatricula.add(Box.createRigidArea(new Dimension(0, 5)));
        painelMatricula.add(matriculaField);

        // Senha
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Aumentado para 16
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
            String matricula = matriculaField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();
            
            String[] dados = LeitorUsuarios.buscarFuncionarioCompleto("dados/funcionarios.csv", matricula);

            if (dados != null && dados.length > 4 && dados[4].equals(senha)) { // Verifica dados.length para evitar IndexOutOfBounds
                String tipo = dados[6].toUpperCase(); // tipo na sétima coluna (índice 6)
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                switch (tipo) {
                    case "PILOTO":
                        // implementar depois
                        JOptionPane.showMessageDialog(this, "Funcionalidade de Piloto em desenvolvimento.", "Aguarde", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "COMISSARIO":
                        // implementar depois
                        JOptionPane.showMessageDialog(this, "Funcionalidade de Comissário em desenvolvimento.", "Aguarde", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "ADMINISTRATIVO":
                        // O índice da senha e matrícula podem estar trocados aqui, verificar o CSV
                        // Supondo que o CSV seja: id,nome,cpf,dataNascimento,matricula,senha,tipo
                        Administrativo admin = new Administrativo(
                            dados[0], // id
                            dados[1], // nome
                            dados[2], // cpf
                            dados[3], // dataNascimento
                            dados[5], // senha (dados[5] se a senha for a sexta coluna)
                            dados[4]  // matricula (dados[4] se a matricula for a quinta coluna)
                        );
                        // Registro na CentralComunicacao (importada)
                        CentralComunicacao.registrar(admin); 
                        new TelaAdministrativo(admin).setVisible(true);
                        dispose();
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Tipo de funcionário desconhecido.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Matrícula ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botão voltar
        JButton voltar = new JButton("Voltar");
        estilizarBotaoVoltar(voltar);
        voltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        // Montagem do layout
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); // Mais espaço
        painelCentral.add(painelMatricula);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20))); // Mais espaço
        painelCentral.add(painelSenha);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 30))); // Mais espaço
        painelCentral.add(entrar);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInferior.setBackground(Color.decode("#e6f0ff"));
        painelInferior.add(voltar);

        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void estilizarCampoTexto(JTextField campo) {
        // Aumentando o tamanho máximo para preencher melhor o espaço
        campo.setMaximumSize(new Dimension(350, 40)); 
        campo.setPreferredSize(new Dimension(350, 40)); // Definindo preferredSize também
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.decode("#003366"), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        campo.setBackground(Color.WHITE);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Aumentado para 16
    }

    private void estilizarBotao(JButton botao) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setBackground(Color.decode("#0052cc")); // Fundo azul para o botão principal de ação
        botao.setForeground(Color.WHITE); // Texto branco para melhor contraste
        botao.setFont(new Font("SansSerif", Font.BOLD, 20)); // Aumentado para 20
        botao.setFocusPainted(false);
        // Aumentando a largura e altura para o novo JFrame
        botao.setPreferredSize(new Dimension(200, 60)); 
        botao.setMaximumSize(new Dimension(200, 60)); // Define o tamanho máximo
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2)); // Adiciona uma borda
    }

    private void estilizarBotaoVoltar(JButton botao) {
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#003366"));
        botao.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Aumentado para 16
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(120, 45)); // Ajuste de tamanho para o botão voltar
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
    }
}
