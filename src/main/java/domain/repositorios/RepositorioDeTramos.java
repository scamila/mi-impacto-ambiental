package domain.repositorios;

import db.EntityManagerHelper;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;

import java.util.List;

public class RepositorioDeTramos {

    public List<Tramo> buscarTodos() {
        List<Tramo> tramos =
         EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Tramo.class.getName())
                .getResultList();

        return tramos;
    }

    public Tramo buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Tramo.class, id);
    }

    public void guardar(Tramo tramo) {

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(tramo);
        EntityManagerHelper.commit();

    }

    public void eliminar(Tramo tramo){

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().remove(tramo);
        EntityManagerHelper.commit();

    }
    public Trayecto buscarTrayecto(Tramo tramo){

        return (Trayecto) EntityManagerHelper.getEntityManager().createQuery("SELECT t FROM Trayecto t, Tramo tr WHERE tr.trayecto = t.id AND tr.id " + tramo.getId() ).getSingleResult();

    }
    
}
