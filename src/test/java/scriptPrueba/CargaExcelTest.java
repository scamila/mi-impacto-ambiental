package scriptPrueba;

import domain.organizacion.*;
import domain.organizacion.adapters.AdapterCargaActividades;
import domain.organizacion.adapters.AdapterCargaExcel;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class CargaExcelTest {

    @Test
    public void testeoCargaDeDatosCorrecta() throws IOException {

        Organizacion utn  = new Organizacion();
        Ubicacion medrano = new Ubicacion();


        medrano.setDireccion(new Direccion(4,"medrano","2500"));
        medrano.setLatitud(400F);
        medrano.setLongitud(2250F);
        utn.setAdapter(new AdapterCargaExcel());
        utn.setUbicacionGeografica(medrano);
        utn.setRazonSocial("Universidad Tecnologica Nacional");
        utn.setMiembros(new ArrayList<>());
        utn.setSectores(new ArrayList<>());
        utn.setTipoOrganizacion(TipoDeOrganizacion.INSTITUCION);
        utn.setRutaAlArchivo("D:\\Facultad\\Sistemas\\DisenioDeSistemas\\TP\\pruebaExcel.xlsx");
        utn.cargaDeDatosActividad();

        System.out.println("Actividades                           |" + "Tipo de consumo        |" + "Valor         |" + "Periodicidad        " + "Periodo de imputacion");
        utn.getMediciones().forEach(this::printMedicion);


    }

    private void printMedicion(Medicion medicion){
        System.out.println(medicion.getActividad() + "        |" +
                medicion.getTipoDeConsumo() + "        |"+
                medicion.getValor() + "        |" +
                medicion.getPeriodicidad() + "        |"+
                medicion.getPeriodoDeImputacion());
    }

}