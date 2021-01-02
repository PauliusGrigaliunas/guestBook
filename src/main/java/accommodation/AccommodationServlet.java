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


public class AccommodationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Object UserDataService;

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
            List<Accommodation> accommodationList =
                    em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();

            request.setAttribute("accommodations", accommodationList);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);


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
            System.out.println(isHouse);
            // Handle a new guest (if any):

            if (!decription.isEmpty()) {
                em.getTransaction().begin();
                Accommodation accommodation;
                if(isHouse == null){
                    accommodation = new HotelRoom(decription, Integer.parseInt(space));
                }
                else{
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