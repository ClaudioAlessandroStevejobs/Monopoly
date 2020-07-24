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
            System.out.println("Il giocatore "+(k+1)+" ha scelto "+availablePawns.get(choice-1)+"!");
            availablePawns.remove(choice-1);
        }

        //MESCOLAMENTO GIOCATORI
        players = m.shuffle(players);

        do {
            for (Player player : players) {
                System.out.println("E' il turno di "+player.getPawn()+":\nLa tua posizione attuale è "+player.getPosition());
                Thread.sleep(1000);
                int dice1 = m.rollDice();
                int dice2 = m.rollDice();
                System.out.println("Tiri il dado!");
                Thread.sleep(2000);
                System.out.println("Sono usciti "+dice1+" e "+dice2);
                //FARE LA ROBA SE E' DOPPIO
                System.out.println("Ti muovi di "+(dice1+dice2)+" caselle");
                if (!player.getPrisoner())  {
                    player.movement(dice1+ dice2);
                    Box.Type type = m.field[player.getPosition()].getType();
                    switch (type){
                        case PROPERTY:
                            for (Player otherPlayer : players) {
                                if (!otherPlayer.checkProprieties(m.field[player.getPosition()].getName())) {
                                    System.out.println("Questa proprietà è libera");
                                    if (player.getBill() < m.field[player.getPosition()].getPrice()) {
                                        System.out.println("Non hai i soldi per comprala");
                                    } else {
                                        char choice = input.next().charAt(0);
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

                                                break;
                                        }
                                    }
                                } else {
                                    // fare derivato del prezzo delle proprietà
                                }
                            }
                        case GO: m.passGo(player);
                        case GO_TO_PRISON: m.goToPrison(player);
                        case PARKING:
                            player.payment(-m.getTaxFund());
                            System.out.println("Sei passato dal parcheggio, ritira " + m.getTaxFund() + " euro");
                            m.setTaxFund(0);
                        case CHANCE:
                            System.out.println(m.chance(player));
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
                    }
                    /*if (m.field[player.getPosition()].getType().equals(Box.Type.PROPERTY)) {
                        for (Player otherPlayer : players) {
                            if (!otherPlayer.checkProprieties(m.field[player.getPosition()].getName())) {
                                System.out.println("Questa proprietà è libera");
                                if (player.getBill() < m.field[player.getPosition()].getPrice()) {
                                    System.out.println("Non hai i soldi per comprala");
                                } else {
                                    char choice = input.next().charAt(0);
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

                                            break;
                                    }
                                }
                            } else {
                                // fare derivato del prezzo delle proprietà
                            }
                        }
                    } else if (m.field[player.getPosition()].getType().equals(Box.Type.CHANCE)) {
                        m.chance(player);
                    } else if (m.field[player.getPosition()].getType().equals(Box.Type.GO)) {
                        m.passGo(player);
                    } else if (m.field[player.getPosition()].getType().equals(Box.Type.GO_TO_PRISON)) {
                        m.goToPrison(player);
                    }
                    else if (m.field[player.getPosition()].getType().equals(Box.Type.PARKING)){
                        player.payment(-m.getTaxFund());
                        System.out.println("Sei passato dal parcheggio, ritira " + m.getTaxFund() + " euro");
                        m.setTaxFund(0);
                    }
                    else if(m.field[player.getPosition()].getType().equals(Box.Type.TAX)){
                        if (m.field[player.getPosition()].getName().equals("Patrimonial tax")){
                            player.payment(200);
                            //sperando
                            m.setTaxFund(+200);
                        }
                        else {
                            player.payment(300);
                            m.setTaxFund(+300);
                        }
                    }*/
                }
                else {
                    if (dice1 == dice2){
                        player.setPrisoner(false);
                        System.out.println("Sono usciti due " + dice1 + ", sei uscito di prigione");
                    }
                }

                if(m.comboBuildableColors(player.getProperties()).size()>0) {
                    char build;
                    do {
                        System.out.println("Vuoi costruire?");
                        build = input.next().charAt(0);
                        if (build!='s' && build!='n') System.out.println("Errore, dai una risposta corretta!");
                    }while(build!='s' && build!='n');

                    if (build=='n') {
                        continue;
                    }

                    System.out.println("Elenco zone ");
                    ArrayList<String> propertiesPerColor = new ArrayList<>();
                    for (Box.Color c: m.comboBuildableColors(player.getProperties())) {
                        System.out.println(c + "ZONE");
                    }
                    Box.Color[] colorsArray = m.comboBuildableColors(player.getProperties()).toArray(new Box.Color[0]);

                    /*for (String s: m.getNamesFromColor(colorechesceglitu)) {
                        System.out.println(s);
                    }*/



                    //input
                }














            }
        }while (m.getWin());
    }
}
