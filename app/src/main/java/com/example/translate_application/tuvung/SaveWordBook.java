package com.example.translate_application.tuvung;

public class SaveWordBook {
    public int IdTuVung;
    public String LuuTuVung;
    public String LuuBanDich;

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
