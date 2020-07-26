package sample;

import java.util.function.BinaryOperator;

public class Box {
    public enum Type{PROPERTY, PRISON, GO, GO_TO_PRISON, PARKING, CHANCE, TAX, SOCIETY, STATION}
    public enum Color{RED, YELLOW, BLUE, LIGHT_BLUE, GREEN, ORANGE, PURPLE, BROWN}
    private int price;
    private int mortgageCost;
    private String name = "";
    private short houses;
    private Type type;
    private Color color;
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
    // Costruttore angoli
    public Box(Type type){
        this.type = type;
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
        return name;
    }
    public Type getType() {
        return type;
    }
    public boolean isBuildable() {return buildable;}
    public int getHouseCost() {
        if (color.equals(Color.BROWN) || color.equals(Color.LIGHT_BLUE)) {
            return 50;
        }
        if (color.equals(Color.PURPLE) || color.equals(Color.ORANGE)) {
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
                return price/6;
            case 1:
                return price/4;
            case 2:
                return price/2;
            case 3:
                return price+50;
            case 4:
                return price+250;
        }
        return 0;
    }
    public boolean isMortgaged() {
        return mortgaged;
    }

    /* metodo per comprare una o più case nella proprietà,
        vuole in input il numero di case da voler comprare ed il fondo del giocatore,
        ritorna il prezzo da pagare SOLO quando il pagamento è fattibile, quindi andato a buon fine. */

    public int housesPurchase(short housesNumber, int playerBill) {
        if (getHouseCost() * housesNumber <= playerBill) {
            houses = housesNumber;
            return getHouseCost() * housesNumber;
        }
        return 0;
    }

}
