package accommodation;

import javax.persistence.Entity;
import java.util.Random;

@Entity
public class HotelRoom extends Accommodation {
    protected int doorCode;

    public HotelRoom() {
    }

    public HotelRoom(String description, int space) {
        super(description, space);
        doorCode = new Random().nextInt(10000);
    }

    public HotelRoom(String description, int space, boolean isReady) {
        super(description, space, isReady);
        doorCode = new Random().nextInt(10000);
    }

    @Override
    public String toString() {

        return "NR:" + super.nr + "   |description: " + super.description + "   |space: "
                + super.space + "    | door code: " + doorCode + "   |Is ready: "
                + super.isReady + "   |(" + super.getClass() + ")";
    }
}

