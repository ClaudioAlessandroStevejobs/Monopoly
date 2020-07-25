package sample;

import java.util.*;

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

    public Monopoly() {
        initialize();
    }

    public void initialize(){
       for (int i = 0; i<field.length; i++){
            switch (i){

                /*Box.Type type, Color color, int price, int mortgageCost, String name, short houses*/

                case 0:
                    field[i] = new Box(Box.Type.GO, "Via");
                    break;
                case 1:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.BROWN, 60, "Primo marrone");
                    break;
                case 2:
                    field[i] = new Box(Box.Type.CHANCE, "Probabilità");
                    break;
                case 3:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.BROWN, 60, "Secondo marrone");
                    break;
                case 4:
                    field[i] = new Box(Box.Type.TAX, "Tassa patrimoniale");
                    break;
                case 5:
                    field[i] = new Box(Box.Type.STATION, 200, "Stazione Sud");
                    break;
                case 6:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.LIGHT_BLUE, 100, "Primo Azzurro");
                    break;
                case 7:
                    field[i] = new Box(Box.Type.CHANCE, "Imprevisti");
                    break;
                case 8:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.LIGHT_BLUE, 100, "Secondo Azzurro");
                    break;
                case 9:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.LIGHT_BLUE, 120,  "Terzo Azzurro");
                    break;
                case 10:
                    field[i] = new Box(Box.Type.PRISON, "Piazza Lanza");
                    break;
                case 11:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.PURPLE, 140, "Primo Viola");
                    break;
                case 12:
                    field[i] = new Box(Box.Type.SOCIETY, 150,  "Società elettrica");
                    break;
                case 13:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.PURPLE, 140, "Secondo Viola");
                    break;
                case 14:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.PURPLE, 140, "Terzo Viola");
                    break;
                case 15:
                    field[i] = new Box(Box.Type.STATION, 200,  "Stazione Ovest");
                    break;
                case 16:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.ORANGE, 180, "Primo Arancione");
                    break;
                case 17:
                    field[i] = new Box(Box.Type.CHANCE, "Probabilità");
                    break;
                case 18:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.ORANGE, 180, "Secondo Arancione");
                    break;
                case 19:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.ORANGE, 200, "Terzo Arancione");
                    break;
                case 20:
                    field[i] = new Box(Box.Type.PARKING, "Parcheggio");
                    break;
                case 21:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.RED, 220, "Primo Rosso");
                    break;
                case 22:
                    field[i] = new Box(Box.Type.CHANCE, "Imprevisti");
                    break;
                case 23:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.RED, 220, "Secondo Rosso");
                    break;
                case 24:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.RED, 240, "Terzo Rosso");
                    break;
                case 25:
                    field[i] = new Box(Box.Type.STATION, 200, "Stazione Nord");
                    break;
                case 26:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.YELLOW, 260, "Primo Giallo");
                    break;
                case 27:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.YELLOW, 260, "Secondo Giallo");
                    break;
                case 28:
                    field[i] = new Box(Box.Type.SOCIETY, 150,  "Società acqua potabile");
                    break;
                case 29:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.YELLOW, 280, "Terzo Giallo");
                    break;
                case 30:
                    field[i] = new Box(Box.Type.GO_TO_PRISON, "Vai in prigione");
                    break;
                case 31:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.GREEN, 300, "Primo Verde");
                    break;
                case 32:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.GREEN, 300, "Secondo Verde");
                    break;
                case 33:
                    field[i] = new Box(Box.Type.CHANCE, "Probabilità");
                    break;
                case 34:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.GREEN, 320, "Terzo Verde");
                    break;
                case 35:
                    field[i] = new Box(Box.Type.STATION, 200,  "Stazione est");
                    break;
                case 36:
                    field[i] = new Box(Box.Type.CHANCE, "Imprevisti");
                    break;
                case 37:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.BLUE, 350, "Primo Blu");
                    break;
                case 38:
                    field[i] = new Box(Box.Type.TAX, "Tassa di lusso");
                    break;
                case 39:
                    field[i] = new Box(Box.Type.PROPERTY, Box.Color.BLUE, 400, "Secondo Blu");
                    break;
            }
        }
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

    public ArrayList<String> getNamesFromColor(Box.Color color){
        ArrayList<String> propertyNames = new ArrayList<>();
        for (Box b: field) {
            if (b.getColor().equals(color)) {
                propertyNames.add(b.getName());
            }
        }
        return propertyNames;
    }

    public void setBuildable(ArrayList<String> properties){

        ArrayList<Box.Color> comboColors = comboBuildableColors(properties);

        // Attivazione buildable per le combo
        for (Box.Color c: comboColors) {
            for (Box b: field) {
                if(b.getColor().equals(c)){
                    b.setBuildable(true);
                }
            }
        }
    }

    public ArrayList<Box.Color> comboBuildableColors(ArrayList<String> properties) {

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
        return comboColors;
    }

    public boolean getWin(){
        return true;
    }

    public String chance(Player player){
        ArrayList<Integer> discardedChance = new ArrayList<>();

        int chanceIndex;

        do {
            chanceIndex = random.nextInt(12);
            if (!discardedChance.contains(chanceIndex)) {
                discardedChance.add(chanceIndex);
            }
            if (discardedChance.size() == 12) {
                discardedChance.clear();
            }
        }while (discardedChance.contains(chanceIndex));

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
                passGo(player);
                return "Vai avanti fino al via";
            case 3:
                goToPrison(player);
                return "senza passare dal via";
            case 4:
                player.movement(-3);
                return "Fai tre passi indietro";
            case 5:
                player.payment(50);
                return "Versi 50 euro in beneficienza";
            case 6:
                player.payment(40);
                taxFund += 40;
                return "40 euro di multa per aver guidato senza patente";
            case 7:
                player.payment(375);
                return "Matrimonio in famiglia:spese impreviste 375 euro";
            case 8:
                if (player.getPrisoner()){
                    player.setPrisoner(false);
                    return "Uscite gratis di prigione, \n" +
                        "se non ci siete potete conservare questo cartoncino sino al momento di servirvene";
                }
                else {
                    player.setCanEscapeFromPrison(true);
                    return "Se andrai in prigione potrai uscire col cartellino";
                }
            case 9:
                player.payment(-400);
                return "Vinci 400 al gratta e vinci";
            case 10:
                player.payment(-130);
                return "E' il tuo compleanno, ti regalano 130 euro";
            case 11:
                player.setPosition((short) 1);
                return "Torni alla prima posizione";
            case 12:
                player.payment(500);
                taxFund += 500;
                return "Paghi 500 euro di debiti alla banca";
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:

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

    public Player[] shuffle(Player[] players) {

        Random rand = new Random();

        for (int i = 0; i < players.length; i++) {
            int randomIndexToSwap = rand.nextInt(players.length);
            Player temp = players[randomIndexToSwap];
            players[randomIndexToSwap] = players[i];
            players[i] = temp;
        }

        return players;
    }

    //controllo se una proprietà è libera oppure no
    public boolean isPropertyFree(Player[] players, String propertyName) {
        for (Player player : players) {
            if (player.checkProprieties(propertyName)) {
                return false;
            }
        }
        return true;
    }
    public ArrayList<String> getActiveProperties(ArrayList<String> playerProperties) {
        ArrayList<String> activeProperties = new ArrayList<>();
        for (Box box: field) {
            if (playerProperties.contains(box.getName())) {
                if (!box.isMortgaged()) {
                    activeProperties.add(box.getName());
                }
            }
        }
        return activeProperties;
    }
    public ArrayList<String> getMortgagedProperties(ArrayList<String> playerProperties) {
        ArrayList<String> activeProperties = new ArrayList<>();
        for (Box box: field) {
            if (playerProperties.contains(box.getName())) {
                if (box.isMortgaged()) {
                    activeProperties.add(box.getName());
                }
            }
        }
        return activeProperties;
    }
    public Player setMortgageProperty (Player player, String propertyName) {
        for (Box box: field) {
            if (box.getName()==propertyName) {
                box.setMortgaged(true);
            }
            player.payment(-box.getMortgageCost());
        }
        return player;
    }
    public Player setActiveProperty (Player player, String propertyName) {
        for (Box box: field) {
            if (box.getName()==propertyName) {
                box.setMortgaged(false);
            }
            player.payment(box.getMortgageCost()+10);
        }
        return player;
    }
    public String stringBoard(Player player) {
        String result = "";
        for (int i = 0; i<40; i++){
            if (i == (player.getPosition())){
                result += "\u001B[31mo\u001B[0m ";
            }
            else {
                result += "o ";
            }
        }
        result += "\n";
        return result;
    }

}
