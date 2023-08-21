package controllers;

import domain.repositorios.RepositorioDeAPie;
import domain.transporte.medios.APie;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class APieController {
    private RepositorioDeAPie repositorioDeAPie;

    public APieController(){
        this.repositorioDeAPie = new RepositorioDeAPie();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response){
        List<APie> todosLosAPie = this.repositorioDeAPie.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("aPie", todosLosAPie);
        }},"aPie/aPie.hbs");
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO ORGANIZACION
    public ModelAndView crear(Request request, Response response){
        return new ModelAndView(null,"aPie/aPie.hbs");
    }
    //METODO QUE INSTANCIA UN NUEVO MEDIO DE TRANSPORTE Y LO GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response){
        APie nuevoAPie = new APie();

        //SETEARLE LOS PARAMETROS AL MEDIO DE TRANSPORTE

        this.repositorioDeAPie.guardar(nuevoAPie);
        response.redirect("/APie");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO MEDIOTRANSPORTE
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        APie aPieBuscado = this.repositorioDeAPie.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("aPie", aPieBuscado);
        }}, "aPie/aPie.hbs");
    }
    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        APie aPieBuscado = this.repositorioDeAPie.buscar(new Integer(idBuscado));

        //aPieBuscado.setRazonSocial(request.queryParams("nombre"));
        // TODO revisar si aPie necesita una descripcion que seria la parte modificable o por lo que podriamos buscar


        this.repositorioDeAPie.guardar(aPieBuscado);

        response.redirect("/aPie");
        return response;
    }

}
