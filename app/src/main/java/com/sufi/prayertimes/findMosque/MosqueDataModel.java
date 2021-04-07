package com.sufi.prayertimes.findMosque;

public class MosqueDataModel {

    private int id;
    private String name,address,image;
    private Double lat,lng, distance;

    public MosqueDataModel(int id, String name, String address,String image, Double lat, Double lng, Double distance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getDistance() {
        return distance;
    }

}
