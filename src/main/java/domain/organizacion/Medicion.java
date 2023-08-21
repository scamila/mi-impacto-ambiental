package domain.organizacion;

import domain.entities.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="medicion")
@Setter
@Getter
public class Medicion extends EntidadPersistente {

    @Column(name="actividad")
    private String actividad;

    @Column(name="tipoDeConsumo")
    private String tipoDeConsumo;

    @Column(name="valor")
    private String valor;

    @Column(name="periodicidad")
    private String periodicidad;

    @Column(name="periodoDeImputacion")
    private String periodoDeImputacion;

    @ManyToOne
    private Organizacion organizacion;
}
