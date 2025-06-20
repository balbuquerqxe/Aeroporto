package visual.funcionarios.piloto;

import enums.Lanche;
import javax.swing.*;
import javax.swing.border.Border; // Importa Border
import javax.swing.border.LineBorder; // Importa LineBorder
import java.awt.*;
import java.awt.event.MouseAdapter; // Para cliques do mouse
import java.awt.event.MouseEvent;   // Para eventos do mouse
import java.awt.image.BufferedImage; // Para imagem de fallback
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet; // Para armazenar lanches selecionados

public class TelaEscolherLanches extends JFrame {

    private String idVoo;
    private static final String ARQUIVO_LANCHES_VOO = "dados/lanches_voo.csv";
    private Set<Lanche> lanchesSelecionados = new HashSet<>();
    private Map<Lanche, JPanel> paineisLanches = new HashMap<>();

    private final Border BORDA_NORMAL = BorderFactory.createLineBorder(Color.decode("#CCCCCC"), 2, true); // Cinza claro, arredondada
    private final Border BORDA_SELECIONADA = BorderFactory.createLineBorder(Color.decode("#0052cc"), 3, true); // Azul, mais grossa, arredondada
    private final Border BORDA_PADRAO_IMAGEM = BorderFactory.createEmptyBorder(5, 5, 5, 5); // Padding para a imagem dentro do painel

