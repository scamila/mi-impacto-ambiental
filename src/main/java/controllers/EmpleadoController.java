package controllers;
import domain.organizacion.*;
import domain.repositorios.RepositorioDeEmpleados;
import domain.repositorios.RepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;


public class EmpleadoController {

    private RepositorioDeEmpleados repositorioDeEmpleados;

    public EmpleadoController() {
        this.repositorioDeEmpleados = new RepositorioDeEmpleados();
    }

    // A -> ALTA | B -> BAJA | M -> MODIFICACIÓN | L -> LISTADO | V -> VISUALIZAR

    public ModelAndView mostrarTodos() {
        List<Empleado> todosLosEmpleados = this.repositorioDeEmpleados.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembros", todosLosEmpleados);
        }}, "organizaciones/empleados.hbs");
        //TODO: revisar que devuelva la lista de los miembros de esa organizacion
    }

    public ModelAndView mostrar(Request request) {
        String idBuscado = request.params("id");
        Empleado empleadoBuscado = this.repositorioDeEmpleados.buscar(new Integer(idBuscado));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idEmpleado", idBuscado);
            put("empleado", empleadoBuscado);
        }}, "empleado/empleado.hbs");
        //TODO: armar la vista de empleados dentro de una organizacion
    }

    //METODO QUE INSTANCIA UN NUEVO EMPLEADO Y LO GUARDA EN LA BASE DE DATOS
    public Response guardar(Response response) {
        Empleado empleado = new Empleado();
        //SETEARLE LOS PARAMETROS AL MIEMBRO
        this.repositorioDeEmpleados.guardar(empleado);
        response.redirect("/organizaciones/empleados");
        return response;
    }
    public Response modificarEstado(Request request, Response response) {
        String idBuscado = request.params("id");
        Empleado empleadoBuscado = this.repositorioDeEmpleados.buscar(new Integer(idBuscado));
        empleadoBuscado.setEstado(EstadoEmpleado.valueOf(request.queryParams("estado")));
        this.repositorioDeEmpleados.guardar(empleadoBuscado);
        response.redirect("/organizaciones/empleados");
        return response;
    }

}