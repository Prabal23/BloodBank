package com.blood.band.bloodband;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Visit website http://www.whats-online.info
 * **/

public class dataAdapter extends ArrayAdapter<Contact>{

    Context context;
    ArrayList<Contact> mcontact;


    public dataAdapter(Context context, ArrayList<Contact> contact){
        super(context, R.layout.mydonor_list_item, contact);
        this.context=context;
        this.mcontact=contact;
    }

    public  class  Holder{
        TextView name, add, grp, status;
        CircleImageView pic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Contact data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.mydonor_list_item, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.add = (TextView) convertView.findViewById(R.id.txtPrice);
            viewHolder.grp = (TextView) convertView.findViewById(R.id.grp);
            viewHolder.status = (TextView) convertView.findViewById(R.id.txtAdd);
            viewHolder.pic = (CircleImageView) convertView.findViewById(R.id.image);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(c.getTime());

        String dateInString = data.getDATE();
        String[] dates = dateInString.split(" ");
        String ld = dates[0];
        String bd = dates[1];
        //Toast.makeText(context, ld, Toast.LENGTH_SHORT).show();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(df2.parse(ld));
            c1.add(Calendar.DATE, 90);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String nextdate = df2.format(c1.getTime());

        long diff = c1.getTimeInMillis() - c.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String dd = String.valueOf(diffDays);
        dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");

        String n = data.getFName();
        if(n.contains("%20")){
            n=n.replace("%20", " ");
        }
        String a = data.getFAdd();
        if(a.contains("%20")){
            a=a.replace("%20", " ");
        }
        viewHolder.name.setText(n);
        viewHolder.grp.setText(data.getFGrp());
        viewHolder.add.setText(a);
        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));
        if(diffDays < 0) {
            viewHolder.status.setText("রক্তদানের জন্য উপলব্ধ আছেন");
            viewHolder.status.setTextColor(Color.parseColor("#006400"));
        }else{
            if(dd.equals("০")){
                viewHolder.status.setText("আগামীকাল থেকে রক্তদান করতে পারবেন");
                viewHolder.status.setTextColor(Color.parseColor("#FF8C00"));
            }else{
                viewHolder.status.setText(dd+" দিন পর রক্তদান করতে পারবেন");
                viewHolder.status.setTextColor(Color.parseColor("#FF8C00"));
            }
        }
        if(ld.equals("na")){
            viewHolder.status.setText("রক্তদানের তথ্য উপলব্ধ নয়");
            viewHolder.status.setTextColor(Color.parseColor("#404040"));
        }
        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}

