package sample;

import java.util.ArrayList;

public class Player {

    public enum Pawn{ARANCINO, ELEFANTE, CAVALLINO, CANNOLO}
    private Pawn pawn;
    private ArrayList<String> properties;
    private int bill = 2000;
    private short position = 0;
    private boolean prisoner;

    public Player(Pawn pawn){
        this.pawn = pawn;
        this.prisoner = false;
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

    public void setPosition(short position) {
        this.position = position;
    }

    public short getPosition() {
        return position;
    }


    public int getBill() {
        return bill;
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

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
    public void addProperty(String property) {
        properties.add(property);
    }
    public ArrayList<String> getProperties() {
        return properties;
    }
    public Pawn getPawn() {
        return pawn;
    }

    public void setPrisoner(boolean prisoner) {
        this.prisoner = prisoner;
    }

    public boolean getPrisoner(){
        return prisoner;
    }


}
