package domain.auxiliares;

import java.util.List;

public class HuellaEvolucionOrganizacion {

    String organizacion;

    List<domain.auxiliares.HuellasPorFecha> HuellasPorFecha;

    public List<domain.auxiliares.HuellasPorFecha> getHuellasPorFecha() {
        return HuellasPorFecha;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setHuellasPorFecha(List<domain.auxiliares.HuellasPorFecha> huellas) {
        this.HuellasPorFecha = huellas;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }
}
