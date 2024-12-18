package fpoly.acount.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import fpoly.acount.asm.database.DBHelper;

public class NguoiDungDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public NguoiDungDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    public boolean checkLogin(String username, String password){
        Cursor cursor = database.rawQuery("SELECT * FROM NGUOIDUNG where tendangnhap=? AND matkhau =?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean register(String username, String password, String hoten){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tendangnhap", username);
        values.put("matkhau", password);
        values.put("hoten", hoten);

        long check = database.insert("NGUOIDUNG", null, values);
        return check != -1;
    }

    public String forGotpassWord(String email){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT matkhau FROM NGUOIDUNG WHERE tendangnhap=?", new String[]{email});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }else {
            return "";
        }
    }
}
