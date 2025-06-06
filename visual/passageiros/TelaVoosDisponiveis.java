package visual.passageiros;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaVoosDisponiveis extends JFrame {

    public TelaVoosDisponiveis() {
        setTitle("Voos Disponíveis");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(60);
        JScrollPane scroll = new JScrollPane(tabela);

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
                    Image imgRedimensionada = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    ImageIcon finalIcone = new ImageIcon(imgRedimensionada);

                    // Adiciona linha com imagem
                    modelo.addRow(new Object[] {
                            partes[0], partes[1], partes[2], partes[3],
                            partes[4], partes[5], partes[6], piloto, finalIcone
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler voos: " + e.getMessage());
        }

        getContentPane().add(scroll, BorderLayout.CENTER);
    }
}
