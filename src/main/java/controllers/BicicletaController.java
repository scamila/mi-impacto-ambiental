package controllers;

import domain.repositorios.RepositorioDeBicicleta;
import domain.transporte.medios.Bicicleta;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class BicicletaController {
    private RepositorioDeBicicleta repositorioDeBicicleta;

    public BicicletaController(){
        this.repositorioDeBicicleta = new RepositorioDeBicicleta();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response){
        List<Bicicleta> todasLasBicicletas = this.repositorioDeBicicleta.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("bicicleta", todasLasBicicletas);
        }},"bicicleta/Bicicleta.hbs");
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO ORGANIZACION
    public ModelAndView crear(Request request, Response response){
        return new ModelAndView(null,"bicicleta/bicicleta.hbs");
    }
    //METODO QUE INSTANCIA UN NUEVO MEDIO DE TRANSPORTE Y LO GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response){
        Bicicleta nuevoBicicleta = new Bicicleta();

        //SETEARLE LOS PARAMETROS AL MEDIO DE TRANSPORTE

        this.repositorioDeBicicleta.guardar(nuevoBicicleta);
        response.redirect("/bicicleta");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO MEDIOTRANSPORTE
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        Bicicleta bicicletaBuscada = this.repositorioDeBicicleta.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("bicicleta", bicicletaBuscada);
        }}, "bicicleta/bicicleta.hbs");
    }
    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        Bicicleta bicicletaBuscado = this.repositorioDeBicicleta.buscar(new Integer(idBuscado));

        //bicicletaBuscado.setRazonSocial(request.queryParams("nombre"));
        // TODO revisar si Bicicleta necesita una descripcion que seria la parte modificable o por lo que podriamos buscar


        this.repositorioDeBicicleta.guardar(bicicletaBuscado);

        response.redirect("/bicicleta");
        return response;
    }

}
