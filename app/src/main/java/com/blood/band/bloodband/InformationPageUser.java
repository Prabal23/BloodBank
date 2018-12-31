package com.blood.band.bloodband;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pranto on 8/24/2017.
 */

public class InformationPageUser extends AppCompatActivity {

    private TextView names, degree, des, con, chamber, phone, mail, sms, edit, delete, badge, jonmodin, roktodan;
    private String pic, url, address, ticket, phn, bd, rating;
    private CircleImageView image;
    private ImageView menu, back, loco;
    Context cntx;
    private DatabaseHandler db;
    private String f_name, f_add, f_grp, f_email, f_phn;
    private dataAdapter data;
    private Contact dataModel;
    private String donate, donate_bng, don_date, b_date_bng, b_date, birth, combine, birthdate, donatedate;
    private byte[] photo;
    Bitmap bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_all);

        db = new DatabaseHandler(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        final TextView add = (TextView) findViewById(R.id.address);
        add.setTypeface(fontAwesomeFont);
        TextView contact = (TextView) findViewById(R.id.contact);
        contact.setTypeface(fontAwesomeFont);
        final TextView email = (TextView) findViewById(R.id.email);
        email.setTypeface(fontAwesomeFont);
        TextView last = (TextView) findViewById(R.id.last_donate);
        last.setTypeface(fontAwesomeFont);
        TextView next = (TextView) findViewById(R.id.gen);
        next.setTypeface(fontAwesomeFont);
        TextView total = (TextView) findViewById(R.id.total);
        total.setTypeface(fontAwesomeFont);
        TextView bdate = (TextView) findViewById(R.id.bd);
        bdate.setTypeface(fontAwesomeFont);
        TextView addicon = (TextView) findViewById(R.id.add);
        addicon.setTypeface(fontAwesomeFont);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(c.getTime());
        String[] tday = today.split("-");
        String ty = tday[0];
        int ty1 = Integer.parseInt(ty);
        String tm = tday[1];
        int tm1 = Integer.parseInt(tm);
        String td = tday[2];
        int td1 = Integer.parseInt(td);
        cntx = this;
        StringBuilder strBuild = new StringBuilder();

        TextView rate = (TextView) findViewById(R.id.txtrate);
        rating = getIntent().getStringExtra("rating");
        String rating1 = getIntent().getStringExtra("rating");
        rating = rating.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        rate.setText("(" + rating + ")");
        if (rating.equals("০")) {
            rate.setVisibility(View.GONE);
        }
        RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBarSmall);
        float ratingbar = Float.parseFloat(rating1);
        ratingBar1.setRating(ratingbar);

        Uri uris = null;
        URL urls = null;
        String pic = getIntent().getStringExtra("pic");
        //Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
        final CircleImageView profile_img = (CircleImageView) findViewById(R.id.image);
        if (!pic.equals("")) {
            //Picasso.with(InformationPageUser.this).load(pic).into(profile_img);
            /*uris = Uri.parse(pic);
            bp = decodeUri(uris, 100);*/
            Picasso.with(this)
                    .load(pic)
                    .into(new Target() {

                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            // Set it in the ImageView
                            profile_img.setImageBitmap(bitmap);
                            bp = bitmap;
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }
                    });
            //Glide.with(MyProfile.this).load(picture).into(profile_pic);
        } else {
            uris = Uri.parse("android.resource://com.blood.band.bloodband/drawable/profiles");
            if (uris != null) {
                bp = decodeUri(uris, 100);
            }
        }

        String interest = getIntent().getStringExtra("interest");
        //ProgressBar progressBar = (ProgressBar)findViewById(R.id.pgbAwardProgress);
        String ld = getIntent().getStringExtra("ld");
        //Toast.makeText(getBaseContext(), ld, Toast.LENGTH_SHORT).show();
        bd = getIntent().getStringExtra("bd");
        //Toast.makeText(getBaseContext(), bd, Toast.LENGTH_SHORT).show();
        donatedate = getIntent().getStringExtra("ld");
        birthdate = getIntent().getStringExtra("bd");
        combine = donatedate + " " + birthdate;
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(df2.parse(ld));
            c1.add(Calendar.DATE, 90);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Toast.makeText(cntx, bd, Toast.LENGTH_SHORT).show();
        TextView birth = (TextView) findViewById(R.id.birth_date);
        if (ld.equals("na") || ld.equals("")) {
            if (ld.equals("na")) {
                TextView l_donate = (TextView) findViewById(R.id.l_donate);
                l_donate.setText("সর্বশেষ রক্তদান");
            } else {
                ld = ld.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                TextView l_donate = (TextView) findViewById(R.id.l_donate);
                l_donate.setText("সর্বশেষ রক্তদান : " + ld);
            }

            if (bd.equals("na")) {
                birth.setText("জন্ম তারিখ");
                TextView t_donate = (TextView) findViewById(R.id.t_donate);
                t_donate.setText("বয়স");
            } else {
                String[] ddd = bd.split("-");
                String y = ddd[0];
                int y1 = Integer.parseInt(y);
                String m = ddd[1];
                int m1 = Integer.parseInt(m);
                String d = ddd[2];
                int d1 = Integer.parseInt(d);
                bd = bd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                Calendar dateOfYourBirth = new GregorianCalendar(y1, m1, d1);
                int yourAge = c.get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR);
                Calendar cc = Calendar.getInstance();
                try {
                    cc.setTime(df2.parse(bd));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateOfYourBirth.add(Calendar.YEAR, yourAge);
                if (c.get(Calendar.DAY_OF_YEAR) < cc.get(Calendar.DAY_OF_YEAR)) {
                    yourAge--;
                }
                String age = String.valueOf(yourAge);
                age = age.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                //Toast.makeText(cntx, "You are " + yourAge + " old!", Toast.LENGTH_SHORT).show();
                //strBuild.append("You are " + yourAge + " old!");

                int byear = ty1 - y1;
                int bmonth = 0;
                if (tm1 < m1) {
                    byear--;
                    tm1 = tm1 + 12;
                    bmonth = tm1 - m1;
                } else {
                    bmonth = tm1 - m1;
                }
                int bday = 0;
                if (td1 < d1) {
                    bmonth--;
                    td1 = td1 + 31;
                    bday = td1 - d1;
                } else {
                    bday = td1 - d1;
                }
                String year = String.valueOf(byear);
                year = year.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                String month = String.valueOf(bmonth);
                month = month.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                String day = String.valueOf(bday);
                day = day.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                String nextdate = df2.format(c1.getTime());
                bd = bd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                birth.setText("জন্ম তারিখ : " + bd);
                TextView t_donate = (TextView) findViewById(R.id.t_donate);
                t_donate.setText("বয়স : " + year + " বছর " + month + " মাস " + day + " দিন");
            }
        } else {
            String[] ddd = bd.split("-");
            String y = ddd[0];
            int y1 = Integer.parseInt(y);
            String m = ddd[1];
            int m1 = Integer.parseInt(m);
            String d = ddd[2];
            int d1 = Integer.parseInt(d);
            bd = bd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            Calendar dateOfYourBirth = new GregorianCalendar(y1, m1, d1);
            int yourAge = c.get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR);
            Calendar cc = Calendar.getInstance();
            try {
                cc.setTime(df2.parse(bd));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateOfYourBirth.add(Calendar.YEAR, yourAge);
            if (c.get(Calendar.DAY_OF_YEAR) < cc.get(Calendar.DAY_OF_YEAR)) {
                yourAge--;
            }
            String age = String.valueOf(yourAge);
            age = age.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            //Toast.makeText(cntx, "You are " + yourAge + " old!", Toast.LENGTH_SHORT).show();
            //strBuild.append("You are " + yourAge + " old!");

            int byear = ty1 - y1;
            int bmonth = 0;
            if (tm1 < m1) {
                byear--;
                tm1 = tm1 + 12;
                bmonth = tm1 - m1;
            } else {
                bmonth = tm1 - m1;
            }
            int bday = 0;
            if (td1 < d1) {
                bmonth--;
                td1 = td1 + 31;
                bday = td1 - d1;
            } else {
                bday = td1 - d1;
            }
            String year = String.valueOf(byear);
            year = year.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            String month = String.valueOf(bmonth);
            month = month.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            String day = String.valueOf(bday);
            day = day.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            String nextdate = df2.format(c1.getTime());
            bd = bd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            birth.setText("জন্ম তারিখ : " + bd);
            TextView t_donate = (TextView) findViewById(R.id.t_donate);
            t_donate.setText("বয়স : " + year + " বছর " + month + " মাস " + day + " দিন");
            ld = ld.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            TextView l_donate = (TextView) findViewById(R.id.l_donate);
            l_donate.setText("সর্বশেষ রক্তদান : " + ld);
        }

        TextView status = (TextView) findViewById(R.id.status);

        long diff = c1.getTimeInMillis() - c.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String dd = String.valueOf(diffDays);
        dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        if (diffDays < 0) {
            //n_donate.setText("Available From "+nextdate);
            status.setText("রক্তদানের জন্য উপলব্ধ আছেন");
            status.setTextColor(Color.parseColor("#006400"));
        } else {
            if (dd.equals("০")) {
                status.setText("আগামীকাল থেকে রক্তদান করতে পারবেন");
                status.setTextColor(Color.parseColor("#FF8C00"));
            } else {
                status.setText(dd + " দিন পর রক্তদান করতে পারবেন");
                status.setTextColor(Color.parseColor("#FF8C00"));
            }
        }
        if (ld.equals("০০০০-০০-০০")) {
            status.setText("রক্তদানের তথ্য উপলব্ধ নয়");
            status.setTextColor(Color.parseColor("#404040"));
            TextView l_donate = (TextView) findViewById(R.id.l_donate);
            l_donate.setText("সর্বশেষ রক্তদান");
        }

        names = (TextView) findViewById(R.id.name);
        degree = (TextView) findViewById(R.id.degree);
        des = (TextView) findViewById(R.id.des);
        con = (TextView) findViewById(R.id.consult);
        phone = (TextView) findViewById(R.id.phone);
        phone.setTypeface(fontAwesomeFont);
        phone.setPaintFlags(phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        chamber = (TextView) findViewById(R.id.chamber);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        final String name = getIntent().getStringExtra("name");
        String grp = getIntent().getStringExtra("gp");
        grp = grp.replace("(", "").replace(")", "");
        des.setText(grp);
        final String id = getIntent().getStringExtra("id");
        final String gender = getIntent().getStringExtra("gender");
        TextView gen = (TextView) findViewById(R.id.gender);
        if (gender.equals("1")) {
            gen.setText("পুরুষ");
        }
        if (gender.equals("2")) {
            gen.setText("নারী");
        }
        //id = id.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        TextView available = (TextView) findViewById(R.id.ok_notok);
        available.setTypeface(fontAwesomeFont);
        if (interest.equals("1")) {
            available.setText(getResources().getString(R.string.ok));
            available.setTextColor(Color.parseColor("#006400"));
        } else {
            available.setText(getResources().getString(R.string.wrong));
            available.setTextColor(Color.parseColor("#e83d3d"));
        }

        TextView ok = (TextView) findViewById(R.id.ok_notok2);
        ok.setTypeface(fontAwesomeFont);
        TextView notok = (TextView) findViewById(R.id.ok_notok1);
        notok.setTypeface(fontAwesomeFont);

        String counter = getIntent().getStringExtra("count");
        int cc = Integer.valueOf(counter);
        //Toast.makeText(this, "" + cc, Toast.LENGTH_SHORT).show();
        TextView total_count = (TextView) findViewById(R.id.t_donation);
        if (counter.equals("0")) {
            counter = counter.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            total_count.setText("এখনো রক্তদান করেননি");
        } else {
            counter = counter.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            total_count.setText(counter + " বার রক্ত দিয়েছেন");
        }
        TextView count = (TextView) findViewById(R.id.award);
        count.setTypeface(fontAwesomeFont);
        badge = (TextView) findViewById(R.id.badge);
        badge.setTypeface(fontAwesomeFont);
        TextView medals = (TextView) findViewById(R.id.medals);
        //badge.setVisibility(View.VISIBLE);
        if (cc == 0) {
            badge.setVisibility(View.GONE);
            medals.setVisibility(View.GONE);
        }
        if (cc >= 1 && cc <= 10) {
            //Toast.makeText(this, ""+cc, Toast.LENGTH_SHORT).show();
            badge.setVisibility(View.VISIBLE);
            badge.setText(getResources().getString(R.string.medal));
            badge.setTextColor(Color.parseColor("#CD7F32"));
            medals.setVisibility(View.VISIBLE);
            medals.setText("(ব্রোঞ্জ)");
            medals.setTextColor(Color.parseColor("#CD7F32"));
        }
        if (cc >= 11 && cc <= 19) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(getResources().getString(R.string.medal));
            badge.setTextColor(Color.parseColor("#C0C0C0"));
            medals.setVisibility(View.VISIBLE);
            medals.setText("(সিলভার)");
            medals.setTextColor(Color.parseColor("#C0C0C0"));
        }
        if (cc >= 20) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(getResources().getString(R.string.medal));
            badge.setTextColor(Color.parseColor("#FFD700"));
            medals.setVisibility(View.VISIBLE);
            medals.setText("(গোল্ড)");
            medals.setTextColor(Color.parseColor("#FFD700"));
        }

        mail = (TextView) findViewById(R.id.mail);
        mail.setTypeface(fontAwesomeFont);
        mail.setPaintFlags(mail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        sms = (TextView) findViewById(R.id.sms);
        sms.setTypeface(fontAwesomeFont);
        sms.setPaintFlags(sms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        url = getIntent().getStringExtra("email");

        byte[] b1 = new byte[0];
        try {
            b1 = url.getBytes("UTF-8");
            String link = new String(b1, "UTF-8");
            degree.setText(link);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        address = getIntent().getStringExtra("address");
        String address_upazila = getIntent().getStringExtra("upazila");
        byte[] b2 = new byte[0];
        byte[] b3 = new byte[0];
        try {
            b2 = address.getBytes("UTF-8");
            String link = new String(b2, "UTF-8");
            b3 = address_upazila.getBytes("UTF-8");
            String link1 = new String(b3, "UTF-8");
            chamber.setText(link + ", " + link1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //des.setText(name);
        names.setText(name);

        ticket = getIntent().getStringExtra("contact");
        f_phn = getIntent().getStringExtra("contact");
        ticket = ticket.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        con.setText(ticket);

        jonmodin = (TextView) findViewById(R.id.birth_date);
        roktodan = (TextView) findViewById(R.id.l_donate);
        LinearLayout add_to_donor_list = (LinearLayout) findViewById(R.id.add_to_donor_list);
        add_to_donor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDonor alert1 = new AddDonor();
                alert1.showDialog(InformationPageUser.this, "");
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialContactPhone(f_phn);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] mail = {url};
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, mail);
                email.putExtra(Intent.EXTRA_SUBJECT, "রক্ত দিয়ে সাহায্য করুন");
                email.putExtra(Intent.EXTRA_TEXT, "জনাব/জনাবা,\n\n");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "ইমেইলের মাধ্যম বাছাই করুন..."));
            }
        });
        if (url.equals("")) {
            mail.setClickable(false);
        }

        sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                phn = con.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + f_phn));
                intent.putExtra("address", f_phn);
                intent.putExtra("sms_body", "");
                intent.setType("vnd.android-dir/mms-sms");
                startActivity(intent);
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(InformationPageUser.this);
                //builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            //*Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
                            //startActivityForResult(myIntent, 0);//*
                            Toast.makeText(getBaseContext(), "ডেভোলাপার", Toast.LENGTH_LONG).show();
                        }

                        if (item == 1) {
                            Toast.makeText(getBaseContext(), "রেটিং দিন", Toast.LENGTH_LONG).show();
                            //*Intent myIntent = new Intent(view.getContext(), ListItemActivity2.class);
                            //startActivityForResult(myIntent, 0);//*
                        }

                        if (item == 2) {
                            Toast.makeText(getBaseContext(), "শেয়ার করুন", Toast.LENGTH_LONG).show();
                            //*Intent myIntent = new Intent(view.getContext(), ListItemActivity1.class);
                            //startActivityForResult(myIntent, 0);//*
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        back = (ImageView) findViewById(R.id.backing);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public class AddDonor {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.add_donor_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button yes = (Button) dialog.findViewById(R.id.yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    roktodan = (TextView) findViewById(R.id.l_donate);
                    String ld = roktodan.getText().toString();
                    if (ld.equals("সর্বশেষ রক্তদান")) {
                        donate = "na";
                    } else {
                        donate = donatedate;
                    }
                    jonmodin = (TextView) findViewById(R.id.birth_date);
                    String bd = jonmodin.getText().toString();
                    String bdd;
                    if (bd.equals("জন্ম তারিখ")) {
                        bdd = "na";
                    } else {
                        bdd = birthdate;
                    }
                    combine = donate + " " + bdd;
                    f_name = names.getText().toString();
                    if (f_name.contains(" ")) {
                        f_name = f_name.replace(" ", "%20");
                    }
                    //Toast.makeText(InformationPageUser.this, f_name, Toast.LENGTH_SHORT).show();
                    f_add = chamber.getText().toString();
                    if (f_add.contains(" ")) {
                        f_add = f_add.replace(" ", "%20");
                    }
                    f_grp = des.getText().toString();
                    photo = profileImage(bp);
                    /*Toast.makeText(InformationPageUser.this, f_name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(InformationPageUser.this, f_add, Toast.LENGTH_SHORT).show();
                    Toast.makeText(InformationPageUser.this, combine, Toast.LENGTH_SHORT).show();
                    Toast.makeText(InformationPageUser.this, f_grp, Toast.LENGTH_SHORT).show();
                    Toast.makeText(InformationPageUser.this, url, Toast.LENGTH_SHORT).show();
                    Toast.makeText(InformationPageUser.this, f_phn, Toast.LENGTH_SHORT).show();*/
                    db.addContacts(new Contact(combine, f_name, f_add, f_grp, url, f_phn, photo));
                    Toast.makeText(getApplicationContext(), "সফলভাবে তালিকাভূক্ত করা হয়েছে", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            Button no = (Button) dialog.findViewById(R.id.no);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent1 = new Intent(getBaseContext(), MyDonor.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent2 = new Intent(getBaseContext(), LoginProfile.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };
}