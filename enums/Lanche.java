package enums;

public enum Lanche {
    HAMBURGUER("Hambúrguer", "burguer.png"),
    LASANHA("Lasanha", "lasanha.png"),
    BOLO("Bolo", "bolo.png"),
    SOREVTE("Sorvete", "sorvete.png"),
    HOTDOG("Hot Dog", "hotdog.png"),
    CAFE("Café", "cafe.png"),
    REFRIGERANTE("Refrigerante", "refri.png"),
    PIPOCA("Pipoca", "pipoca.png"),
    MANGA("Manga", "manga.png");

    private final String nome;
    private final String imagemPath;

    Lanche(String nome, String imagemPath) {
        this.nome = nome;
        this.imagemPath = imagemPath;
    }

    public String getNome() {
        return nome;
    }


    public String getImagemPath() {
        return imagemPath;
    }

    @Override
    public String toString() {
        return nome;
    }
}
