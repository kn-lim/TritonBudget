package teammemes.tritonbudget.db;

import java.util.Date;

/**
 * Created by andrewli on 2/26/17.
 */

public class TranHistory {

    private int id;
    private int menuId;
    private int quantity;
    private Date tdate;
    private double cost;

    public TranHistory(){}

    public TranHistory(int id, int menuId, int quantity, Date tdate, double cost){
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
        this.tdate = tdate;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
