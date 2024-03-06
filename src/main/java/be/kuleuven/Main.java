package be.kuleuven;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello and welcome to my scorebord!");
        Scorebord scorebord = new Scorebord();
        scorebord.voegToe("Papa", 20);
        scorebord.voegToe("Mama", 31);
        //scorebord.getTotaleScore("Darren");
        System.out.println("De tot score van Papa is: "+ scorebord.getTotaleScore("Papa"));
        System.out.println("De winnaar is genaamd: "+ scorebord.getWinnaar());
    }
}