package com.blood.band.bloodband;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
public class CustomListAdapter1 extends ArrayAdapter<Product> {

    ArrayList<Product> products;
    Context context;
    int resource;

    public CustomListAdapter1(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.mydonor_list_item, null, true);

        }
        Product product = getItem(position);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(c.getTime());

        String dateInString = product.getLastDonate();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(df2.parse(dateInString));
            c1.add(Calendar.DATE, 90);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String nextdate = df2.format(c1.getTime());

        long diff = c1.getTimeInMillis() - c.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String dd = String.valueOf(diffDays);
        dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProduct);
        //Picasso.with(context).load(product.getImage()).into(imageView);

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
            TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            String address = product.getAddress();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtPrice.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
            String address = product.getPhn();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            if(diffDays < 0) {
                txtAdd.setText("রক্তদানের জন্য উপলব্ধ আছেন *");
                txtAdd.setTextColor(Color.parseColor("#006400"));
            }else{
                txtAdd.setText(dd+" দিন পর রক্তদান করতে পারবেন");
                txtAdd.setTextColor(Color.parseColor("#FF8C00"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        TextView txtphn = (TextView) convertView.findViewById(R.id.txtphn);
        txtphn.setText(product.getPhn());
        return convertView;
    }
}