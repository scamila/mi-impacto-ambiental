package domain.repositorios;

import db.EntityManagerHelper;
import domain.transporte.medios.TransportePublico;

import java.util.List;

public class RepositorioDeTransportePublico {
    public List<TransportePublico> buscarTodos(){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from "+TransportePublico.class.getName())
                .getResultList();
    }
    public TransportePublico buscar(Integer id){
        return EntityManagerHelper
                .getEntityManager()
                .find(TransportePublico.class,id);
    }
    public void guardar(TransportePublico transportePublico){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(transportePublico);
        EntityManagerHelper.commit();
    }
}
