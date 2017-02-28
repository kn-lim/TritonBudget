package teammemes.tritonbudget;

/**
 * Created by andrewli on 2/16/17.
 */

public class Menu {
    private String name;
    private int id;


    private String location;

    // breakfast, lunch, dinner
    private String type;
    // pizza, burger
    private String category;
    //true: available everyday. false: only available on certain days
    private boolean daily;
    private int day;//day on which the food is available
    //private int freq;
    private boolean vegeterian;
    private boolean vegan;
    //private boolean glutenFree;
    private double cost;

    public Menu(){}
    // Normal food
    public Menu(String name, String category, String location, double cost){
        daily = true;
        this.name = name;
        this.location = location;
        this.category = category;
        this.cost = cost;
    }

    // special food
    public Menu(String name, String category, String location, double cost, int day){
        daily = false;
        this.name = name;
        this.location = location;
        this.category = category;
        this.cost = cost;
        this.day = day;
    }

    public String getType(){

        return type;
    }
    public void setType(String type){ this.type = type;}
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public boolean isDaily() {
        return daily;
    }

    public int getDay() {
        return day;
    }

//    public int getFreq() {
//        return freq;
//    }

    public boolean isVegeterian() {
        return vegeterian;
    }

    public boolean isVegan() {
        return vegan;
    }

//    public boolean isGlutenFree() {
//        return glutenFree;
//    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDaily(boolean daily)
    {
        this.daily = daily;
    }
    public void setDay(int day) {
        this.day = day;
    }

//    public void setFreq(int freq) {
//        this.freq = freq;
//    }

    public void setVegeterian(boolean vegeterian) {
        this.vegeterian = vegeterian;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

//    public void setGlutenFree(boolean glutenFree) {
//        this.glutenFree = glutenFree;
//    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", vegeterian=" + vegeterian +
                ", vegan=" + vegan +
                ", cost=" + cost +
                '}';
    }
}
