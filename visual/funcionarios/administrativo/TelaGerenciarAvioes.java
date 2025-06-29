package visual.funcionarios.administrativo;

import pessoas.Administrativo;
import enums.TipoAviao;
import aviao.Aviao;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class TelaGerenciarAvioes extends JFrame {

    private static final String CAMINHO_ARQUIVO = "dados/avioes.csv";
    private JComboBox<TipoAviao> comboAdicionar;
    private JComboBox<Aviao> comboRemover;

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

        comboAdicionar = new JComboBox<>();
        comboRemover = new JComboBox<>();

        recarregarCombos(); // carrega ambos ao iniciar

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

    private void adicionarAviao() {
        TipoAviao selecionado = (TipoAviao) comboAdicionar.getSelectedItem();
        if (selecionado == null) return;

        String identificador = gerarIdentificador("BR");
        File arquivo = new File(CAMINHO_ARQUIVO);
        boolean precisaPular = arquivo.exists() && arquivo.length() > 0;

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
            if (precisaPular) {
                pw.print(System.lineSeparator());
            } else {
                pw.println("tipo,identificacao"); // escreve cabeçalho se for novo
            }
            pw.print(selecionado.name() + "," + identificador);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar avião: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Avião adicionado com sucesso: " + identificador);
        recarregarCombos();
    }

    private void removerAviao() {
        Aviao aviaoSelecionado = (Aviao) comboRemover.getSelectedItem();
        if (aviaoSelecionado == null) return;

        List<Aviao> avioes = Aviao.carregarDeCSV(CAMINHO_ARQUIVO);
        avioes.removeIf(a -> a.getIdentificador().equals(aviaoSelecionado.getIdentificador()));

        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            pw.println("tipo,identificacao");
            for (Aviao a : avioes) {
                pw.println(a.getTipo().name() + "," + a.getIdentificador());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Avião removido!");
        recarregarCombos();
    }

    private void recarregarCombos() {
        comboAdicionar.removeAllItems();
        comboRemover.removeAllItems();

        Set<TipoAviao> tiposDisponiveis = new HashSet<>(Arrays.asList(TipoAviao.values()));
        List<Aviao> avioesNoCSV = Aviao.carregarDeCSV(CAMINHO_ARQUIVO);

        // Popular combo de adicionar (todos os tipos disponíveis)
        for (TipoAviao tipo : tiposDisponiveis) {
            comboAdicionar.addItem(tipo);
        }

        // Popular combo de remover (todos os aviões)
        for (Aviao aviao : avioesNoCSV) {
            comboRemover.addItem(aviao);
        }

        comboAdicionar.setEnabled(comboAdicionar.getItemCount() > 0);
        comboRemover.setEnabled(comboRemover.getItemCount() > 0);
    }

    private String gerarIdentificador(String pais) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(letras.charAt(rand.nextInt(letras.length())));
        }
        return pais.toUpperCase() + "-" + sb.toString();
    }
}
