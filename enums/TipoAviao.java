package enums;

public enum TipoAviao {
    BOEING_737("Boeing 737", 14, 8, 850, 0.20, 0.50),
    AIRBUS_A320("Airbus A320", 12, 2, 830, 0.22, 0.55),
    EMBRAER_E195("Embraer E195", 16, 4, 820, 0.18, 0.45),
    BOEING_787("Boeing 787 Dreamliner", 10, 2, 900, 0.25, 0.60),
    AIRBUS_A380("Airbus A380", 12, 4, 900, 0.30, 0.70);

    // Modelo do avião.
    private final String modelo;
    // Quantidade de poltornas econômicas.
    private final int poltronasEconomica;
    // Quantidade de poltronas executivas.
    private final int poltronasExecutiva;
    // Velocidade média do avião.
    private final int velocidadeMediaKmH;
    // Preço por km do avião para passageiros econômicos.
    private final double precoPorKmEconomica;
    // Preço por km do avião para passageiros executivos.
    private final double precoPorKmExecutiva;

    // Inicialização do tipo de avião.
    TipoAviao(String modelo, int economica, int executiva, int velocidadeMedia,
              double precoEconomica, double precoExecutiva) {
        this.modelo = modelo;
        this.poltronasEconomica = economica;
        this.poltronasExecutiva = executiva;
        this.velocidadeMediaKmH = velocidadeMedia;
        this.precoPorKmEconomica = precoEconomica;
        this.precoPorKmExecutiva = precoExecutiva;
    }

    // Retorna o modelo do avião.
    public String getModelo() {
        return modelo;
    }

    // Retorna a quantidade de poltronas econômicas.
    public int getPoltronasEconomicas() {
        return poltronasEconomica;
    }

    // Retorna a quantidade de poltronas executivas.
    public int getPoltronasExecutivas() {
        return poltronasExecutiva;
    }

    // Retorna a velocidade média.
    public int getVelocidadeMediaKmH() {
        return velocidadeMediaKmH;
    }

    // Retorna o preço por km da poltrona econômica.
    public double getPrecoPorKmEconomica() {
        return precoPorKmEconomica;
    }

    // Retorna o preço por km da poltrona executiva.
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
