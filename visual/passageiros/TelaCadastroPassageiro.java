package visual.passageiros;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import visual.TelaInicial;

public class TelaCadastroPassageiro extends JFrame {

    public TelaCadastroPassageiro() {
        setTitle("Cadastro de Passageiro");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        // Painel principal com alinhamento vertical
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        JLabel titulo = new JLabel("Cadastro de Novo Passageiro");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentral.add(titulo);
        painelCentral.add(Box.createVerticalStrut(20));

        // Formulário
        JTextField nomeField = criarCampo("Nome:", painelCentral);
        JTextField cpfField = criarCampo("CPF:", painelCentral);
        JTextField dataField = criarCampo("Data de Nascimento (aaaa-dd-mm):", painelCentral);
        JPasswordField senhaField = new JPasswordField();
        adicionarCampoPersonalizado("Senha:", senhaField, painelCentral);

        JComboBox<String> classeBox = new JComboBox<>(new String[]{"ECONOMICA", "EXECUTIVA", "INDEFINIDA"});
        adicionarCampoPersonalizado("Classe:", classeBox, painelCentral);

        // Botão Confirmar
        JButton confirmar = new JButton("Confirmar");
        confirmar.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmar.setMaximumSize(new Dimension(200, 40));
        confirmar.setBackground(Color.decode("#0052cc"));
        confirmar.setForeground(Color.decode("#003366"));
        confirmar.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirmar.setFocusPainted(false);

        confirmar.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String data = dataField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();
            String classe = (String) classeBox.getSelectedItem();

            if (nome.isEmpty() || cpf.isEmpty() || data.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.");
                return;
            }

            String id = String.valueOf(System.currentTimeMillis());

            try (FileWriter fw = new FileWriter("dados/passageiros.csv", true)) {
                fw.write(id + "," + nome + "," + cpf + "," + data + "," + senha + "," + classe + "\n");
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar cadastro: " + ex.getMessage());
            }
        });

        painelCentral.add(Box.createVerticalStrut(10));
        painelCentral.add(confirmar);
        painelCentral.add(Box.createVerticalStrut(15));

        // Botão Voltar
        JButton voltar = new JButton("Voltar");
        estilizarBotaoVoltar(voltar);
        voltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInferior.setBackground(Color.decode("#e6f0ff"));
        painelInferior.add(voltar);

        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private JTextField criarCampo(String label, JPanel painel) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField campo = new JTextField();
        estilizarCampo(campo);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(l);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campo);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));

        return campo;
    }

    private void adicionarCampoPersonalizado(String label, JComponent campo, JPanel painel) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);

        estilizarCampo(campo);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(l);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campo);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void estilizarCampo(JComponent campo) {
        campo.setPreferredSize(new Dimension(300, 40));
        campo.setMaximumSize(new Dimension(300, 40));
        campo.setMinimumSize(new Dimension(300, 40));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        if (campo instanceof JTextField || campo instanceof JPasswordField) {
            campo.setBorder(BorderFactory.createLineBorder(Color.decode("#003366")));
            campo.setBackground(Color.WHITE);
        }
    }

    private void estilizarBotaoVoltar(JButton botao) {
        botao.setBackground(Color.decode("#e6f0ff"));
        botao.setForeground(Color.decode("#003366"));
        botao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
    }
}
