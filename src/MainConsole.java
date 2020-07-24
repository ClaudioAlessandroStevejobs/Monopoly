import sample.Box;
import sample.Monopoly;
import sample.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args) {
        Monopoly m = new Monopoly();
        Player[] players = new Player[3];
        Scanner input = new Scanner(System.in);

        //p1 = m.play(p1);
        do {
            for (Player player : players) {
                int dice1 = m.rollDice();
                int dice2 = m.rollDice();
                if (!player.getPrisoner()) {
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
            }
        }while (m.getWin());
    }
}
