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

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapterDonor extends ArrayAdapter<Product> {

    ArrayList<Product> products;
    Context context;
    int resource;
    Typeface fontAwesomeFont;

    public CustomListAdapterDonor(Context context, int resource, ArrayList<Product> products) {
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
            convertView = layoutInflater.inflate(R.layout.user_list_item, null, true);

        }
        Product product = getItem(position);

        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        CircleImageView imageView = (CircleImageView) convertView.findViewById(R.id.grp);
        String pic = product.getPicture();
        if(!pic.equals("")) {
            Picasso.with(context).load(pic).into(imageView);
        }

        try {
            TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
            String address = product.getName();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            txtName.setText(add);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}