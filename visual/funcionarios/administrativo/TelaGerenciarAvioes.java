package visual.funcionarios.administrativo;

import pessoas.Administrativo;
import enums.TipoAviao;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class TelaGerenciarAvioes extends JFrame {

    private static final String CAMINHO_ARQUIVO = "dados/avioes.csv";
    private JComboBox<TipoAviao> comboAdicionar;
    private JComboBox<TipoAviao> comboRemover;

    public TelaGerenciarAvioes(Administrativo adm) {
        setTitle("Gerenciar Aviões");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gerenciar Frota de Aviões", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(Color.decode("#e6f0ff"));

        List<TipoAviao> avioesAtuais = carregarAvioesDoCSV();
        List<TipoAviao> todosTipos = Arrays.asList(TipoAviao.values());

        List<TipoAviao> disponiveisParaAdicionar = new ArrayList<>(todosTipos);
        disponiveisParaAdicionar.removeAll(avioesAtuais);

        comboAdicionar = new JComboBox<>(disponiveisParaAdicionar.toArray(new TipoAviao[0]));
        comboRemover = new JComboBox<>(avioesAtuais.toArray(new TipoAviao[0]));

        // Sessão Adicionar
        JPanel painelAdicionar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelAdicionar.setBackground(Color.decode("#e6f0ff"));
        painelAdicionar.add(new JLabel("Adicionar avião:"));
        painelAdicionar.add(comboAdicionar);
        JButton btnAdicionar = new JButton("Adicionar à frota");
        estilizarBotao(btnAdicionar);
        btnAdicionar.addActionListener(e -> adicionarAviao());
        painelAdicionar.add(btnAdicionar);
        painelCentral.add(painelAdicionar);

        // Sessão Remover
        JPanel painelRemover = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelRemover.setBackground(Color.decode("#e6f0ff"));
        painelRemover.add(new JLabel("Remover avião:"));
        painelRemover.add(comboRemover);
        JButton btnRemover = new JButton("Remover da frota");
        estilizarBotao(btnRemover);
        btnRemover.addActionListener(e -> removerAviao());
        painelRemover.add(btnRemover);
        painelCentral.add(painelRemover);

        add(painelCentral, BorderLayout.CENTER);

        // Botão voltar
        JButton btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(Color.decode("#e6f0ff"));
        painelInferior.add(btnVoltar);

        add(painelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("SansSerif", Font.BOLD, 14));
        botao.setBackground(Color.decode("#0052cc"));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(160, 35));
    }

    private List<TipoAviao> carregarAvioesDoCSV() {
        List<TipoAviao> avioes = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) return avioes;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // pula o cabeçalho "tipo,identificacao"
                }
                try {
                    String tipoStr = linha.split(",")[0].trim(); // lê apenas o tipo
                    avioes.add(enums.TipoAviao.valueOf(tipoStr));
                } catch (IllegalArgumentException e) {
                    System.err.println("Tipo inválido no CSV: " + linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler aviões: " + e.getMessage());
        }
        return avioes;
    }


    private void adicionarAviao() {
        TipoAviao tipo = (TipoAviao) comboAdicionar.getSelectedItem();
        if (tipo == null) return;

        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
            pw.println(tipo.name());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Avião adicionado!");
        recarregar();
    }

    private void removerAviao() {
        TipoAviao tipo = (TipoAviao) comboRemover.getSelectedItem();
        if (tipo == null) return;

        List<TipoAviao> avioes = carregarAvioesDoCSV();
        avioes.remove(tipo);

        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            pw.println("tipo,identificacao"); // preserva o cabeçalho
            for (TipoAviao aviao : avioes) {
                pw.println(aviao.name()); // ou .toString(), se incluir mais dados
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Avião removido!");
        recarregar();
    }

    private void recarregar() {
        dispose();
        new TelaGerenciarAvioes(null).setVisible(true);
    }
}
