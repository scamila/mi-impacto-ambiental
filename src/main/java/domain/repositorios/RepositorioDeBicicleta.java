package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.medios.Bicicleta;

import java.util.List;

public class RepositorioDeBicicleta {
    public List<Bicicleta> buscarTodos(){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from "+ Bicicleta.class.getName())
                .getResultList();
    }
    public Bicicleta buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(Bicicleta.class,id);
    }
    public void guardar(Bicicleta bicicleta){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(bicicleta);
        EntityManagerHelper.commit();
    }
}
