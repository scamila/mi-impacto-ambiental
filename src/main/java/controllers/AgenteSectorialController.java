package controllers;

import domain.organizacion.AgenteSectorial;
import domain.repositorios.RepositorioDeAgentes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;

public class AgenteSectorialController {

    private RepositorioDeAgentes repositorioDeAgentes;

    public AgenteSectorialController() {
        this.repositorioDeAgentes = new RepositorioDeAgentes();
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        List<AgenteSectorial> todosLosAgentes = this.repositorioDeAgentes.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("agenteSectorial", todosLosAgentes);
        }}, "organizaciones/agente-sectorial.hbs");
    }

    public ModelAndView crear(Request request, Response response) {
        return new ModelAndView(null, "organizaciones/agente-sectorial.hbs");
    }

    public Response guardar(Request request, Response response) {
        AgenteSectorial nuevoAgenteSectorial = new AgenteSectorial();

        //SETEARLE LOS PARAMETROS A LA ORGANIZACION

        nuevoAgenteSectorial.setNombre(request.queryParams("nombre"));
        //nuevoAgenteSectorial.setSectorEncargado(request.queryParams("sector-territorial"));

        this.repositorioDeAgentes.guardar(nuevoAgenteSectorial);

        response.redirect("/agenteSectorial");
        return response;
    }

    public ModelAndView editar(Request request, Response response) {
        String idBuscado = request.params("id");
        AgenteSectorial agenteBuscado = this.repositorioDeAgentes.buscar(new Integer(idBuscado));

        return new ModelAndView(new HashMap<String, Object>(){{
            put("agenteSectorial", agenteBuscado);
        }}, "organizaciones/agente-sectorial.hbs");
    }

    public Response modificar(Request request, Response response) {
        String idBuscado = request.params("id");
        
        AgenteSectorial agenteBuscado = this.repositorioDeAgentes.buscar(new Integer(idBuscado));

        agenteBuscado.setNombre(request.queryParams("nombre")); //"nombre " tiene que matchear con el name="nombre" del input del campo del formulario

        this.repositorioDeAgentes.guardar(agenteBuscado);

        response.redirect("/agenteSectorial");
        return response;
    }
}
