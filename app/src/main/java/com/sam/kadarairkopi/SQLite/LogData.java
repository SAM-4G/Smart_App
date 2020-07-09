package com.sam.kadarairkopi.SQLite;

import androidx.annotation.NonNull;

public class LogData {
    private long idKopi;
    private String beratKopi;
    private String kadarAirKopi;

    public long getIdKopi() {
        return idKopi;
    }

    public void setIdKopi(long idKopi) {
        this.idKopi = idKopi;
    }

    public String getBeratKopi() {
        return beratKopi;
    }

    public void setBeratKopi(String beratKopi) {
        this.beratKopi = beratKopi;
    }

    public String getKadarAirKopi() {
        return kadarAirKopi;
    }

    public void setKadarAirKopi(String kadarAirKopi) {
        this.kadarAirKopi = kadarAirKopi;
    }

    @NonNull
    @Override
    public String toString() {
        return "Weight : " + beratKopi + "\nWater Level :" + kadarAirKopi;
    }
}
