package teammemes.tritonbudget;

/**
 * Created by andrewli on 2/12/17.
 */

public class User {
    private String Name;
    private double Balance;
    private String Id;

    public User() {}

    public User(String name,double balance,String id) {
        Name = name;
        Balance = balance;
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getName() {
        return Name;
    }
    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
    public double getBalance() {
        return Balance;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getId() {
        return Id;
    }
}
