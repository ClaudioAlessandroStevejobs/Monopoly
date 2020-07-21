import sample.Box;
import sample.Monopoly;
import sample.Player;

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
                                        System.out.println("va all'asta");
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
