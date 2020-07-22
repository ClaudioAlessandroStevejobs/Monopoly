package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Monopoly {

    public Box[] field = new Box[40];
    Random random = new Random();

    private int taxFund = 0;

    public void setTaxFund(int taxFund) {
        this.taxFund = taxFund;
    }

    public int getTaxFund() {
        return taxFund;
    }

    public Monopoly(){
        initialize();
    }

    public void initialize(){
       /* for (int i = 0; i<field.length; i++){
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
        }*/
    }

    public int rollDice(){
       return random.nextInt(6) + 1;
    }

    public Box.Color getColorFromName(String name){
        for (Box b: field) {
            if(b.getName().equals(name)) {
                return b.getColor();
            }
        }
        return null;
    }

    public void setBuildable(ArrayList<String> properties){

        HashMap<Box.Color,Integer> colors = new HashMap<>();

        // Controllo dei colori diversi delle proprietà di un giocatore
        for (String s: properties) {
            if (colors.containsKey(getColorFromName(s))) {
                colors.replace(getColorFromName(s), colors.get(getColorFromName(s))+1);
            }
            else {
                colors.put(getColorFromName(s), 1);
            }
        }

        ArrayList<Box.Color> comboColors = new ArrayList<>();

        // Controllo delle combo per i colori del giocatore
        for (Map.Entry c: colors.entrySet()) {
            if(c.getValue().equals(2) && (c.getKey().equals(Box.Color.BLUE)
            || c.getKey().equals(Box.Color.BROWN))){
                comboColors.add((Box.Color) c.getKey());
            }
            else if(c.getValue().equals(3)){
                comboColors.add((Box.Color) c.getKey());
            }
        }
        // Attivazione buildable per le combo
        for (Box.Color c: comboColors) {
            for (Box b: field) {
                if(b.getColor().equals(c)){
                    b.setBuildable(true);
                }
            }
        }
    }

    public boolean getWin(){
        return true;
    }

    public String chance(Player player){
        int chanceIndex = random.nextInt(3);
        switch (chanceIndex){
            case 0:
                player.payment(-60);
                return "Hai vinto 60 euro al centro scommesse";
            case 1:
                if(player.payment(125)) {
                    //boh da vedere
                    setTaxFund(+125);
                    return "Hai parcheggiato in doppia fila paghi la multa di 125 euro";
                }
                else {return "Non hai i soldi";}
            case 2:
                player.setPosition((short) 0);
                return "Torni al via";
        }
        return null;
    }

    public String passGo(Player player){
        if(player.getPosition() == 0){
            player.payment(-500);
        }
        return "Ritiri 500 euro per il passaggio dal via";
    }

    public String goToPrison(Player player){
        player.setPosition((short) 10);
        player.setPrisoner(true);
        return "Vai in prigione";
    }
}
