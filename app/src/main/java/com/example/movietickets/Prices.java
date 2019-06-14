package com.example.movietickets;

/* Class to populate the ArrayAdapter */
public class Prices {

    private String demography;
    private String price;

    public Prices(String demo, String val) {
        demography = demo;
        price = val;
    }

    /* Retrieves demography variable */
    public String getDemography() {
        return demography;
    }

    /* Retrieves price variable */
    public String getPrice() {
        return price;
    }

}
