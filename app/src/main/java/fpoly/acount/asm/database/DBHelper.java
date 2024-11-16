package fpoly.acount.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "ASM", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String nguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        sqLiteDatabase.execSQL(nguoiDung);

        String sanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT, giaban INTEGER, soluong INTEGER)";
        sqLiteDatabase.execSQL(sanPham);

        String insert_user = "INSERT INTO NGUOIDUNG (tendangnhap,matkhau,hoten) VALUES " +
                "('admin','123456','avanmin'), \n" +
                "('phong','123456','phiongle'),\n" +
                "('abc','123456','acv')";
        sqLiteDatabase.execSQL(insert_user);

        String insert_product = "INSERT INTO SANPHAM (masp,tensp,giaban,soluong) VALUES \n" +
                "('1','Banh','5000','12'),\n" +
                "('2','Keo','2000','40'),\n" +
                "('3','Sua','8000','10')";
        sqLiteDatabase.execSQL(insert_product);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(sqLiteDatabase);
        }
    }
}
