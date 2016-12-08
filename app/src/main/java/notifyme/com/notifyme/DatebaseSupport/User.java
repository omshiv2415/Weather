package notifyme.com.notifyme.DatebaseSupport;

/**
 * Created by eaalm on 30/11/2016.
 */

public class User {

    public String FullName;

    public User() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public User( String FullName ) {


        this.FullName = FullName;

    }
}
