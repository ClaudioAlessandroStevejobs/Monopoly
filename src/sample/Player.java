package sample;

import java.util.ArrayList;

public class Player {

    public enum Pawn{ARANCINO, ELEFANTE, CAVALLINO, CANNOLO, CASSATA, VULCANO, NONE}
    private Pawn pawn;
    private ArrayList<String> properties = new ArrayList<>();
    private int bill = 2000;
    private short position = 0;
    private boolean prisoner;
    private boolean canEscapeFromPrison;
    private boolean outOfAuction;

    public Player(){
    }

    public Player(Pawn pawn){
        this.pawn = pawn;
        this.prisoner = false;
        this.canEscapeFromPrison = false;
    }

    public void movement(int increasePosition) {
        this.position += increasePosition;
        if (this.position >= 40){
            if (this.position > 40){
                this.bill += 500;
            }
            this.position -= 40;
        }
    }

    public boolean checkProprieties(String boxName){
        if (properties.contains(boxName)){
            return true;
        }
        else return false;
    }

    public boolean payment(int tax) {
        if (this.bill - tax < 0) {
            return false;
        }
        else {
            this.bill -= tax;
            return true;
        }
    }

    public void addProperty(String property) {
        properties.add(property);
    }

    public void setPosition(short position) {
        this.position = position;
    }
    public short getPosition() {
        return position;
    }
    public int getBill() {
        return bill;
    }
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
    public Pawn getPawn() {
        return pawn;
    }
    public ArrayList<String> getProperties() {
        return properties;
    }
    public static ArrayList<Pawn> getPawnArray() {
        ArrayList<Pawn> pawns = new ArrayList<Pawn>() {{
                add(Pawn.ELEFANTE);
                add(Pawn.CANNOLO);
                add(Pawn.ARANCINO);
                add(Pawn.CAVALLINO);
                add(Pawn.VULCANO);
                add(Pawn.CASSATA);
            }};
        return pawns;
    }


    public void setPrisoner(boolean prisoner) {
        this.prisoner = prisoner;
    }
    public boolean getPrisoner(){
        return prisoner;
    }
    public void setCanEscapeFromPrison(boolean canEscapeFromPrison) {
        this.canEscapeFromPrison = canEscapeFromPrison;
    }
    public boolean getCanEscapeFromPrison(){
        return canEscapeFromPrison;
    }

    public void setOutOfAuction(boolean outOfAuction) {
        this.outOfAuction = outOfAuction;
    }

    public boolean isOutOfAuction() {
        return outOfAuction;
    }
/*
    public int countHoses(){
        for (String s: properties) {
            if(){

            }
        }
    }
*/
    @Override
    public String toString() {
        return "Dati "+pawn +":\nPosizione: "+position+"\nConto: "+bill+"€"+"\nCaselle in tuo possesso:\n"+properties+"\n";
    }
}
