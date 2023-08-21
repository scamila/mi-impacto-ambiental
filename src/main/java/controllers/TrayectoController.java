package controllers;

import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.organizacion.Direccion;
import domain.organizacion.Empleado;
import domain.organizacion.Organizacion;
import domain.repositorios.RepoDeDirecciones;
import domain.repositorios.RepoParadas;
import domain.repositorios.RepoRecorridos;
import domain.repositorios.RepositorioDeAPie;
import domain.repositorios.RepositorioDeEmpleados;
import domain.repositorios.RepositorioDeMedioTransporte;
import domain.repositorios.RepositorioDeOrganizaciones;
import domain.repositorios.RepositorioDeTramos;
import domain.repositorios.RepositorioDeTransportePublico;
import domain.repositorios.RepositorioDeTrayectos;
import domain.transporte.*;
import domain.transporte.enums.TipoCombustible;
import domain.transporte.enums.TipoVehiculo;
import domain.transporte.medios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TrayectoController {

    private RepositorioDeTrayectos repositorioDeTrayectos;
    private RepositorioDeOrganizaciones repoOrg = new RepositorioDeOrganizaciones();
    private RepositorioDeEmpleados repoEmpleados = new RepositorioDeEmpleados();
    private RepoDeDirecciones repoDirecciones = new RepoDeDirecciones();
    private RepositorioDeTramos repoTramos = new RepositorioDeTramos();
    private RepoParadas repoParadas = new RepoParadas();
    private RepositorioDeTransportePublico repoTPublico = new RepositorioDeTransportePublico();
    private RepoRecorridos repoRecorridos = new RepoRecorridos();

    public TrayectoController() {
        this.repositorioDeTrayectos = new RepositorioDeTrayectos();
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        String idBuscado = request.params("id");

        List<Trayecto> todosLosTrayectos = this.repositorioDeTrayectos.buscarPorMiembro(new Integer(idBuscado));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", request.params("id"));
            put("trayectos", todosLosTrayectos);
        }}, "trayecto/trayectos.hbs");
    }

    public ModelAndView mostrar(Request request, Response response) {
        String idBuscado = request.params("idTrayecto");
        Trayecto trayectoBuscado;
       try {
            trayectoBuscado = this.repositorioDeTrayectos.buscar(new Integer(idBuscado));
       }
        catch (Exception e) {
            trayectoBuscado = new Trayecto();
            List<Tramo> tramos = new ArrayList<Tramo>();
            trayectoBuscado.setTramos(tramos);

        }
        if(trayectoBuscado == null){
            trayectoBuscado = new Trayecto();
            List<Tramo> tramos = new ArrayList<Tramo>();
            trayectoBuscado.setTramos(tramos);
        }
        Trayecto finalTrayectoBuscado = trayectoBuscado;
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", request.params("id"));
            put("trayecto", finalTrayectoBuscado);
            put("tramos", finalTrayectoBuscado.getTramos());
            put("partida" , finalTrayectoBuscado.getPuntoPartida());
        }}, "trayecto/trayecto.hbs");
    }

    public ModelAndView crear(Request request, Response response) {
        String idBuscado = request.params("id");
        List<Organizacion> orgConTrayectos = this.repositorioDeTrayectos
                                        .buscarPorMiembro(new Integer(idBuscado))
                                        .stream().map(trayecto -> trayecto.getEmpleado().getOrganizacion())
                                        .collect(Collectors.toList());

        List<Organizacion> orgMiembro = repoEmpleados.buscarPorMiembro(new Integer(idBuscado))
                                                            .stream().map(empleo -> empleo.getOrganizacion())
                                                            .collect(Collectors.toList());

        List<Organizacion> orgSinTrayecto = new ArrayList<>();
        for(Organizacion org : orgMiembro){
            if(!orgConTrayectos.contains(org)){
                orgSinTrayecto.add(org);
            }
        }                                                                   

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones",orgSinTrayecto);
            put("idMiembro", request.params("id"));
        }}, "trayecto/trayecto-crear.hbs");
    }

    public Response guardar(Request request, Response response) {
        Trayecto nuevoTrayecto = new Trayecto();
        setearAtributos(nuevoTrayecto, request);
        this.repositorioDeTrayectos.guardar(nuevoTrayecto);

        Integer idTrayecto = this.repositorioDeTrayectos.buscarTodos().stream()
                                    .filter(t->t.equals(nuevoTrayecto))
                                    .findFirst().get().getId();

        response.redirect("trayectos");
        return response;
    }
    public ModelAndView guardarV2(Request request, Response response) {
        Trayecto nuevoTrayecto = new Trayecto();
        setearAtributos(nuevoTrayecto, request);
        this.repositorioDeTrayectos.guardar(nuevoTrayecto);

        Integer idTrayecto = this.repositorioDeTrayectos.buscarTodos().stream()
                .filter(t->t.equals(nuevoTrayecto))
                .findFirst().get().getId();

        //response.redirect("trayectos");
        return this.mostrarTodos(request,response);
    }
    /* NO ME INTERSA EDITAR UN TRAYECTO, ME INTERESA EDITAR UN TRAMODEL TRAYECTO
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("idTrayecto");
        Trayecto trayectoBuscado = this.repositorioDeTrayectos.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("trayecto", trayectoBuscado);
            put("tramos",trayectoBuscado.getTramos());
        }}, "trayecto/trayecto-editar.hbs");
    }
    */
    public Response modificar(Request request, Response response) {
        //LLLAMO A MODIFICAR SOLO CUANDO SE AGREGA UN NUEVO TRAMO , SETEO EL TRAMO DESDE ACA PARA QUE LE PERSISTA A LA BASE EL ID DEL TRAYECTO
        String idBuscado = request.params("idTrayecto");
        
        Trayecto trayectoBuscado = this.repositorioDeTrayectos.buscar(new Integer(idBuscado));
        Tramo nuevoTramo = new Tramo();
        asignarParametrosATramo(nuevoTramo, request);
        nuevoTramo.setTrayecto(trayectoBuscado);
        trayectoBuscado.agregarTramo(nuevoTramo);

        this.repositorioDeTrayectos.guardar(trayectoBuscado);

        response.redirect(idBuscado+"/tramos");
        return response;
    }

    public Response eliminar(Request request, Response response){
        Trayecto trayecto = this.repositorioDeTrayectos.buscar(new Integer(request.params("idTrayecto")));

        /*for(Tramo tramo: trayecto.getTramos()){
            this.repoTramos.eliminar(tramo);
        }*/
        trayecto.getEmpleado().getTrayectos().remove((trayecto));
        trayecto.getEmpleado().getMiembro().getTrayectos().remove(trayecto);
        trayecto.getTramos().stream().forEach((tramo -> tramo.setTrayecto(null)));
        this.repositorioDeTrayectos.eliminar(trayecto);

        response.redirect("trayectos");
        return response;
    }
    public ModelAndView eliminarV2(Request request, Response response){
        Trayecto trayecto = this.repositorioDeTrayectos.buscar(new Integer(request.params("idTrayecto")));

        /*for(Tramo tramo: trayecto.getTramos()){
            this.repoTramos.eliminar(tramo);
        }*/
        trayecto.getEmpleado().getTrayectos().remove((trayecto));
        trayecto.getEmpleado().getMiembro().getTrayectos().remove(trayecto);
        trayecto.getTramos().stream().forEach((tramo -> tramo.setTrayecto(null)));
        this.repositorioDeTrayectos.eliminar(trayecto);

        return this.mostrarTodos(request,response);
    }

    public void setearAtributos(Trayecto trayecto, Request request){
        if(request.queryParams("organizacion") != null){
            Integer idMiembro = new Integer(request.params("id"));
            Organizacion org = repoOrg.buscar(new Integer(request.queryParams("organizacion")));
            Empleado empleo = repoEmpleados.buscarPorMiembro(idMiembro).stream()
                                            .filter(e->e.getOrganizacion().getId().equals(new Integer(request.queryParams("organizacion"))))
                                            .findFirst().get();
            trayecto.setOrgDestino(org);
            trayecto.setEmpleado(empleo);
            trayecto.setPuntoLLegada(org.getUbicacionGeografica().getDireccion());
            trayecto.setTramos(new ArrayList<Tramo>());
        }

    }

    private void asignarParametrosATramo(Tramo tramo, Request request){

        Direccion direccionPartida = generarDireccion(request, "partida");
        Direccion direccionDestino = generarDireccion(request, "destino");
        

        repoDirecciones.guardar(direccionPartida);
        repoDirecciones.guardar(direccionDestino);

        crearMedio(direccionPartida,direccionDestino,request,tramo);

        tramo.setPuntoPartida(direccionPartida);
        tramo.setPuntoDestino(direccionDestino);

    }
    
    private void crearMedio(Direccion direccionPartida, Direccion direccionDestino, Request request, Tramo tramo) {
        String medio = request.queryParams("medio");
        RepositorioDeMedioTransporte repo = new RepositorioDeMedioTransporte();

        switch (medio) {
            case "CAMINANDO":
                APie apie = new APie();
                apie.setDirecSalida(direccionPartida);
                apie.setDirecLlegada(direccionDestino);
                apie.setParticular(apie);
                apie.setMedio(apie.getParticular());
                //medioTransporte = apie;
                repo.guardar(apie);

                tramo.setMedioTransporte(apie);
                break;
            case "BICICLETA":
                Bicicleta bici = new Bicicleta();
                bici.setDirecSalida(direccionPartida);
                bici.setDirecLlegada(direccionDestino);
                bici.setParticular(bici);
                bici.setMedio(bici.getParticular());

                repo.guardar(bici);
                tramo.setMedioTransporte(bici);
                break;
            case "SUBTE": case "COLECTIVO": case "TREN":
                String tipoTransporte = medio.toLowerCase();
                TransportePublico publico = new TransportePublico();
                Integer paradaInicial = new Integer(request.queryParams("p-"+tipoTransporte+"-inicial"));
                Integer paradaFinal = new Integer(request.queryParams("p-"+tipoTransporte+"-final"));
                Integer recorrido = new Integer(request.queryParams("l-"+tipoTransporte));

                setParametrosPublico(publico,paradaInicial,paradaFinal,recorrido);
                publico.setPublico(publico);

                repo.guardar(publico);
                tramo.setMedioTransporte(publico);         
                break;
            case "MOTO": case "AUTO": case "CAMIONETA":
                VehiculoParticular vehiculo = new VehiculoParticular();
                String combustible = request.queryParams("combustible");
                vehiculo.setParticular(vehiculo);
                vehiculo.setMedio(vehiculo.getParticular());

                this.setParametrosVehiculo(vehiculo, direccionPartida, direccionDestino, combustible, medio);

                repo.guardar(vehiculo);
                tramo.setMedioTransporte(vehiculo);
                break;
            case "SERVICIO_CONTRATADO":
                ServicioContratado contratado = new ServicioContratado();
                String nombreServicio = request.queryParams("servicio");
                contratado.setParticular(contratado);
                contratado.setMedio(contratado.getParticular());

                contratado.setDirecSalida(direccionPartida);
                contratado.setDirecLlegada(direccionDestino);
                contratado.setNombreServicio(nombreServicio);
                
                repo.guardar(contratado);
                tramo.setMedioTransporte(contratado);
                break;
        }
    }

    private void setParametrosPublico(TransportePublico transporte,Integer idParadaInicial,Integer idParadaFinal,Integer idRecorrido){
        Parada paradaInicial = this.repoParadas.buscar(idParadaInicial);
        Parada paradaFinal = this.repoParadas.buscar(idParadaFinal);
        Recorrido recorrido = this.repoRecorridos.buscar(idRecorrido);

        transporte.setParadaInicial(paradaInicial);
        transporte.setParadaFinal(paradaFinal);
        transporte.setRecorrido(recorrido);
    }

    private void setParametrosVehiculo(VehiculoParticular vehiculo,Direccion partida,Direccion destino,String combustible,String tipoVehiculo){
        vehiculo.setDirecSalida(partida);
        vehiculo.setDirecLlegada(destino);
        vehiculo.setTipoCombustible(TipoCombustible.valueOf(combustible));
        vehiculo.setTipoVehiculo(TipoVehiculo.valueOf(tipoVehiculo));
    }

    private Direccion generarDireccion(Request request, String tipo){
        
        Direccion direccion = new Direccion();
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
