package com.example.translate_application;

public class TuVung {
    private int Id;
    private String TuCanDich;
    private String BanDich;



    public TuVung(int id, String tuCanDich, String banDich) {
        Id = id;
        TuCanDich = tuCanDich;
        BanDich = banDich;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTuCanDich() {
        return TuCanDich;
    }

    public void setTuCanDich(String tuCanDich) {
        TuCanDich = tuCanDich;
    }

    public String getBanDich() {
        return BanDich;
    }

    public void setBanDich(String banDich) {
        BanDich = banDich;
    }
}
