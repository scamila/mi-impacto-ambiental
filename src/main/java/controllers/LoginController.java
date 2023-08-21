package controllers;

import db.EntityManagerHelper;
import domain.organizacion.Miembro;
import domain.organizacion.Organizacion;
import domain.repositorios.RepositorioDeMiembros;
import domain.repositorios.RepositorioDeOrganizaciones;
import domain.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

    RepositorioDeOrganizaciones repoOrg = new RepositorioDeOrganizaciones();
    RepositorioDeMiembros repoMiembro = new RepositorioDeMiembros();

    public ModelAndView pantallaDeLogin(Request request, Response response) {
        return new ModelAndView(null, "login.hbs");
    }

    public Response login(Request request, Response response) {
        try {
            String query = "from "
                    + Usuario.class.getName()
                    +" WHERE nombreDeUsuario='"
                    + request.queryParams("nombre_de_usuario")
                    +"' AND contrasenia='"
                    + request.queryParams("contrasenia")
                    +"'";
            Usuario usuario = (Usuario) EntityManagerHelper
                    .getEntityManager()
                    .createQuery(query)
                    .getSingleResult();

            if(usuario.getRol().getId() == 3) {
                Organizacion org = repoOrg.buscarPorUsuario(usuario.getId());
                
                request.session(true);
                request.session().attribute("id", usuario.getId());
                response.redirect("/organizaciones/"+(org.getId())); //TODO: usuario.id o organizacion.id
            }
            if(usuario.getRol().getId() == 2) {
                Miembro miembro = repoMiembro.buscarPorUsuario(usuario.getId());
                
                request.session(true);
                request.session().attribute("id", usuario.getId()); 
                response.redirect("/miembro/"+(miembro.getId())); //TODO: usuario.id o miembro.id
            }
            else {
                response.redirect("/login");
            }
        }
        catch (Exception exception) {
            response.redirect("/login");
        }
        return response;
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/login");
        return response;
    }

    public ModelAndView prohibido(Request request, Response response) {
        return new ModelAndView(null, "prohibido.hbs");
    }
}
