package domain.auxiliares;

public class HuellasPorFecha {

    String mesAnio;

    String huellaCarbono;

    public HuellasPorFecha(String mesAnio, String huellaCarbono){
        this.mesAnio = mesAnio;
        this.huellaCarbono = huellaCarbono;
    }

    public HuellasPorFecha(){}

    public String getHuellaCarbono() {
        return huellaCarbono;
    }

    public void setMesAnio(String mesAnio) {
        this.mesAnio = mesAnio;
    }

    public void setHuellaCarbono(String huellaCarbono) {
        this.huellaCarbono = huellaCarbono;
    }

    public String getMesAnio() {
        return mesAnio;
    }
}
