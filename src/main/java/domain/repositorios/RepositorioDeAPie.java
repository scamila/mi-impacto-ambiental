package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.medios.APie;

import java.util.List;
public class RepositorioDeAPie {
    public List<APie> buscarTodos(){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from "+APie.class.getName())
                .getResultList();
    }
    public APie buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(APie.class,id);
    }
    public void guardar(APie aPie){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(aPie);
        EntityManagerHelper.commit();
    }
}
