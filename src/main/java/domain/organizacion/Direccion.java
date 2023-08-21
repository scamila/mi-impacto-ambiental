package domain.organizacion;

import domain.entities.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "direccion")
public class Direccion extends EntidadPersistente {
    @Column(name =  "localidadID")
    private Integer localidadID; //TODO id?
    @Column(name = "calle")
    private String calle;
    @Column(name = "altura")
    private  String altura;
    @Column(name = "piso")
    private  Integer piso;

    public Integer getLocalidadID() {
        return localidadID;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public void setLocalidadID(Integer localidadID) {
        this.localidadID = localidadID;
    }

    public Direccion(){}
    public Direccion(Integer localidadID, String calle, String altura) {
        super();
        this.localidadID = localidadID;
        this.calle = calle;
        this.altura = altura;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }
}
