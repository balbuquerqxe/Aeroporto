package visual.funcionarios.piloto;

import pessoas.Piloto;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

public class TelaVoosDoPiloto extends JFrame {

    private JTable tabela;
    private Piloto pilotoLogado;
    private JButton botaoEscolherLanches; // O novo botão

    public TelaVoosDoPiloto(Piloto piloto) {
        this.pilotoLogado = piloto;

        setTitle("Meus Voos Agendados - " + piloto.getNome());
        setSize(800, 500); // Um pouco mais de altura para os botões inferiores
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Próximos Voos Agendados", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        String[] colunas = { "ID", "Avião", "Destino", "Data Saída", "Horário Saída", "Data Chegada", "Horário Chegada", "Piloto" };
        DefaultTableModel modelo = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(Color.decode("#003366"));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setSelectionBackground(Color.decode("#cce0ff"));
        tabela.setSelectionForeground(Color.BLACK);
        
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarVoosDoPiloto(modelo);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10)); // Botões à direita
        painelBotoes.setBackground(Color.decode("#e6f0ff"));

        // --- Novo Botão: Escolher Lanches ---
        botaoEscolherLanches = new JButton("Escolher Lanches");
        botaoEscolherLanches.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoEscolherLanches.setBackground(Color.decode("#0052cc"));
        botaoEscolherLanches.setForeground(Color.WHITE);
        botaoEscolherLanches.setFocusPainted(false);
        botaoEscolherLanches.setPreferredSize(new Dimension(180, 45));
        botaoEscolherLanches.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2));
        botaoEscolherLanches.setEnabled(false); // Desabilitado inicialmente
        botaoEscolherLanches.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                String idVoo = (String) tabela.getValueAt(selectedRow, 0); // Pega o ID do voo
                new TelaEscolherLanches(idVoo).setVisible(true); // Abre a tela de escolha de lanches
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um voo na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });
        painelBotoes.add(botaoEscolherLanches);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoVoltar.setBackground(Color.decode("#e6f0ff")); // Estilo de botão "voltar"
        botaoVoltar.setForeground(Color.decode("#003366"));
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setPreferredSize(new Dimension(120, 45));
        botaoVoltar.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2));
        botaoVoltar.addActionListener(e -> dispose());
        painelBotoes.add(botaoVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona um listener para habilitar/desabilitar o botão de escolha de lanches
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                    botaoEscolherLanches.setEnabled(true);
                } else {
                    botaoEscolherLanches.setEnabled(false);
                }
            }
        });
    }

    private void carregarVoosDoPiloto(DefaultTableModel modelo) {
        String caminhoArquivoVoos = "dados/voos.csv";
        File arquivoVoos = new File(caminhoArquivoVoos);

        if (!arquivoVoos.exists()) {
            JOptionPane.showMessageDialog(this, "Arquivo de voos não encontrado em: " + caminhoArquivoVoos, "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoVoos))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length >= 8) {
                    String nomePilotoNoVoo = partes[7].trim();

                    if (pilotoLogado != null && pilotoLogado.getNome().equals(nomePilotoNoVoo)) {
                        modelo.addRow(new Object[] {
                                partes[0], partes[1], partes[2], partes[3],
                                partes[4], partes[5], partes[6], partes[7]
                        });
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler voos agendados: " + e.getMessage(), "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        if (modelo.getRowCount() == 0 && pilotoLogado != null) {
            JOptionPane.showMessageDialog(this, "Nenhum voo agendado encontrado para o piloto " + pilotoLogado.getNome() + ".", "Voos Não Encontrados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
