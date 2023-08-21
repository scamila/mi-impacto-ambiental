package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Miembro;
import domain.trayecto.Trayecto;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeTrayectos {


    public List<Trayecto> buscarTodos() {
        List<Trayecto> trayectos =  EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Trayecto.class.getName())
                .getResultList();
        return trayectos;

    }

    public Trayecto buscar(Integer id) {

        EntityManagerHelper.beginTransaction();
        Trayecto trayecto =EntityManagerHelper
                        .getEntityManager()
                        .find(Trayecto.class, id);
        EntityManagerHelper.getEntityManager().flush();
        EntityManagerHelper.commit();
        return trayecto;

    }


    public void guardar(Trayecto trayecto) {

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(trayecto);


        EntityManagerHelper.commit();


    }

    public void eliminar(Trayecto trayecto){



        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().remove(trayecto);

        EntityManagerHelper.commit();



    }


    public List<Trayecto> buscarPorMiembro(Integer id){

       /* RepositorioDeEmpleados repo = new RepositorioDeEmpleados();*/

        List<Trayecto> trayectos = EntityManagerHelper.getEntityManager().
                createQuery("SELECT t FROM Trayecto t,Empleado e where e.id = t.empleado AND e.miembro =  " + id).getResultList();

        /* Miembro miembro =
        for(Trayecto trayecto : buscarTodos()){
            if(trayecto.getEmpleado().getMiembro().getId().equals(id)){
                trayectos.add(trayecto);
            }
        }*/

        return trayectos;
    }
}
