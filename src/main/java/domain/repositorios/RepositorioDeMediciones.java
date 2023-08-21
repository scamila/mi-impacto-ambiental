package domain.repositorios;

import domain.auxiliares.HuellasPorActividad;
import domain.auxiliares.HuellasPorFecha;
import db.EntityManagerHelper;
import domain.organizacion.Medicion;

import java.util.List;

public class RepositorioDeMediciones {

    public List<Medicion> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Medicion.class.getName())
                .getResultList();
    }

    public Medicion buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Medicion.class, id);
    }

    public void guardar(Medicion medicion) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(medicion);
        EntityManagerHelper.commit();
    }

    public List<HuellasPorFecha> buscarPorOrganizacion(int id) {
        List<HuellasPorFecha> mediciones = EntityManagerHelper
                .getEntityManager()
                .createQuery("select new domain.auxiliares.HuellasPorFecha(m.periodoDeImputacion, SUM(m.valor)) from Medicion m " +
                        ",Organizacion o \n" +
                        "WHERE m.organizacion = o.id " +
                        "AND o.id = " + id +
                        " GROUP BY m.periodoDeImputacion " +
                        " ORDER BY m.periodoDeImputacion DESC")
                .getResultList();

        return mediciones;
    }

    public List<HuellasPorActividad> buscarPorOrganizacionComposicion(int id) {
        List<HuellasPorActividad> mediciones = EntityManagerHelper
                .getEntityManager()
                .createQuery("select new domain.auxiliares.HuellasPorActividad(m.actividad, SUM(m.valor)) from Medicion m " +
                        ",Organizacion o \n" +
                        "WHERE m.organizacion = o.id " +
                        "AND o.id = " + id +
                        " GROUP BY m.actividad " +
                        " ORDER BY m.actividad DESC")
                .getResultList();

        return mediciones;
    }

    public List<HuellasPorFecha> buscarPorSectorEvolucion(int id) {
        List<HuellasPorFecha> mediciones = EntityManagerHelper
                .getEntityManager()
                .createQuery("select new domain.auxiliares.HuellasPorFecha(m.periodoDeImputacion, SUM(m.valor)) from Medicion m " +
                        ",Organizacion o \n" +
                        ",SectorTerritorial s \n"+
                        ",Ubicacion u \n" +
                        "WHERE s.id = u.sectorTerritorial " +
                        "AND o.ubicacionGeografica = u.id " +
                        "AND s.id = " + id +
                        "AND o.id = m.organizacion" +
                        " GROUP BY m.periodoDeImputacion " +
                        " ORDER BY m.periodoDeImputacion DESC")
                .getResultList();

        return mediciones;
    }
}