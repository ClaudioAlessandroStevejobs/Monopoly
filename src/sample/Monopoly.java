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

    public boolean isOnePlayerRemained(Player[] players){
        short losersNumber = 0;
        for (Player player: players) {
            if (player.isLoser()){
                losersNumber++;
            }
        }
        return losersNumber == players.length-1;
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
                    taxFund += 125;
                    return "Hai parcheggiato in tripla fila paghi la multa di 125 euro";
                }
                else {
                    taxFund += player.getBill();
                    return "Hai parcheggiato in tripla fila paghi la multa di 125 euro,\n" +
                        "ma non puoi pagari, lasci il tuo conto nel fondocassa";
                }
            case 2:
                player.setPosition((short) 0);
                passGo(player);
                return "Hai scordato il portafoglio, torna al via per riprenderlo";
            case 3:
                goToPrison(player);
                return "Ti hanno fermato alla guida con un elevato stato di ebrezza, passi 3 turni in prigione";
            case 4:
                player.movement(-3);
                return "Un pedone ti punta il ferro, fai tre passi indietro";
            case 5:
                if (player.payment(50)) {
                    return "Fai la spesa per un festino, spendi 50 euro";
                } else {
                    taxFund += player.getBill();
                    return "Fai la spesa per un festino, spendi 50 euro," +
                            "ma vai in bancarotta perché è la sessantesima volta in un mese";
                }
            case 6:
                if (player.payment(40)) {
                    taxFund += 40;
                    return "40 euro di multa per aver guidato senza patente";
                } else {
                    taxFund += player.getBill();
                    return "40 euro di multa per aver guidato senza patente" +
                            " ma non puoi pagare, lasci tutti i tuoi beni nel fondocassa";
                }
            case 7:
                if(player.payment(375)) {
                    return "Ti rompono un vetro della macchina, devi ripararla, spendi 375 euro";
                } else {
                    taxFund += player.getBill();
                    return "Ti rompono un vetro della macchina, devi ripararla, spendi 375 euro" +
                            " ma non puoi pagare, lasci tutti i tuoi beni nel fondocassa";
                }
            case 8:
                if (player.isPrisoner()){
                    player.setPrisoner(false);
                    return "Hai snichato il gang esci di prigione";
                }
                else {
                    player.setCanEscapeFromPrison(true);
                    return "Se andrai in prigione potrai uscire col cartellino";
                }
            case 9:
                player.payment(-400);
                return "Vinci 400 euro al gratta e vinci";
            case 10:
                player.payment(-130);
                return "E' il tuo compleanno, ti regalano 130 euro";
            case 11:
                player.setPosition((short) 1);
                return "Torni alla prima posizione";
            case 12:
                if(player.payment(500)) {
                    taxFund += 500;
                    return "Paghi 500 euro di debiti alla banca";
                } else {
                    taxFund += player.getBill();
                    return "Paghi 500 euro di debiti alla banca" +
                            " ma non puoi pagare, lasci tutti i tuoi beni nel fondocassa";
                }
            case 13:
                if (player.getPosition()>25){
                    player.payment(-500);
                }
                player.setPosition((short) 25);
                return "Vai fino alla stazione nord, se passate dal via ritirate 500";
            case 14:
                if (player.getPosition()>11){
                    player.payment(-500);
                }
                player.setPosition((short) 11);
                return "Vai fino al primo viola, se passate da via ritirate 500";
            case 15:
                player.setPosition((short) 39);
                return "Vai nel secondo blu";
            case 16:
                player.setPosition((short) (player.getPosition()+2));
                return "Il posteggiatore ti ferma, non hai monetine " +
                        "\nquindi cerchi parcheggio due caselle più avanti";
            case 17:
                if(player.payment(60* countHousesAndHotels(player)[0] + 250* countHousesAndHotels(player)[1])) {
                    return "Avete tutti i vostri stabili da riparare: " +
                            "pagate 60 euro per ogni casa e 250 per ogni albergo.";
                } else {
                    taxFund += player.getBill();
                    return "Avete tutti i vostri stabili da riparare: " +
                            "pagate 60 euro per ogni casa e 250 per ogni albergo\n" +
                            " ma non puoi pagare, lasci tutti i tuoi beni nel fondocassa";
                }
            case 18:
                if(player.payment(100* countHousesAndHotels(player)[0] + 250* countHousesAndHotels(player)[1])) {
                    return "Dovete pagare un contributo di miglioria stradale per una gara di cavalli, " +
                            "\n100 per ogni casa, 250 euro per ogni albergo che possedete";
                } else {
                    return "Dovete pagare un contributo di miglioria stradale per una gara di cavalli," +
                            "\n100 per ogni casa, 250 euro per ogni albergo che possedete" +
                            " ma non puoi pagare, lasci tutti i tuoi beni nel fondocassa";
                }
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

    public Box.Type getTypeFromName(String name){
        for (Box b: field) {
            if (b.getName().equals(name)) {
                return b.getType();
            }
        }
        return null;
    }

    public int getStationTax(ArrayList<String> playerProperties){
        short stationNumber = 0;
        for (String s: playerProperties) {
            if (getTypeFromName(s).equals(Box.Type.STATION)){
                stationNumber++;
            }
        }
        switch (stationNumber){
            case 1: return 25;
            case 2: return 50;
            case 3: return 100;
            case 4: return 200;
        }
        return 0;
    }

    public int getSocietyTax(ArrayList<String> playerProperties, int dices){
        short societyNumber = 0;
        for (String s: playerProperties) {
            if (getTypeFromName(s).equals(Box.Type.SOCIETY)){
                societyNumber++;
            }
        }
        switch (societyNumber){
            case 1: return dices*4;
            case 2: return dices*10;
        }
        return 0;
    }

    public short[] countHousesAndHotels(Player player){
    short playerHouses = 0;
    short playerHotels = 0;
        for (String s: player.getProperties()) {
            for (Box b: field) {
                if (b.getHouses() == 5){
                    playerHotels++;
                }
                else if (s.equals(b.getName()) && b.getHouses()>0){
                    playerHouses += b.getHouses();
                }
            }
        }
        return new short[]{playerHouses, playerHotels};
    }

    public Player getWinner(Player[] players){
        for (Player player: players) {
            if (!player.isLoser()){
                return player;
            }
        }
        return null;
    }

    public String stringBoard(Player player) {
        String result = "";
        /*int i;
        int j = 0;
        for (i = 0; i<11; i++){
            result += "o  ";
            j++;
        }
        for (i = 9; i>0; i--){
            result += "\no                             o  ";
        }
        result += "\n";
        for (i = 0; i < 11; i++){
            result += "o  ";
        }
        result += "\n";
        return result;*/

        for (int i = 0; i<40; i++){
            if (i == player.getPosition()){
                switch (player.getPawn()){
                    case CANNOLO:
                        result += "\u001B[31mo \u001B[0m";
                        break;
                    case CASSATA:
                        result += "\u001B[32mo \u001B[0m";
                        break;
                    case VULCANO:
                        result += "\u001B[33mo \u001B[0m";
                        break;
                    case ARANCINO:
                        result += "\u001B[34mo \u001B[0m";
                        break;
                    case ELEFANTE:
                        result += "\u001B[35mo \u001B[0m";
                        break;
                    case CAVALLINO:
                        result += "\u001B[36mo \u001B[0m";
                        break;
                }
            }
            else {
                result += "o ";
            }
        }
        result += "\n";
        return result;
    }

}
