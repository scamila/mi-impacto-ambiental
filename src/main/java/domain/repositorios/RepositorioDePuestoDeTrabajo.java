package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Organizacion;
import domain.organizacion.PuestoDeTrabajo;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RepositorioDePuestoDeTrabajo {

    public List<PuestoDeTrabajo> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + PuestoDeTrabajo.class.getName())
                .getResultList();
    }

    public PuestoDeTrabajo buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(PuestoDeTrabajo.class, id);
    }

    public void guardar(PuestoDeTrabajo puestoDeTrabajo) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(puestoDeTrabajo);
        EntityManagerHelper.commit();
    }

    public List<PuestoDeTrabajo> buscarPorSector(Integer sectorID) {
        return buscarTodos().stream().filter(puesto -> puesto.getSector().getId().equals(sectorID)).collect(Collectors.toList());
    }

}
