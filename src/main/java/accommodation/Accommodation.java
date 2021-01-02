package accommodation;

import guest.Guest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public abstract class Accommodation implements Serializable {
    private static final long serialVersionUID = 1L;

    // Persistent Fields:
    @Id
    @GeneratedValue
    Long nr;
    protected String description;
    protected int space;
    protected Boolean isReady;
    protected ArrayList<Guest> Guests;

    public Accommodation(){

    }

    public Accommodation(String description, int space) {
        this.description = description;
        this.space = space;
        this.isReady = false;
    }

    public Accommodation(String description, int space, boolean isReady) {
        this.description = description;
        this.space = space;
        this.isReady = isReady;
    }


}
