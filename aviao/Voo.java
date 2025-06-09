package aviao;

import enums.Destino;
import java.time.LocalDateTime;
import java.util.UUID;
import pessoas.Piloto;

public class Voo {
    // Identificador do voo.
    private String id;
    // Avião que será utilizado no voo.
    private Aviao aviao;
    // Destino do voo.
    private Destino destino;
    // Horário de saída.
    private LocalDateTime horarioSaida;
    // Horário de chegada (definido pelo horário de saída + velocidade média do avião).
    private LocalDateTime horarioChegada;
    // Define quem será o piloto do voo.
    private Piloto piloto;

    // Quantidade de poltronas ocupadas.
    private int ocupadasEconomicas;
    private int ocupadasExecutivas;

    // Inicialização do voo.
    public Voo(Aviao aviao, Destino destino, LocalDateTime horarioSaida,
               LocalDateTime horarioChegada, Piloto piloto) {
        this.id = UUID.randomUUID().toString();
        this.aviao = aviao;
        this.destino = destino;
        this.horarioSaida = horarioSaida;
        this.horarioChegada = horarioChegada;
        this.piloto = piloto;
        // Começa com nenhuma poltrona ocupada, já que ainda não tem reservas.
        this.ocupadasEconomicas = 0;
        this.ocupadasExecutivas = 0;
    }

    // Retorna qual é o voo.
    public String getId() {
        return id;
    }

    // Retorna qual é o avião.
    public Aviao getAviao() {
        return aviao;
    }

    // Retorna o destino.
    public Destino getDestino() {
        return destino;
    }

    // Retorna o horário de saída.
    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    // Reotrna o horário de chegada.
    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }

    // Retorna qual é o piloto.
    public Piloto getPiloto() {
        return piloto;
    }

    // Retorna a quantidade de poltronas ocupadas.
    public int getOcupadasEconomicas() {
        return ocupadasEconomicas;
    }

    // Retorna a quantidade de poltronas ocupadas.
    public int getOcupadasExecutivas() {
        return ocupadasExecutivas;
    }

    // Retorna a quantidade de poltronas disponíveis.
    public int getDisponiveisEconomicas() {
        return aviao.getTipo().getPoltronasEconomicas() - ocupadasEconomicas;
    }

    // Retorna a quantidade de poltronas disponíveis.
    public int getDisponiveisExecutivas() {
        return aviao.getTipo().getPoltronasExecutivas() - ocupadasExecutivas;
    }

    // Diminui a quantidade de poltronas disponíveis (nova reserva).
    public boolean reservarPoltrona(boolean executiva) {
        if (executiva && getDisponiveisExecutivas() > 0) {
            ocupadasExecutivas++;
            return true;
        } else if (!executiva && getDisponiveisEconomicas() > 0) {
            ocupadasEconomicas++;
            return true;
        }
        return false;
    }

    // Calcula o preço da poltrona econômica.
    public double calcularPrecoEconomica() {
        int km = destino.getDistanciaKm();
        double precoEco = aviao.getTipo().getPrecoPorKmEconomica() * km;
        return precoEco;
    }

    // Calcula o preço da poltrona executiva.
    public double calcularPrecoExecutiva() {
        int km = destino.getDistanciaKm();
        double precoExe = aviao.getTipo().getPrecoPorKmExecutiva() * km;
        return precoExe;
    }  

    // O que ficará escrito nos relatórios dos voos.
    @Override
    public String toString() {
        return "Voo para " + destino.name() + " | Saída: " + horarioSaida +
               " | Avião: " + aviao.getIdentificador();
    }
}
