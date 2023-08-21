package controllers;
import db.EntityManagerHelper;
import domain.organizacion.*;
import domain.repositorios.RepositorioDeEmpleados;
import domain.repositorios.RepositorioDeMiembros;
import domain.repositorios.RepositorioDeOrganizaciones;
import domain.repositorios.RepositorioDeTrayectos;
import domain.trayecto.Trayecto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroController {

    private RepositorioDeMiembros repositorioDeMiembros;
    private RepositorioDeEmpleados repositorioDeEmpleados;
    private RepositorioDeOrganizaciones repoOrg;
    private RepositorioDeTrayectos repoTrayectos = new RepositorioDeTrayectos();

    public MiembroController() {
        this.repositorioDeMiembros = new RepositorioDeMiembros();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response) {
        List<Miembro> todosLosMiembros = this.repositorioDeMiembros.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembros", todosLosMiembros);
        }}, "organizaciones/miembros.hbs");
        //TODO: revisar que devuelva la lista de los miembros de esa organizacion
    }
    
    public ModelAndView mostrar(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
        }}, "miembro/miembro.hbs");
    }

    public ModelAndView inicio(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembro = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
            put("miembro", miembro);
        }}, "miembro/inicio_miembros.hbs");
    }

    public ModelAndView mostrarOrganizaciones(Request request, Response response) {
        String idBuscado = request.params("id");
        this.repositorioDeEmpleados = new RepositorioDeEmpleados();
        List<Organizacion> organizaciones = new ArrayList<Organizacion>();
        List<Empleado> empleos = this.repositorioDeEmpleados.buscarPorMiembro(new Integer(idBuscado));
        for(Empleado empleo: empleos){
            if(empleo.getEstado() == EstadoEmpleado.ACEPTADO){
                organizaciones.add(empleo.getOrganizacion());
            }
        }
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
            put("organizaciones", organizaciones);
        }}, "miembro/organizaciones-miembro.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembro = this.repositorioDeMiembros.buscar(new Integer(idBuscado));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
            put("miembro", miembro);
        }}, "miembro/recomendaciones.hbs");
    }
    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO MIEMBRO
    public ModelAndView crear(Request request, Response response) {
        return new ModelAndView(null, "organizaciones/miembro.hbs");
    }

    //METODO QUE INSTANCIA UN NUEVO MIEMBRO Y LO GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response) {
        Miembro nuevoMiembro = new Miembro();

        //SETEARLE LOS PARAMETROS AL MIEMBRO

        this.repositorioDeMiembros.guardar(nuevoMiembro);

        response.redirect("/organizaciones/miembros");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO MIEMBRO
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembroBuscado);
        }}, "organizaciones/miembro.hbs");
    }

    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        miembroBuscado.setNombre(request.queryParams("nombre"));

        this.repositorioDeMiembros.guardar(miembroBuscado);

        response.redirect("/organizaciones/miembros");
        return response;
    }

    public ModelAndView calculadora(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));
        List<Trayecto> trayectos = repoTrayectos.buscarPorMiembro(miembroBuscado.getId());
        miembroBuscado.setTrayectos(trayectos);

        String org = request.params("idOrganizacion");
        this.repoOrg = new RepositorioDeOrganizaciones();
        Organizacion organizacion = repoOrg.buscar(new Integer(org));
        organizacion.setMiembros(repositorioDeMiembros.buscarPorOrganizacion(organizacion.getId()));

        Float huella = miembroBuscado.huellaTotalEnOrganizacion(organizacion);
        Float impacto = miembroBuscado.impactoSobreOrganizacion(organizacion);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
            put("huella",huella);
            put("impacto",impacto);
        }}, "miembro/calculadora.hbs");
    }


}