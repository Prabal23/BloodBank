package com.blood.band.bloodband;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pranto on 8/24/2017.
 */

public class InformationPage extends AppCompatActivity {

    private TextView names, degree, des, con, chamber, phone, mail, sms, edit, delete, completion, statuses, ok, addicon;
    private String pic, url, address, ticket, phn, bd, imglength;
    private CircleImageView image;
    private ImageView menu, back, loco;
    Context cntx;
    private DatabaseHandler db;
    private String f_name, f_add, f_grp, f_email, f_phn, id;
    private dataAdapter data;
    private Contact dataModel;
    ProgressBar progressBar;
    LinearLayout add_to_donor_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

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
        TextView next = (TextView) findViewById(R.id.next_donate);
        next.setTypeface(fontAwesomeFont);
        TextView total = (TextView) findViewById(R.id.total);
        total.setTypeface(fontAwesomeFont);
        TextView bdate = (TextView) findViewById(R.id.bd);
        bdate.setTypeface(fontAwesomeFont);
        ok = (TextView) findViewById(R.id.ok_notok);
        ok.setTypeface(fontAwesomeFont);

        edit = (TextView) findViewById(R.id.edit);
        edit.setTypeface(fontAwesomeFont);
        delete = (TextView) findViewById(R.id.delete);
        delete.setTypeface(fontAwesomeFont);
        progressBar = (ProgressBar) findViewById(R.id.pgbAwardProgress);
        completion = (TextView) findViewById(R.id.completion);
        statuses = (TextView) findViewById(R.id.statuses);
        addicon = (TextView) findViewById(R.id.add);
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

