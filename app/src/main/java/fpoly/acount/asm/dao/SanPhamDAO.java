package fpoly.acount.asm.dao;

import android.content.ContentValues;
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

    public boolean removeProduct(int masp){
        database = dbHelper.getWritableDatabase();
        int check = database.delete("SANPHAM", "masp=?", new String[]{String.valueOf(masp)});
        return check > 0;
    }

    public boolean addProdduct(Product product){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masp", product.getMasp());
        values.put("tensp", product.getTensp());
        values.put("giaban", product.getGiaban());
        values.put("soluong", product.getSoluong());

        long check = database.insert("SANPHAM", null, values);

        return check != -1;
    }


    public boolean editProduct(Product product){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tensp", product.getTensp());
        values.put("giaban", product.getGiaban());
        values.put("soluong", product.getSoluong());

        int check = database.update("SANPHAM", values, "masp = ?", new String[]{String.valueOf(product.getMasp())});

        return check >= 0;
    }
}
