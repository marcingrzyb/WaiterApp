package com.restaurant.waiterapp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Piwo");
        cricket.add("Piwo");
        cricket.add("Piwoa");
        cricket.add("Piwo");
        cricket.add("Piwo");

        List<String> football = new ArrayList<String>();
        football.add("Wóda");
        football.add("Tekla");
        football.add("Whisky");
        football.add("Whsikey");
        football.add("Rum");

        List<String> basketball = new ArrayList<String>();
        basketball.add("Rum");
        basketball.add("Rum");
        basketball.add("Rum");
        basketball.add("Rum");
        basketball.add("Rum");

        expandableListDetail.put("Dla Cip", cricket);
        expandableListDetail.put("Szanowane", football);
        expandableListDetail.put("Oferta dnia", basketball);
        return expandableListDetail;
    }
}