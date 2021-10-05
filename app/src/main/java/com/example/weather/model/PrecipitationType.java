package com.example.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrecipitationType {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("intensity")
    @Expose
    private String intensity;

    public String getIntensity() { return intensity; }

    public void setIntensity(String intensity) {this.intensity = intensity;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type; }
}
