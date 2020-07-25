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
        int playerNum;
        do {
            System.out.println("Quanti giocatori partecipano? (2-6)");
            playerNum = input.nextInt();
            if (playerNum <= 1 || playerNum > 6) {
                System.out.println("Errore!");
            }
        }while(playerNum <= 1 || playerNum > 6);
        Player[] players = new Player[playerNum];
        players[0] = new Player();

        /* SET PEDINE, RICORDA TRY CATCH */
        ArrayList<Player.Pawn> availablePawns = players[0].getPawnArray();
        for (int k = 0; k<playerNum; k++) {
            System.out.println("Giocatore "+(k+1)+":\nScegli la pedina tra queste:");
            for (int j = 0; j < availablePawns.size(); j++) {
                System.out.println((j+1)+" - "+ availablePawns.get(j));
            }
            int choice;
            choice = input.nextInt();
            players[k]= new Player(availablePawns.get(choice-1));
            System.out.println("Il giocatore "+(k+1)+" ha scelto "+availablePawns.get(choice-1)+"!\n");
            availablePawns.remove(choice-1);
        }

        //MESCOLAMENTO GIOCATORI
        players = m.shuffle(players);

        do {
            for (Player player : players) {
                char choice;
                System.out.println("E' il turno di "+player.getPawn()+":\nLa tua posizione attuale è "+player.getPosition());
                Thread.sleep(1000);
                int dice1 = m.rollDice();
                int dice2 = m.rollDice();
                System.out.println("Tiri il dado!");
                Thread.sleep(2000);
                System.out.println("Sono usciti "+dice1+" e "+dice2);
                //FARE LA ROBA SE E' DOPPIO

                System.out.println("Ti smuovi di "+(dice1+dice2)+" caselle");
                if (!player.getPrisoner())  {
                    player.movement((dice1 + dice2));
                    Box.Type type = m.field[player.getPosition()].getType();
                    System.out.println("Sei su "+ m.field[player.getPosition()].getName());
                    switch (type){
                        case PROPERTY:
                            Thread.sleep(1000);
                            //se la proprietà è tua
                            if (player.checkProprieties(m.field[player.getPosition()].getName())) {
                                System.out.println("La proprietà è tua");
                            }
                            //se la proprietà è libera
                            else if (m.isPropertyFree(players, m.field[player.getPosition()].getName())) {
                                System.out.println("Questa proprietà è libera");
                                //controlla se hai i soldi, se non ce li hai imposta direttamente di andare all'asta
                                if (player.getBill() < m.field[player.getPosition()].getPrice()) {
                                    System.out.println("Non hai i soldi per comprala");
                                    choice = 'n';
                                }
                                //altrimenti ti chiede di inserire la tua scelta
                                else {

                                    do {
                                        System.out.println("Vuoi comprarla oppure andare all'asta? (s/n)");
                                        choice = input.next().charAt(0);
                                        if (choice != 's' && choice != 'n') {
                                            System.out.println("Errore!");
                                        }
                                    }while(choice != 's' && choice != 'n');

                                }
                                    switch (choice) {
                                        case 's':
                                            player.payment(m.field[player.getPosition()].getPrice());
                                            player.addProperty(m.field[player.getPosition()].getName());
                                            m.setBuildable(player.getProperties());
                                            break;
                                        case 'n':
                                            //arraylist di gente esclusa dall'asta
                                            ArrayList<Player> excludedPlayers = new ArrayList<Player>();
                                            excludedPlayers.add(player);
                                            //prezzo iniziale
                                            int price = m.field[player.getPosition()].getPrice();
                                            //QUESTO SI DEVE FARE NEL MONOPOLY
                                            do {
                                                for (Player auctionPlayer : players) {
                                                    if (!(excludedPlayers.size() == players.length - 1)) {
                                                        if (!excludedPlayers.contains(auctionPlayer)) {
                                                            int raise;
                                                            //controllo se puoi puntare già di base o no
                                                            if (!auctionPlayer.payment(m.field[player.getPosition()].getPrice())) {
                                                                excludedPlayers.add(auctionPlayer);
                                                                continue;
                                                            }
                                                            do {
                                                                System.out.println("prezzo attuale: " + price + "\nrilancia (oppure scrivi 0 per lasciare)");
                                                                raise = input.nextInt();
                                                                if ((raise != 0 && raise <= price) && auctionPlayer.payment(m.field[player.getPosition()].getPrice()))
                                                                    System.out.println("errore");
                                                            } while ((raise != 0 && raise <= price) && auctionPlayer.payment(m.field[player.getPosition()].getPrice()));
                                                            if (raise == 0) {
                                                                excludedPlayers.add(auctionPlayer);
                                                                continue;
                                                            }
                                                            price = raise;
                                                        }
                                                    } else {
                                                        player.payment(m.field[player.getPosition()].getPrice());
                                                        auctionPlayer.addProperty(m.field[player.getPosition()].getName());
                                                    }
                                                }

                                            } while (m.getWin());
                                            //QUESTO SI DEVE FARE NEL MONOPOLY
                                            break;
                                    }
                                } else {
                                    /*tanto per mostrare i soldi*/System.out.println(player.getBill());
                                    System.out.println("Devi pagare "+m.field[player.getPosition()].getPropertyTax()+" euri");
                                    if (!player.payment(m.field[player.getPosition()].getPropertyTax())) {
                                        //scelte robe da ipotecare?, magari mettiamo un booleano per entrare in un menù di ipoteca DOPO questo switch? ho aggiunto anche la booleana alle box "mortgaged"
                                        System.out.println("ma non puoi pagare");
                                    }
                                    /*tanto per mostrare i soldi*/System.out.println(player.getBill());
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
                int choice;

                do {
                    System.out.println("\nMenù azioni:\n1) Costruisci\n2) Ipoteca una proprietà\n3) Ricompra una proprietà ipotecata\n4) Fine turno\n5) Mostra conto\n6) Mostra proprietà");
                    choice = input.nextInt();
                    if (choice <= 1 || choice > 4) {
                        System.out.println("Errore!");
                    }
                    if(choice == 1 && !(m.comboBuildableColors(player.getProperties()).size()>0)) {
                        System.out.println("Non hai proprietà edificabili");
                        choice = 0; /* lo imposto a 0 così il ciclo si ripete */
                    }
                    if(choice == 2 && player.getProperties().size()==0) {
                        System.out.println("Non hai proprietà attive");
                        choice = 0; /* lo imposto a 0 così il ciclo si ripete */
                    }
                }while(choice <= 1 || choice > 4);

                switch (choice) {
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
                            choice = input.nextInt();
                            if (choice < 1 || choice > activeProperties.size()) {
                                System.out.println("Errore!");
                            }
                        }while(choice <= 1 || choice > activeProperties.size());

                        player = m.setMortgageProperty(player, activeProperties.get(choice));
                        break;
                    case 3:
                        ArrayList<String> mortgagedProperties = m.getMortgagedProperties(player.getProperties());
                        String mortgagedListOutput = "";
                        for (int k = 0; k < mortgagedProperties.size(); k++) {
                            mortgagedListOutput += ((k+1)+" - "+mortgagedProperties.get(k));
                        }
                        do {
                            System.out.println("Elenco proprietà ipotecate:\n"+mortgagedListOutput);
                            choice = input.nextInt();
                            if (choice < 1 || choice > mortgagedProperties.size()) {
                                System.out.println("Errore!");
                            }
                        }while(choice <= 1 || choice > mortgagedProperties.size());

                        player = m.setActiveProperty(player, mortgagedProperties.get(choice));
                        break;
                    case 4:
                        break;
                }

            }
        }while (m.getWin());
    }
}
