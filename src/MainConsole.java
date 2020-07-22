import sample.Box;
import sample.Monopoly;
import sample.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args) {
        Monopoly m = new Monopoly(new Player(Player.Pawn.ARANCINO), new Player(Player.Pawn.ELEFANTE));
        Player[] players = new Player[3];
        Scanner input = new Scanner(System.in);

        //p1 = m.play(p1);
        do {
            for (Player player : players) {
                player.movement(m.rollDice());
                if (m.field[player.getPosition()].getType().equals(Box.Type.PROPERTY)) {
                    for (Player otherPlayer : players) {
                        if (!otherPlayer.checkProprieties(m.field[player.getPosition()].getName())) {
                            System.out.println("Questa proprietà è libera");
                            if (player.getBill() < m.field[player.getPosition()].getPrice()) {
                                System.out.println("Non hai i soldi per comprala");}
                            else {
                                char choice = input.next().charAt(0);
                                switch (choice){
                                    case 's' :
                                        player.payment(m.field[player.getPosition()].getPrice());
                                        player.addProperty(m.field[player.getPosition()].getName());
                                        break;
                                    case 'n' :

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
                                                            System.out.println("prezzo attuale: "+price+"\nrilancia (oppure scrivi 0 per lasciare)");
                                                            raise = input.nextInt();
                                                            if ((raise!=0 && raise<=price) && auctionPlayer.payment(m.field[player.getPosition()].getPrice())) System.out.println("errore");
                                                        }while((raise!=0 && raise<=price) && auctionPlayer.payment(m.field[player.getPosition()].getPrice()));
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

                                        }while (m.getWin());

                                        break;
                                }
                            }
                        }
                        else {
                            // fare derivato del prezzo delle proprietà
                        }
                    }
                }
            }
        }while (m.getWin());
    }
}
