package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.Parada;

import java.util.List;

public class RepoParadas {
    public List<Parada> buscarTodos(){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from "+Parada.class.getName())
                .getResultList();
    }
    public Parada buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(Parada.class,id);
    }
    public void guardar(Parada parada){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(parada);
        EntityManagerHelper.commit();
    }

    public List<Parada> buscarPorRecorrido(Integer idRecorrido){
        String query = "from "
                    + Parada.class.getName()
                    +" WHERE recorrido_id='"
                    + idRecorrido
                    +"'";
        List<Parada> miembros = EntityManagerHelper
                                .getEntityManager()
                                .createQuery(query)
                                .getResultList();
        return miembros;
    }
}
