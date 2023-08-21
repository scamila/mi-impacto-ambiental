package controllers;
import db.EntityManagerHelper;
import domain.organizacion.*;
import domain.organizacion.Empleado;
import domain.organizacion.Medicion;
import domain.organizacion.Miembro;
import domain.organizacion.Organizacion;
import domain.organizacion.PuestoDeTrabajo;
import domain.organizacion.Sector;
import domain.repositorios.RepoDeSectores;
import domain.repositorios.RepositorioDeEmpleados;
import domain.repositorios.RepositorioDeMediciones;
import domain.repositorios.RepositorioDeOrganizaciones;
import domain.repositorios.RepositorioDePuestoDeTrabajo;
import domain.repositorios.RepositorioDeTrayectos;
import domain.trayecto.Trayecto;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
public class OrganizacionController {

    private RepositorioDeOrganizaciones repositorioDeOrganizaciones;
    private RepositorioDeEmpleados repoEmpleado = new RepositorioDeEmpleados();
    private RepoDeSectores repoSectores = new RepoDeSectores();
    private RepositorioDePuestoDeTrabajo repoPuestos = new RepositorioDePuestoDeTrabajo();
    private RepositorioDeTrayectos repoTrayectos = new RepositorioDeTrayectos();

    public OrganizacionController() {
        this.repositorioDeOrganizaciones = new RepositorioDeOrganizaciones();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos(Request request, Response response) {
        List<Organizacion> todasLasOrganizaciones = this.repositorioDeOrganizaciones.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones", todasLasOrganizaciones);
        }}, "organizaciones/organizaciones.hbs");
    }

    public ModelAndView inicio(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
        }}, "organizaciones/inicio_organizaciones.hbs");
    }
    public ModelAndView mostrar(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
        }}, "organizaciones/organizacion-base.hbs");
    }

    public ModelAndView mostrarEmpleados(Request request,Response response){
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        List<Empleado> empleados = repoEmpleado.buscarPorOrg(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("empleados",empleados);
        }}, "organizaciones/empleados.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
        }}, "organizaciones/recomendaciones.hbs");
    }
    //DEVUELVE LA VISTA QUE NOS PERMITE CREAR UN RECURSO ORGANIZACION
    public ModelAndView crear(Request request, Response response) {
        return new ModelAndView(null, "organizaciones/organizacion.hbs");
    }

    //METODO QUE INSTANCIA UNA NUEVA ORGANIZACION Y LA GUARDA EN LA BASE DE DATOS
    public Response guardar(Request request, Response response) {
        Organizacion nuevaOrganizacion = new Organizacion();

        //SETEARLE LOS PARAMETROS A LA ORGANIZACION

        this.repositorioDeOrganizaciones.guardar(nuevaOrganizacion);

        response.redirect("/organizaciones");
        return response;
    }

    //DEVUELVE LA VISTA QUE NOS PERMITE EDITAR UN RECURSO ORGANIZACION
    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", organizacionBuscada);
        }}, "organizaciones/organizacion.hbs");
    }
    public ModelAndView calcularHuella(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        List<Sector> sectores = this.repoSectores.buscarPorOrganizacion(organizacionBuscada.getId());
        List<Empleado> empleados = repoEmpleado.buscarPorOrg(organizacionBuscada.getId());
        List<Miembro> miembros = new ArrayList();

        for(Empleado empleado : empleados){
            Miembro miembroBuscado = empleado.getMiembro();
            List<Trayecto> trayectos = repoTrayectos.buscarPorMiembro(miembroBuscado.getId());
            miembroBuscado.setTrayectos(trayectos);
            miembros.add(miembroBuscado);
        }

        for(Sector sector : sectores){
            List<Miembro> miembrosSector = repoPuestos.buscarPorSector(sector.getId()).stream()
                                            .map(puesto -> puesto.getEmpleado().getMiembro())
                                            .collect(Collectors.toList());
            sector.setMiembrosSector(miembrosSector);
        }

        String fecha[]  = LocalDate.now().toString().split("-");
        String anio = fecha[0];
        String mes = fecha[1];

        organizacionBuscada.setMiembros(miembros);
        organizacionBuscada.hcPorCantMiembros();
        organizacionBuscada.huellaTotalAnual(anio);
        this.repositorioDeOrganizaciones.actualizarHuella(organizacionBuscada);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellaMensual", organizacionBuscada.huellaTotalMensual(mes+anio));
            put("sectores",sectores);
        }}, "calculadora/calculadora_hc_org.hbs");
    }

    public void actualizarHuella(Organizacion organizacion){
        String fecha[]  = LocalDate.now().toString().split("-");
        String anio = fecha[0];
        String mes = fecha[1];

        organizacion.huellaTotalAnual(anio);
        organizacion.huellaTotalMensual(mes+anio);
        organizacion.hcPorCantMiembros();

        this.repositorioDeOrganizaciones.guardar(organizacion);
    }
    
    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        organizacionBuscada.setRazonSocial(request.queryParams("nombre"));

        this.repositorioDeOrganizaciones.guardar(organizacionBuscada);

        response.redirect("/organizaciones");
        return response;
    }
    public  ModelAndView vincularEmpleados(Request request, Response response){
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        List<Empleado> empleados = repoEmpleado.buscarPorNoVinculado(new Integer(idBuscado));
        return new ModelAndView(new HashMap<String, Object>(){{

            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("empleados",empleados);
        }},"organizaciones/aceptarVinculacion.hbs");
    }


    public Response aceptarEmpleado(Request request, Response response){
        String idEmpleado = request.queryParams("idEmpleado");

        Empleado empleado = repoEmpleado.buscarPorMiembroYOrganizacion(new Integer(idEmpleado));

        empleado.setEstado(EstadoEmpleado.ACEPTADO);

        repoEmpleado.actualizarEstado(empleado);


        response.redirect("/organizaciones/" + empleado.getOrganizacion().getId() + "/vinculacion");

        return response;
    }
    public ModelAndView aceptarEmpleadoV2(Request request, Response response){
        String idEmpleado = request.queryParams("idEmpleado");

        Empleado empleado = repoEmpleado.buscarPorMiembroYOrganizacion(new Integer(idEmpleado));

        empleado.setEstado(EstadoEmpleado.ACEPTADO);

        repoEmpleado.actualizarEstado(empleado);

        return this.vincularEmpleados(request,response);
    }
    public ModelAndView rechazarEmpleadoV2(Request request, Response response){
        String idEmpleado = request.queryParams("idEmpleado");

        Empleado empleado = repoEmpleado.buscarPorMiembroYOrganizacion(new Integer(idEmpleado));

        empleado.setEstado(EstadoEmpleado.RECHAZADO);

        repoEmpleado.actualizarEstado(empleado);

        return this.vincularEmpleados(request,response);
    }
    public Response rechazarEmpleado(Request request, Response response){

        String idEmpleado = request.queryParams("idEmpleado");

        Empleado empleado = repoEmpleado.buscarPorMiembroYOrganizacion(new Integer(idEmpleado));

        empleado.setEstado(EstadoEmpleado.RECHAZADO);

        repoEmpleado.actualizarEstado(empleado);

        response.redirect("/organizaciones/" + empleado.getOrganizacion().getId() + "/vinculacion");

        return response;
    }
    //TODO tengo que armar una vista que te permita ver los empleados no vinculados y modificarles el estado
}