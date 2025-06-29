package visual.passageiros;

import pessoas.Passageiro;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TelaReservaPoltrona extends JFrame {

    private String idVooSelecionado;
    private Passageiro passageiroLogado;
    private JButton[][] botoesAssentos;
    private List<String> assentosOcupados = new ArrayList<>();
    private String assentoSelecionado = null;

    private static final int FILAS = 20; // 20 Filas de assentos
    // Assentos serão A, B (corredor) C, D
    private static final String ARQUIVO_RESERVAS = "dados/reservas.csv";
    private static final ImageIcon ICONE_JANELINHA = carregarIconeJanelinha(); // Carrega a imagem da janelinha

    public TelaReservaPoltrona(String idVoo, Passageiro passageiro) {
        this.idVooSelecionado = idVoo;
        this.passageiroLogado = passageiro;

        setTitle("Reservar Poltrona para Voo " + idVooSelecionado);
        setSize(800, 700); // Ajusta o tamanho da janela para mais filas
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#e6f0ff"));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Escolha sua Poltrona", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24)); // Aumenta a fonte do título
        titulo.setForeground(Color.decode("#003366"));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel principal para os assentos e janelinhas
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(Color.decode("#e6f0ff"));

        // Painel para as filas (numeros) e os próprios assentos
        // Contamos 2 (colunas de assentos na esquerda) + 1 (espaço do corredor) + 2 (colunas de assentos na direita)
        // Mais 2 para as janelinhas nas pontas e 1 para o número da fila
        JPanel painelAssentos = new JPanel(new GridLayout(FILAS + 1, 1 + 2 + 1 + 2 + 2, 5, 5)); // +1 para cabeçalho, 1 para num fila, 2 para janelas, 1 para corredor
        painelAssentos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        painelAssentos.setBackground(Color.decode("#e6f0ff"));
        botoesAssentos = new JButton[FILAS][4]; // 4 assentos por fila (A, B, C, D)

        // Carrega os assentos ocupados para este voo antes de renderizar
        carregarAssentosOcupados();

        // Adiciona o cabeçalho (letras das colunas A B | C D) e janelinhas
        painelAssentos.add(new JLabel("")); // Canto superior esquerdo vazio (para o número da fila)
        painelAssentos.add(new JLabel("Janela", SwingConstants.CENTER)); // Espaço para janelinha esquerda
        painelAssentos.add(new JLabel("A", SwingConstants.CENTER)); // Assento A
        painelAssentos.add(new JLabel("B", SwingConstants.CENTER)); // Assento B
        painelAssentos.add(new JLabel("Corredor", SwingConstants.CENTER)); // Corredor
        painelAssentos.add(new JLabel("C", SwingConstants.CENTER)); // Assento C
        painelAssentos.add(new JLabel("D", SwingConstants.CENTER)); // Assento D
        painelAssentos.add(new JLabel("Janela", SwingConstants.CENTER)); // Espaço para janelinha direita

        // Adiciona os botões de assento
        for (int i = 0; i < FILAS; i++) {
            JLabel labelFila = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            labelFila.setFont(new Font("SansSerif", Font.BOLD, 14));
            labelFila.setForeground(Color.decode("#003366"));
            painelAssentos.add(labelFila); // Número da fila

            // Adiciona a janelinha esquerda
            JLabel janelaEsquerda = new JLabel(ICONE_JANELINHA);
            painelAssentos.add(janelaEsquerda);

            // Assento A
            String numeroAssentoA = (i + 1) + "A";
            JButton botaoA = criarBotaoAssento(numeroAssentoA);
            botoesAssentos[i][0] = botaoA;
            painelAssentos.add(botaoA);

            // Assento B
            String numeroAssentoB = (i + 1) + "B";
            JButton botaoB = criarBotaoAssento(numeroAssentoB);
            botoesAssentos[i][1] = botaoB;
            painelAssentos.add(botaoB);

            // Adiciona o espaço do corredor
            JPanel corredor = new JPanel();
            corredor.setBackground(Color.decode("#d9e6f2")); // Cor mais clara para o corredor
            painelAssentos.add(corredor);

            // Assento C
            String numeroAssentoC = (i + 1) + "C";
            JButton botaoC = criarBotaoAssento(numeroAssentoC);
            botoesAssentos[i][2] = botaoC;
            painelAssentos.add(botaoC);

            // Assento D
            String numeroAssentoD = (i + 1) + "D";
            JButton botaoD = criarBotaoAssento(numeroAssentoD);
            botoesAssentos[i][3] = botaoD;
            painelAssentos.add(botaoD);

            // Adiciona a janelinha direita
            JLabel janelaDireita = new JLabel(ICONE_JANELINHA);
            painelAssentos.add(janelaDireita);
        }
        painelCentral.add(new JScrollPane(painelAssentos), BorderLayout.CENTER); // Adiciona ScrollPane
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelInferior.setBackground(Color.decode("#e6f0ff"));

        JButton botaoConfirmar = new JButton("Confirmar Reserva");
        botaoConfirmar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoConfirmar.setBackground(Color.decode("#0052cc"));
        botaoConfirmar.setForeground(Color.WHITE);
        botaoConfirmar.setFocusPainted(false);
        botaoConfirmar.setPreferredSize(new Dimension(200, 50));
        botaoConfirmar.addActionListener(e -> confirmarReserva());
        painelInferior.add(botaoConfirmar);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        botaoVoltar.setBackground(Color.decode("#e6f0ff"));
        botaoVoltar.setForeground(Color.decode("#003366"));
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setPreferredSize(new Dimension(100, 40));
        botaoVoltar.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
        botaoVoltar.addActionListener(e -> dispose());
        painelInferior.add(botaoVoltar);

        add(painelInferior, BorderLayout.SOUTH);
    }

    // Método auxiliar para criar e configurar um botão de assento
    private JButton criarBotaoAssento(String numeroAssento) {
        JButton botao = new JButton(numeroAssento);
        botao.setFont(new Font("SansSerif", Font.PLAIN, 12));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(60, 50)); // Ajuste o tamanho do botão
        
        if (assentosOcupados.contains(numeroAssento)) {
            botao.setBackground(Color.decode("#ff9999")); // Ocupado
            botao.setForeground(Color.WHITE);
            botao.setEnabled(false);
            botao.setText(numeroAssento + "\n(Ocupado)"); // Pode ser que o \n não funcione bem em botão simples
            botao.setVerticalTextPosition(SwingConstants.CENTER);
            botao.setHorizontalTextPosition(SwingConstants.CENTER);
            botao.setMargin(new Insets(2,2,2,2)); // Ajusta margem para texto maior
        } else {
            botao.setBackground(Color.decode("#99ff99")); // Disponível
            botao.setForeground(Color.decode("#003366"));
            botao.addActionListener(e -> selecionarAssento(numeroAssento, botao));
        }
        return botao;
    }

    // Carrega o ícone da janelinha
    private static ImageIcon carregarIconeJanelinha() {
        try {
            // Tenta carregar do classpath (ideal para JAR)
            ImageIcon originalIcon = new ImageIcon(TelaReservaPoltrona.class.getResource("/imagens/janelinha.png"));
            if (originalIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // Redimensiona para um tamanho adequado (ex: 50x50)
                Image img = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar ou redimensionar a imagem da janelinha: " + e.getMessage());
        }
        // Retorna um ícone padrão se a imagem não puder ser carregada
        return new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB)); // Retorna uma imagem vazia transparente
    }

    private void carregarAssentosOcupados() {
        File arquivo = new File(ARQUIVO_RESERVAS);
        if (!arquivo.exists()) {
            try {
                // Garante que o diretório 'dados' exista
                File dir = new File("dados");
                if (!dir.exists()) dir.mkdirs();
                arquivo.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao criar arquivo de reservas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_RESERVAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String idVooReserva = partes[2].trim();
                    String numeroAssento = partes[3].trim();
                    if (idVooReserva.equals(idVooSelecionado)) {
                        assentosOcupados.add(numeroAssento);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler reservas existentes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarAssento(String numeroAssento, JButton botaoClicado) {
        // Desseleciona o assento anterior, se houver
        if (assentoSelecionado != null) {
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < 4; j++) { // 4 assentos por fila (A, B, C, D)
                    if (botoesAssentos[i][j] != null && botoesAssentos[i][j].getText().contains(assentoSelecionado)) {
                        botoesAssentos[i][j].setBackground(Color.decode("#99ff99")); // Volta para verde
                        botoesAssentos[i][j].setForeground(Color.decode("#003366"));
                        break;
                    }
                }
            }
        }

        // Seleciona o novo assento
        assentoSelecionado = numeroAssento;
        botaoClicado.setBackground(Color.decode("#ffcc66")); // Cor para assento selecionado (laranja claro)
        botaoClicado.setForeground(Color.decode("#003366"));
    }

    private void confirmarReserva() {
        if (assentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma poltrona para reservar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (passageiroLogado == null || passageiroLogado.getCPF() == null || passageiroLogado.getCPF().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não foi possível identificar o passageiro. Por favor, faça o login novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gera um ID de reserva simples (timestamp com cpf)
        String cpfPassageiro = passageiroLogado.getCPF();
        String idReserva = cpfPassageiro + "." + String.valueOf(System.currentTimeMillis());

        try (FileWriter fw = new FileWriter(ARQUIVO_RESERVAS, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            // Verifica se a combinação CPF, ID_Voo e Assento já existe (evita duplicação)
            if (isReservaDuplicada(cpfPassageiro, idVooSelecionado, assentoSelecionado)) {
                 JOptionPane.showMessageDialog(this, "Você já possui uma reserva para este voo e assento.", "Reserva Duplicada", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            out.println(idReserva + "," + cpfPassageiro + "," + idVooSelecionado + "," + assentoSelecionado);
            JOptionPane.showMessageDialog(this, "Poltrona " + assentoSelecionado + " reservada com sucesso para o voo " + idVooSelecionado + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Atualiza a visualização (desabilita o assento recém-reservado)
            // Percorre todos os botões de assento para encontrar o selecionado
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < 4; j++) { // 4 assentos por fila
                    JButton currentButton = botoesAssentos[i][j];
                    // Verifica se o texto do botão (apenas o número do assento) corresponde ao assento selecionado
                    if (currentButton != null && currentButton.getText().equals(assentoSelecionado)) {
                        currentButton.setBackground(Color.decode("#ff9999"));
                        currentButton.setForeground(Color.WHITE);
                        currentButton.setEnabled(false);
                        currentButton.setText(assentoSelecionado + "\n(Ocupado)");
                        currentButton.setVerticalTextPosition(SwingConstants.CENTER);
                        currentButton.setHorizontalTextPosition(SwingConstants.CENTER);
                        currentButton.setMargin(new Insets(2,2,2,2));
                        break; // Sai do loop interno
                    }
                }
            }
            assentosOcupados.add(assentoSelecionado); // Adiciona à lista de ocupados
            assentoSelecionado = null; // Limpa a seleção atual
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar a reserva: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar para verificar se a reserva já existe
    private boolean isReservaDuplicada(String cpf, String idVoo, String assento) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_RESERVAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4 && partes[1].trim().equals(cpf) && partes[2].trim().equals(idVoo) && partes[3].trim().equals(assento)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar duplicidade de reserva: " + e.getMessage());
        }
        return false;
    }
}
