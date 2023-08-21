package domain.auxiliares;

import domain.organizacion.Organizacion;

import java.util.List;

public class HuellaSectorDeterminado {

    String sector;
    List<Organizacion> organizaciones;

    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public String getSector() {
        return sector;
    }

    public void setOrganizaciones(List<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
