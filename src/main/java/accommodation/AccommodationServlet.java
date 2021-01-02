package accommodation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class AccommodationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EntityManager createEntityManager() {
        EntityManagerFactory emf =
                (EntityManagerFactory) getServletContext().getAttribute("emf");
        return emf.createEntityManager();
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtain a PersistenceManager instance:
        EntityManager em = createEntityManager();

        try {
            String description = request.getParameter("description");
            String space = request.getParameter("space");

            // Display the list of guests:
            List<Accommodation> guestList;
            if (space != null && !space.isEmpty()) {
                long spaceNum = Long.parseLong(space);
                guestList =
                        em.createQuery("SELECT a FROM Accommodation a " +
                                "WHERE  a.space = :selectedSpace " +
                                "AND a.description LIKE :selectedDescription ", Accommodation.class)
                                .setParameter("selectedSpace", spaceNum)
                                .setParameter("selectedDescription", "%" + description + "%")
                                .getResultList();

            } else {
                guestList =
                        em.createQuery("SELECT a FROM Accommodation a " +
                                "WHERE a.description LIKE :selectedDescription ", Accommodation.class)
                                .setParameter("selectedDescription", "%" + description + "%")
                                .getResultList();

            }

            request.setAttribute("accommodations", guestList);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);


        } catch (NumberFormatException nfe) {
            System.out.println("Can not convert space input to number!");
            throw nfe;
        } finally {
            // Close the PersistenceManager:
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }

    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = createEntityManager();
        try {
            String decription = request.getParameter("decription");
            String space = request.getParameter("space");
            String isHouse = request.getParameter("isHouse");
            // Handle a new guest (if any):

            if (!decription.isEmpty()) {
                em.getTransaction().begin();
                Accommodation accommodation;
                if (isHouse == null) {
                    accommodation = new HotelRoom(decription, Integer.parseInt(space));
                } else {
                    accommodation = new House(decription, Integer.parseInt(space));
                }
                em.persist(accommodation);
                em.getTransaction().commit();
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