package domain.auxiliares;

public class HuellaPorOrganizacion {

    float huellaEmpresa;
    float huellaGubernamental;
    float huellaInstitucion;
    float huellaOng;


    public void setHuellaEmpresa(float huella) {
        this.huellaEmpresa = huella;
    }
    public void sethuellaGubernamental(float huella) {
        this.huellaGubernamental = huella;
    }
    public void sethuellaInstitucion(float huella) {
        this.huellaInstitucion = huella;
    }
    public void sethuellaOng(float huella) {
        this.huellaOng = huella;
    }

    public float getHuellaEmpresa() {
        return huellaEmpresa;
    }

    public float getHuellaGubernamental() {
        return huellaGubernamental;
    }

    public float getHuellaInstitucion() {
        return huellaInstitucion;
    }

    public float getHuellaOng() {
        return huellaOng;
    }
}
