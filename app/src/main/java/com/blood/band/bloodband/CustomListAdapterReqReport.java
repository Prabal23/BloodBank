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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapterReqReport extends ArrayAdapter<ProductBloodReqReport> {

    ArrayList<ProductBloodReqReport> products;
    Context context;
    int resource;
    Typeface fontAwesomeFont;

    public CustomListAdapterReqReport(Context context, int resource, ArrayList<ProductBloodReqReport> products) {
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
            convertView = layoutInflater.inflate(R.layout.blood_req_list_item, null, true);

        }
        ProductBloodReqReport product = getItem(position);
        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        TextView drop = (TextView) convertView.findViewById(R.id.last_donate);
        drop.setTypeface(fontAwesomeFont);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setTypeface(fontAwesomeFont);
        TextView place = (TextView) convertView.findViewById(R.id.place);
        place.setTypeface(fontAwesomeFont);

        try {
            TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
            String address = product.getName();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtName.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView gp = (TextView) convertView.findViewById(R.id.grp);
            TextView urgent = (TextView) convertView.findViewById(R.id.urgent);
            String address = product.getUpazila();
            String ur = product.getType();
            //Toast.makeText(context, ur, Toast.LENGTH_SHORT).show();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            add = add.replace("(", "").replace(")", "");
            gp.setText(add);
            if (ur.equals("2") || ur.equals("0")){
                urgent.setVisibility(View.GONE);
            }if (ur.equals("1")){
                urgent.setVisibility(View.VISIBLE);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            String address = product.getPhn();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            //add = add.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            txtPrice.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
            String address = product.getAddress();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtAdd.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}