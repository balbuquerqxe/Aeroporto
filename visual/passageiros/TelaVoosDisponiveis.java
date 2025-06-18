package visual.passageiros;

import pessoas.Passageiro; // Importa a classe Passageiro
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.File; // Adiciona esta linha para resolver o erro 'File cannot be resolved to a type'
import java.io.FileWriter; // Adiciona esta linha para garantir que FileWriter também seja reconhecido

import javax.swing.event.ListSelectionEvent; // Para ouvir a seleção da tabela
import javax.swing.event.ListSelectionListener; // Para ouvir a seleção da tabela

public class TelaVoosDisponiveis extends JFrame {

    private JButton botaoSelecionarVoo;
    private JTable tabela;
    private Passageiro passageiroLogado; // Armazenar o passageiro logado

    // Construtor agora aceita um objeto Passageiro
    public TelaVoosDisponiveis(Passageiro passageiro) {
        this.passageiroLogado = passageiro; // Armazena o passageiro

        setTitle("Voos Disponíveis");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout()); // Define o layout para BorderLayout

        String[] colunas = { "ID", "Avião", "Destino", "Data Saída", "Horário Saída", "Data Chegada", "Horário Chegada",
                "Piloto", "Foto do Piloto" };
        DefaultTableModel modelo = new DefaultTableModel(null, colunas) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 8)
                    return ImageIcon.class; // coluna da foto
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // deixar tabela não editável
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(60);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(Color.decode("#003366"));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setSelectionBackground(Color.decode("#cce0ff")); // Cor de seleção da linha
        tabela.setSelectionForeground(Color.BLACK); // Cor do texto da linha selecionada

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER); // Adiciona a tabela ao centro

        // Carrega os dados do arquivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader("dados/voos.csv"))) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    primeira = false;
                    continue; // Pular cabeçalho
                }

                String[] partes = linha.split(",");
                if (partes.length >= 8) {
                    String piloto = partes[7].trim();

                    // Caminho da imagem do piloto
                    String caminhoImagem = "imagens/" + piloto + ".jpg";
                    ImageIcon icone = new ImageIcon(caminhoImagem);
                    // Redimensiona a imagem, se ela existir e for válida
                    Image imgRedimensionada = null;
                    if (icone.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        imgRedimensionada = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    } else {
                        // Se a imagem não carregar, usa um ícone de placeholder
                        // Ou pode deixar nulo para não exibir nada
                        imgRedimensionada = new ImageIcon(getClass().getResource("/imagens/default_pilot.jpg"))
                                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    }
                    ImageIcon finalIcone = (imgRedimensionada != null) ? new ImageIcon(imgRedimensionada) : null;
                   
                    modelo.addRow(new Object[] {
                            partes[0], partes[1], partes[2], partes[3],
                            partes[4], partes[5], partes[6], piloto, finalIcone
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler voos: " + e.getMessage(), "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
            // Cria um arquivo voos.csv de exemplo se não existir
            criarArquivoVoosExemplo();
        }

        // Painel para o botão de seleção
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.decode("#e6f0ff"));

        botaoSelecionarVoo = new JButton("Reservar Poltrona para Voo Selecionado");
        botaoSelecionarVoo.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoSelecionarVoo.setBackground(Color.decode("#0052cc"));
        botaoSelecionarVoo.setForeground(Color.WHITE);
        botaoSelecionarVoo.setFocusPainted(false);
        botaoSelecionarVoo.setEnabled(false); // Desabilita inicialmente

        botaoSelecionarVoo.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                String idVoo = (String) tabela.getValueAt(selectedRow, 0); // Pega o ID do voo da primeira coluna
                if (passageiroLogado != null) {
                    new TelaReservaPoltrona(idVoo, passageiroLogado).setVisible(true);
                    this.dispose(); // Fecha a tela de voos após selecionar para reservar
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: Passageiro não logado. Por favor, faça o login novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um voo na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        painelBotoes.add(botaoSelecionarVoo);
        add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona um listener para habilitar/desabilitar o botão de seleção
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                    botaoSelecionarVoo.setEnabled(true);
                } else {
                    botaoSelecionarVoo.setEnabled(false);
                }
            }
        });
    }

    // Construtor padrão (pode ser útil para testes, mas prefira o construtor com Passageiro)
    public TelaVoosDisponiveis() {
        this(null); // Chama o construtor com passageiro nulo para compatibilidade
    }

    // Método para criar um arquivo voos.csv de exemplo se ele não existir
    private void criarArquivoVoosExemplo() {
        File arquivo = new File("dados/voos.csv");
        if (!arquivo.exists()) {
            try {
                // Cria o diretório 'dados' se não existir
                new File("dados").mkdirs(); 
                FileWriter fw = new FileWriter(arquivo);
                fw.write("ID,Avião,Destino,Data Saída,Horário Saída,Data Chegada,Horário Chegada,Piloto,Foto Piloto\n");
                fw.write("V001,Airbus A320,Rio de Janeiro,2025-07-01,10:00,2025-07-01,11:30,João Silva,joao_silva.jpg\n");
                fw.write("V002,Boeing 737,São Paulo,2025-07-02,14:00,2025-07-02,15:30,Maria Souza,maria_souza.jpg\n");
                fw.write("V003,Embraer E195,Belo Horizonte,2025-07-03,08:00,2025-07-03,09:00,Carlos Mendes,carlos_mendes.jpg\n");
                fw.close();
                JOptionPane.showMessageDialog(this, "Arquivo 'dados/voos.csv' criado com dados de exemplo.", "Arquivo Criado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao criar arquivo de exemplo 'voos.csv': " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
