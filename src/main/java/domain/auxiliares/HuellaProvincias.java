package domain.auxiliares;

public class HuellaProvincias {

    private float huellaTotal;

    private String  razonSocial;

    private String provincia;

    public HuellaProvincias(float huellaTotal, String razonSocial, String provincia){
        this.huellaTotal = huellaTotal;
        this.razonSocial = razonSocial;
        this.provincia = provincia;
    }
    public HuellaProvincias(){
    }

    public float getHuellaTotal() {
        return huellaTotal;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setHuellaTotal(float huellaTotal) {
        this.huellaTotal = huellaTotal;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
