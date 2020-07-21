package sample;

public class Box {
    enum Type{PROPERTY, PRISON, GO, GO_TO_PRISON, PARKING, CHANCE, TAX, SOCIETY, STATION}
    enum Color{RED, BLUE, LIGHT_BLUE, GREEN, PINK, PURPLE}
    int price;
    int mortgageCost;
    String name;
    short houses;
    Type type;
    Color color;

    public Box(Type type, Color color, int price,
               int mortgageCost, String name, short houses){
        this.type = type;
        this.color = color;
        this.price = price;
        this.mortgageCost = mortgageCost;
        this.name = name;
        this.houses = houses;
    }
}
