package scriptPrueba;

import adapters.impl.AdapterAPIUTN;
import data.model.DistanceModel;
import domain.organizacion.Direccion;
import domain.transporte.Parada;
import domain.transporte.Recorrido;
import domain.transporte.enums.TipoTransportePublico;
import domain.transporte.medios.ServicioContratado;
import domain.transporte.medios.TransportePublico;
import domain.transporte.medios.VehiculoParticular;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrayectosTest {

    @Test
    public void calcularDistanciaTramo(){
        Tramo tramo = new Tramo();
        VehiculoParticular gol = new VehiculoParticular();
        gol.setDirecSalida(new Direccion(3,"corriente","4500"));
        gol.setDirecLlegada(new Direccion(5,"medrano","2500"));
        gol.setAdapterDistancia((new AdapterAPIUTN()));
        tramo.setMedioTransporte(gol);

        DistanceModel distancia = tramo.getDistanciaTramo();

        System.out.println(distancia.getValor()+" "+ distancia.getUnidad());

    }

    @Test
    public void calcularDistanciaTrayecto(){

        Trayecto trayectoDePedro = new Trayecto();

        Tramo tramoA = new Tramo();
        Tramo tramoB = new Tramo();
        VehiculoParticular gol = new VehiculoParticular();
        TransportePublico lineaCientoCatorce = new TransportePublico();

        Recorrido recorridoA = new Recorrido();


        Parada laNoria = new Parada();
        laNoria.setNombre("La noria");
        laNoria.setDistanciaAnterior(0);
        laNoria.setDistanciaSiguiente(200);

        Parada gralPazYLaRoca = new Parada();
        gralPazYLaRoca.setNombre("Gral paz y av Gral Roca");
        gralPazYLaRoca.setDistanciaSiguiente(250);
        gralPazYLaRoca.setDistanciaAnterior(200);

        recorridoA.agregarParadaInicial(laNoria);
        recorridoA.agregarParadaDespuesDe(laNoria, gralPazYLaRoca);

        //lineaCientoCatorce.setLinea("114");
        //lineaCientoCatorce.setTipoTransportePublico(TipoTransportePublico.COLECTIVO);
        lineaCientoCatorce.setParadaInicial(laNoria);
        lineaCientoCatorce.setParadaFinal(gralPazYLaRoca);
        lineaCientoCatorce.setRecorrido(recorridoA);

        gol.setDirecSalida(new Direccion(3,"corriente","4500"));
        gol.setDirecLlegada(new Direccion(5,"medrano","2500"));
        gol.setAdapterDistancia((new AdapterAPIUTN()));

        lineaCientoCatorce.setParadaInicial(laNoria);
        lineaCientoCatorce.setParadaFinal(gralPazYLaRoca);

        tramoA.setMedioTransporte(gol);
        tramoB.setMedioTransporte(lineaCientoCatorce);

        List<Tramo> tramos = new ArrayList<>();
        tramos.add(tramoA);
        tramos.add(tramoB);
        trayectoDePedro.setTramos(tramos);
        DistanceModel distanciaTotal = trayectoDePedro.getDistanciaTrayecto();

        System.out.println(distanciaTotal.getValor() + " " + distanciaTotal.getUnidad());
    }
}
