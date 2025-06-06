package aviao;

import enums.TipoAviao;
import java.util.LinkedHashMap;
import java.util.Map;

public class Aviao {
    private String identificador;
    private TipoAviao tipo;

    private Map<String, Boolean> poltronasExecutivas;
    private Map<String, Boolean> poltronasEconomicas;

    public Aviao(String identificador, TipoAviao tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.poltronasExecutivas = gerarPoltronas(tipo.getPoltronasExecutivas(), "E");
        this.poltronasEconomicas = gerarPoltronas(tipo.getPoltronasEconomicas(), "C");
    }

    private Map<String, Boolean> gerarPoltronas(int quantidade, String prefixo) {
        Map<String, Boolean> mapa = new LinkedHashMap<>();
        for (int i = 1; i <= quantidade; i++) {
            mapa.put(prefixo + i, false); // false = desocupada
        }
        return mapa;
    }

    public String getIdentificador() {
        return identificador;
    }

    public TipoAviao getTipo() {
        return tipo;
    }

    public Map<String, Boolean> getPoltronasExecutivas() {
        return poltronasExecutivas;
    }

    public Map<String, Boolean> getPoltronasEconomicas() {
        return poltronasEconomicas;
    }

    // Ocupar uma poltrona
    public void ocuparPoltrona(String codigo) {
        if (poltronasExecutivas.containsKey(codigo)) {
            poltronasExecutivas.put(codigo, true);
        } else if (poltronasEconomicas.containsKey(codigo)) {
            poltronasEconomicas.put(codigo, true);
        }
    }

    public boolean isPoltronaDisponivel(String codigo) {
        if (poltronasExecutivas.containsKey(codigo)) {
            return !poltronasExecutivas.get(codigo);
        } else if (poltronasEconomicas.containsKey(codigo)) {
            return !poltronasEconomicas.get(codigo);
        }
        return false;
    }
}
