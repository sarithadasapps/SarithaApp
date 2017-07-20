package com.prog.logicprog.petdeal.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prog.logicprog.petdeal.Activity.Events;
import com.prog.logicprog.petdeal.Listitem_Structures.Feedcontent_events;
import com.prog.logicprog.petdeal.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;

/**
 * Created by logicprog on 6/5/2017.
 */

public class RecyclerViewAdapter_Events extends RecyclerView.Adapter<RecyclerViewAdapter_Events.SimpleViewHolder> {
    private static final String TAG = "RecyclerViewAdapter_Events";
    ArrayList<Feedcontent_events> feedsarray;
    View view;
    Activity context;
    String date;
    String eventStatus, eventid;

    /**
     * Created by Shiva
     * Constructor to load the complete details of the student.
     * Parameters Activity,Content ,List of studentsDB,SelectionIndex,Exam Map Array,Message MapArray,FeeNotification Map Array,ResultNotification Map Array
     */
    public RecyclerViewAdapter_Events(Activity context, ArrayList<Feedcontent_events> feedsarray) {
        this.context = context;
        this.feedsarray = feedsarray;
    }

    @Override
    public RecyclerViewAdapter_Events.SimpleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_listitem, parent, false);
        return new RecyclerViewAdapter_Events.SimpleViewHolder(view);
        //return new SimpleViewHolder(view);
    }

    /**
     * Created by Shiva
     * View Holder class containing all the elements of view for each student
     */

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Typeface montserrat = Typeface.createFromAsset(context.getAssets(), "Montserrat-Regular.ttf");
        Typeface proximanova = Typeface.createFromAsset(context.getAssets(), "proximanova-regular-webfont.ttf");

        holder.participents_tv.setTypeface(proximanova);
        holder.hideinfo_tv.setTypeface(proximanova);
        holder.moreinfo_ll.setVisibility(GONE);
        holder.moreinfo_tv.setVisibility(View.VISIBLE);
        holder.moreinfo_tv.setTypeface(proximanova);

        holder.moreinfo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.moreinfo_tv.setVisibility(GONE);
                holder.moreinfo_ll.setVisibility(View.VISIBLE);
            }
        });
        holder.hideinfo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.moreinfo_tv.setVisibility(View.VISIBLE);
                holder.moreinfo_ll.setVisibility(View.GONE);
            }
        });
        holder.isbooked = "" + feedsarray.get(position).getIsbooked();
        if (holder.isbooked.equals("YES")) {
            holder.booked_iv.setVisibility(View.VISIBLE);
            holder.bookevent_btn.setText("CANCEL");

        } else {
            holder.bookevent_btn.setText("BOOK");
            holder.booked_iv.setVisibility(View.GONE);
        }

        holder.bookevent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feedsarray.get(position).getIsbooked().equals("YES")) {
                    AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
                    b.setMessage("Do you want to cancel the Event?");
                    b.setCancelable(false);
                    b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eventid=""+feedsarray.get(position).getId();
                            eventStatus = "REJECTED";
                            //events.cancelEvent(context,eventid);
                            ((Events)context).cancelEvent(context,eventid);
                        }
                    });
                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = b.create();
                    alert.show();

                } else {
                    eventid=""+feedsarray.get(position).getId();
                    eventStatus = "ACCEPTED";
                    ((Events)context).bookEvent(context,eventid);

                }


            }
        });

        holder.conductedby.setTypeface(proximanova);
        holder.conductedby.setText("Conducted By : " + feedsarray.get(position).getConductedby());

        holder.location_tv.setTypeface(proximanova);
        holder.location_tv.setText(feedsarray.get(position).getEventlocation());

        holder.description_title.setTypeface(proximanova);

        holder.description_short.setTypeface(proximanova);
        holder.description_short.setText(feedsarray.get(position).getDescription());

        holder.description_short.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                holder.eventdescription_tv.setVisibility(View.VISIBLE);
                holder.description_short.setVisibility(GONE);
            }
        });

        holder.eventdescription_tv.setTypeface(proximanova);
        holder.eventdescription_tv.setText(feedsarray.get(position).getDescription());

        holder.eventdescription_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.description_short.setVisibility(View.VISIBLE);
                holder.eventdescription_tv.setVisibility(GONE);
            }
        });

        holder.eventcost.setTypeface(montserrat);
        holder.eventcost.setText("₹" + feedsarray.get(position).getEventcost());

        holder.date_tv.setTypeface(proximanova);

        String date = feedsarray.get(position).getEventdate();
        String[] date_array = date.split(" ");
        String date_new = date_array[0];
        String time = date_array[1];
        String dateformat = changeDateFormat("yyyy-mm-dd", "dd-mm-yyyy", date_new);
        holder.date_tv.setText(dateformat + " " + time);

        holder.eventname.setTypeface(montserrat);
        holder.eventname.setText(feedsarray.get(position).getEventname());

        holder.participentname.setTypeface(proximanova);
        holder.participentname.setText(feedsarray.get(position).getParticipent());


        Picasso.with(context)
                .load(feedsarray.get(position).getImagepath())
                .resize(450, 250)
                .centerCrop()
                .into(holder.eventimage);

    }

    @Override
    public int getItemCount() {
        return feedsarray.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        String isbooked;
        ImageView eventimage, booked_iv;
        TextView location_tv, date_tv, eventname, participentname, eventcost,
                conductedby, eventdescription_tv, description_short, description_title, moreinfo_tv, hideinfo_tv, participents_tv;
        LinearLayout moreinfo_ll;
        Button bookevent_btn;

        public SimpleViewHolder(View convertView) {
            super(convertView);

            booked_iv = (ImageView) convertView.findViewById(R.id.booked_iv);
            participents_tv = (TextView) convertView.findViewById(R.id.participents_tv);
            location_tv = (TextView) convertView.findViewById(R.id.location_tv);
            description_short = (TextView) convertView.findViewById(R.id.description_short);
            conductedby = (TextView) convertView.findViewById(R.id.conductedby);
            description_title = (TextView) convertView.findViewById(R.id.description_title);
            eventdescription_tv = (TextView) convertView.findViewById(R.id.eventdescription_tv);
            eventcost = (TextView) convertView.findViewById(R.id.eventcost);
            date_tv = (TextView) convertView.findViewById(R.id.date_tv);
            eventname = (TextView) convertView.findViewById(R.id.eventname);
            participentname = (TextView) convertView.findViewById(R.id.participentname);
            eventimage = (ImageView) convertView.findViewById(R.id.eventimage);

            hideinfo_tv = (TextView) convertView.findViewById(R.id.hideinfo_tv);

            moreinfo_ll = (LinearLayout) convertView.findViewById(R.id.moreinfo_ll);

            moreinfo_tv = (TextView) convertView.findViewById(R.id.moreinfo_tv);
            bookevent_btn = (Button) convertView.findViewById(R.id.bookevent_btn);

        }
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

}

