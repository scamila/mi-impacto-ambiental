package server;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.servlet.MultipartConfigElement;

import controllers.*;
import domain.organizacion.Medicion;
import domain.usuarios.Usuario;
import domain.usuarios.Permiso;
import domain.usuarios.Rol;
import middlewares.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import helpers.PermisoHelper;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        Spark.staticFiles.externalLocation("upload");

        // !!!!!!Template!!!!!

        LoginController loginController = new LoginController();
        OrganizacionController organizacionController = new OrganizacionController();
        MedicionController medicionController = new MedicionController();
        AgenteSectorialController agenteController = new AgenteSectorialController();
        TrayectoController trayectoController = new TrayectoController();
        TramoController tramosController = new TramoController();
        MiembroController miembroController = new MiembroController();
        SectorTerritorialController sectorTerritorialController = new SectorTerritorialController();
        ReporteController reporteController = new ReporteController();
        Spark.path("/login", () -> {
            Spark.get("", loginController::pantallaDeLogin, engine);
            Spark.post("", loginController::login);
            Spark.post("/logout", loginController::logout);
        });

        Spark.get("/prohibido", loginController::prohibido, engine);

        Spark.path("/recomendaciones", ()->{
            Spark.get("",medicionController::mostrarRecomendaciones,engine);
        });
        Spark.path("/organizaciones", () -> {
            Spark.before("", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("", ((request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACIONES)) {
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            }));

            Spark.get("", organizacionController::mostrarTodos, engine);
            Spark.get("/crear", organizacionController::crear, engine);
            Spark.post("", organizacionController::guardar);
            Spark.get("/:id/recomendaciones",organizacionController::mostrarRecomendaciones,engine);
            Spark.get("/:id", organizacionController::inicio, engine);
            Spark.get("/:id/empleados", organizacionController::mostrarEmpleados, engine);
            Spark.get("/:id/editar", organizacionController::editar, engine);
            Spark.post("/:id", organizacionController::modificar);
            Spark.get("/:id/huellaCarbono", organizacionController::calcularHuella,engine);
            Spark.get("/:id/vinculacion",organizacionController::vincularEmpleados,engine);
            Spark.post("/:id/vinculacion-aceptar",organizacionController::aceptarEmpleadoV2,engine);
            Spark.post("/:id/vinculacion-rechazar",organizacionController::rechazarEmpleadoV2,engine);
            Spark.path("/:idorganizacion/mediciones", () -> {
                
                Spark.get("", medicionController::crear, engine);
                //Spark.post("", medicionController::guardar);
                Spark.post("", (req, res) -> {
                    String idBuscado = req.params("idorganizacion");
                    Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
        
                    req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        
                    try (InputStream input = req.raw().getPart("excel").getInputStream()) { // getPart needs to use same "name" as input field in form
                        Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                    
                    medicionController.setearMediciones(tempFile.toAbsolutePath().toString(),idBuscado);
                    res.redirect("/organizaciones/"+idBuscado+"/mediciones");
                    return res;
        
                });
            });

            Spark.path("/:id/reportes",() -> {
                Spark.get("",reporteController::mostrarReportes,engine);
                Spark.get("/reporte1",reporteController::reportePorSectorTerritorial,engine);
                Spark.get("/reporte2",reporteController::reportePorTipoDeOrganizacion,engine);
                Spark.get("/reporte3",reporteController::reporteDeUnSectorParticular,engine);
                Spark.get("/reporte4",reporteController::reportePorProvincia,engine);
                Spark.get("/reporte5",reporteController::reporteComposicionOrganizacion,engine);
                Spark.get("/reporte6",reporteController::reporteEvolucionDeOrganizacion,engine);
                Spark.get("/reporte7",reporteController::reporteEvolucionSector,engine);


            });
        });

        Spark.path("/miembro", () ->{
            
            Spark.before("", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("", ((request, response) -> {
                if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACIONES_MIEMBRO)) {
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            }));
            Spark.get("/:id/recomendaciones",miembroController::mostrarRecomendaciones,engine);
            Spark.get("/:id", miembroController::inicio,engine);
            Spark.get("/:id/organizaciones", miembroController::mostrarOrganizaciones,engine);
            Spark.get("/:id/calculadora/:idOrganizacion", miembroController::calculadora,engine);
            Spark.path("/:id/trayectos",() -> {
                Spark.get("", trayectoController::mostrarTodos,engine); 
                Spark.get("/crear",trayectoController::crear,engine); 
                Spark.post("", trayectoController::guardarV2,engine);
                Spark.post("/:idTrayecto", trayectoController::modificar);
                Spark.delete("/:idTrayecto", trayectoController::eliminar);
                Spark.path("/:idTrayecto/tramos",() -> { //TODO sacarle el tramos
                    Spark.get("", trayectoController::mostrar, engine);    
                    Spark.get("/crear",tramosController::crear,engine);
                    Spark.get("/:idTramo/editar", tramosController::editar, engine);
                    Spark.post("/:idTramo", tramosController::modificar);
                    Spark.delete("/:idTramo", tramosController::eliminar);
                });
                
            });
            Spark.path("/:id/reportes",() -> {
                Spark.get("",reporteController::mostrarReportesMiembro,engine);
                Spark.get("/reporte1",reporteController::reportePorSectorTerritorialMiembro,engine);
                Spark.get("/reporte2",reporteController::reportePorTipoDeOrganizacionMiembro,engine);
                Spark.get("/reporte3",reporteController::reporteDeUnSectorParticularMiembro,engine);
                Spark.get("/reporte4",reporteController::reportePorProvinciaMiembro,engine);
                Spark.get("/reporte5",reporteController::reporteComposicionOrganizacionMiembro,engine);
                Spark.get("/reporte6",reporteController::reporteEvolucionDeOrganizacionMiembro,engine);
                Spark.get("/reporte7",reporteController::reporteEvolucionSectorMiembro,engine);
            });
            
        });

        Spark.path("/agenteSectorial", () ->{


            Spark.get("", agenteController::mostrarTodos,engine);
            Spark.get("/crear",agenteController::crear,engine);
            Spark.post("", agenteController::guardar);
            Spark.get("/:id/editar", agenteController::editar, engine);
            Spark.post("/:id", agenteController::modificar);

        });

        Spark.path("/sector-territorial",()->{
            Spark.get("", sectorTerritorialController::mostrarTodos,engine);
            Spark.get("/crear",sectorTerritorialController::crear,engine);
            Spark.post("", sectorTerritorialController::guardar);
        });

        Spark.path("/organizacion/calcularHuella",()->{
            Spark.get("", organizacionController::mostrarTodos,engine);
            Spark.get("/crear",organizacionController::crear,engine);
            Spark.post("", organizacionController::guardar);
        });

    }
}
