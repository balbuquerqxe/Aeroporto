package visual.funcionarios.administrativo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import pessoas.Administrativo;
import aviao.Voo;

public class TelaGerarRelatorios extends JFrame {

    private Administrativo admin;
    private Map<JCheckBox, Voo> mapaVoosCheckBoxes = new HashMap<>();

    public TelaGerarRelatorios(Administrativo funcionario) {
        this.admin = funcionario;

        setTitle("Gerar Relatório de Voos");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout(10, 10)); 

        JLabel titulo = new JLabel("Selecione os Voos para o Relatório", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.PAGE_AXIS)); 
        painelCentral.setBackground(Color.decode("#e6f0ff"));
        
        // Pega a lista de voos que este administrador cadastrou
        List<Voo> voosCadastrados = admin.getVoosCadastrados();

        if (voosCadastrados.isEmpty()) {
            JLabel aviso = new JLabel("Nenhum voo cadastrado por este administrador.");
            aviso.setFont(new Font("SansSerif", Font.ITALIC, 14));
            painelCentral.add(aviso);
        } else {
            for (Voo voo : voosCadastrados) {
                // Cria uma checkbox para cada voo, exibindo apenas seu ID.
                JCheckBox checkBoxVoo = new JCheckBox(voo.getId());
                checkBoxVoo.setFont(new Font("SansSerif", Font.PLAIN, 16));
                checkBoxVoo.setBackground(Color.decode("#e6f0ff"));
                checkBoxVoo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                painelCentral.add(checkBoxVoo);
                mapaVoosCheckBoxes.put(checkBoxVoo, voo); // Associa a checkbox ao voo
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(painelCentral);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 20, 0, 20), 
            BorderFactory.createLineBorder(Color.decode("#003366")) 
        ));
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        painelBotao.setBackground(Color.decode("#e6f0ff"));
        painelBotao.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton botaoGerar = new JButton("Gerar Relatório");
        botaoGerar.setFocusPainted(false);
        botaoGerar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoGerar.setBackground(Color.decode("#0052cc"));
        botaoGerar.setForeground(Color.WHITE);
        botaoGerar.setPreferredSize(new Dimension(200, 40));

        botaoGerar.addActionListener(e -> {
            List<Voo> voosSelecionados = new ArrayList<>();
            for (Map.Entry<JCheckBox, Voo> entry : mapaVoosCheckBoxes.entrySet()) {
                if (entry.getKey().isSelected()) {
                    voosSelecionados.add(entry.getValue());
                }
            }
            
            if (voosSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione pelo menos um voo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            String conteudoDoRelatorio = admin.gerarConteudoRelatorio(voosSelecionados);

            boolean salvouComSucesso = admin.salvarRelatorioEmArquivo(conteudoDoRelatorio);
            
            if (salvouComSucesso) {
                JOptionPane.showMessageDialog(this, "Relatório salvo em arquivo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                new TelaExibirRelatorio(conteudoDoRelatorio).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar o arquivo do relatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        painelBotao.add(botaoGerar);
        add(painelBotao, BorderLayout.SOUTH);
    }
}