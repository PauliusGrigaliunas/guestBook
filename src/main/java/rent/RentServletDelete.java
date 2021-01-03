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


public class RentServletDelete extends HttpServlet {
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

            List<Guest> guestList =
                    em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();
            List<Accommodation> accommodationList =
                    em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();


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
            String id = request.getParameter("id");
            String nr = request.getParameter("nr");
            if(id != null && nr != null){
                long idNum = Long.parseLong(id);
                int nrNum = Integer.parseInt(nr);
                em.getTransaction().begin();

                List<Guest> guestList = em.createQuery(
                        "SELECT g FROM Guest g " +
                        "WHERE  g.id = :selectedId", Guest.class)
                        .setParameter("selectedId", idNum)
                        .getResultList();

                Accommodation accommodation = em.createQuery("SELECT a FROM Accommodation a " +
                        "WHERE a.nr = :selectedNr", Accommodation.class)
                        .setParameter("selectedNr", nrNum)
                        .getSingleResult();

                accommodation.RemoveGuest(guestList);
                for(Guest guest : guestList){
                    guest.SetAccomodation(null);
                }

                em.getTransaction().commit();

                }

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