package domain.repositorios;


import db.EntityManagerHelper;
import domain.organizacion.Sector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepoDeSectores {

    public List<Sector> buscarPorOrganizacion(Integer orgID) {
        String query = "from Sector"
                    +" WHERE organizacion_id ='"
                    + orgID
                    +"'";

        return EntityManagerHelper
                .getEntityManager()
                .createQuery(query)
                .getResultList();
    }

}
