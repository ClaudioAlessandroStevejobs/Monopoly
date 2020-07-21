package sample;

public class Box {
    public enum Type{PROPERTY, PRISON, GO, GO_TO_PRISON, PARKING, CHANCE, TAX, SOCIETY, STATION}
    public enum Color{RED, YELLOW, BLUE, LIGHT_BLUE, GREEN, ORANGE, PURPLE, BROWN, NONE}
    private int price;
    private int mortgageCost;
    private String name;
    private short houses;
    private Type type;
    private Color color;

    public void setPrice(int price) {
        this.price = price;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setHouses(short houses) {
        this.houses = houses;
    }
    public void setMortgageCost(int mortgageCost) {
        this.mortgageCost = mortgageCost;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Color getColor() {
        return color;
    }
    public int getMortgageCost() {
        return mortgageCost;
    }
    public int getPrice() {
        return price;
    }
    public short getHouses() {
        return houses;
    }
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }

    public Box(Type type, Color color, int price,
               int mortgageCost, String name, short houses){
        this.type = type;
        this.color = color;
        this.price = price;
        this.mortgageCost = mortgageCost;
        this.name = name;
        this.houses = houses;
    }

    public Box(Type type){
        this.type = type;
    }

    public Box(Type type, String name){
        this.type = type;
        this.name = name;
    }

    public Box(Type type, int price, int mortgageCost, String name){
        this.type = type;
        this.price = price;
        this.mortgageCost = mortgageCost;
        this.name = name;
    }

}
