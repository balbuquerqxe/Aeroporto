package enums;

public enum Destino {
    PARIS("Paris", 9400),
    LONDRES("Londres", 9600),
    ROMA("Roma", 9300),
    CANCUN("Cancún", 7200),
    CHICAGO("Chicago", 8350),
    LISBOA("Lisboa", 7800),
    SANTIAGO("Santiago", 3000),
    DUBAI("Dubai", 12400),
    TÓQUIO("Tóquio", 18500);

    // Nome da cidade de destino.
    private final String nome;

    // Disnt6ancia em km até o destino.
    private final int distanciaKm;

    // Inicialização do destino.
    Destino(String nome, int distanciaKm) {
        this.nome = nome;
        this.distanciaKm = distanciaKm;
    }

    // Retorna o nome da cidade.
    public String getNome() {
        return nome;
    }

    // Retorna a distância até o destino.
    public int getDistanciaKm() {
        return distanciaKm;
    }

    // Retorna as informações sobre o destino.
    @Override
    public String toString() {
        return nome + " (" + distanciaKm + " km)";
    }
}
