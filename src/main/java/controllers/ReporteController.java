package controllers;
import domain.auxiliares.*;
import domain.organizacion.*;
import domain.repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReporteController {

    RepoDeSectorTerritorial repositorioSectores = new RepoDeSectorTerritorial();
    RepositorioDeOrganizaciones repositorioDeOrganizaciones = new RepositorioDeOrganizaciones();
    RepositorioDeAgentes repositorioDeAgentes = new RepositorioDeAgentes();
    RepositorioDeMediciones repositorioDeMediciones = new RepositorioDeMediciones();
    RepositorioDeMiembros repositorioDeMiembros = new RepositorioDeMiembros();
    RepositorioDeEmpleados repositorioDeEmpleados = new RepositorioDeEmpleados();

    public ModelAndView mostrarReportes(Request request,Response response){
        String idBuscado = request.params("id");

        return new ModelAndView(new HashMap<String, Object>(){{
            put("idOrganizacion", idBuscado);
        }}, "reportes/reportes.hbs");
    }
    public ModelAndView mostrarReportesMiembro(Request request,Response response){
        String idBuscado = request.params("id");
        return new ModelAndView(new HashMap<String, Object>(){{
            put("idMiembro", idBuscado);
        }}, "miembro/reportes/reportes.hbs");
    }

    public ModelAndView reportePorSectorTerritorial(Request request, Response response) {

        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        List<AgenteSectorial> agentes = repositorioDeAgentes.buscarTodos();


        List<HuellaPorSector> huellas = new ArrayList<>();

        for (AgenteSectorial agente : agentes) {
            HuellaPorSector huella = new HuellaPorSector();

            List<Organizacion> organizaciones = repositorioDeAgentes.buscarOrganizaciones(agente.getId());

            huella.setHuella(agente.huellaPorSector(organizaciones));
            huella.setNombreSector(agente.getSectorEncargado().getNombre());

            huellas.add(huella);
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellas", huellas);
        }}, "reportes/hc_sector_territorial.hbs");
    }


    public ModelAndView reportePorTipoDeOrganizacion(Request request, Response response) {

        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));
        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        HuellaPorOrganizacion huellasPorOrganizacion = new HuellaPorOrganizacion();


        for (Organizacion organizacion : organizaciones) {

            switch (organizacion.getTipo()) {
                case INSTITUCION:
                    huellasPorOrganizacion.sethuellaInstitucion(huellasPorOrganizacion.getHuellaInstitucion() + organizacion.getHuellaAnual());
                    break;
                case ONG:
                    huellasPorOrganizacion.sethuellaOng(huellasPorOrganizacion.getHuellaOng() + organizacion.getHuellaAnual());
                    break;

                case EMPRESA:
                    huellasPorOrganizacion.setHuellaEmpresa(huellasPorOrganizacion.getHuellaEmpresa() + organizacion.getHuellaAnual());
                    break;

                case GUBERNAMENTAL:
                    huellasPorOrganizacion.sethuellaGubernamental(huellasPorOrganizacion.getHuellaGubernamental() + organizacion.getHuellaAnual());
                    break;
            }
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("organizaciones", huellasPorOrganizacion);
        }}, "reportes/hc_tipo_organizacion.hbs");

    }

    public ModelAndView reporteDeDeterminadoSectorTerritorial(Request request, Response response) {

        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        HuellaPorOrganizacion huellasPorOrganizacion = new HuellaPorOrganizacion();

        for (Organizacion organizacion : organizaciones) {

            switch (organizacion.getTipo()) {
                case INSTITUCION:
                    huellasPorOrganizacion.sethuellaInstitucion(huellasPorOrganizacion.getHuellaInstitucion() + organizacion.getHuellaAnual());
                    break;
                case ONG:
                    huellasPorOrganizacion.sethuellaOng(huellasPorOrganizacion.getHuellaOng() + organizacion.getHuellaAnual());
                    break;

                case EMPRESA:
                    huellasPorOrganizacion.setHuellaEmpresa(huellasPorOrganizacion.getHuellaEmpresa() + organizacion.getHuellaAnual());
                    break;

                case GUBERNAMENTAL:
                    huellasPorOrganizacion.sethuellaGubernamental(huellasPorOrganizacion.getHuellaGubernamental() + organizacion.getHuellaAnual());
                    break;
            }
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("organizaciones", huellasPorOrganizacion);
        }}, "reportes/hc_tipo_organizacion.hbs");

    }

    public ModelAndView reportePorProvincia(Request request, Response response){
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<HuellaProvincias> huellaProvincias = repositorioDeOrganizaciones.buscarOrganizacionesOrdenadoPorProvincia();

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellas", huellaProvincias);
        }}, "reportes/hc_por_provincia.hbs");

    }

    public ModelAndView reporteDeUnSectorParticular(Request request, Response response){
        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<SectorTerritorial> sectores = repositorioSectores.buscarTodos();
        List<HuellaSectorDeterminado> huellaDeSectores = new ArrayList<>();
        for(SectorTerritorial sector: sectores){
            HuellaSectorDeterminado huellaParticular = new HuellaSectorDeterminado();

            huellaParticular.setOrganizaciones(repositorioSectores.buscarOrganizaciones(sector.getId()));
            huellaParticular.setSector(sector.getNombre());

            huellaDeSectores.add(huellaParticular);
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellaDeSectores", huellaDeSectores);
        }}, "reportes/hc_determinado_sector.hbs");
    }

    public ModelAndView reporteEvolucionDeOrganizacion(Request request, Response response){


        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        List<HuellaEvolucionOrganizacion> huellas = new ArrayList<>();

        for(Organizacion organizacion: organizaciones){
            HuellaEvolucionOrganizacion huellaEvolucion = new HuellaEvolucionOrganizacion();
            huellaEvolucion.setHuellasPorFecha(repositorioDeMediciones.buscarPorOrganizacion(organizacion.getId()));
            huellaEvolucion.setOrganizacion(organizacion.getRazonSocial());

            huellas.add(huellaEvolucion);
        }
        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellas", huellas);
        }}, "reportes/hc_evolucion_organizacion.hbs");
    }

    public ModelAndView reporteComposicionOrganizacion(Request request, Response response){

        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        List<HuellasPorActividadOrganizacion> huellas = new ArrayList<>();



        for(Organizacion organizacion: organizaciones){

            HuellasPorActividadOrganizacion huellaComposicion = new HuellasPorActividadOrganizacion();
            List<HuellasPorActividad> huellasActividad = repositorioDeMediciones.buscarPorOrganizacionComposicion(organizacion.getId());
            HuellasPorActividad huellaActividad = new HuellasPorActividad();


            huellaActividad.setActividad("Miembros");

            huellaActividad.setHuellaCarbono(String.valueOf(repositorioDeOrganizaciones.huellaCarbonoEnTrayctosOrganizacion(organizacion.getId())));
            huellasActividad.add(huellaActividad);

            huellaComposicion.setHuellasActividad(huellasActividad);
            huellaComposicion.setOrganizacion(organizacion.getRazonSocial());


            huellas.add(huellaComposicion);
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellas", huellas);
        }}, "reportes/hc_composicion_organizacion.hbs");
    }

    public ModelAndView reporteEvolucionSector(Request request, Response response){

        String idBuscado = request.params("id");
        Organizacion organizacionBuscada = this.repositorioDeOrganizaciones.buscar(new Integer(idBuscado));

        List<SectorTerritorial> sectores = repositorioSectores.buscarTodos();
        List<HuellaEvolucionSector> huellas = new ArrayList<>();


        for(SectorTerritorial sector: sectores){
            HuellaEvolucionSector huellaEvolucionSector = new HuellaEvolucionSector();
            huellaEvolucionSector.setHuellasPorFecha(repositorioDeMediciones.buscarPorSectorEvolucion(sector.getId()));
            huellaEvolucionSector.setSector(sector.getNombre());

            huellas.add(huellaEvolucionSector);
        }
        return new ModelAndView(new HashMap<String, Object>() {{
            put("idOrganizacion", idBuscado);
            put("organizacion", organizacionBuscada);
            put("huellas", huellas);
        }}, "reportes/hc_evolucion_sector.hbs");
    }

    public ModelAndView reportePorSectorTerritorialMiembro(Request request, Response response) {
        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<AgenteSectorial> agentes = repositorioDeAgentes.buscarTodos();


        List<HuellaPorSector> huellas = new ArrayList<>();

        for (AgenteSectorial agente : agentes) {
            HuellaPorSector huella = new HuellaPorSector();

            List<Organizacion> organizaciones = repositorioDeAgentes.buscarOrganizaciones(agente.getId());

            huella.setHuella(agente.huellaPorSector(organizaciones));
            huella.setNombreSector(agente.getSectorEncargado().getNombre());

            huellas.add(huella);
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellas", huellas);
        }}, "miembro/reportes/hc_sector_territorial.hbs");
    }


    public ModelAndView reportePorTipoDeOrganizacionMiembro(Request request, Response response) {

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        HuellaPorOrganizacion huellasPorOrganizacion = new HuellaPorOrganizacion();

        for (Organizacion organizacion : organizaciones) {

            switch (organizacion.getTipo()) {
                case INSTITUCION:
                    huellasPorOrganizacion.sethuellaInstitucion(huellasPorOrganizacion.getHuellaInstitucion() + organizacion.getHuellaAnual());
                    break;
                case ONG:
                    huellasPorOrganizacion.sethuellaOng(huellasPorOrganizacion.getHuellaOng() + organizacion.getHuellaAnual());
                    break;

                case EMPRESA:
                    huellasPorOrganizacion.setHuellaEmpresa(huellasPorOrganizacion.getHuellaEmpresa() + organizacion.getHuellaAnual());
                    break;

                case GUBERNAMENTAL:
                    huellasPorOrganizacion.sethuellaGubernamental(huellasPorOrganizacion.getHuellaGubernamental() + organizacion.getHuellaAnual());
                    break;
            }
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("organizaciones", huellasPorOrganizacion);
        }}, "miembro/reportes/hc_tipo_organizacion.hbs");

    }

    public ModelAndView reporteDeDeterminadoSectorTerritorialMiembro(Request request, Response response) {

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        HuellaPorOrganizacion huellasPorOrganizacion = new HuellaPorOrganizacion();

        for (Organizacion organizacion : organizaciones) {

            switch (organizacion.getTipo()) {
                case INSTITUCION:
                    huellasPorOrganizacion.sethuellaInstitucion(huellasPorOrganizacion.getHuellaInstitucion() + organizacion.getHuellaAnual());
                    break;
                case ONG:
                    huellasPorOrganizacion.sethuellaOng(huellasPorOrganizacion.getHuellaOng() + organizacion.getHuellaAnual());
                    break;

                case EMPRESA:
                    huellasPorOrganizacion.setHuellaEmpresa(huellasPorOrganizacion.getHuellaEmpresa() + organizacion.getHuellaAnual());
                    break;

                case GUBERNAMENTAL:
                    huellasPorOrganizacion.sethuellaGubernamental(huellasPorOrganizacion.getHuellaGubernamental() + organizacion.getHuellaAnual());
                    break;
            }
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("organizaciones", huellasPorOrganizacion);
        }}, "miembro/reportes/hc_tipo_organizacion.hbs");

    }

    public ModelAndView reportePorProvinciaMiembro(Request request, Response response){

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<HuellaProvincias> huellaProvincias = repositorioDeOrganizaciones.buscarOrganizacionesOrdenadoPorProvincia();

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellas", huellaProvincias);
        }}, "miembro/reportes/hc_por_provincia.hbs");

    }

    public ModelAndView reporteDeUnSectorParticularMiembro(Request request, Response response){

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<SectorTerritorial> sectores = repositorioSectores.buscarTodos();
        List<HuellaSectorDeterminado> huellaDeSectores = new ArrayList<>();
        for(SectorTerritorial sector: sectores){
            HuellaSectorDeterminado huellaParticular = new HuellaSectorDeterminado();

            huellaParticular.setOrganizaciones(repositorioSectores.buscarOrganizaciones(sector.getId()));
            huellaParticular.setSector(sector.getNombre());

            huellaDeSectores.add(huellaParticular);
        }

        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellaDeSectores", huellaDeSectores);
        }}, "miembro/reportes/hc_determinado_sector.hbs");
    }

    public ModelAndView reporteEvolucionDeOrganizacionMiembro(Request request, Response response){

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        List<HuellaEvolucionOrganizacion> huellas = new ArrayList<>();

        for(Organizacion organizacion: organizaciones){
            HuellaEvolucionOrganizacion huellaEvolucion = new HuellaEvolucionOrganizacion();
            huellaEvolucion.setHuellasPorFecha(repositorioDeMediciones.buscarPorOrganizacion(organizacion.getId()));
            huellaEvolucion.setOrganizacion(organizacion.getRazonSocial());

            huellas.add(huellaEvolucion);
        }
        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellas", huellas);
        }}, "miembro/reportes/hc_evolucion_organizacion.hbs");
    }

    public ModelAndView reporteComposicionOrganizacionMiembro(Request request, Response response){

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<Organizacion> organizaciones = repositorioDeOrganizaciones.buscarTodos();
        List<HuellasPorActividadOrganizacion> huellas = new ArrayList<>();

        for(Organizacion organizacion: organizaciones){
            HuellasPorActividadOrganizacion huellaComposicion = new HuellasPorActividadOrganizacion();
            List<HuellasPorActividad> huellasActividad = repositorioDeMediciones.buscarPorOrganizacionComposicion(organizacion.getId());
            HuellasPorActividad huellaActividad = new HuellasPorActividad();


            huellaActividad.setActividad("Miembros");

            huellaActividad.setHuellaCarbono(String.valueOf(repositorioDeOrganizaciones.huellaCarbonoEnTrayctosOrganizacion(organizacion.getId())));
            huellasActividad.add(huellaActividad);

            huellaComposicion.setHuellasActividad(huellasActividad);
            huellaComposicion.setOrganizacion(organizacion.getRazonSocial());

            huellas.add(huellaComposicion);
        }
        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellas", huellas);
        }}, "miembro/reportes/hc_composicion_organizacion.hbs");
    }

    public ModelAndView reporteEvolucionSectorMiembro(Request request, Response response){

        String idBuscado = request.params("id");
        Miembro miembroBuscado = this.repositorioDeMiembros.buscar(new Integer(idBuscado));

        List<SectorTerritorial> sectores = repositorioSectores.buscarTodos();
        List<HuellaEvolucionSector> huellas = new ArrayList<>();


        for(SectorTerritorial sector: sectores){
            HuellaEvolucionSector huellaEvolucionSector = new HuellaEvolucionSector();
            huellaEvolucionSector.setHuellasPorFecha(repositorioDeMediciones.buscarPorSectorEvolucion(sector.getId()));
            huellaEvolucionSector.setSector(sector.getNombre());

            huellas.add(huellaEvolucionSector);
        }
        return new ModelAndView(new HashMap<String, Object>() {{
            put("idMiembro", idBuscado);
            put("miembro", miembroBuscado);
            put("huellas", huellas);
        }}, "miembro/reportes/hc_evolucion_sector.hbs");
    }
}


