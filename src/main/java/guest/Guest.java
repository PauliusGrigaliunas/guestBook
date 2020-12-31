package guest;
 
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
 
@Entity
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
 
    // Persistent Fields:
    @Id @GeneratedValue
    Long id;
    private String name;
    private String surname;
    private Date signingDate;
 
    // Constructors:
    public Guest() {
    }
 
    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.signingDate = new Date(System.currentTimeMillis());
    }
 
    // String Representation:
    @Override
    public String toString() {
        return "ID:" + id + "   |Name:" + name + "   |Surname:" + surname + "   |(signed on " + signingDate + ")";
    }
}