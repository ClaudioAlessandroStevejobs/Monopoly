package sample;

public class Box {
    public enum Type{PROPERTY, PRISON, GO, GO_TO_PRISON, PARKING, CHANCE, TAX, SOCIETY, STATION}
    public enum Color{RED, YELLOW, BLUE, CYAN, GREEN, WHITE, PURPLE, GREY, NONE}
    private int price;
    private int mortgageCost;
    private String name = "";
    private short houses;
    private Type type;
    private Color color = Color.NONE;
    private boolean buildable;
    private boolean mortgaged;


    // Costruttore per le proprietà
    public Box(Type type, Color color, int price, String name) {
        this.type = type;
        this.color = color;
        this.price = price;
        this.mortgageCost = price/2;
        this.name = name;
        this.buildable = false;

    }
    // Costruttore tasse
    public Box(Type type, String name){
        this.type = type;
        this.name = name;
    }
    // Costruttore stazioni
    public Box(Type type, int price, String name){
        this.type = type;
        this.price = price;
        this.mortgageCost = price/2;
        this.name = name;
    }
    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
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
        switch (getColor()) {
            case BLUE:
                return  "\u001B[34m" + name + "\u001B[0m";
            case RED:
                return "\u001B[31m" + name + "\u001B[0m";
            case GREY:
                return "\u001B[37m" + name + "\u001B[0m";
            case GREEN:
                return "\u001B[32m" + name + "\u001B[0m";
            case WHITE:
                return "\u001B[30m" + name + "\u001B[0m";
            case PURPLE:
                return "\u001B[35m" + name + "\u001B[0m";
            case YELLOW:
                return "\u001B[33m" + name + "\u001B[0m";
            case CYAN:
                return "\u001B[36m" + name + "\u001B[0m";
            default:
                return name;
        }
    }
    public Type getType() {
        return type;
    }
    public boolean isBuildable() {return buildable;}
    public void setHouses(short houses) {
        this.houses = houses;
    }
    public int getHouseCost() {
        if (color.equals(Color.GREY) || color.equals(Color.CYAN)) {
            return 50;
        }
        if (color.equals(Color.PURPLE) || color.equals(Color.WHITE)) {
            return 100;
        }
        if (color.equals(Color.RED) || color.equals(Color.YELLOW)) {
            return 150;
        }
        if (color.equals(Color.GREEN) || color.equals(Color.BLUE)) {
            return 200;
        }
        return 0;
    }
    public int getPropertyTax() {
        switch (houses) {
            case 0:
                return price/2;
            case 1:
                return (int) (price*1.5);
            case 2:
                return price*3;
            case 3:
                return price*4;
            case 4:
                return price*5;
        }
        return 0;
    }
    public boolean isMortgaged() {
        return mortgaged;
    }

    @Override
    public String toString() {
        switch (getColor()){
            case BLUE:
                return "\u001B[34m" + name + "\u001B[0m";
            case RED:
                return "\u001B[31m" + name + "\u001B[0m";
            case GREY:
                return "\u001B[37m" + name + "\u001B[0m";
            case GREEN:
                return "\u001B[32m" + name + "\u001B[0m";
            case WHITE:
                return "\u001B[30m" + name + "\u001B[0m";
            case PURPLE:
                return "\u001B[35m" + name + "\u001B[0m";
            case YELLOW:
                return "\u001B[33m" + name + "\u001B[0m";
            case CYAN:
                return "\u001B[36m" + name + "\u001B[0m";
            default:
                return name;
        }
    }

}
