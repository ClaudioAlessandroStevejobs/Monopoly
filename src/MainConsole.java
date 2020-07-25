import sample.Box;
import sample.Monopoly;
import sample.Player;

import java.lang.reflect.Array;
import java.util.*;

public class MainConsole {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Monopoly m = new Monopoly();

        /* SET GIOCATORI, RICORDA TRY CATCH */
        int playerNum = 0;

            do {
                System.out.println("Quanti giocatori partecipano? (2-6)");
                playerNum = input.nextInt();
                if (playerNum <= 1 || playerNum > 6) {
                    System.out.println("Errore!");
                }
            }while(playerNum <= 1 || playerNum > 6);

        Player[] players = new Player[playerNum];

        /* SET PEDINE, RICORDA TRY CATCH */
        ArrayList<Player.Pawn> availablePawns = Player.getPawnArray();
        for (int k = 0; k<playerNum; k++) {
            int choice;
            do {
                System.out.println("Giocatore "+(k+1)+":\nScegli la pedina tra queste:");
                for (int j = 0; j < availablePawns.size(); j++) {
                    System.out.println((j+1)+" - "+ availablePawns.get(j));
                }
                choice = input.nextInt();
                if (choice < 1 || choice > availablePawns.size()) {
                    System.out.println("Errore!");
                }
            }while(choice < 1 || choice > availablePawns.size());
            players[k]= new Player(availablePawns.get(choice-1));
            System.out.println("Il giocatore "+(k+1)+" ha scelto "+availablePawns.get(choice-1)+"!\n");
            availablePawns.remove(choice-1);


        }

        //MESCOLAMENTO GIOCATORI f
        players = m.shuffle(players);

