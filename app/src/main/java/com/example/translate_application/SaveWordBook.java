package com.example.translate_application;

public class SaveWordBook {
    private int IdTuVung;
    private String LuuTuVung;
    private String LuuBanDich;

    public SaveWordBook(int idTuVung, String luuTuVung, String luuBanDich) {
        IdTuVung = idTuVung;
        LuuTuVung = luuTuVung;
        LuuBanDich = luuBanDich;
    }

    public void setIdTuVung(int idTuVung) {
        IdTuVung = idTuVung;
    }

    public int getIdTuVung() {
        return IdTuVung;
    }

    public String getLuuTuVung() {
        return LuuTuVung;
    }

    public String getLuuBanDich() {
        return LuuBanDich;
    }

    public void setLuuTuVung(String luuTuVung) {
        LuuTuVung = luuTuVung;
    }

    public void setLuuBanDich(String luuBanDich) {
        LuuBanDich = luuBanDich;
    }
}
