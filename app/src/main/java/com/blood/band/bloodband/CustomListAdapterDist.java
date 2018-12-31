package com.blood.band.bloodband;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CustomListAdapterDist extends ArrayAdapter<ProductDist> {

    ArrayList<ProductDist> products;
    Context context;
    int resource;
    private TextView taskid, taskname, proname, taskdet, enddate, status;
    View view;
    Typeface fontAwesomeFont;

    public CustomListAdapterDist(Context context, int resource, ArrayList<ProductDist> products) {
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
            convertView = layoutInflater.inflate(R.layout.dist_list_item, null, true);

        }
        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        TextView loc = (TextView) convertView.findViewById(R.id.loc);
        loc.setTypeface(fontAwesomeFont);
        taskid = (TextView) convertView.findViewById(R.id.id);
        ProductDist product = getItem(position);
        String id = product.getID();
        taskid.setText(id);

        try {
            taskname = (TextView) convertView.findViewById(R.id.txtName);
            String address = product.getName();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            taskname.setText(add);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}