package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Organizacion;
import domain.organizacion.SectorTerritorial;

import java.util.List;

public class RepoDeSectorTerritorial {

    public List<SectorTerritorial> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + SectorTerritorial.class.getName())
                .getResultList();
    }

    public SectorTerritorial buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(SectorTerritorial.class, id);
    }

    public void guardar(SectorTerritorial sector) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(sector);
        EntityManagerHelper.commit();
    }

    public List<Organizacion> buscarOrganizaciones(int id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("select o from SectorTerritorial s " +
                        ",Ubicacion u \n" +
                        ",Organizacion o "+
                        "WHERE s.id = u.sectorTerritorial\n" +
                        "AND o.ubicacionGeografica = u.id  " +
                        "AND s.id = " +  id)
                .getResultList();
    }
}
