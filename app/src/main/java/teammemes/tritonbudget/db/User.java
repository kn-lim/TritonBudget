package teammemes.tritonbudget.db;

/**
 * Created by andrewli on 2/12/17.
 */

public class User {
    private String name;
    private double balance;
    private int id;

    public User() {}

    public User(String name, double balance, int id) {
        this.name = name;
        this.balance = balance;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
