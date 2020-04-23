package com.sam.kadarairkopi.Data;

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
            String beratKopi = object.getString("berat");
            String kadarAir = object.getString("air");

            this.weight = beratKopi;
            this.waterLevel = kadarAir;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}