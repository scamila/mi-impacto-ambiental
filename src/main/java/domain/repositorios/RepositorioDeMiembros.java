package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Miembro;

import java.util.List;

public class RepositorioDeMiembros {

    public List<Miembro> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Miembro.class.getName())
                .getResultList();
    }

    public Miembro buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Miembro.class, id);
    }

    public void guardar(Miembro miembro) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(miembro);
        EntityManagerHelper.commit();
    }

    public Miembro buscarPorUsuario(Integer usuarioID){
        String query = "from Miembro"
                    +" WHERE usuario='"
                    + usuarioID
                    +"'";
        Miembro miembro = (Miembro) EntityManagerHelper
                    .getEntityManager()
                    .createQuery(query)
                    .getSingleResult();

        return miembro;
    }

    public List<Miembro> buscarPorOrganizacion(int id){
        String query = " SELECT m from Miembro m," +
                " Empleado e"
                +" WHERE e.organizacion ='"
                + id
                +"'";
        List<Miembro> miembros = EntityManagerHelper
                .getEntityManager()
                .createQuery(query)
                .getResultList();

        return miembros;
    }
}