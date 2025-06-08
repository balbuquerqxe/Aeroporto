package aviao;

import enums.TipoAviao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Classe do avião.
public class Aviao {
    // Qual é o código que identifica o avião.
    private String identificador;
    // Qual é o modelo do avião (definido pelo Enum).
    private TipoAviao tipo;

    // Mapeamento das poltronas executivas para reserva.
    private Map<String, Boolean> poltronasExecutivas;

    // Mapeamento das poltronas econômicas para reserva.
    private Map<String, Boolean> poltronasEconomicas;

    // Inicializa o avião.
    public Aviao(String identificador, TipoAviao tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
        // Para o mapeamento das poltronas.
        this.poltronasExecutivas = gerarPoltronas(tipo.getPoltronasExecutivas(), "E");
        // Para o mapeamento das poltronas.
        this.poltronasEconomicas = gerarPoltronas(tipo.getPoltronasEconomicas(), "C");
    }

    // Mapa das poltronas para que o usuário escolha.
    private Map<String, Boolean> gerarPoltronas(int quantidade, String prefixo) {
        Map<String, Boolean> mapa = new LinkedHashMap<>();
        for (int i = 1; i <= quantidade; i++) {
            // Quando for "false", a poltrona estará desocupada.
            mapa.put(prefixo + i, false);
        }
        // Retorna o mapa.
        return mapa;
    }

    // Retorna qual é o avião.
    public String getIdentificador() {
        return identificador;
    }

    // Retorna o tipo de avião.
    public TipoAviao getTipo() {
        return tipo;
    }

    // Retorna mapa das poltronas executivas.
    public Map<String, Boolean> getPoltronasExecutivas() {
        return poltronasExecutivas;
    }

    // Retorna mapa das poltronas econômicas.
    public Map<String, Boolean> getPoltronasEconomicas() {
        return poltronasEconomicas;
    }

    // Ocupa uma poltrona (não sei se deve ficar aqui ou no passageiro...)
    public void ocupaPoltrona(String codigo) {
        // Se o código da poltrona for executiva...
        if (poltronasExecutivas.containsKey(codigo)) {
            //...ocupa a poltrona executiva.
            poltronasExecutivas.put(codigo, true);
        // Se a poltrona for economica...
        } else if (poltronasEconomicas.containsKey(codigo)) {
            //...ocupa a poltrona economica.
            poltronasEconomicas.put(codigo, true);
        }
    }

    // Verifica se a poltrona está disponível.
    public boolean PoltronaDisponivel(String codigo) {
        // Verifica as poltronas executivas.
        if (poltronasExecutivas.containsKey(codigo)) {
            return !poltronasExecutivas.get(codigo);
        // Verifica as poltronas economicas.
        } else if (poltronasEconomicas.containsKey(codigo)) {
            return !poltronasEconomicas.get(codigo);
        }
        // Está ocupada!
        return false;
    }

    // Leitura do arquivo CSV, que possui todos os aviões do aeroporto.
    public static List<Aviao> carregarDeCSV(String caminho) {
        // Lista os aviões do aeroporto.
        List<Aviao> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                // Pula a primeira linha (cabeçalho).
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Extrai as informações do CSV.
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    String tipoStr = partes[0].trim().toUpperCase();
                    String identificador = partes[1].trim();

                    try {
                        TipoAviao tipo = TipoAviao.valueOf(tipoStr);
                        Aviao aviao = new Aviao(identificador, tipo);
                        lista.add(aviao);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de avião inválido: " + tipoStr);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar aviões: " + e.getMessage());
        }
        return lista;
    }

    // Retorna o identificador do avião e o seu tipo.
    @Override
    public String toString() {
        return identificador + " (" + tipo.name() + ")";
    }

}
