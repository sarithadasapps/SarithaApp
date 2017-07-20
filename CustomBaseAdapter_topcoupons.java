package com.prog.logicprog.petdeal.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prog.logicprog.petdeal.Listitem_Structures.Feedcontent_topcoupons;
import com.prog.logicprog.petdeal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomBaseAdapter_topcoupons extends BaseAdapter {
    Context context;
    ArrayList<Feedcontent_topcoupons> feedsarray;
    int selectedposition;


    public CustomBaseAdapter_topcoupons(Context context, ArrayList<Feedcontent_topcoupons> feedsarray, int selectedposition) {
        this.context = context;
        this.feedsarray = feedsarray;
        this.selectedposition = selectedposition;
    }

    /* private view holder class */
    private class ViewHolder {
        CircleImageView topcoupons_image;
        TextView servicecoupons_couponname_tv, servicecoupons_discountrange_tv,
                detailcoupon_expiresin_tv, detailcoupon_expiretime_tv, servicecoupons_cost_tv,servicecoupons_onsale_tv;
        LinearLayout listitem_ll;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface montserrat = Typeface.createFromAsset(context.getAssets(), "Montserrat-Regular.ttf");
        Typeface Aaargh_font = Typeface.createFromAsset(context.getAssets(), "Aaargh.ttf");
        Typeface proximanova = Typeface.createFromAsset(context.getAssets(), "proximanova-regular-webfont.ttf");


        String  discounttype, date, imagepath;
        int expiry_days;


        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.topcoupons_listitem, null);
        ViewHolder holder = new ViewHolder();
        holder.detailcoupon_expiretime_tv = (TextView) convertView.findViewById(R.id.detailcoupon_expiretime_tv);
        holder.detailcoupon_expiresin_tv = (TextView) convertView.findViewById(R.id.detailcoupon_expiresin_tv);
        //  holder.servicecoupons_cost_tv = (TextView) convertView.findViewById(R.id.servicecoupons_cost_tv);
        holder.servicecoupons_couponname_tv = (TextView) convertView.findViewById(R.id.servicecoupons_couponname_tv);
        holder.servicecoupons_discountrange_tv = (TextView) convertView.findViewById(R.id.servicecoupons_discountrange_tv);
        holder.topcoupons_image = (CircleImageView) convertView.findViewById(R.id.topcoupons_image);
        // holder.topcoupons_image.setVisibility(View.GONE);
        holder.servicecoupons_couponname_tv.setTypeface(montserrat);
        // holder.servicecoupons_discountrange_tv.setTypeface(proximanova);
        holder.detailcoupon_expiretime_tv.setTypeface(proximanova);
        holder.detailcoupon_expiresin_tv.setTypeface(proximanova);
        //  holder.servicecoupons_cost_tv.setTypeface(proximanova);

        holder.servicecoupons_onsale_tv = (TextView) convertView.findViewById(R.id.servicecoupons_onsale_tv);
        int availableQuantity= Integer.parseInt(feedsarray.get(position).getAvailablecoupons());
        if (availableQuantity>0) {
            holder.servicecoupons_onsale_tv.setText("On Sale");
            holder.servicecoupons_onsale_tv.setTextColor(Color.GREEN);
        }else{
            holder.servicecoupons_onsale_tv.setText("Out of Stock");
            holder.servicecoupons_onsale_tv.setTextColor(Color.RED);
        }
        imagepath = feedsarray.get(position).getCouponimage();
       /* imageLoader=new ImageLoader(context);
        imageLoader.DisplayImage( feedsarray.get(position).getCouponimage(),holder.topcoupons_image);*/

       /* Picasso.with(context)
                .load(feedsarray.get(position).getCouponimage())
                .resize(200, 200)
                .centerCrop()
                .into(holder.topcoupons_image);*/

        holder.servicecoupons_couponname_tv.setText(feedsarray.get(position).getCouponname());
        discounttype = feedsarray.get(position).getDiscounttype();
        if (discounttype.equals("percentage")) {
            holder.servicecoupons_discountrange_tv.setText(feedsarray.get(position).getDiscountrange() + "% off");
        } else {
            holder.servicecoupons_discountrange_tv.setText("₹" + feedsarray.get(position).getDiscountrange() + " off");
        }
        //  holder.servicecoupons_cost_tv.setText("₹"+feedsarray.get(position).getCost());
        expiry_days = Integer.parseInt(feedsarray.get(position).getExpirydays());
        if (expiry_days < 0) {
            holder.detailcoupon_expiretime_tv.setText("Expired!");
        } else {
            date = feedsarray.get(position).getExpiretime();
            String[] time = date.split(" ");
            String date1 = time[0];
            holder.detailcoupon_expiretime_tv.setText(changeDateFormat("yyyy-MM-dd", "dd-MMM-yyyy", date1));
        }

        holder.listitem_ll = (LinearLayout) convertView.findViewById(R.id.listitem_ll);
        /*if (position%2==0){
            holder.listitem_ll.setBackgroundColor(Color.parseColor("#efffffff"));
        }*/
        /*if (selectedposition==position){
            holder.listitem_ll.setBackgroundColor(Color.parseColor("#e9cccccc"));
        }*/

       /* String str = feedsarray.get(position).getMoviename();
        if (str.length() > 40) {
            str = str.substring(0, 40) + " ...";
        }*/
       /* holder.txtTitle.setText("" + str);



        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(feedsarray.get(position).getRelease_date());
            // Log.v("Sample", getTimeAgo(date.getTime()));
          //  holder.txtdate.setText(getTimeAgo(date.getTime()));
        }catch (Exception e){
            Log.v("Sample", "sqlDate:" + e);
        }
*/


        return convertView;
    }

    private String changeDateFormat(String currentFormat, String requiredFormat, String dateString) {
        String result = "";
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Log.v("details2", "" + e.toString());
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }

    public int getCount() {
        return feedsarray.size();
    }

    public Object getItem(int position) {
        return feedsarray.get(position);
    }

    public long getItemId(int position) {
        //return feedsarray.indexOf(getItem(position));
        return position;
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