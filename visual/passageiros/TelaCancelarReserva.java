package visual.passageiros;

import pessoas.Passageiro;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

import java.util.List;

public class TelaCancelarReserva extends JFrame {

    private static final String ARQUIVO_RESERVAS = "dados/reservas.csv";
    private Passageiro passageiroLogado;
    private JComboBox<String> comboReservas;
    private Map<String, String> mapaLinhaCompletaPorId = new HashMap<>();

    public TelaCancelarReserva(Passageiro passageiro) {
        this.passageiroLogado = passageiro;
        setTitle("Cancelar Reserva");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#e6f0ff"));

        JLabel label = new JLabel("Selecione a reserva que deseja cancelar:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(label, BorderLayout.NORTH);

        comboReservas = new JComboBox<>();
        carregarReservas();
        add(comboReservas, BorderLayout.CENTER);

        JButton btnCancelar = new JButton("Cancelar Reserva");
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancelar.setBackground(Color.decode("#0052cc"));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(180, 40));
        btnCancelar.addActionListener(e -> cancelarReserva());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVoltar.setBackground(Color.decode("#e6f0ff"));
        btnVoltar.setForeground(Color.decode("#003366"));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setPreferredSize(new Dimension(100, 40));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.decode("#003366"), 1));
        btnVoltar.addActionListener(e -> dispose());

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelInferior.setBackground(Color.decode("#e6f0ff"));
        painelInferior.add(btnCancelar);
        painelInferior.add(btnVoltar);

        add(painelInferior, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void carregarReservas() {
        comboReservas.removeAllItems();
        mapaLinhaCompletaPorId.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_RESERVAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4 && partes[1].trim().equals(passageiroLogado.getCPF())) {
                    String idReserva = partes[0].trim();
                    String info = "Voo " + partes[2].trim() + " - Assento " + partes[3].trim() + " (ID: " + idReserva + ")";
                    comboReservas.addItem(info);
                    mapaLinhaCompletaPorId.put(info, linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        if (comboReservas.getItemCount() == 0) {
            comboReservas.addItem("Nenhuma reserva encontrada");
            comboReservas.setEnabled(false);
        }
    }

    private void cancelarReserva() {
        String selecionado = (String) comboReservas.getSelectedItem();
        if (selecionado == null || !mapaLinhaCompletaPorId.containsKey(selecionado)) {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva válida.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String linhaParaRemover = mapaLinhaCompletaPorId.get(selecionado);
        List<String> novasLinhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_RESERVAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.equals(linhaParaRemover)) {
                    novasLinhas.add(linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_RESERVAS))) {
            for (String novaLinha : novasLinhas) {
                pw.println(novaLinha);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Reserva cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carregarReservas(); // Atualiza a tela
    }
}