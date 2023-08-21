package scriptPrueba;

import adapters.impl.AdapterAPIUTN;
import data.model.DistanceModel;
import domain.organizacion.Direccion;
import domain.transporte.Parada;
import domain.transporte.Recorrido;
import domain.transporte.enums.TipoTransportePublico;
import domain.transporte.medios.*;
import org.junit.Assert;
import org.junit.Test;
import scriptContrasenias.ValidarPassword;

import java.util.Objects;

public class DistanceTest {

    //Prueba a pie
    @Test
    public void calcularDistanciaAPie(){
        APie caminando = new APie();
        caminando.setDirecSalida(new Direccion(3,"corrientes","4500"));
        caminando.setDirecLlegada((new Direccion(5,"mozart","2300")));
        caminando.setAdapterDistancia((new AdapterAPIUTN()));
        caminando.calcularDistancia();
    }

    // Prueba de bicicleta
    @Test
    public void calcularDistanciaBici(){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setDirecSalida(new Direccion(1, "maipu", "100"));
        bicicleta.setDirecLlegada(new Direccion(457, "O%27Higgins", "200"));
        bicicleta.setAdapterDistancia(new AdapterAPIUTN());
        bicicleta.calcularDistancia();
    }

    // Prueba de servicios contratados
    @Test
    public void calcularDistancaServicioContratado(){
        ServicioContratado uber = new ServicioContratado();
        uber.setDirecSalida(new Direccion(3,"corrientes","4500"));
        uber.setDirecLlegada((new Direccion(5,"mozart","2300")));
        uber.setAdapterDistancia((new AdapterAPIUTN()));
        uber.calcularDistancia();

    }

    //Prueba de vehiculo particular
    @Test
    public void calcularDistanciaVehiculoParticular(){
        VehiculoParticular megane = new VehiculoParticular();
        megane.setDirecSalida(new Direccion(3,"corrientes","4500"));
        megane.setDirecLlegada((new Direccion(5,"mozart","2300")));
        megane.setAdapterDistancia((new AdapterAPIUTN()));
        megane.calcularDistancia();
    }

    //Prueba de transporte publico
    @Test
    public void distanciaEntreParadas() {
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

        Parada piedrabuena = new Parada();
        piedrabuena.setNombre("Piedrabuena");
        piedrabuena.setDistanciaAnterior(250);
        piedrabuena.setDistanciaSiguiente(400);

        Parada soldadoDeLaFrontera = new Parada();
        soldadoDeLaFrontera.setNombre("Soldado de la frontera");
        soldadoDeLaFrontera.setDistanciaAnterior(400);
        soldadoDeLaFrontera.setDistanciaSiguiente(0);

        recorridoA.agregarParadaInicial(laNoria);
        recorridoA.agregarParadaDespuesDe(laNoria, gralPazYLaRoca);
        recorridoA.agregarParadaDespuesDe(gralPazYLaRoca, piedrabuena);
        recorridoA.agregarParadaDespuesDe(piedrabuena, soldadoDeLaFrontera);
        //lineaCientoCatorce.setLinea("114");
        //lineaCientoCatorce.setTipoTransportePublico(TipoTransportePublico.COLECTIVO);
        lineaCientoCatorce.setParadaInicial(laNoria);
        lineaCientoCatorce.setParadaFinal(soldadoDeLaFrontera);
        lineaCientoCatorce.setRecorrido(recorridoA);

        lineaCientoCatorce.calcularDistancia();

    }

}
