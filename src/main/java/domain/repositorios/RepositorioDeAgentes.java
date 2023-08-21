package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.AgenteSectorial;
import domain.organizacion.Organizacion;

import java.util.List;

public class RepositorioDeAgentes {

    public List<AgenteSectorial> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + AgenteSectorial.class.getName())
                .getResultList();
    }

    public AgenteSectorial buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(AgenteSectorial.class, id);
    }

    public void guardar(AgenteSectorial agente) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(agente);
        EntityManagerHelper.commit();
    }

    public List<Organizacion> buscarOrganizaciones(int id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("select o from AgenteSectorial a " +
                                ",SectorTerritorial s \n" +
                                ",Ubicacion u \n" +
                                ",Organizacion o "+
                                "WHERE a.sectorEncargado = s.id\n" +
                                "AND s.id = u.sectorTerritorial\n" +
                                "AND o.ubicacionGeografica = u.id  " +
                                "AND a.id = " +  id)
                .getResultList();
    }
}
