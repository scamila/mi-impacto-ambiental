package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Direccion;

import java.util.List;

public class RepoDeDirecciones {

    public List<Direccion> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Direccion.class.getName())
                .getResultList();
    }

    public Direccion buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Direccion.class, id);
    }

    public void guardar(Direccion direccion) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(direccion);
        EntityManagerHelper.commit();
    }

    public void eliminar(Direccion direccion){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().remove(direccion);
        EntityManagerHelper.commit();
    }
    
}
