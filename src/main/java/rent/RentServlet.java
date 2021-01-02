package rent;

import accommodation.Accommodation;
import accommodation.AccommodationSearch;
import guest.Guest;
import guest.GuestSearch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class RentServlet extends HttpServlet {
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
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String description = request.getParameter("description");
            String space = request.getParameter("space");

            // Display the list of guests:

            List<Guest> guestList;
            List<Accommodation> accommodationList;
            if (id == null && name == null && surname == null &&
                    description == null && space == null) {
                guestList =
                        em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();
                accommodationList =
                        em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();
            } else {
                guestList = new GuestSearch().Search(em, id, name, surname);
                accommodationList = new AccommodationSearch().Search(em, description, space);
            }

            request.setAttribute("guests", guestList);
            request.setAttribute("accommodations", accommodationList);
            request.getRequestDispatcher("/rent.jsp").forward(request, response);


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
            List<Accommodation> accommodationList =
                    em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();
            List<Guest> guestList =
                    em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();

            request.setAttribute("accommodations", accommodationList);
            request.setAttribute("guests", guestList);
            request.getRequestDispatcher("/rent.jsp").forward(request, response);

        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }

}