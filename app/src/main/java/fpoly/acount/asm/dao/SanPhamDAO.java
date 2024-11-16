package fpoly.acount.asm.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.asm.database.DBHelper;
import fpoly.acount.asm.model.Product;

public class SanPhamDAO {
    private DBHelper dbHelper;
    SQLiteDatabase database;

    public SanPhamDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    public ArrayList<Product> getDS(){
        Cursor cursor = database.rawQuery("SELECT * FROM SANPHAM", null);
        ArrayList<Product> lst = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                lst.add(new Product(cursor.getInt(0), cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getInt(3)));
            }while (cursor.moveToNext());
        }

        return lst;
    }
}
