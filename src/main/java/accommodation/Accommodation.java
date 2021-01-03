package accommodation;

import guest.Guest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public abstract class Accommodation implements Serializable {
    private static final long serialVersionUID = 1L;

    // Persistent Fields:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nr;
    protected String description;
    protected int space;
    protected Boolean isReady;
    @OneToMany(targetEntity=Guest.class, mappedBy="accommodation")
    protected List<Guest> guests;

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

    public void AddGuests(List<Guest> newGuests){
        for (Guest guest : newGuests) {
            if(!guests.contains(guest)){
                guests.add(guest);

            }
        }
    }

    public void RemoveGuest (List<Guest> newGuests){
        for (Guest guest : newGuests) {
            if(guests.contains(guest)){
                guests.remove(guest);
            }
        }
    }

    public List<Guest> GetGuests(){
        return guests;
    }
}
