package com.example.translate_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.DatabaseErrorHandler;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    //select
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }
   /* public void INSERT_HINH(String ten,byte[] hinh,String tentaikhoan){
        SQLiteDatabase database=getWritableDatabase();
        String sql="Insert into Hinh values(null,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindBlob(2,hinh);
        statement.bindString(3,tentaikhoan);

        statement.executeInsert();
    }
    //insert trong lv
        public long INSERT_TuVung(String tuCanDich , String banDich){
        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO TuVung VALUES(null, ?,?)";
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(1, tuCanDich);
//        statement.bindString(2, banDich);
            ContentValues contentValues = new ContentValues();
            contentValues.put("TuCanDich",tuCanDich);
            contentValues.put("BanDich",banDich);
            long res = database.insert("TuVung",null,contentValues);
            database.close();
            return res;

    }

    }*/
//    public void UPDATE_HINH(String ten,byte[] hinh,int Id){
//        SQLiteDatabase database=getWritableDatabase();
//        String sql="Update Hinh set ten=?,hinh=? where Id=null";
//        SQLiteStatement statement=database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(0,ten);
//        statement.bindBlob(1,hinh);
//
//        statement.execute();
//    }
   public long INSERT_Luu(String luuTuVung,String luuBanDich){
       SQLiteDatabase database = getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("LuuTuVung",luuTuVung);
       contentValues.put("LuuBanDich",luuBanDich);
       long res = database.insert("SaveWordBook",null,contentValues);
       database.close();
       return res;
    }
    //insert,update,delete
    public void Uploaddata(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
}