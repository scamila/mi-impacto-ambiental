package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.AgenteSectorial;
import domain.organizacion.Organizacion;
import domain.organizacion.Ubicacion;

import java.util.List;

public class RepositorioDeUbicaciones {

    public List<Ubicacion> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Ubicacion.class.getName())
                .getResultList();
    }

    public Ubicacion buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Ubicacion.class, id);
    }

    public void guardar(Ubicacion ubicacion) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(ubicacion);
        EntityManagerHelper.commit();
    }


}
