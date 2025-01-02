package id.myryan.laundry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.myryan.laundry.model.ModelPelanggan;
import id.myryan.laundry.model.ModelLayanan;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_laundry.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_PELANGGAN = "pelanggan";
    public static final String KEY_PELANGGAN_ID = "pelanggan_id";
    public static final String KEY_PELANGGAN_NAMA = "nama";
    public static final String KEY_PELANGGAN_EMAIL = "email";
    public static final String KEY_PELANGGAN_HP = "hp";

    public static final String TABLE_LAYANAN = "layanan";
    public static final String KEY_LAYANAN_ID = "layanan_id";
    public static final String KEY_LAYANAN_TIPE = "tipeLayanan";
    public static final String KEY_LAYANAN_HARGA = "harga";

    private static final String CREATE_TABLE_PELANGGAN = "CREATE TABLE " +
            TABLE_PELANGGAN + "("
            + KEY_PELANGGAN_ID + " TEXT, " + KEY_PELANGGAN_NAMA + " TEXT, "+
            KEY_PELANGGAN_EMAIL + " TEXT, "+KEY_PELANGGAN_HP +" TEXT )";

    private static final String CREATE_TABLE_LAYANAN = "CREATE TABLE " +
            TABLE_LAYANAN + "("
            + KEY_LAYANAN_ID + " TEXT, " + KEY_LAYANAN_TIPE + " TEXT, " +
            KEY_LAYANAN_HARGA + " TEXT )";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PELANGGAN);
        db.execSQL(CREATE_TABLE_LAYANAN);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELANGGAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAYANAN);
        onCreate(db);
    }
    public boolean insertPelanggan(ModelPelanggan mp){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_ID, mp.getId());
        contentValues.put(KEY_PELANGGAN_NAMA, mp.getNama());
        contentValues.put(KEY_PELANGGAN_EMAIL, mp.getEmail());
        contentValues.put(KEY_PELANGGAN_HP, mp.getHp());
        long id = database.insert(TABLE_PELANGGAN, null, contentValues);
        database.close();
        if (id != -1){
            return true;
        }else{
            return false;
        }
    }
    public List<ModelPelanggan> getPelanggan(){
        List<ModelPelanggan> pel = new ArrayList<ModelPelanggan>();
        String query = "SELECT * FROM " + TABLE_PELANGGAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                ModelPelanggan k = new ModelPelanggan();
                k.setId(cursor.getString(0));
                k.setNama(cursor.getString(1));
                k.setEmail(cursor.getString(2));
                k.setHp(cursor.getString(3));
                pel.add(k);
            }while (cursor.moveToNext());
        }
        return pel;
    }

    // Fungsi untuk memperbarui pelanggan
    public boolean updatePelanggan(String id, String name, String email, String hp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_NAMA, name);
        contentValues.put(KEY_PELANGGAN_EMAIL, email);
        contentValues.put(KEY_PELANGGAN_HP, hp);

        // Update the row where the id matches
        int result = db.update(TABLE_PELANGGAN, contentValues, KEY_PELANGGAN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0; // Mengembalikan true jika update berhasil
    }

    // Fungsi untuk menghapus pelanggan
    public boolean deletePelanggan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PELANGGAN, KEY_PELANGGAN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0; // Mengembalikan true jika penghapusan berhasil
    }

    public boolean insertLayanan(ModelLayanan ml){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_ID, ml.getId());
        contentValues.put(KEY_LAYANAN_TIPE, ml.getTipe());
        contentValues.put(KEY_LAYANAN_HARGA, ml.getHarga());
        long id = database.insert(TABLE_LAYANAN, null, contentValues);
        database.close();
        if (id != -1){
            return true;
        }else{
            return false;
        }
    }
    public List<ModelLayanan> getLayanan() {
        List<ModelLayanan> lay = new ArrayList<ModelLayanan>();
        String query = "SELECT * FROM " + TABLE_LAYANAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ModelLayanan k = new ModelLayanan();
                k.setId(cursor.getString(0));
                k.setTipe(cursor.getString(1));
                k.setHarga(cursor.getString(2));
                lay.add(k);
            } while (cursor.moveToNext());
        }
        return lay;
    }

    public boolean updateLayanan(String id, String tipe, String harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_TIPE, tipe);
        contentValues.put(KEY_LAYANAN_HARGA, harga);
        int result = db.update(TABLE_LAYANAN, contentValues, KEY_LAYANAN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }

    public boolean deleteLayanan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_LAYANAN, KEY_LAYANAN_ID + " = ?", new String[]{id});
        db.close();
        return result > 0;
    }

}