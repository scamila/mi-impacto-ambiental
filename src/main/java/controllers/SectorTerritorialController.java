package controllers;

import domain.organizacion.SectorTerritorial;
import domain.repositorios.RepoDeSectorTerritorial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;

public class SectorTerritorialController {

    private RepoDeSectorTerritorial repositorioDeSectorTerritorial;

    public SectorTerritorialController() {
        this.repositorioDeSectorTerritorial = new RepoDeSectorTerritorial();
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        List<SectorTerritorial> todosLosSectorTerritoriales = this.repositorioDeSectorTerritorial.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("SectorTerritorials", todosLosSectorTerritoriales);
        }}, "SectorTerritorial/SectorTerritorials.hbs");
    }

    public ModelAndView crear(Request request, Response response) {
        return new ModelAndView(null, "sectorTerritorial/SectorTerritoriales.hbs");
    }

    public Response guardar(Request request, Response response) {
        SectorTerritorial nuevoSectorTerritorial = new SectorTerritorial();

        //SETEARLE LOS PARAMETROS A LA ORGANIZACION

        this.repositorioDeSectorTerritorial.guardar(nuevoSectorTerritorial);

        response.redirect("/SectorTerritorials");
        return response;
    }

}
