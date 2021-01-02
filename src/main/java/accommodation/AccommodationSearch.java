package accommodation;

import javax.persistence.EntityManager;
import java.util.List;

public class AccommodationSearch {

    public List<Accommodation> Search(EntityManager em, String description, String space) {
        if (space != null && !space.isEmpty()) {
            long spaceNum = Long.parseLong(space);
            return em.createQuery("SELECT a FROM Accommodation a " +
                    "WHERE  a.space = :selectedSpace " +
                    "AND a.description LIKE :selectedDescription ", Accommodation.class)
                    .setParameter("selectedSpace", spaceNum)
                    .setParameter("selectedDescription", "%" + description + "%")
                    .getResultList();

        } else {
            return em.createQuery("SELECT a FROM Accommodation a " +
                    "WHERE a.description LIKE :selectedDescription ", Accommodation.class)
                    .setParameter("selectedDescription", "%" + description + "%")
                    .getResultList();

        }
    }
}
