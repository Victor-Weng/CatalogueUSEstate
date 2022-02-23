package com.example.catalogueusestate.Model;

import android.widget.TextView;

import java.io.Serializable;

public class Maison implements Serializable
{
    //primary_photo
    private String  href;
    // no array
    private String status, list_date,list_price, type;
    //description
    private Integer sqft, lot_sqft,  baths, beds, garage, year_built, sold_price;
    private String sold_date;
    //location
        //adress
    private String line, city, state;

    // id
    private String property_id;

    //details
    private String price, noise_score;
    private String stories;

    public Maison()
    {
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setStories(String stories) {
        this.stories = stories;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNoise_score() {
        return noise_score;
    }

    public void setNoise_score(String noise_score) {
        this.noise_score = noise_score;
    }

    public String getproperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getList_date() {
        return list_date;
    }

    public void setList_date(String list_date) {
        this.list_date = list_date;
    }

    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSqft() {
        return sqft;
    }

    public void setSqft(Integer sqft) {
        this.sqft = sqft;
    }

    public Integer getLot_sqft() {
        return lot_sqft;
    }

    public void setLot_sqft(Integer lot_sqft) {
        this.lot_sqft = lot_sqft;
    }


    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public Integer getGarage() {
        return garage;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    public Integer getYear_built() {
        return year_built;
    }

    public void setYear_built(Integer year_built) {
        this.year_built = year_built;
    }

    public Integer getSold_price() {
        return sold_price;
    }

    public void setSold_price(Integer sold_price) {
        this.sold_price = sold_price;
    }

    public String getSold_date() {
        return sold_date;
    }

    public void setSold_date(String sold_date) {
        this.sold_date = sold_date;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
