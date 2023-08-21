package domain.organizacion;

import domain.admin.Admin;
import domain.entities.EntidadPersistente;
import domain.organizacion.adapters.AdapterCargaActividades;
import domain.transporte.medios.FE;
import domain.usuarios.Usuario;
import lombok.Getter;
import lombok.Setter;
import sendmessage.EmailConfig;
import sendmessage.WhatsAppConfig;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="organizacion")
@Setter
@Getter
public class Organizacion extends EntidadPersistente {

    @Column(name = "razonSocial")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoDeOrganizacion tipo;

    @OneToOne
    private Ubicacion ubicacionGeografica;

    @OneToMany(mappedBy = "organizacion",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sector> sectores;

    @Transient
    private AdapterCargaActividades adapter;

    @Transient
    private List<Miembro> miembros;

    @Column(name = "clasificacion")
    private String clasificacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id")
    private List<Medicion> mediciones;

    @Column
    private float huellaAnual;

    @Transient
    private String rutaAlArchivo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id",referencedColumnName = "id")
    private List<Contacto> contactos;

    @OneToOne
    private Usuario usuario;

    public Organizacion() {
        this.contactos = new ArrayList<>();
        this.mediciones = new ArrayList<>();
    }
    public void setRutaAlArchivo(String ruta){ this.rutaAlArchivo = ruta; }

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void setTipoOrganizacion(TipoDeOrganizacion tipo) { this.tipo = tipo; }

    public void setUbicacionGeografica(Ubicacion ubicacionGeografica) { this.ubicacionGeografica = ubicacionGeografica; }

    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public void setMediciones(List<Medicion> mediciones) { this.mediciones = mediciones; }

    public void setSectores(List<Sector> sectores) { this.sectores = sectores; }

    public void setMiembros(List<Miembro> miembros) { this.miembros = miembros; }

    public void setAdapter(AdapterCargaActividades _adapter){
        this.adapter = _adapter;
    }



    public String getRutaAlArchivo() { return rutaAlArchivo; }

    public String getRazonSocial() { return razonSocial; }

    public TipoDeOrganizacion getTipo() { return tipo; }

    public Ubicacion getUbicacionGeografica() { return ubicacionGeografica; }

    public List<Sector> getSectores() { return sectores; }

    public List<Miembro> getMiembros() { return miembros; }

    public String getClasificacion() { return clasificacion; }

    public Float getHuellaAnual(){ return huellaAnual; }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void cargaDeDatosActividad() throws IOException {
        this.mediciones = adapter.cargaActividades(this.getRutaAlArchivo());
    }
    public List<Contacto> getContactos() {
        return contactos;
    }

    public void addContactos(List<Contacto> contactos) {
        this.contactos.addAll(contactos);
    }

    public void notificar(EmailConfig emailConfig, WhatsAppConfig whatsAppConfig) {
        contactos.forEach(contacto -> contacto.serNotificadoPor(emailConfig, whatsAppConfig));
    }

    public void hcPorCantMiembros(){
        Double huellaTotal = huellaTotalMiembros().doubleValue();
        //sectores.forEach(sector -> System.out.printf("%s : %.2f %n",sector.getNombre(),huellaTotal/sector.cantidadMiembrosSector()));
        sectores.forEach(sector -> {
            if(sector.cantidadMiembrosSector() != 0){
                sector.setProporcionHuella(huellaTotal/sector.cantidadMiembrosSector());
            }
            else sector.setProporcionHuella(0.0);
        });
    }

    private Float datoActividad(Medicion medicion){
        return Float.parseFloat(medicion.getValor());
    }

    private Float factorEmision(Medicion medicion){
        FE fe = new FE();
        return fe.feActividad(medicion.getActividad()).floatValue();
    }

    private Float huellaPorActividad(Medicion medicion){
        return datoActividad(medicion) * factorEmision(medicion);
    }

    public Float huellaTotalMiembros(){
        return  miembros.stream().map(miembro -> miembro.huellaTotalEnOrganizacion(this)).reduce(0F, Float::sum);
    }

    private Float huellaPorMediciones(List<Medicion> lista){
        return lista.stream().map(this::huellaPorActividad).reduce( 0F, Float::sum);
    }

    public Float huellaTotalMensual(String mesAnio){
        return huellaPorMediciones(filtrarMedicionesPorMesAnio(mesAnio)) + huellaTotalMiembros() * 20;
    }

    public Float huellaTotalAnual(String anio){
        this.huellaAnual = huellaPorMediciones(filtrarMedicionesPorAnio(String.valueOf(LocalDate.now().getYear()))) + huellaTotalMiembros() * 20 * 12;
        return huellaAnual;
    }

    private List<Medicion> filtrarMedicionesPorMesAnio(String valorAComparar){
        return mediciones.stream().filter(m -> m.getPeriodoDeImputacion().equals(valorAComparar)).collect(Collectors.toList());
    }

    private List<Medicion> filtrarMedicionesPorAnio(String valorAComparar) {
        return mediciones.stream().filter(m-> m.getPeriodoDeImputacion().substring(m.getPeriodoDeImputacion().length() - 4).equals(valorAComparar)).collect(Collectors.toList());
    }

}
