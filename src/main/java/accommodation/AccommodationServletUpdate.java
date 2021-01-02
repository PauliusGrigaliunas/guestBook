package accommodation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccommodationServletUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EntityManager createEntityManager() {
        EntityManagerFactory emf =
                (EntityManagerFactory) getServletContext().getAttribute("emf");
        return emf.createEntityManager();
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = createEntityManager();
        try {
            String nr = request.getParameter("nr");
            String ready = request.getParameter("ready");
            String space = request.getParameter("space");

            // Display the list of guests:
            int updateCount = 0;
            if (nr != null && !nr.isEmpty()) {
                long nrNum = Long.parseLong(nr);
                boolean isReady;
                if (ready == null) {
                    isReady = false;
                } else {
                    isReady = true;
                }

                em.getTransaction().begin();
                if (space != null && !space.isEmpty()) {
                    int spaceNum = Integer.parseInt(space);
                    updateCount =
                            em.createQuery("UPDATE Accommodation a " +
                                    "SET a.isReady = :changeReady, a.space = :changeSpace " +
                                    "WHERE a.nr = :selectedNr", Accommodation.class)
                                    .setParameter("changeReady", isReady)
                                    .setParameter("changeSpace", spaceNum)
                                    .setParameter("selectedNr", nrNum)
                                    .executeUpdate();

                } else {
                    updateCount =
                            em.createQuery("UPDATE Accommodation a " +
                                    "SET a.isReady = :changeReady " +
                                    "WHERE a.nr = :selectedNr", Accommodation.class)
                                    .setParameter("changeReady", isReady)
                                    .setParameter("selectedNr", nrNum)
                                    .executeUpdate();
                }
                em.getTransaction().commit();
                System.out.println("It updates " + updateCount + " entity(-ies) successfully");
            }

            List<Accommodation> guestList =
                    em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();
            request.setAttribute("accommodations", guestList);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);

        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }
}