    public TelaEscolherLanches(String idVoo) {
        this.idVoo = idVoo;

        setTitle("Escolher Lanches para Voo " + idVoo);
        setSize(700, 750); // Aumenta o tamanho para acomodar as imagens e mais lanches
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Definir Lanches para o Voo " + idVoo, SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28)); // Fonte maior
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Padding inferior
        add(titulo, BorderLayout.NORTH);

        // Painel para conter os itens de lanche (imagens + nome)
        // Usaremos GridLayout para organizar em colunas, com 3 colunas de lanches
        JPanel painelItensLanches = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 colunas, espaçamento 20x20
        painelItensLanches.setBackground(Color.decode("#e6f0ff"));
        
        JScrollPane scrollPane = new JScrollPane(painelItensLanches);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));

        // Carrega lanches já salvos para este voo, se houver, para pré-selecionar
        Set<String> lanchesExistentes = carregarLanchesExistentes(idVoo);

        for (Lanche lanche : Lanche.values()) {
            JPanel itemPanel = criarPainelLanche(lanche);
            paineisLanches.put(lanche, itemPanel); // Mapeia o Lanche para o seu painel

            if (lanchesExistentes.contains(lanche.getNome())) {
                lanchesSelecionados.add(lanche);
                itemPanel.setBorder(BORDA_SELECIONADA);
            } else {
                itemPanel.setBorder(BORDA_NORMAL);
            }

            painelItensLanches.add(itemPanel);
        }

        add(scrollPane, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelInferior.setBackground(Color.decode("#e6f0ff"));

        JButton botaoSalvar = new JButton("Salvar Lanches Selecionados");
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18)); // Fonte maior
        botaoSalvar.setBackground(Color.decode("#0052cc"));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setPreferredSize(new Dimension(250, 50)); // Tamanho maior
        botaoSalvar.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 2));
        botaoSalvar.addActionListener(e -> salvarLanches());
        painelInferior.add(botaoSalvar);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Fonte maior
        botaoVoltar.setBackground(Color.decode("#e6f0ff"));
        botaoVoltar.setForeground(Color.decode("#003366"));
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setPreferredSize(new Dimension(120, 45)); // Tamanho
        botaoVoltar.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
        botaoVoltar.addActionListener(e -> dispose());
        painelInferior.add(botaoVoltar);

        add(painelInferior, BorderLayout.SOUTH);
    }

    // Método para criar um painel clicável para cada lanche
    private JPanel criarPainelLanche(Lanche lanche) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); 
        panel.setBackground(Color.WHITE); // Fundo branco para os itens de lanche
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mão para indicar clicável

        // Carrega a imagem do lanche
        ImageIcon lancheIcon = carregarImagemLanche(lanche.getImagemPath());
        JLabel imagemLabel = new JLabel(lancheIcon);
        imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagemLabel.setVerticalAlignment(SwingConstants.CENTER);
        imagemLabel.setBorder(BORDA_PADRAO_IMAGEM); // Adiciona padding à imagem

        JLabel nomeLabel = new JLabel(lanche.getNome(), SwingConstants.CENTER);
        nomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nomeLabel.setForeground(Color.decode("#333333")); // Cor de texto para o nome

        panel.add(imagemLabel, BorderLayout.CENTER);
        panel.add(nomeLabel, BorderLayout.SOUTH);

        // Adiciona o listener de clique
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lanchesSelecionados.contains(lanche)) {
                    lanchesSelecionados.remove(lanche);
                    panel.setBorder(BORDA_NORMAL); // Desseleciona
                } else {
                    lanchesSelecionados.add(lanche);
                    panel.setBorder(BORDA_SELECIONADA); // Seleciona
                }
            }
        });

        return panel;
    }

    // Método para carregar e redimensionar a imagem do lanche
    private ImageIcon carregarImagemLanche(String filename) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource("/imagens/lanches/" + filename));
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE || icon.getIconWidth() == -1) {
                System.err.println("Imagem '" + filename + "' não carregada corretamente. Usando fallback.");
                icon = null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem '" + filename + "': " + e.getMessage());
            icon = null; // Garante que o fallback será usado
        }

        if (icon == null) {
            // Fallback: Imagem padrão ou imagem vazia
            try {
                icon = new ImageIcon(getClass().getResource("/imagens/default_food.png")); // Tente carregar uma imagem padrão
                if (icon.getImageLoadStatus() != MediaTracker.COMPLETE || icon.getIconWidth() == -1) {
                    System.err.println("Imagem de fallback 'default_food.png' não carregada. Usando imagem vazia.");
                    icon = new ImageIcon(new BufferedImage(120, 90, BufferedImage.TYPE_INT_ARGB)); // Cria uma imagem vazia
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem de fallback: " + e.getMessage());
                icon = new ImageIcon(new BufferedImage(120, 90, BufferedImage.TYPE_INT_ARGB)); // Garante uma imagem vazia
            }
        }

        // Redimensiona a imagem para um tamanho padrão para todos os lanches
        int larguraDesejada = 120;
        int alturaDesejada = 90;
        Image img = icon.getImage().getScaledInstance(larguraDesejada, alturaDesejada, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // Carrega os lanches já salvos para um voo específico (apenas o nome do lanche)
    private Set<String> carregarLanchesExistentes(String vooId) {
        Set<String> lanches = new HashSet<>();
        File arquivo = new File(ARQUIVO_LANCHES_VOO);

        if (!arquivo.exists()) {
            return lanches;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                // Formato esperado: ID_Voo,Nome_Lanche,Quantidade (se houver)
                if (partes.length >= 2 && partes[0].trim().equals(vooId)) {
                    lanches.add(partes[1].trim()); // Adiciona o nome do lanche selecionado
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar lanches existentes para pré-seleção: " + e.getMessage());
        }
        return lanches;
    }

    // Salva os lanches selecionados no arquivo CSV
    private void salvarLanches() {
        List<String> linhasParaManter = new ArrayList<>();
        File arquivo = new File(ARQUIVO_LANCHES_VOO);
        boolean arquivoExiste = arquivo.exists();

        if (arquivoExiste) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (!linha.startsWith(idVoo + ",")) {
                        linhasParaManter.add(linha);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo de lanches para atualização: " + e.getMessage());
            }
        } else {
            // Garante que o diretório 'dados' exista se o arquivo for novo
            File dir = new File("dados");
            if (!dir.exists()) dir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_LANCHES_VOO, false))) { // 'false' para sobrescrever
            for (String linha : linhasParaManter) {
                bw.write(linha);
                bw.newLine();
            }

            // Adiciona as novas seleções do voo atual com quantidade 1 (já que não há spinner de quantidade individual)
            for (Lanche lanche : lanchesSelecionados) {
                bw.write(idVoo + "," + lanche.getNome() + "," + 1); // Salva quantidade 1 para o lanche selecionado
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Lanches salvos com sucesso para o voo " + idVoo + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Fecha a tela após salvar
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os lanches: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("Erro ao salvar lanches: " + e.getMessage());
        }
    }
}
