package controllers;

import domain.repositorios.RepositorioDeTransportePublico;
import domain.transporte.medios.TransportePublico;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class TransportePublicoController {
    private RepositorioDeTransportePublico repositorioDeTransportePublico;

    public TransportePublicoController(){
        this.repositorioDeTransportePublico = new RepositorioDeTransportePublico();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response){
        List<TransportePublico> todosLosTransportePublico = this.repositorioDeTransportePublico.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("transportePublico", todosLosTransportePublico);
        }},"transportePublico/transportePublico.hbs");
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO ORGANIZACION
    public ModelAndView crear(Request request, Response response){
        return new ModelAndView(null,"transportePublico/transportePublico.hbs");
    }
    //METODO QUE INSTANCIA UN NUEVO MEDIO DE TRANSPORTE Y LO GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response){
        TransportePublico nuevoTransportePublico = new TransportePublico();

        //SETEARLE LOS PARAMETROS AL MEDIO DE TRANSPORTE

        this.repositorioDeTransportePublico.guardar(nuevoTransportePublico);
        response.redirect("/TransportePublico");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO MEDIOTRANSPORTE
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        TransportePublico transportePublicoBuscado = this.repositorioDeTransportePublico.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("transportePublico", transportePublicoBuscado);
        }}, "transportePublico/transportePublico.hbs");
    }
    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        TransportePublico transportePublicoBuscado = this.repositorioDeTransportePublico.buscar(new Integer(idBuscado));

        //transportePublicoBuscado.setRazonSocial(request.queryParams("nombre"));
        // TODO revisar si transportePublico necesita una descripcion que seria la parte modificable o por lo que podriamos buscar


        this.repositorioDeTransportePublico.guardar(transportePublicoBuscado);

        response.redirect("/transportePublico");
        return response;
    }

}
