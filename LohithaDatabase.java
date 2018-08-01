package com.iprismtech.lohitha.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iprismtech.lohitha.R;
import com.iprismtech.lohitha.other.Util;

import org.json.JSONArray;

import java.io.File;

/**
 * Created by DEVELOPER on 24-Dec-16.
 */

public class LohithaDatabase {

    private static final String DATABASE_NAME = "DataBase";
    private static final int DATABASE_VERSION = 1;
    private DbHelper ourHelper;
    public final Context ourContext;
    private SQLiteDatabase ourDatabase;
    String STORAGE_PATH = "";
    public static String ItemsCartCreateQuery = "CREATE TABLE IF NOT EXISTS ItemsCart ( PrimID INTEGER PRIMARY KEY AUTOINCREMENT, ItemID INTEGER NOT NULL, CategoryID INTEGER NOT NULL,ProductID INTEGER NOT NULL, Kgs NVARCHAR(20) NOT NULL, Amount NVARCHAR(50) NOT NULL,price NVARCHAR(50) NOT NULL,ItemName NVARCHAR(500) NOT NULL,Quantity INTEGER NOT NULL,Image NVARCHAR(500) NOT NULL,Units NVARCHAR(20) NOT NULL);";

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, STORAGE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ItemsCartCreateQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + "ItemsCart");
            /*if (newVersion > oldVersion) {
                HelperObj.cusToast(ourContext,"fdgdfg");
            }*/
            onCreate(db);
        }
    }

    public LohithaDatabase(Context c) {

        ourContext = c;
    }

    /**
     * Method is use to open the local database of app
     *
     * @return
     * @throws SQLException
     */
    public LohithaDatabase open() throws SQLException {
        String appName = ourContext.getResources().getString(R.string.app_name);
        STORAGE_PATH = ourContext.getApplicationInfo().dataDir + "/" + appName + "/LohithaDatabase/";
        //STORAGE_PATH = Environment.getExternalStorageDirectory()+"/"+appName+"/LohithaDatabase/";
        File dir = new File(STORAGE_PATH);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    /**
     * This method is use to close the database.
     */
    public void close() {

        ourHelper.close();
    }

    //FOR ItemsCart item here.
    public long CreateItemsCart(Integer ItemID, Integer CategoryID, Integer productID, String Kgs, String produnctamt, String price, String ItemName, Integer Quantity, String Image, String units) {
        ContentValues cv = new ContentValues();
        cv.put("ItemID", ItemID);
        cv.put("CategoryID", CategoryID);
        cv.put("ProductID", productID);
        cv.put("Kgs", Kgs);
        cv.put("Amount", produnctamt);
        cv.put("price", price);
        cv.put("ItemName", ItemName);
        cv.put("Quantity", Quantity);
        cv.put("Image", Image);
        cv.put("Units", units);
        return ourDatabase.insert("ItemsCart", null, cv);
    }


    //FOR UpdateCart item here.
    public long UpdateItemsCart(Integer ItemID, Integer CategoryID, Integer productID, String Kgs, String productamt, String price, String ItemName, Integer Quantity, String Image, String units) {
        ContentValues cv = new ContentValues();
        cv.put("ItemID", ItemID);
        cv.put("CategoryID", CategoryID);
        cv.put("ProductID", productID);
        cv.put("Kgs", Kgs);
        cv.put("Amount", productamt);
        cv.put("price", price);
        cv.put("ItemName", ItemName);
        cv.put("Quantity", Quantity);
        cv.put("Image", Image);
        cv.put("Units", units);
        return ourDatabase.update("ItemsCart", cv, "ItemID=?", new String[]{Integer.toString(ItemID)});
    }

    /**
     * Method is use to all the data which are stored into the local database.
     *
     * @return
     */
    public JSONArray GetDataItemsCartAll() {
        String searchQuery;
        searchQuery = "SELECT * FROM ItemsCart";
        Cursor cursor = ourDatabase.rawQuery(searchQuery, null);
        return Util.getInstance().GetJSONArray(cursor);
    }

    /**
     * This method is use to delete the item from the cartlist.
     *
     * @return
     */
    public long DeleteItemsCartTableData() {
        long result;
        result = ourDatabase.delete("ItemsCart", null, null);
        return result;
    }

    /**
     * Method is use to check if item already exit in database of not.
     *
     * @param ItemID
     * @return
     */
    public boolean CheckItemExist(Integer ItemID) {
        String searchQuery = "SELECT * FROM ItemsCart WHERE ItemID = " + ItemID;
        Cursor cursor = ourDatabase.rawQuery(searchQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /**
     * Method is use to delete the row from the sqlite database.
     *
     * @param ItemID
     * @return
     */
    public long DeleteItemRow(Integer ItemID) {
        long result;
        result = ourDatabase.delete("ItemsCart", "ItemID = ?", new String[]{String.valueOf(ItemID)});
        return result;
    }

}
