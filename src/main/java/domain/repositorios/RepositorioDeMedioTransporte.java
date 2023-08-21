package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.MedioTransporte;
import java.util.List;
public class RepositorioDeMedioTransporte {
    public List<MedioTransporte> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + MedioTransporte.class.getName())
                .getResultList();
    }
    public MedioTransporte buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(MedioTransporte.class, id);
    }
    public void guardar(MedioTransporte medioTransporte){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(medioTransporte);
        EntityManagerHelper.commit();
    }
}