        String dateInString = getIntent().getStringExtra("date");
        String[] dates = dateInString.split(" ");
        String ld = dates[0];
        //Toast.makeText(getBaseContext(), ld, Toast.LENGTH_SHORT).show();
        bd = dates[1];
        //Toast.makeText(getBaseContext(), bd, Toast.LENGTH_SHORT).show();
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
        if (ld.equals("na") || bd.equals("na")) {
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
                dateInString = dateInString.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
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
            dateInString = dateInString.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
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

        TextView n_donate = (TextView) findViewById(R.id.n_donate);
        TextView status = (TextView) findViewById(R.id.status);

        //Toast.makeText(this, ld, Toast.LENGTH_SHORT).show();
        long diff = c1.getTimeInMillis() - c.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String dd = String.valueOf(diffDays);
        dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        if (diffDays < 0) {
            //n_donate.setText("Available From "+nextdate);
            n_donate.setTextColor(Color.parseColor("#8b8a8a"));
            status.setText("রক্তদানের জন্য উপলব্ধ আছেন");
            status.setTextColor(Color.parseColor("#006400"));
        } else {
            //n_donate.setText("Available After : "+nextdate);
            n_donate.setTextColor(Color.parseColor("#8b8a8a"));
            if (dd.equals("০")) {
                status.setText("আগামীকাল থেকে রক্তদান করতে পারবেন");
                status.setTextColor(Color.parseColor("#FF8C00"));
            } else {
                status.setText(dd + " দিন পর রক্তদান করতে পারবেন");
                status.setTextColor(Color.parseColor("#FF8C00"));
            }
        }
        if (ld.equals("০০০০-০০-০০") || ld.equals("na")) {
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
        String name = getIntent().getStringExtra("name");
        //final String name1 = getIntent().getStringExtra("name");
        if (name.contains("%20")) {
            name = name.replace("%20", " ");
        }
        final String name2 = name;
        final String grp = getIntent().getStringExtra("grp");
        des.setText(grp);
        id = getIntent().getStringExtra("id");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete alert1 = new Delete();
                alert1.showDialog(InformationPage.this, "");
            }
        });

        //id = id.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");

        //pic = getIntent().getStringExtra("img");
        final Bitmap myBitmap = (Bitmap) getIntent().getParcelableExtra("img");
        //final Bitmap myBitmap = BitmapFactory.decodeFile(pic);
        //Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
        image = (CircleImageView) findViewById(R.id.image);
        image.setImageBitmap(myBitmap);
        //Glide.with(this).load(pic).into(image);

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

        address = getIntent().getStringExtra("add");
        //final String address1 = getIntent().getStringExtra("add");
        if (address.contains("%20")) {
            address = address.replace("%20", " ");
        }
        final String address2 = address;
        byte[] b2 = new byte[0];
        try {
            b2 = address.getBytes("UTF-8");
            String link = new String(b2, "UTF-8");
            chamber.setText(link);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //des.setText(name);
        names.setText(name);

        ticket = getIntent().getStringExtra("phone");
        f_phn = getIntent().getStringExtra("phone");
        ticket = ticket.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        con.setText(ticket);

        LinearLayout stts = (LinearLayout) findViewById(R.id.stts);
        imglength = getIntent().getStringExtra("imglength");
        if (url.equals("") && ld.equals("na") && imglength.equals("3206")) {
            progressBar.setProgress(62);
            statuses.setText("ছবি, রক্তদানের তথ্য এবং ইমেইল দেওয়া হয়নি");
            completion.setText("৬২%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (ld.equals("na") && imglength.equals("3206")) {
            progressBar.setProgress(75);
            statuses.setText("রক্তদানের তথ্য এবং ছবি দেওয়া হয়নি");
            completion.setText("৭৫%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (url.equals("") && imglength.equals("3206")) {
            progressBar.setProgress(75);
            statuses.setText("ইমেইল এবং ছবি দেওয়া হয়নি");
            completion.setText("৭৫%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (url.equals("") && ld.equals("na")) {
            progressBar.setProgress(75);
            statuses.setText("রক্তদানের তথ্য এবং ইমেইল দেওয়া হয়নি");
            completion.setText("৭৫%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (url.equals("")) {
            progressBar.setProgress(87);
            statuses.setText("ইমেইল দেওয়া হয়নি");
            completion.setText("৮৭%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (ld.equals("na")) {
            progressBar.setProgress(87);
            statuses.setText("রক্তদানের তথ্য দেওয়া হয়নি");
            completion.setText("৮৭%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else if (imglength.equals("3206")) {
            progressBar.setProgress(87);
            statuses.setText("ছবি দেওয়া হয়নি");
            completion.setText("৮৭%");
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.pending));
            ok.setTextColor(Color.parseColor("#FF8C00"));
        } else {
            progressBar.setProgress(100);
            statuses.setText("সম্পূর্ন");
            completion.setText("১০০%");
            completion.setTextColor(Color.parseColor("#006400"));
            ok.setVisibility(View.VISIBLE);
            ok.setText(getResources().getString(R.string.ok));
            ok.setTextColor(Color.parseColor("#006400"));
            stts.setVisibility(View.GONE);
        }

        phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialContactPhone(f_phn);
            }
        });

        add_to_donor_list = (LinearLayout) findViewById(R.id.add_to_donor_list);
        add_to_donor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDonor alert1 = new AddDonor();
                alert1.showDialog(InformationPage.this, "");
            }
        });

        final String finalDateInString = dateInString;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalId = id;
                String finalDate = finalDateInString;
                Bitmap bit = myBitmap;
                String nameuser = name2;
                Intent intent = new Intent(getBaseContext(), MyDonorEdit.class);
                intent.putExtra("id", finalId);
                intent.putExtra("date", finalDate);
                intent.putExtra("name", nameuser);
                intent.putExtra("add", address2);
                intent.putExtra("grp", grp);
                intent.putExtra("email", url);
                intent.putExtra("phone", f_phn);
                intent.putExtra("img", bit);
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"prabalbhatt2@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "রক্ত দিয়ে সাহায্য করুন");
                email.putExtra(Intent.EXTRA_TEXT, "জনাব/জনাবা,\n\n");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "ইমেইলের মাধ্যম বাছাই করুন..."));
            }
        });

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

                AlertDialog.Builder builder = new AlertDialog.Builder(InformationPage.this);
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

    public class Delete {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.log_delete);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button yes = (Button) dialog.findViewById(R.id.yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int i = Integer.parseInt(id);
                    db.deleteContact(i);
                    Intent intent = new Intent(getBaseContext(), MyDonor.class);
                    startActivity(intent);
                    finish();
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