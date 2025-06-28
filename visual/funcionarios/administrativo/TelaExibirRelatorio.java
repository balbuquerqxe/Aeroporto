package visual.funcionarios.administrativo;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class TelaExibirRelatorio extends JFrame {

    public TelaExibirRelatorio(String conteudoDoRelatorio) {
        
        setTitle("Relatório Gerado");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        // Título da Janela
        JLabel titulo = new JLabel("Visualização de Relatório", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(titulo, BorderLayout.NORTH);

        // Área de Texto para o Relatório
        JTextArea areaDeTexto = new JTextArea();
        areaDeTexto.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte monoespaçada para alinhamento
        areaDeTexto.setEditable(false);
        areaDeTexto.setLineWrap(true);
        areaDeTexto.setWrapStyleWord(true);
        areaDeTexto.setMargin(new Insets(10, 10, 10, 10));
        areaDeTexto.setText(conteudoDoRelatorio);
        areaDeTexto.setCaretPosition(0); // Garante que o scroll comece do topo

        // Painel de Rolagem
        JScrollPane painelComRolagem = new JScrollPane(areaDeTexto);
        painelComRolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        painelComRolagem.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));

        // Adiciona um padding ao redor do painel de rolagem
        painelComRolagem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 20, 20, 20), // Margem externa
            BorderFactory.createLineBorder(Color.decode("#003366")) // Borda interna
        ));
        
        add(painelComRolagem, BorderLayout.CENTER);
    }
}