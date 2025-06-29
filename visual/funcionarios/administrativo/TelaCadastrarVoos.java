package visual.funcionarios.administrativo;
import aviao.Aviao;
import aviao.Voo;
import enums.Destino;
import enums.TipoAviao;
import pessoas.Administrativo;
import pessoas.Piloto;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.*;
import java.util.*;
import java.util.List;

public class TelaCadastrarVoos extends JFrame {

    private static final String CAMINHO_AVIOES = "dados/avioes.csv";

    public TelaCadastrarVoos(Administrativo adm, List<Piloto> pilotosDisponiveis) {
        setTitle("Cadastrar Voo");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        List<Aviao> avioesCSV = carregarAvioesDoCSV();

        JComboBox<Aviao> comboAvioes = new JComboBox<>(avioesCSV.toArray(new Aviao[0]));
        JComboBox<Destino> comboDestinos = new JComboBox<>(Destino.values());
        JComboBox<Piloto> comboPilotos = new JComboBox<>(pilotosDisponiveis.toArray(new Piloto[0]));

        JTextField campoSaida = new JTextField("10:00");
        JLabel labelChegadaCalculada = new JLabel("Será calculada automaticamente.");
        add(new JLabel("Hora de Chegada:"));
        add(labelChegadaCalculada);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinnerData = new JSpinner(dateModel);
        spinnerData.setEditor(new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy"));

        JButton cadastrar = new JButton("Cadastrar Voo");
        cadastrar.addActionListener(e -> {
            try {
                Aviao aviaoSelecionado = (Aviao) comboAvioes.getSelectedItem();
                Destino destinoSelecionado = (Destino) comboDestinos.getSelectedItem();
                Piloto pilotoSelecionado = (Piloto) comboPilotos.getSelectedItem();

                Date dataSelecionada = (Date) spinnerData.getValue();
                LocalDate dataLocal = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                LocalTime horaSaida = LocalTime.parse(campoSaida.getText());
                LocalDateTime saida = LocalDateTime.of(dataLocal, horaSaida);

                int distancia = destinoSelecionado.getDistanciaKm();
                int velocidade = aviaoSelecionado.getTipo().getVelocidadeMediaKmH();
                long duracaoMin = Math.round((double) distancia / velocidade * 60);
                LocalDateTime chegada = saida.plusMinutes(duracaoMin);

                Voo voo = new Voo(aviaoSelecionado, destinoSelecionado, saida, chegada, pilotoSelecionado);
                adm.cadastrarVoo(voo);
                adm.salvarVoosEmArquivo();

                removerAviaoDoCSV(aviaoSelecionado);

                JOptionPane.showMessageDialog(this, "Voo cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar voo: " + ex.getMessage());
            }
        });

        // Adicionando componentes na tela
        add(new JLabel("Avião:"));
        add(comboAvioes);

        add(new JLabel("Destino:"));
        add(comboDestinos);

        add(new JLabel("Piloto:"));
        add(comboPilotos);

        add(new JLabel("Data do Voo:"));
        add(spinnerData);

        add(new JLabel("Hora de Saída (HH:mm):"));
        add(campoSaida);

        add(new JLabel());
        add(cadastrar);
    }

    private List<Aviao> carregarAvioesDoCSV() {
        List<Aviao> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_AVIOES);
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeira = true;
            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    primeira = false;
                    continue;
                }
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    TipoAviao tipo = TipoAviao.valueOf(partes[0].trim());
                    String identificacao = partes[1].trim();
                    lista.add(new Aviao(identificacao, tipo));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar aviões: " + e.getMessage());
        }
        return lista;
    }

    private void removerAviaoDoCSV(Aviao aviao) {
        File arquivo = new File(CAMINHO_AVIOES);
        List<String> novasLinhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeira = true;
            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    novasLinhas.add(linha); // preserva cabeçalho
                    primeira = false;
                    continue;
                }
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    String tipo = partes[0].trim();
                    String id = partes[1].trim();
                    if (!(tipo.equals(aviao.getTipo().name()) && id.equals(aviao.getIdentificador()))) {
                        novasLinhas.add(linha);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar remoção: " + e.getMessage());
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (String linha : novasLinhas) {
                pw.println(linha);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar novo arquivo: " + e.getMessage());
        }
    }
}
