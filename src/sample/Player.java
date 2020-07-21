package sample;

import java.util.ArrayList;

public class Player {
    enum Pawn{FUNGO}
    Pawn pawn;
    ArrayList<String> properties = new ArrayList<String>();

    public Player(Pawn pawn, ArrayList<String> properties){
        int bill = 100000;
        this.pawn = pawn;
        this.properties = properties;
    }
}
