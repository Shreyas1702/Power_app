package com.example.testme;

public class Addval {
    String location,provider;
    int charperut;

    public Addval(String location, String provider, int charperut) {
      this.location = location;
      this.provider = provider;
      this.charperut = charperut;
    }

    public int getCharperut() {
        return charperut;
    }

    public void setCharperut(int charperut) {
        this.charperut = charperut;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
