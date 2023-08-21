package controllers;

import domain.organizacion.Organizacion;
import domain.repositorios.RepositorioDeMedioTransporte;
import domain.transporte.MedioTransporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class MedioTransporteController {
    private RepositorioDeMedioTransporte repositorioDeMedioTransporte;

    public MedioTransporteController(){
        this.repositorioDeMedioTransporte = new RepositorioDeMedioTransporte();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response){
        List<MedioTransporte> todosLosMediosTransporte = this.repositorioDeMedioTransporte.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
                put("mediosTransporte", todosLosMediosTransporte);
            }},"mediosTransporte/medioTransporte.hbs");
        }

    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO ORGANIZACION
    public ModelAndView crear(Request request, Response response){
        return new ModelAndView(null,"mediosTransporte/medioTransporte.hbs");
    }
    //METODO QUE INSTANCIA UN NUEVO MEDIO DE TRANSPORTE Y LO GUARDA EN LA BASE DE DATOS
/*
    public Response guardar(Request request, Response response){
        MedioTransporte nuevoMedioTransporte = new MedioTransporte();
        //TODO Si persistimos los medios de transporte, pero no los podemos instanciar, como se resuelve esta parte?
        //SETEARLE LOS PARAMETROS AL MEDIO DE TRANSPORTE

        this.repositorioDeMedioTransporte.guardar(nuevoMedioTransporte);
        response.redirect("/mediosTransporte");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO MEDIOTRANSPORTE
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        MedioTransporte medioTransporteBuscado = this.repositorioDeMedioTransporte.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("mediosTransporte", medioTransporteBuscado);
        }}, "mediosTransporte/medioTransporte.hbs");
    }
    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        MedioTransporte medioTransporteBuscado = this.repositorioDeMedioTransporte.buscar(new Integer(idBuscado));
*/
        //medioTransporteBuscado.setRazonSocial(request.queryParams("nombre"));
        /* TODO revisar si un medio de transporte necesita una descripcion que seria la parte modificable
            Queda la duda de si realmente lo persistimos, dado que no tiene nada */
/*
        this.repositorioDeMedioTransporte.guardar(medioTransporteBuscado);

        response.redirect("/mediosTransporte");
        return response;
    }
*/



}

