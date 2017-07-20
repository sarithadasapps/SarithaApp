package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

public class ApplicationUtils {

	public static boolean dialogstatus = false;

	@SuppressWarnings("deprecation")
	public static void showAlertDialog(Activity activity, String title,
			String msg) {
		AlertDialog alert = new AlertDialog.Builder(activity).create();
		alert.setTitle(title);
		alert.setMessage(msg);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		alert.show();
	}

	@SuppressWarnings("deprecation")
	public static void showAlertDialog(Activity activity, String title,
			String msg, int icon) {
		AlertDialog alert = new AlertDialog.Builder(activity).create();
		alert.setTitle(title);
		alert.setMessage(msg);
		alert.setIcon(icon);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		alert.show();
	}

	public static void setdialogstatus(boolean val) {

		dialogstatus = val;
	}

	public boolean alertdialogwithresult(Context context, String Title,
			String message, String Posbutname, String Negbuttext) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(Title);
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(Posbutname,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								setdialogstatus(true);
								dialog.cancel();
							}
						})
				.setNegativeButton(Negbuttext,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								setdialogstatus(false);
								dialog.cancel();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return dialogstatus;
	}


	public boolean checknetwork(Context contextname) {
		boolean Status;
		ConnectivityManager connectivityManager = (ConnectivityManager) contextname.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			Status = connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
			Status = !false;
		} catch (Exception e) { Status = false; }
		return Status;
	}
	public String getsharedpreferencesString(Context contextname,String sharedprefname,String fieldname)
	{
		SharedPreferences sh_Pref = contextname.getSharedPreferences(sharedprefname,Context.MODE_PRIVATE);
		return sh_Pref.getString(fieldname, "");

	}
	public String getsharedpreferencesString(Context contextname,String sharedprefname,String fieldname,String defaultValue)
	{
		SharedPreferences sh_Pref = contextname.getSharedPreferences(sharedprefname,Context.MODE_PRIVATE);
		return sh_Pref.getString(fieldname, defaultValue);
	}

	public void setsharedpreferencesString(Context contextname,String sharedprefname,String fieldname,String fieldvalue )
	{
		SharedPreferences sp=contextname.getSharedPreferences(sharedprefname,Context.MODE_PRIVATE);
		SharedPreferences.Editor toEdit=sp.edit();
		toEdit.putString(fieldname,fieldvalue);
		toEdit.commit();


	}

	private static final int SECOND_MILLIS = 1000;
	private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
	private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
	private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

	public static String getTimeAgo(long time) {
		if (time < 1000000000000L) {
			// if timestamp given in seconds, convert to millis
			time *= 1000;
		}

		long now = System.currentTimeMillis();
		if (time > now || time <= 0) {
			return null;
		}

		final long diff = now - time;
		if (diff < MINUTE_MILLIS) {
			return "just now";
		} else if (diff < 2 * MINUTE_MILLIS) {
			return "a minute ago";
		} else if (diff < 50 * MINUTE_MILLIS) {
			return diff / MINUTE_MILLIS + " minutes ago";
		} else if (diff < 90 * MINUTE_MILLIS) {
			return "an hour ago";
		} else if (diff < 24 * HOUR_MILLIS) {
			return diff / HOUR_MILLIS + " hours ago";
		} else if (diff < 48 * HOUR_MILLIS) {
			return "yesterday";
		} else {
			return diff / DAY_MILLIS + " days ago";
		}
	}

}
