package accommodation;

import javax.persistence.Entity;

@Entity
public class House extends Accommodation {
    public House() {
    }

    public House(String description, int space) {
        super(description, space);
    }

    public House(String description, int space, boolean isReady) {
        super(description, space, isReady);
    }

    @Override
    public String toString() {

        return "NR:" + super.nr + "   |description: " + super.description + "   |space: "
                + super.space + "   |Is ready: " + super.isReady + "   |Guests: "
                + super.guests + "   |(" + super.getClass() + ")";
    }
}
