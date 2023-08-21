package controllers;
import db.EntityManagerHelper;
import domain.organizacion.Organizacion;
import domain.organizacion.adapters.AdapterCargaExcel;
import domain.organizacion.Medicion;
//import models.repositorios.RepositorioDeMediciones;
import domain.repositorios.RepositorioDeMediciones;
import domain.repositorios.RepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
public class MedicionController {

    private RepositorioDeMediciones repositorioDeMediciones;
    private RepositorioDeOrganizaciones repoOrg;

    public MedicionController() {
        this.repositorioDeMediciones = new RepositorioDeMediciones();
        this.repoOrg = new RepositorioDeOrganizaciones();
    }
    
        // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR
/*
    public ModelAndView mostrarTodos(Request request, Response response) {
        List<Organizacion> todasLasOrganizaciones = this.repositorioDeOrganizaciones.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", todasLasOrganizaciones);
        }}, "organizacioines/organizaciones.hbs");
    }

    public ModelAndView mostrar(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", organizacionBuscada);
            //put("cant_tareas", servicioBuscado.cantTareas());
        }}, "organizacion/organizacion.hbs");
    }
*/
    public ModelAndView mostrarRecomendaciones(Request request, Response response) {

        return new ModelAndView(new HashMap<String, Object>(){{
        }}, "organizaciones/recomendaciones.hbs");
    }
    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO medicion
    public ModelAndView crear(Request request, Response response) {

        String idBuscado = request.params("idorganizacion");
        Organizacion org = repoOrg.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("organizacion", org);
            put("idOrg",idBuscado);
            put("mediciones", org.getMediciones());
        }}, "organizaciones/medicion.hbs");
    }

    //METODO QUE INSTANCIA UNA NUEVA ORGANIZACION Y LA GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response) {
        Medicion nuevaMedicion = new Medicion();

        //SETEARLE LOS PARAMETROS A LA MEDICION


        //this.repositorioDeMediciones.guardar(nuevaMedicion);

        response.redirect("/organizaciones");
        return response;
    }

    public void setearMediciones(String path, String idOrg) throws IOException {
        Organizacion orgBuscada = this.repoOrg.buscar(new Integer(idOrg));
        orgBuscada.setAdapter(new AdapterCargaExcel()); //TODO
        orgBuscada.setRutaAlArchivo(path);
        orgBuscada.cargaDeDatosActividad();
        this.repoOrg.guardar(orgBuscada);

    }
/*
    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO ORGANIZACION
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        Medicion medicionBuscada = this.repositorioDeMediciones.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscada);
        }}, "organizacion/organizacion.hbs");
    }

    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        organizacionBuscada.setRazonSocial(request.queryParams("nombre"));

        this.repositorioDeOrganizaciones.guardar(organizacionBuscada);

        response.redirect("/organizaciones");
        return response;
    }
 */

}