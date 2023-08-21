package domain.repositorios;

import db.EntityManagerHelper;
import domain.organizacion.Empleado;
import domain.organizacion.Organizacion;
import domain.trayecto.Trayecto;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static domain.organizacion.EstadoEmpleado.NOVINCULADO;

public class RepositorioDeEmpleados {

    public List<Empleado> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Empleado.class.getName())
                .getResultList();
    }

    public Empleado buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Empleado.class, id);
    }

    public void guardar(Empleado empleado) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(empleado);
        EntityManagerHelper.commit();
    }

    public List<Empleado> buscarPorMiembro(Integer idMiembro){
        List<Empleado> empleos = new ArrayList<Empleado>();

        for(Empleado empleado : buscarTodos()){
            if(empleado.getMiembro().getId().equals(idMiembro)){
                empleos.add(empleado);
            }
        }
        return empleos;
    }

    public List<Empleado> buscarPorOrg(Integer idOrg){
        List<Empleado> empleos = new ArrayList<Empleado>();

        for(Empleado empleado : buscarTodos()){
            if(empleado.getOrganizacion().getId().equals(idOrg)){
                empleos.add(empleado);
            }
        }
        return empleos;
    }

    public List<Empleado> buscarPorNoVinculado(int id){
        List<Empleado> empleadosNoVinculados = new ArrayList<Empleado>();
        for (Empleado empleado : buscarPorOrg(id)){
            if (empleado.getEstado().equals(NOVINCULADO)){
                empleadosNoVinculados.add(empleado);
            }
        }
        return empleadosNoVinculados;

    }
    public Empleado buscarPorMiembroYOrganizacion(int idEmpleado){
        String Query  = "SELECT e FROM Empleado e" +
                        " WHERE e.id = " + idEmpleado;

        Empleado e = (Empleado) EntityManagerHelper.getEntityManager().createQuery(Query).getSingleResult();

       return e;
    }

    public void actualizarEstado(Empleado empleado) {
        EntityManagerHelper.beginTransaction();

        String query = " UPDATE Empleado SET estado = " + empleado.getEstado()
                +" WHERE id ='"
                + empleado.getId()
                +"'";
       EntityManagerHelper.getEntityManager().createQuery(query);
       EntityManagerHelper.commit();

    }


}
