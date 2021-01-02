package guest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GuestServletUpdate extends HttpServlet {
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
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            // Display the list of guests:
            int updateCount = 0;
            if (id != null && !id.isEmpty()) {
                long idNum = Long.parseLong(id);
                em.getTransaction().begin();
                updateCount =
                        em.createQuery("UPDATE Guest g " +
                                "SET g.name = :selectedName, g.surname = :selectedSurname " +
                                "WHERE g.id = :selectedId", Guest.class)
                                .setParameter("selectedName", name)
                                .setParameter("selectedSurname", surname)
                                .setParameter("selectedId", idNum)
                                .executeUpdate();
                em.getTransaction().commit();
                System.out.println("It updates " + updateCount + " entity(-ies) successfully");

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
