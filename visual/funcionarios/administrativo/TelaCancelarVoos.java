package visual.funcionarios.administrativo;

import aviao.Aviao;
import aviao.Voo;
import enums.Destino;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import pessoas.Administrativo;
import pessoas.Piloto;


public class TelaCancelarVoos extends JFrame{
    
    public TelaCancelarVoos(Administrativo adm, List<Aviao> avioesDisponiveis, List<Piloto> pilotosDisponiveis) {
        setTitle("Cancelar Voo");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        // Componentes de seleção
        JComboBox<String> comboVoos = new JComboBox<>();
        carregarVoos("/dados/voos.csv", comboVoos);
        add(comboVoos);
        
        setVisible(true);        
    }

    private void carregarVoos(String caminhoCSV, JComboBox<String> comboBox) {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(caminhoCSV));
            for (String linha : linhas) {
                comboBox.addItem(linha);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }

}
