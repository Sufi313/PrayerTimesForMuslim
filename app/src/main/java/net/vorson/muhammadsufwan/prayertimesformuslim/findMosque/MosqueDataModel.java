package net.vorson.muhammadsufwan.prayertimesformuslim.findMosque;

public class MosqueDataModel {

    private int id;
    private String name,address;
    private float lat,lng;

    public MosqueDataModel(int id, String name, String address, float lat, float lng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
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

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

}
