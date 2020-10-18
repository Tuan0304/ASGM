package com.example.translate_application;

import android.content.Context;
import android.database.Cursor;
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
    public void INSERT_HINH(String ten,byte[] hinh,String tentaikhoan){
        SQLiteDatabase database=getWritableDatabase();
        String sql="Insert into Hinh values(null,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindBlob(2,hinh);
        statement.bindString(3,tentaikhoan);

        statement.executeInsert();
    }
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
    //insert,update,delete
    public void Uploaddata(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
