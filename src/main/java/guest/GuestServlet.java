package guest;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.persistence.*;


public class GuestServlet extends HttpServlet {
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

            // Display the list of guests:
            List<Guest> guestList;
            if (id != null && !id.isEmpty()) {
                long idNum = Long.parseLong(id);
                guestList =
                        em.createQuery("SELECT g FROM Guest g " +
                                "WHERE  g.id = :selectedId", Guest.class)
                                .setParameter("selectedId", idNum)
                                .getResultList();

            } else {
                guestList =
                        em.createQuery("SELECT g FROM Guest g " +
                                "WHERE  g.name LIKE :selectedName " +
                                "AND g.surname LIKE :selectedSurname ", Guest.class)
                                .setParameter("selectedName", "%" + name + "%")
                                .setParameter("selectedSurname", "%" + surname + "%")
                                .getResultList();

            }

            request.setAttribute("guests", guestList);
            request.getRequestDispatcher("/guest.jsp").forward(request, response);

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
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            // Handle a new guest (if any):

            if (!name.isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Guest(name, surname));
                em.getTransaction().commit();
            }
            List<Guest> guestList =
                    em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();
            request.setAttribute("guests", guestList);
            request.getRequestDispatcher("/guest.jsp").forward(request, response);

        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }

}