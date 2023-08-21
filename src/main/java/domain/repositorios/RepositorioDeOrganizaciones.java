package domain.repositorios;

import domain.auxiliares.HuellaProvincias;
import db.EntityManagerHelper;
import domain.organizacion.Organizacion;
import domain.trayecto.Trayecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositorioDeOrganizaciones {

    public List<Organizacion> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Organizacion.class.getName())
                .getResultList();
    }

    public Organizacion buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Organizacion.class, id);
    }

    public void guardar(Organizacion organizacion) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(organizacion);
        EntityManagerHelper.commit();
    }

    public List<HuellaProvincias> buscarOrganizacionesOrdenadoPorProvincia() {
        List<HuellaProvincias> huellas =  EntityManagerHelper
                .getEntityManager()
                .createQuery("SELECT new domain.auxiliares.HuellaProvincias(o.huellaAnual,o.razonSocial, p.nombre) from Organizacion o " +
                                ",Ubicacion u \n" +
                                ",Provincia p \n" +
                                "WHERE o.ubicacionGeografica = u.id\n" +
                                "AND u.provincia = p.id \n" +
                                "ORDER BY u.provincia")
                .getResultList();

        return huellas;
    }
    
    public Organizacion buscarPorUsuario(Integer usuarioID){
        String query = "from Organizacion"
                    +" WHERE usuario ='"
                    + usuarioID
                    +"'";
        Organizacion org = (Organizacion) EntityManagerHelper
                    .getEntityManager()
                    .createQuery(query)
                    .getSingleResult();

        return org;
    }

    public float huellaCarbonoEnTrayctosOrganizacion(Integer idOrg){
        List<Trayecto> trayectos = new ArrayList<>();

        trayectos =  EntityManagerHelper
                .getEntityManager()
                .createQuery("select t from Trayecto t " +
                        ",Organizacion o\n" +
                        "WHERE o.id = t.orgDestino\n" +
                        "AND o.id = " +  idOrg)
                .getResultList();

        float valor = trayectos.stream().map(a -> a.huellaDeCarbonoDeTrayecto()).reduce(0F, (a, b) -> a + b);
        return valor;
    }

    public void actualizarHuella(Organizacion organizacion) {
        EntityManagerHelper.beginTransaction();

        String query = " UPDATE Organizacion SET huellaAnual = " + organizacion.getHuellaAnual()
                +" WHERE id ='"
                + organizacion.getId()
                +"'";
       EntityManagerHelper.getEntityManager().createQuery(query);
       EntityManagerHelper.commit();

    }


}
