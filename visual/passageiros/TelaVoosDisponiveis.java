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
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Avião", "Destino", "Saída", "Chegada", "Piloto"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
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
                if (partes.length >= 6) {
                    modelo.addRow(partes);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler voos: " + e.getMessage());
        }

        getContentPane().add(scroll, BorderLayout.CENTER);
    }
}
