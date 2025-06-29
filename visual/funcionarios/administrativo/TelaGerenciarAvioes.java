package visual.funcionarios.administrativo;

import pessoas.Administrativo;
import enums.TipoAviao;

import javax.swing.*;

import aviao.Aviao;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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

        List<TipoAviao> avioesAtuais = carregarAvioesDoCSV();
        List<TipoAviao> todosTipos = Arrays.asList(TipoAviao.values());

        List<TipoAviao> disponiveisParaAdicionar = new ArrayList<>(todosTipos);
        disponiveisParaAdicionar.removeAll(avioesAtuais);

        comboAdicionar = new JComboBox<>(disponiveisParaAdicionar.toArray(new TipoAviao[0]));
        comboRemover = new JComboBox<>();  

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
        TipoAviao selecionado = (TipoAviao) comboAdicionar.getSelectedItem();
        if (selecionado == null) return;

        String identificador = gerarIdentificador("BR");
        File arquivo = new File(CAMINHO_ARQUIVO);

        try {
            boolean arquivoExiste = arquivo.exists();
            boolean precisaPularLinha = false;

            // Verifica se o arquivo termina com quebra de linha
            if (arquivoExiste && arquivo.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
                    raf.seek(arquivo.length() - 1);
                    int ultimoByte = raf.read();
                    if (ultimoByte != '\n') {
                        precisaPularLinha = true;
                    }
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
                if (!arquivoExiste) {
                    pw.println("tipo,identificacao"); // escreve cabeçalho se for novo
                } else if (precisaPularLinha) {
                    pw.println(); // adiciona quebra de linha só se necessário
                }

                pw.println(selecionado.name() + "," + identificador);
            }

            JOptionPane.showMessageDialog(this, "Avião adicionado com sucesso: " + identificador);
            recarregarCombos();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar avião: " + e.getMessage());
        }
    }



    private void recarregarCombos() {
        comboAdicionar.removeAllItems();
        comboRemover.removeAllItems();

        // Adicionar todos os tipos de avião no comboAdicionar, mesmo que já existam no CSV
        for (TipoAviao tipo : TipoAviao.values()) {
            comboAdicionar.addItem(tipo);
        }

        // Carrega os aviões do CSV para o comboRemover
        List<Aviao> avioesNoCSV = Aviao.carregarDeCSV(CAMINHO_ARQUIVO);
        for (Aviao aviao : avioesNoCSV) {
            comboRemover.addItem(aviao);
        }

        // Habilita ou desabilita os combos com base no conteúdo
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

    private void removerAviao() {
        Aviao aviaoSelecionado = (Aviao) comboRemover.getSelectedItem();
        if (aviaoSelecionado == null) return;

        List<Aviao> avioes = Aviao.carregarDeCSV(CAMINHO_ARQUIVO);
        avioes.removeIf(a -> a.getIdentificador().equals(aviaoSelecionado.getIdentificador()));

        try (PrintWriter pw = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            pw.println("tipo,identificacao");
            for (Aviao aviao : avioes) {
                pw.println(aviao.getTipo().name() + "," + aviao.getIdentificador());
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
