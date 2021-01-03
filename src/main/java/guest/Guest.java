package guest;
 
import accommodation.Accommodation;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import javax.persistence.OneToMany;

@Entity
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    // Persistent Fields:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String surname;
    private Date signingDate;
    @ManyToOne(optional=true)
    private Accommodation accommodation;
 
    // Constructors:
    public Guest() {
    }
 
    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.signingDate = new Date(System.currentTimeMillis());
    }

    public void SetAccommodation(Accommodation accommodation){
        this.accommodation = accommodation;
    }

    // String Representation:
    @Override
    public String toString() {
        return "ID:" + id + "   |Name:" + name + "   |Surname:" + surname + "   |(signed on " + signingDate + ")";
    }
}