package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.Recorrido;

import java.util.List;

public class RepoRecorridos {
    public List<Recorrido> buscarTodos(){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from "+Recorrido.class.getName())
                .getResultList();
    }
    public Recorrido buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(Recorrido.class,id);
    }
    public void guardar(Recorrido recorrido){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(recorrido);
        EntityManagerHelper.commit();
    }
}

