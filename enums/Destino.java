package enums;

public enum Destino {
    PARIS("Paris", 9400),
    LONDRES("Londres", 9600),
    ROMA("Roma", 9300),
    CANCUN("Cancún", 7200),
    NOVA_YORK("Nova York", 7700),
    LISBOA("Lisboa", 7800),
    BUENOS_AIRES("Buenos Aires", 2200),
    SANTIAGO("Santiago", 3000),
    DUBAI("Dubai", 12400),
    TÓQUIO("Tóquio", 18500);

    private final String nome;
    private final int distanciaKm;

    Destino(String nome, int distanciaKm) {
        this.nome = nome;
        this.distanciaKm = distanciaKm;
    }

    public String getNome() {
        return nome;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    @Override
    public String toString() {
        return nome + " (" + distanciaKm + " km)";
    }
}
