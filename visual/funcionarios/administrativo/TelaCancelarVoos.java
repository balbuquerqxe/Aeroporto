package visual.funcionarios.administrativo;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import pessoas.Administrativo;

public class TelaCancelarVoos extends JFrame {

    private JComboBox<String> comboVoos;
    private Administrativo adm;
    private JFrame telaAnterior;

    public TelaCancelarVoos(Administrativo adm, JFrame telaAnterior) {
        this.adm = adm;
        this.telaAnterior = telaAnterior;

        setTitle("Cancelar Voo");
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitulo = new JLabel("Selecione o voo para cancelar:", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(labelTitulo, BorderLayout.NORTH);

        comboVoos = new JComboBox<>();
        carregarVoos("dados/voos.csv", comboVoos);
        add(comboVoos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.setBackground(Color.decode("#e6f0ff"));

        JButton botaoCancelar = new JButton("Cancelar Voo Selecionado");
        botaoCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoCancelar.setBackground(Color.decode("#cc0000")); // nao tá pegando o vermelho no macbook
        botaoCancelar.setForeground(Color.WHITE);           

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoVoltar.addActionListener(e -> voltarParaTelaAnterior());

        painelBotoes.add(botaoCancelar, BorderLayout.EAST); 
        painelBotoes.add(botaoVoltar, BorderLayout.WEST);  

        add(painelBotoes, BorderLayout.SOUTH);

        botaoCancelar.addActionListener(e -> {
            String vooSelecionado = (String) comboVoos.getSelectedItem();
            if (vooSelecionado == null || vooSelecionado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um voo para cancelar.", "Nenhum Voo Selecionado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja cancelar o voo?\n" + vooSelecionado, "Confirmar Cancelamento", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                String idVoo = vooSelecionado.split(",")[0];
                try {
                    boolean sucesso = adm.cancelarVooPorId(idVoo);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Voo cancelado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        comboVoos.removeItem(vooSelecionado);
                    } else {
                        JOptionPane.showMessageDialog(this, "Não foi possível encontrar o voo para cancelar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo de voos: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarParaTelaAnterior();
            }
        });

        setVisible(true);
    }

    private void carregarVoos(String caminhoCSV, JComboBox<String> comboBox) {
        File arquivo = new File(caminhoCSV);
        if (!arquivo.exists()) {
            JOptionPane.showMessageDialog(this, "Arquivo de voos não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                comboBox.addItem(linha);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de voos: " + e.getMessage(), "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void voltarParaTelaAnterior() {
        if (telaAnterior != null) {
            telaAnterior.setVisible(true);
        }
        dispose();
    }
}