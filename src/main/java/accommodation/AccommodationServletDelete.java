package accommodation;

import guest.Guest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccommodationServletDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Object UserDataService;

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

            // Display the list of guests:
            int deletedCount = 0;
            if (nr != null && !nr.isEmpty()) {
                long nrNum = Long.parseLong(nr);
                em.getTransaction().begin();
                deletedCount =
                        em.createQuery("DELETE FROM Accommodation a " +
                                "WHERE  a.nr = :selectedNr", Accommodation.class)
                                .setParameter("selectedNr", nrNum)
                                .executeUpdate();
                em.getTransaction().commit();
                System.out.println("It deletes " + deletedCount + " entity(-ies) successfully");

            }
            List<Accommodation> accommodationList =
                    em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();
            request.setAttribute("accommodations", accommodationList);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);

        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }
}
