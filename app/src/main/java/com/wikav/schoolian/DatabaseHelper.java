package com.wikav.schoolian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wikav.schoolian.Adeptor.ModelList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "VOULU.db";
    public static final String TABLE_NAME = "jobposts";
    public static final String ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String PROFILE = "PROFILE";
    public static final String TITLE = "TITLE";
    public static final String DEC = "DEC";
    public static final String TIME = "TIME";
    public static final String POST_ID = "POST_ID";
    public static final String IMAGE = "IMAGE";
    public static final String LIKES = "LIKES";
    public static final String IMG2 = "IMG2";
    public static final String IMG3 = "IMG3";
    public static final String STATUS = "STATUS";
    public static final String MOBILE = "MOBILE";
    public static final String RATE = "RATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PROFILE TEXT,TITLE TEXT, DEC TEXT, TIME TEXT, POST_ID TEXT, IMAGE TEXT, LIKES TEXT, IMG2 TEXT, IMG3 TEXT, STATUS TEXT,MOBILE TEXT,RATE TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username,String profile,String title,String dec, String time,String post_id,String image,String likes,
                              String img2,String img3,String status,String mobile,String rate) {
        if(!status.equals("3")) {
            SQLiteDatabase db = this.getWritableDatabase();
            String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + POST_ID + " LIKE " + post_id;
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
           // Log.i("count", "" + count);
            if (count == 0) {
               // Log.i("count", "" + count + " " + post_id);
                ContentValues contentValues = new ContentValues();
                contentValues.put(USERNAME, username);
                contentValues.put(PROFILE, profile);
                contentValues.put(TITLE, title);
                contentValues.put(DEC, dec);
                contentValues.put(TIME, time);
                contentValues.put(POST_ID, post_id);
                contentValues.put(IMAGE, image);
                contentValues.put(LIKES, likes);
                contentValues.put(IMG2, img2);
                contentValues.put(IMG3, img3);
                contentValues.put(STATUS, status);
                contentValues.put(MOBILE, mobile);
                contentValues.put(RATE, rate);
                long result = db.insert(TABLE_NAME, null, contentValues);

                if (result == -1)
                    return false;
                else
                    return true;
            } else {
                return false;
            }
        }
        else
        {
            SQLiteDatabase db = this.getWritableDatabase();
            String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + POST_ID + " LIKE " + post_id;
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            // Log.i("count", "" + count);
            if (count >0) {
                Log.i("delete", "" + post_id);
                deleteData(post_id);
            }
        }
       return false;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public List<ModelList> getdata(){
        // DataModel dataModel = new DataModel();
        List<ModelList> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ORDER BY TIME DESC ",null);
        StringBuffer stringBuffer = new StringBuffer();
        ModelList dataModel = null;
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(MOBILE));
            String des = cursor.getString(cursor.getColumnIndexOrThrow(DEC));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE));
            String img = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE));
            String img2 = cursor.getString(cursor.getColumnIndexOrThrow(IMG2));
            String img3 = cursor.getString(cursor.getColumnIndexOrThrow(IMG3));
            String id = cursor.getString(cursor.getColumnIndexOrThrow(POST_ID));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME));
            String profile = cursor.getString(cursor.getColumnIndexOrThrow(PROFILE));
            String like = cursor.getString(cursor.getColumnIndexOrThrow(LIKES));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS));
            dataModel= new ModelList(img, title, des, rate, id, time, mobile, like, profile, username, status, img2, img3);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (ModelList mo:data ) {

            Log.i("Hellomo",""+mo.getUsername());
        }

        //

        return data;
    }
   /* public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }*/
   public boolean updateData(String status,String id) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(ID,id);
       contentValues.put(STATUS,status);
       db.update(TABLE_NAME, contentValues, "POST_ID = ?",new String[] { id });
       return true;
   }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "POST_ID = ?" +
                "",new String[] {id});
    }
}
