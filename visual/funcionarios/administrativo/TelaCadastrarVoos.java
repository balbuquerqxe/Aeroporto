package visual.funcionarios.administrativo;

import aviao.Aviao;
import aviao.Voo;
import enums.Destino;
import pessoas.Administrativo;
import pessoas.Piloto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public class TelaCadastrarVoos extends JFrame {

    public TelaCadastrarVoos(Administrativo adm, List<Aviao> avioesDisponiveis, List<Piloto> pilotosDisponiveis) {
        setTitle("Cadastrar Voo");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        // Componentes de seleção
        JComboBox<Aviao> comboAvioes = new JComboBox<>(avioesDisponiveis.toArray(new Aviao[0]));
        JComboBox<Destino> comboDestinos = new JComboBox<>(Destino.values());
        JComboBox<Piloto> comboPilotos = new JComboBox<>(pilotosDisponiveis.toArray(new Piloto[0]));
        JTextField campoSaida = new JTextField("10:00");
        JTextField campoChegada = new JTextField("14:00");

        // Botão cadastrar
        JButton cadastrar = new JButton("Cadastrar Voo");
        cadastrar.addActionListener(e -> {
            Aviao aviaoSelecionado = (Aviao) comboAvioes.getSelectedItem();
            Destino destinoSelecionado = (Destino) comboDestinos.getSelectedItem();
            Piloto pilotoSelecionado = (Piloto) comboPilotos.getSelectedItem();

            try {
                LocalTime horaSaida = LocalTime.parse(campoSaida.getText());
                LocalTime horaChegada = LocalTime.parse(campoChegada.getText());

                Voo voo = new Voo(aviaoSelecionado, destinoSelecionado, pilotoSelecionado, horaSaida, horaChegada);
                adm.cadastrarVoo(voo);
                JOptionPane.showMessageDialog(this, "Voo cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar voo: " + ex.getMessage());
            }
        });

        // Labels e campos
        add(new JLabel("Avião:"));
        add(comboAvioes);

        add(new JLabel("Destino:"));
        add(comboDestinos);

        add(new JLabel("Piloto:"));
        add(comboPilotos);

        add(new JLabel("Hora de Saída (HH:mm):"));
        add(campoSaida);

        add(new JLabel("Hora de Chegada (HH:mm):"));
        add(campoChegada);

        add(new JLabel()); // espaço em branco
        add(cadastrar);
    }
}
