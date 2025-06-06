package aviao;

import enums.Destino;
import java.time.LocalDateTime;
import java.util.UUID;
import pessoas.Piloto;

public class Voo {
    private String id;
    private Aviao aviao;
    private Destino destino;
    private LocalDateTime horarioSaida;
    private LocalDateTime horarioChegada;
    private Piloto piloto;

    private int ocupadasEconomicas;
    private int ocupadasExecutivas;

    public Voo(Aviao aviao, Destino destino, LocalDateTime horarioSaida,
               LocalDateTime horarioChegada, Piloto piloto) {
        this.id = UUID.randomUUID().toString();
        this.aviao = aviao;
        this.destino = destino;
        this.horarioSaida = horarioSaida;
        this.horarioChegada = horarioChegada;
        this.piloto = piloto;
        this.ocupadasEconomicas = 0;
        this.ocupadasExecutivas = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Aviao getAviao() {
        return aviao;
    }

    public Destino getDestino() {
        return destino;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    public LocalDateTime getHorarioChegada() {
        return horarioChegada;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public int getOcupadasEconomicas() {
        return ocupadasEconomicas;
    }

    public int getOcupadasExecutivas() {
        return ocupadasExecutivas;
    }

    public int getDisponiveisEconomicas() {
        return aviao.getTipo().getPoltronasEconomicas() - ocupadasEconomicas;
    }

    public int getDisponiveisExecutivas() {
        return aviao.getTipo().getPoltronasExecutivas() - ocupadasExecutivas;
    }

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

    public double calcularPrecoTotal() {
        int km = destino.getDistanciaKm();
        double precoEco = aviao.getTipo().getPrecoPorKmEconomica() * km * ocupadasEconomicas;
        double precoExe = aviao.getTipo().getPrecoPorKmExecutiva() * km * ocupadasExecutivas;
        return precoEco + precoExe;
    }

    @Override
    public String toString() {
        return "Voo para " + destino.name() + " | Saída: " + horarioSaida +
               " | Avião: " + aviao.getIdentificador();
    }
}
