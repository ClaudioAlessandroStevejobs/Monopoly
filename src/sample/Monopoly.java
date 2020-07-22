package sample;

import java.util.Random;

public class Monopoly {
    public Box[] field = new Box[40];
    public Player[] players;

    Random random = new Random();

    public Monopoly(Player ... players){
        this.players = players;
        initialize();
    }

    public void initialize(){
        for (int i = 0; i<field.length; i++){
            switch (i){
                case 0: field[i] = new Box(Box.Type.GO);
                case 1: field[i] = new Box();
                case 2: field[i] = new Box(Box.Type.CHANCE, "Chance");
                case 3: field[i] = new Box();
                case 4: field[i] = new Box(Box.Type.TAX, "Patrimonial tax");
                case 5: field[i] = new Box(Box.Type.STATION, 200, 110, "South");
                case 6: field[i] = new Box();
                case 7: field[i] = new Box(Box.Type.CHANCE, "Unexpected");
                case 8: field[i] = new Box();
                case 9: field[i] = new Box();
                case 10: field[i] = new Box(Box.Type.PRISON);
                case 11: field[i] = new Box();
                case 12: field[i] = new Box(Box.Type.SOCIETY, 150, 100, "Electric society");
                case 13: field[i] = new Box();
                case 14: field[i] = new Box();
                case 15: field[i] = new Box(Box.Type.STATION, 200, 110, "West");
                case 16: field[i] = new Box();
                case 17: field[i] = new Box(Box.Type.CHANCE, "Chance");
                case 18: field[i] = new Box();
                case 19: field[i] = new Box();
                case 20: field[i] = new Box(Box.Type.PARKING);
                case 21: field[i] = new Box();
                case 22: field[i] = new Box(Box.Type.CHANCE, "Unexpected");
                case 23: field[i] = new Box();
                case 24: field[i] = new Box();
                case 25: field[i] = new Box(Box.Type.STATION, 200, 110, "North");
                case 26: field[i] = new Box();
                case 27: field[i] = new Box();
                case 28: field[i] = new Box(Box.Type.SOCIETY, 150, 100, "Drinking water society");
                case 29: field[i] = new Box();
                case 30: field[i] = new Box(Box.Type.GO_TO_PRISON);
                case 31: field[i] = new Box();
                case 32: field[i] = new Box();
                case 33: field[i] = new Box(Box.Type.CHANCE, "Chance");
                case 34: field[i] = new Box();
                case 35: field[i] = new Box(Box.Type.STATION, 200, 110, "East");
                case 36: field[i] = new Box(Box.Type.CHANCE, "Unexpected");
                case 37: field[i] = new Box();
                case 38: field[i] = new Box(Box.Type.TAX, "Luxury tax");
                case 39: field[i] = new Box();
            }
        }
    }

    public int rollDice(){
       return random.nextInt(12) + 1;
    }

    public


    public boolean getWin(){
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i<9; i++)
            result += "o  ";
        for (int i = 0; i<9; i++)
            result += "o                          o\n";
        for (int i = 0; i<10; i++)
            result += "o  ";
        return result;
    }
}
