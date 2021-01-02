package guest;

import javax.persistence.EntityManager;
import java.util.List;

public class GuestSearch {
    public List<Guest> Search(EntityManager em, String id, String name, String surname) {
        List<Guest> guestList;
        if (id != null && !id.isEmpty()) {
            long idNum = Long.parseLong(id);
            return em.createQuery("SELECT g FROM Guest g " +
                    "WHERE  g.id = :selectedId", Guest.class)
                    .setParameter("selectedId", idNum)
                    .getResultList();

        } else {
            return em.createQuery("SELECT g FROM Guest g " +
                    "WHERE  g.name LIKE :selectedName " +
                    "AND g.surname LIKE :selectedSurname ", Guest.class)
                    .setParameter("selectedName", "%" + name + "%")
                    .setParameter("selectedSurname", "%" + surname + "%")
                    .getResultList();

        }
    }
}
