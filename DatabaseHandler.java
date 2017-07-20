package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.prog.logicprog.petdeal.Listitem_Structures.post_structure;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context) {
		super(context, ApplicationConstants.DATABASE_NAME, null,  ApplicationConstants.DATABASE_VERSION);


	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_COUPONS_TABLE = "CREATE TABLE " +  ApplicationConstants.TABLE_NAME + "("
				+  ApplicationConstants.KEY_ID + " INTEGER PRIMARY KEY,"
				+  ApplicationConstants.KEY_couponid + " TEXT,"
				+  ApplicationConstants.KEY_couponname + " TEXT,"
				+  ApplicationConstants.KEY_couponcost + " TEXT,"
				+  ApplicationConstants.KEY_coupondiscount + " TEXT,"
				+  ApplicationConstants.KEY_couponquantity + " TEXT,"
				+  ApplicationConstants.KEY_coupondescription + " TEXT,"
				+  ApplicationConstants.KEY_couponimage + " TEXT,"
				+  ApplicationConstants.KEY_behaviour + " TEXT,"
		        +ApplicationConstants.KEY_availableCount+" TEXT)";

		db.execSQL(CREATE_COUPONS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " +  ApplicationConstants.TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public boolean addCoupon(String coupon_id,String coupon_name, String coupon_cost,String coupon_discount,String coupon_quantity,String coupon_description,String coupon_image,String behaviour,int availablecount) {
		Log.v("Delete",coupon_id+"    "+coupon_name+"    "+ coupon_cost+"    "+coupon_discount+"    "+coupon_quantity+"    "+coupon_description+"    "+ coupon_image+"    "+ behaviour);
		boolean exceededlimmit=false;
		ArrayList<post_structure> existingarray=getAllCoupons();
		for(int loopvar=0;loopvar<existingarray.size();loopvar++)
		{
			if(existingarray.get(loopvar).getCouponId().equals(coupon_id)&&existingarray.get(loopvar).getBehaviour().equals(behaviour) )
			{
				int count= Integer.parseInt(existingarray.get(loopvar).getCouponquantity());
				count=count+Integer.parseInt(coupon_quantity);
				Deleteitem(existingarray.get(loopvar).getId());
				coupon_quantity=count+"";
				if(availablecount<count) {
					coupon_quantity = availablecount + "";
					exceededlimmit=true;
				}
				Log.v("Delete",exceededlimmit+"  "+availablecount+ "  "+count+"  "+coupon_quantity);
			}
		}

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( ApplicationConstants.KEY_couponid, coupon_id.trim());
		values.put( ApplicationConstants.KEY_couponname, coupon_name.trim());
		values.put( ApplicationConstants.KEY_couponcost, coupon_cost.trim());
		values.put( ApplicationConstants.KEY_coupondiscount, coupon_discount);
		values.put( ApplicationConstants.KEY_couponquantity, coupon_quantity.trim());
		values.put( ApplicationConstants.KEY_coupondescription, coupon_description);
		values.put( ApplicationConstants.KEY_couponimage, coupon_image.trim());
		values.put( ApplicationConstants.KEY_behaviour, behaviour.trim());
		values.put( ApplicationConstants.KEY_availableCount, availablecount);

		db.insert( ApplicationConstants.TABLE_NAME, null, values);
		db.close(); // Closing database connection
		return exceededlimmit;
	}







	public void addCoupon1(String coupon_id,String coupon_name, String coupon_cost,String coupon_discount,String coupon_quantity,String coupon_description,String coupon_image,String behaviour) {
		Log.v("Delete",coupon_id+"    "+coupon_name+"    "+ coupon_cost+"    "+coupon_discount+"    "+coupon_quantity+"    "+coupon_description+"    "+ coupon_image+"    "+ behaviour);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( ApplicationConstants.KEY_couponid, coupon_id.trim());
		values.put( ApplicationConstants.KEY_couponname, coupon_name.trim());
		values.put( ApplicationConstants.KEY_couponcost, coupon_cost.trim());
		values.put( ApplicationConstants.KEY_coupondiscount, coupon_discount);
		values.put( ApplicationConstants.KEY_couponquantity, coupon_quantity.trim());
		values.put( ApplicationConstants.KEY_coupondescription, coupon_description);
		values.put( ApplicationConstants.KEY_couponimage, coupon_image.trim());
		values.put( ApplicationConstants.KEY_behaviour, behaviour.trim());

		db.insert( ApplicationConstants.TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	/*Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_category, KEY_title }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}*/

	// Getting All Coupons

	public ArrayList<post_structure> getAllCoupons() {
		ArrayList<post_structure> couponList = new ArrayList<post_structure>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " +  ApplicationConstants.TABLE_NAME+" order by "+ApplicationConstants.KEY_ID+" DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				post_structure poststructure_obj = new post_structure();
				poststructure_obj.setId(Integer.parseInt(cursor.getString(0)));
				Log.v("DElete"," ghf "+Integer.parseInt(cursor.getString(0)));
				poststructure_obj.setCouponId(cursor.getString(1).trim());
				poststructure_obj.setCouponname(cursor.getString(2).trim());
				poststructure_obj.setCouponcost(cursor.getString(3).trim());
				poststructure_obj.setCoupondiscount(cursor.getString(4).trim());
				poststructure_obj.setCouponquantity(cursor.getString(5).trim());
				poststructure_obj.setCoupondescription(cursor.getString(6).trim());
				poststructure_obj.setCouponimage(cursor.getString(7).trim());
				poststructure_obj.setBehaviour(cursor.getString(8).trim());
				poststructure_obj.setAvailablecount(Integer.parseInt(cursor.getString(9).trim()));
				// Adding coupon to list
				couponList.add(poststructure_obj);
			} while (cursor.moveToNext());
		}
		db.close();
		// return coupon list
		return couponList;
	}

	public int getcartsize()
	{
		return getAllCoupons().size();
	}


	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(ApplicationConstants.TABLE_NAME, null, null);
		db.close();
	}
	public void Deleteitem(int selectionid) {
		Log.v("DElete"," "+selectionid);
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete( ApplicationConstants.TABLE_NAME,  ApplicationConstants.KEY_ID + " = ?",
				new String[] { ""+selectionid });
		db.close();
	}
}
