package be.kuleuven;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;

public class Scorebord {
    private static final String FILENAME = "scorebord.json";
    private ArrayList<Speler> spelers;

    public Scorebord() {//constructor of scorebord, searches for names and scores of last players.
        this.spelers = new ArrayList<>();
        //load scores van JSON file met GSON
        LoadScores();
    }

    public void voegToe(String naam, int huidigeScore) throws IOException {
        Speler speler = findSpeler(naam);

        if (speler == null) {//create speler want speler is niet gevonden in de list
            speler = new Speler(naam, huidigeScore); //maak een object aan van een speler en voeg het toe in de arrayList
            spelers.add(speler);
        }
        //speler is een object in de list
        speler = new Speler(speler.getNaam(), speler.getScore() + huidigeScore);
        //schrijf de score en naam weg in json
        save();

    }

    private Speler findSpeler(String naam) {
        for (Speler speler : spelers) {
            if (speler.getNaam().equals(naam)) {
                return speler;
            }
        }
        return null;
    }

    private void save(){
            JsonArray jsonArray = new JsonArray();
            for (Speler speler : spelers)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("naam", speler.getNaam());
                jsonObject.addProperty("score", speler.getScore());
                jsonArray.add(jsonObject);
            }
        try (Writer writer = new FileWriter(FILENAME)) {
            Gson gson = new Gson();
            gson.toJson(jsonArray,writer);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public int getTotaleScore(String x) {
        Speler speler = findSpeler(x);
        if (speler != null) {
            return speler.getScore();
        } else {
            return 0;
        }
    }

    public String getWinnaar() {
        int min_score_val = 0;
        String winnaar = "Geen winnaar";
        for (Speler speler : spelers){
            if (speler.getScore() > min_score_val){
                min_score_val = speler.getScore();
                winnaar = speler.getNaam();
            }
        }
        return winnaar;
    }

    private void LoadScores() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILENAME)) {
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            if (jsonArray != null) {
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    voegToe(jsonObject.get("naam").getAsString(), jsonObject.get("score").getAsInt());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}