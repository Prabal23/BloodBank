package com.blood.band.bloodband;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> products;
    Context context;
    int resource;
    Typeface fontAwesomeFont;

    public CustomListAdapter(Context context, int resource, ArrayList<Product> products) {
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
            convertView = layoutInflater.inflate(R.layout.list_item, null, true);

        }
        Product product = getItem(position);

        final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
        String pic = product.getPicture();
        final CircleImageView image = (CircleImageView)convertView.findViewById(R.id.imageViewProduct);
        if(!pic.equals("")){
            //Picasso.with(context).load(pic).into(image);
            Picasso.with(context)
                    .load(pic)
                    .into(new Target() {

                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            /* Save the bitmap or do something with it here */

                            // Set it in the ImageView
                            image.setImageBitmap(bitmap);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }
                    });
        }else{
            progressBar.setVisibility(View.GONE);
        }

        fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

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
        //Toast.makeText(context, dd, Toast.LENGTH_SHORT).show();
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
            TextView gp = (TextView) convertView.findViewById(R.id.gp);
            String address = product.getGroup();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            add = add.replace("(", "").replace(")", "");
            gp.setText(add);
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
            //RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBarSmall);
            TextView rate = (TextView) convertView.findViewById(R.id.txtrate);
            String address = product.getRating();
            if(address.equals("0")){
                rate.setVisibility(View.GONE);
            }
            //Toast.makeText(context, address, Toast.LENGTH_SHORT).show();
            RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBarSmall);
            float rating = Float.parseFloat(address);
            ratingBar.setRating(rating);
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            add = add.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            rate.setText("("+add+")");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            TextView txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
            String address = product.getPhn();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            if(diffDays < 0) {
                txtAdd.setText("রক্তদানের জন্য উপলব্ধ আছেন");
                txtAdd.setTextColor(Color.parseColor("#006400"));
            }else{
                if(dd.equals("০")){
                    txtAdd.setText("আগামীকাল থেকে রক্তদান করতে পারবেন");
                    txtAdd.setTextColor(Color.parseColor("#FF8C00"));
                }else{
                    txtAdd.setText(dd+" দিন পর রক্তদান করতে পারবেন");
                    txtAdd.setTextColor(Color.parseColor("#FF8C00"));
                }
            }if(dateInString.equals("0000-00-00")){
                txtAdd.setText("রক্তদানের তথ্য উপলব্ধ নয়");
                txtAdd.setTextColor(Color.parseColor("#404040"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        TextView txtphn = (TextView) convertView.findViewById(R.id.txtphn);
        txtphn.setText(product.getPhn());

        TextView status = (TextView) convertView.findViewById(R.id.ok_notok);
        String interest = product.getInterest();
        if(interest.equals("1")){
            status.setText(getContext().getResources().getString(R.string.ok));
            status.setTextColor(Color.parseColor("#006400"));
            status.setTypeface(fontAwesomeFont);
        }else{
            status.setText(getContext().getResources().getString(R.string.wrong));
            status.setTextColor(Color.parseColor("#e83d3d"));
            status.setTypeface(fontAwesomeFont);
        }

        try {
            TextView medals = (TextView) convertView.findViewById(R.id.medals);
            TextView badge = (TextView) convertView.findViewById(R.id.badge);
            badge.setTypeface(fontAwesomeFont);
            String address = product.getCounter();
            byte[] b1 = address.getBytes("UTF-8");
            String add = new String(b1, "UTF-8");
            int cc = Integer.valueOf(add);
            if (cc == 0) {
                badge.setVisibility(View.GONE);
                medals.setVisibility(View.GONE);
            }
            if (cc >= 1 && cc <= 10) {
                //Toast.makeText(this, ""+cc, Toast.LENGTH_SHORT).show();
                badge.setVisibility(View.VISIBLE);
                badge.setText(getContext().getResources().getString(R.string.medal));
                badge.setTextColor(Color.parseColor("#CD7F32"));
                medals.setVisibility(View.VISIBLE);
                medals.setText("(ব্রোঞ্জ)");
                medals.setTextColor(Color.parseColor("#CD7F32"));
            }
            if (cc >= 11 && cc <= 19) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(getContext().getResources().getString(R.string.medal));
                badge.setTextColor(Color.parseColor("#C0C0C0"));
                medals.setVisibility(View.VISIBLE);
                medals.setText("(সিলভার)");
                medals.setTextColor(Color.parseColor("#C0C0C0"));
            }
            if (cc >= 20) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(getContext().getResources().getString(R.string.medal));
                badge.setTextColor(Color.parseColor("#FFD700"));
                medals.setVisibility(View.VISIBLE);
                medals.setText("(গোল্ড)");
                medals.setTextColor(Color.parseColor("#FFD700"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}