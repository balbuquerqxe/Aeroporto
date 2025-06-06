package visual.funcionarios.administrativo;

import aviao.Aviao;
import aviao.Voo;
import enums.Destino;
import java.awt.*;
import java.time.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import pessoas.Administrativo;
import pessoas.Piloto;

public class TelaCadastrarVoos extends JFrame {

    public TelaCadastrarVoos(Administrativo adm, List<Aviao> avioesDisponiveis, List<Piloto> pilotosDisponiveis) {
        setTitle("Cadastrar Voo");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        // Componentes de seleção
        JComboBox<Aviao> comboAvioes = new JComboBox<>(avioesDisponiveis.toArray(new Aviao[0]));
        JComboBox<Destino> comboDestinos = new JComboBox<>(Destino.values());
        JComboBox<Piloto> comboPilotos = new JComboBox<>(pilotosDisponiveis.toArray(new Piloto[0]));

        JTextField campoSaida = new JTextField("10:00");
        JLabel labelChegadaCalculada = new JLabel("Será calculada automaticamente.");
        add(new JLabel("Hora de Chegada:"));
        add(labelChegadaCalculada);

        // Seletor de data
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinnerData = new JSpinner(dateModel);
        spinnerData.setEditor(new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy"));

        // Botão cadastrar
        JButton cadastrar = new JButton("Cadastrar Voo");
        cadastrar.addActionListener(e -> {
            try {
                Aviao aviaoSelecionado = (Aviao) comboAvioes.getSelectedItem();
                Destino destinoSelecionado = (Destino) comboDestinos.getSelectedItem();
                Piloto pilotoSelecionado = (Piloto) comboPilotos.getSelectedItem();

                // Pega a data
                Date dataSelecionada = (Date) spinnerData.getValue();
                LocalDate dataLocal = dataSelecionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Pega os horários
                LocalTime horaSaida = LocalTime.parse(campoSaida.getText());
                LocalDateTime saida = LocalDateTime.of(dataLocal, horaSaida);

                // cálculo automático
                int distancia = destinoSelecionado.getDistanciaKm();
                int velocidade = aviaoSelecionado.getTipo().getVelocidadeMediaKmH();
                long duracaoHoras = Math.round((double) distancia / velocidade * 60); // minutos
                LocalDateTime chegada = saida.plusMinutes(duracaoHoras);

                Voo voo = new Voo(aviaoSelecionado, destinoSelecionado, saida, chegada, pilotoSelecionado);
                adm.cadastrarVoo(voo);
                adm.salvarVoosEmArquivo();
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
}
