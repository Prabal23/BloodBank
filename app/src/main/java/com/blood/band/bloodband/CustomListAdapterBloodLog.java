package com.blood.band.bloodband;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapterBloodLog extends ArrayAdapter<ProductBloodLog> {

    ArrayList<ProductBloodLog> products;
    Context context;
    int resource;
    Typeface fontAwesomeFont;

    public CustomListAdapterBloodLog(Context context, int resource, ArrayList<ProductBloodLog> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.log_list_item1, null, true);

        }
        ProductBloodLog product = getItem(position);
        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

        String date = getContext().getResources().getString(R.string.date);
        try {
            TextView day = (TextView) convertView.findViewById(R.id.day);
            TextView month = (TextView) convertView.findViewById(R.id.month);
            TextView year = (TextView) convertView.findViewById(R.id.year);
            String address = product.getDate();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            /*TextView datestring = (TextView) convertView.findViewById(R.id.date);
            datestring.setText(add);*/
            String[] tarikh = add.split("-");
            String d = tarikh[2];
            String m = tarikh[1];
            String y = tarikh[0];
            if(d.startsWith("0")){
                d = d.replace("0", "");
            }
            d = d.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            day.setText(d);
            if (m.equals("01")) {
                month.setText("জানু");
            }
            if (m.equals("02")) {
                month.setText("ফেব্রু");
            }
            if (m.equals("03")) {
                month.setText("মার্চ");
            }
            if (m.equals("04")) {
                month.setText("এপ্রিল");
            }
            if (m.equals("05")) {
                month.setText("মে");
            }
            if (m.equals("06")) {
                month.setText("জুন");
            }
            if (m.equals("07")) {
                month.setText("ঞ্জুলা");
            }
            if (m.equals("08")) {
                month.setText("আগস্ট");
            }
            if (m.equals("09")) {
                month.setText("সেপ্ট");
            }
            if (m.equals("10")) {
                month.setText("অক্টো");
            }
            if (m.equals("11")) {
                month.setText("নভে");
            }
            if (m.equals("12")) {
                month.setText("ডিসে");
            }
            y = y.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            year.setText(y);
            TextView drop = (TextView) convertView.findViewById(R.id.drop);
            drop.setTypeface(fontAwesomeFont);
            /*TextView date_logo = (TextView) convertView.findViewById(R.id.date_logo);
            date_logo.setTypeface(fontAwesomeFont);
            TextView place = (TextView) convertView.findViewById(R.id.place);
            place.setTypeface(fontAwesomeFont);*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            String address = product.getIssue();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtPrice.setText("রোগীর সমস্যা - "+add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
            String address = product.getPlace();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtAdd.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}