        do {
            for (Player player : players) {
                char choiceYesOrNot;
                System.out.println("E' il turno di "+player.getPawn()+":\nLa tua posizione attuale è "+player.getPosition()+"\n");
                Thread.sleep(1000);
                int dice1 = m.rollDice();
                int dice2 = m.rollDice();
                System.out.println("Tiri il dado!");
                Thread.sleep(2000);
                System.out.println("Sono usciti "+dice1+" e "+dice2);
                //FARE LA ROBA SE E' DOPPIO

                System.out.println("Ti muovi di "+(dice1+dice2)+" caselle!\n");
                if (!player.getPrisoner())  {
                    player.movement((dice1 + dice2));
                    Box.Type type = m.field[player.getPosition()].getType();
                    System.out.println("Sei su "+ m.field[player.getPosition()].getName());
                    switch (type){
                        case PROPERTY:
                        case SOCIETY:
                        case STATION:
                            Thread.sleep(1000);
                            //se la proprietà è tua
                            if (player.checkProprieties(m.field[player.getPosition()].getName())) {
                                System.out.println("La proprietà è tua");
                            }
                            //se la proprietà è libera
                            else if (m.isPropertyFree(players, m.field[player.getPosition()].getName())) {
                                System.out.println("Questa proprietà è libera e costa "+m.field[player.getPosition()].getPrice());
                                //controlla se hai i soldi, se non ce li hai imposta direttamente di andare all'asta
                                if (player.getBill() < m.field[player.getPosition()].getPrice()) {
                                    System.out.println("Non hai i soldi per comprala");
                                    choiceYesOrNot = 'n';
                                }
                                //altrimenti ti chiede di inserire la tua scelta
                                else {

                                    do {
                                        System.out.println("Vuoi comprarla? (s/n)");
                                        choiceYesOrNot = input.next().charAt(0);
                                        if (choiceYesOrNot != 's' && choiceYesOrNot != 'n') {
                                            System.out.println("Errore!");
                                        }
                                    }while(choiceYesOrNot != 's' && choiceYesOrNot != 'n');

                                }
                                    switch (choiceYesOrNot) {
                                        case 's':
                                            player.payment(m.field[player.getPosition()].getPrice());
                                            player.addProperty(m.field[player.getPosition()].getName());
                                            if (type.equals(Box.Type.PROPERTY)) {
                                                    m.setBuildable(player.getProperties());
                                            }
                                            break;
                                        case 'n':
                                            //arraylist di gente esclusa dall'asta
                                            ArrayList<Player> auctionPlayers = new ArrayList<>(Arrays.asList(players));
                                            auctionPlayers.remove(player);
                                            boolean auctionExit = false;
                                            Player.Pawn lastAuctionPlayer = null;

                                            //prezzo iniziale
                                            int price = m.field[player.getPosition()].getPrice();

                                            do {
                                                for (Player auctionPlayer: auctionPlayers) {
                                                    int outOfAuctionPlayerNum = 0;
                                                    for (Player nonePlayer: auctionPlayers) {
                                                        if (nonePlayer.isOutOfAuction()) {
                                                            outOfAuctionPlayerNum ++;
                                                        }
                                                    }

                                                    if (auctionPlayer.getPawn() != Player.Pawn.NONE) {

                                                        int raise;
                                                        if ((auctionPlayers.size()-1==outOfAuctionPlayerNum) && auctionPlayer.getPawn() == lastAuctionPlayer) {
                                                            auctionPlayer.payment(price);
                                                            auctionPlayer.addProperty(m.field[player.getPosition()].getName());
                                                            System.out.println(auctionPlayer.getPawn() + " si è aggiudicato " + m.field[player.getPosition()].getName() + " per " + price + " euro");
                                                            auctionExit = true;
                                                            break;
                                                        }
                                                        //controllo se puoi puntare già di base o no
                                                        if (!auctionPlayer.payment(m.field[player.getPosition()].getPrice())) {
                                                            auctionPlayer.payment(-m.field[player.getPosition()].getPrice());
                                                            System.out.println(auctionPlayer.getPawn() + " non può pagare!");
                                                            auctionPlayer.setOutOfAuction(true);
                                                            continue;
                                                        }
                                                        auctionPlayer.payment(-m.field[player.getPosition()].getPrice());
                                                        do {
                                                            System.out.println("\n"+auctionPlayer.toString());
                                                            //solo nel primo turno
                                                            System.out.println("Ultima puntata:");
                                                            if (lastAuctionPlayer != null) {
                                                                System.out.println("Giocatore: "+lastAuctionPlayer);
                                                            }
                                                            System.out.println("Prezzo attuale: " + price + "\nrilancia (oppure scrivi 0 per lasciare)");
                                                            raise = input.nextInt();
                                                            if ((raise != 0 && raise <= price)||raise>auctionPlayer.getBill())
                                                                System.out.println("errore");
                                                        } while ((raise != 0 && raise <= price)||raise>auctionPlayer.getBill());

                                                        if ((auctionPlayers.size()-1==outOfAuctionPlayerNum) && raise == 0) {
                                                            System.out.println("\n"+m.field[player.getPosition()].getName() + " rimane libera!\n");
                                                            auctionExit = true;
                                                            break;
                                                        }
                                                        if (raise == 0) {
                                                            auctionPlayer.setOutOfAuction(true);
                                                        } else {
                                                            price = raise;
                                                            lastAuctionPlayer = auctionPlayer.getPawn();
                                                        }
                                                    }
                                                }
                                            } while (!auctionExit);
                                            for (Player nonePlayer: auctionPlayers) {
                                                if (nonePlayer.isOutOfAuction()) {
                                                    nonePlayer.setOutOfAuction(false);
                                                }
                                            }
                                            break;
                                    }
                                } else {
                                    if (type.equals(Box.Type.PROPERTY)) {
                                        System.out.println("Devi pagare "+m.field[player.getPosition()].getPropertyTax()+" euri");
                                        if (!player.payment(m.field[player.getPosition()].getPropertyTax())) {
                                            //scelte robe da ipotecare?, magari mettiamo un booleano per entrare in un menù di ipoteca DOPO questo switch? ho aggiunto anche la booleana alle box "mortgaged"
                                            System.out.println("ma non puoi pagare");
                                        }
                                    }
                                    else if (type.equals(Box.Type.STATION)) {
                                        System.out.println("Devi pagare "+m.getStationTax(player.getProperties())+" euri");
                                        if (!player.payment(m.getStationTax(player.getProperties()))) {
                                            //scelte robe da ipotecare?, magari mettiamo un booleano per entrare in un menù di ipoteca DOPO questo switch? ho aggiunto anche la booleana alle box "mortgaged"
                                            System.out.println("ma non puoi pagare");
                                        }
                                    }

                                }
                            break;
                        case GO:
                            System.out.println(m.passGo(player));
                            break;
                        case GO_TO_PRISON:
                            System.out.println(m.goToPrison(player));
                            break;
                        case PARKING:
                            player.payment(-m.getTaxFund());
                            System.out.println("Sei passato dal parcheggio, ritira " + m.getTaxFund() + " euro");
                            m.setTaxFund(0);
                            break;
                        case CHANCE:
                            System.out.println(m.chance(player));
                            break;
                        case TAX:
                            if (m.field[player.getPosition()].getName().equals("Patrimonial tax")){
                                player.payment(200);
                                //sperando
                                m.setTaxFund(+200);
                            }
                            else {
                                player.payment(300);
                                m.setTaxFund(+300);
                            }
                            break;
                    }
                }
                //questo lo fa se è prigioniero
                else {
                    //uscita di prigione
                    if (dice1 == dice2){
                        player.setPrisoner(false);
                        System.out.println("Sono usciti due " + dice1 + ", sei uscito di prigione");
                    }
                }
                /* MENU' POST LANCIO, non va qui se sei in prigione (credo) */
                int secondChoice;

                do {
                    System.out.println(player.toString());
                    System.out.println("Azioni:\n1) Costruisci\n2) Ipoteca una proprietà\n3) Ricompra una proprietà ipotecata\n4) Fine turno\n");
                    secondChoice = input.nextInt();
                    if (secondChoice <= 1 || secondChoice > 4) {
                        System.out.println("Errore!");
                    }
                    if(secondChoice == 1 && !(m.comboBuildableColors(player.getProperties()).size()>0)) {
                        System.out.println("Non hai proprietà edificabili");
                        secondChoice = 0; /* lo imposto a 0 così il ciclo si ripete */
                    }
                    else if (secondChoice == 2 && player.getProperties().size()==0) {
                        System.out.println("Non hai proprietà attive");
                        secondChoice = 0; /* lo imposto a 0 così il ciclo si ripete */
                    }
                    else if (secondChoice == 3 && m.getMortgagedProperties(player.getProperties()).size() == 0) {
                        System.out.println("Non hai proprietà ipotecate");
                        secondChoice = 0; /* lo imposto a 0 così il ciclo si ripete */
                    }

                }while(secondChoice <= 1 || secondChoice > 4);

                switch (secondChoice) {
                    case 1:
                        System.out.println("Elenco zone ");
                        ArrayList<String> propertiesPerColor = new ArrayList<>();
                        for (Box.Color c: m.comboBuildableColors(player.getProperties())) {
                            System.out.println(c + "ZONE");
                        }
                        Box.Color[] colorsArray = m.comboBuildableColors(player.getProperties()).toArray(new Box.Color[0]);
                        /*for (String s: m.getNamesFromColor(colorechesceglitu)) {
                            System.out.println(s);
                        }*/
                        break;
                    case 2:
                        ArrayList<String> activeProperties = m.getActiveProperties(player.getProperties());
                        String activeListOutput = "";
                        for (int k = 0; k < activeProperties.size(); k++) {
                            activeListOutput += ((k+1)+" - "+activeProperties.get(k));
                        }

                        do {
                            System.out.println("Elenco proprietà ipotecabili:\n"+activeListOutput);
                            secondChoice = input.nextInt();
                            if (secondChoice < 1 || secondChoice > activeProperties.size()) {
                                System.out.println("Errore!");
                            }
                        }while(secondChoice < 1 || secondChoice > activeProperties.size());

                        player = m.setMortgageProperty(player, activeProperties.get(secondChoice-1));
                        break;
                    case 3:
                        ArrayList<String> mortgagedProperties = m.getMortgagedProperties(player.getProperties());
                        String mortgagedListOutput = "";
                        for (int k = 0; k < mortgagedProperties.size(); k++) {
                            mortgagedListOutput += ((k+1)+" - "+mortgagedProperties.get(k));
                        }
                        do {
                            System.out.println("Elenco proprietà ipotecate:\n"+mortgagedListOutput);
                            secondChoice = input.nextInt();
                            if (secondChoice < 1 || secondChoice > mortgagedProperties.size()) {
                                System.out.println("Errore!");
                            }
                        }while(secondChoice < 1 || secondChoice > mortgagedProperties.size());

                        player = m.setActiveProperty(player, mortgagedProperties.get(secondChoice-1));
                        break;
                    case 4:
                        break;
                }

            }
        }while (m.getWin());
    }
}
