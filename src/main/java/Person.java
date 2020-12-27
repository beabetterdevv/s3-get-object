
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "first_name", "last_name", "email", "gender", "ip_address" })
public class Person {

    public int id;
    public String first_name;
    public String last_name;
    public String email;
    public String gender;
    public String ip_address;

    @Override
    public String toString() {
        return "Person{" + "id='" + id + '\'' + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", email='" + email + '\''
                + ", gender='" + gender + '\'' + ", ip_address='" + ip_address + '\'' + '}';
    }
}
