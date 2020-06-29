package com.sam.kadarairkopi.data;

import org.json.JSONObject;

public class DataCore {
    String id;
    String weight;
    String waterLevel;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataCore(JSONObject object) {
        try {
            String id = object.getString("id");
            String kadarAir = object.getString("Kadar Air");
            String beratKopi = object.getString("Berat");

            this.id = id;
            this.waterLevel = kadarAir;
            this.weight = beratKopi;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}