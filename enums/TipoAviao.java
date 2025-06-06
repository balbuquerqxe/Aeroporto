package enums;

public enum TipoAviao {
    BOEING_737("Boeing 737", 14, 8, 850, 0.20, 0.50),
    AIRBUS_A320("Airbus A320", 12, 2, 830, 0.22, 0.55),
    EMBRAER_E195("Embraer E195", 16, 4, 820, 0.18, 0.45),
    BOEING_787("Boeing 787 Dreamliner", 10, 2, 900, 0.25, 0.60),
    AIRBUS_A380("Airbus A380", 12, 4, 900, 0.30, 0.70);

    private final String modelo;
    private final int poltronasEconomica;
    private final int poltronasExecutiva;
    private final int velocidadeMediaKmH;
    private final double precoPorKmEconomica;
    private final double precoPorKmExecutiva;

    TipoAviao(String modelo, int economica, int executiva, int velocidadeMedia,
              double precoEconomica, double precoExecutiva) {
        this.modelo = modelo;
        this.poltronasEconomica = economica;
        this.poltronasExecutiva = executiva;
        this.velocidadeMediaKmH = velocidadeMedia;
        this.precoPorKmEconomica = precoEconomica;
        this.precoPorKmExecutiva = precoExecutiva;
    }

    public String getModelo() {
        return modelo;
    }

    public int getPoltronasEconomica() {
        return poltronasEconomica;
    }

    public int getPoltronasExecutiva() {
        return poltronasExecutiva;
    }

    public int getVelocidadeMediaKmH() {
        return velocidadeMediaKmH;
    }

    public double getPrecoPorKmEconomica() {
        return precoPorKmEconomica;
    }

    public double getPrecoPorKmExecutiva() {
        return precoPorKmExecutiva;
    }

    @Override
    public String toString() {
        return modelo + " | Econômica: " + poltronasEconomica + " (R$" + precoPorKmEconomica + "/km), " +
               "Executiva: " + poltronasExecutiva + " (R$" + precoPorKmExecutiva + "/km), " +
               "Vel: " + velocidadeMediaKmH + " km/h";
    }
}
