package guestBook;
 
import javax.persistence.*;
import javax.servlet.*;
 
public class GuestListener implements ServletContextListener {

    // Prepare the EntityManagerFactory & Enhance:
    @Override
    public void contextInitialized(ServletContextEvent e) {
        com.objectdb.Enhancer.enhance("guest.*");
        com.objectdb.Enhancer.enhance("accommodation.*");
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("$objectdb/db/guest.odb");
        e.getServletContext().setAttribute("emf", emf);
    }
 
    // Release the EntityManagerFactory:
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        EntityManagerFactory emf =
            (EntityManagerFactory)e.getServletContext().getAttribute("emf");
        emf.close();
    }
}