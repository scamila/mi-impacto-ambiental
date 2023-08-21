package controllers;

import domain.trayecto.*;
import domain.transporte.Recorrido;
import domain.transporte.enums.TipoCombustible;
import domain.transporte.enums.TipoTransportePublico;
import domain.transporte.enums.TodosLosTransportes;
import domain.organizacion.Direccion;
import domain.repositorios.RepoDeDirecciones;
import domain.repositorios.RepoParadas;
import domain.repositorios.RepoRecorridos;
import domain.repositorios.RepositorioDeTramos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TramoController {
    private RepositorioDeTramos repositorioDeTramos;
    private RepoDeDirecciones repoDirecciones = new RepoDeDirecciones();
    private RepoRecorridos repoRecorridos = new RepoRecorridos();
    private RepoParadas repoParadas = new RepoParadas();

    public TramoController() {
        this.repositorioDeTramos = new RepositorioDeTramos();
    }
    
    public ModelAndView crear(Request request, Response response) {
        List<Recorrido> recorridos = repoRecorridos.buscarTodos();

        recorridos.forEach(recorrido-> recorrido.setParadas(repoParadas.buscarPorRecorrido(recorrido.getId())));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", request.params("id"));
            put("idTrayecto",request.params("idTrayecto"));
            put("medios",TodosLosTransportes.values());

            put("lineas-tren",filtrar(recorridos,TipoTransportePublico.TREN));
            put("lineas-subte",filtrar(recorridos,TipoTransportePublico.SUBTE));
            put("lineas-colectivo",filtrar(recorridos,TipoTransportePublico.COLECTIVO));
            
            put("combustibles",TipoCombustible.values());
        }}, "trayecto/tramo.hbs");
    }

    public List<Recorrido> filtrar(List<Recorrido> lista, TipoTransportePublico tipo){
        return lista.stream().filter(transporte -> transporte.getTipoTransportePublico().equals(tipo)).collect(Collectors.toList());
    }

    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("idTramo");
        Tramo tramoBuscado = this.repositorioDeTramos.buscar(new Integer(idBuscado));
        List<Recorrido> recorridos = repoRecorridos.buscarTodos();

        recorridos.forEach(recorrido-> recorrido.setParadas(repoParadas.buscarPorRecorrido(recorrido.getId())));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", request.params("id"));
            put("trayecto",request.params("idTrayecto"));

            put("lineas-tren",filtrar(recorridos,TipoTransportePublico.TREN));
            put("lineas-subte",filtrar(recorridos,TipoTransportePublico.SUBTE));
            put("lineas-colectivo",filtrar(recorridos,TipoTransportePublico.COLECTIVO));

            put("tramo",tramoBuscado);
            put("puntoPartida", tramoBuscado.getPuntoPartida());
            put("puntoDestino",tramoBuscado.getPuntoDestino());
            put("transporte",tramoBuscado.getMedioTransporte());
        }}, "trayecto/tramo.hbs");
    }

    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("idTramo");
        String trayecto = request.params("idTrayecto");
        
        Tramo tramoBuscado = this.repositorioDeTramos.buscar(new Integer(idBuscado));

        modificaParametrosDeTramo(tramoBuscado, request);

        this.repositorioDeTramos.guardar(tramoBuscado);

        response.redirect("../tramos");
        return response;
    }

    public Response eliminar(Request request, Response response){
        Tramo tramo = this.repositorioDeTramos.buscar(new Integer(request.params("idTramo")));

        //Trayecto trayecto = this.repositorioDeTramos.buscarTrayecto(tramo);
        //trayecto.getTramos().remove(tramo);
        tramo.getTrayecto().getTramos().remove(tramo);
        this.repositorioDeTramos.eliminar(tramo);


        return response;
    }

    private void modificaParametrosDeTramo(Tramo tramo, Request request){

        Direccion direccionPartida = modificarDireccion(request, "partida",tramo.getPuntoPartida());
        Direccion direccionDestino = modificarDireccion(request, "destino",tramo.getPuntoDestino());

        repoDirecciones.guardar(direccionPartida);
        repoDirecciones.guardar(direccionDestino);

        tramo.setPuntoPartida(direccionPartida);
        tramo.setPuntoDestino(direccionDestino);

    }

    private Direccion modificarDireccion(Request request, String tipo,Direccion direccion){
        
        if(request.queryParams("calle-"+tipo) != null){
            direccion.setCalle(request.queryParams("calle-"+tipo));
        }
        if(request.queryParams("altura-"+tipo) != null){
            direccion.setAltura(request.queryParams("altura-"+tipo));
        }
        if(request.queryParams("piso-"+tipo) != null && !request.queryParams("piso-"+tipo).isEmpty() ){
            int telefono = new Integer(request.queryParams("piso-"+tipo));
            direccion.setPiso(telefono);
        }
        if(request.queryParams("localidad-"+tipo) != null && !request.queryParams("localidad-"+tipo).isEmpty()){
            direccion.setLocalidadID(new Integer(request.queryParams("localidad-"+tipo)));
        }

        return direccion;        
    }

}
