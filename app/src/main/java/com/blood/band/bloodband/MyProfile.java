package com.blood.band.bloodband;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    private String grp, loc, user, Phones;
    private ImageView menu, back, loco, error;
    private ProgressBar progres;
    private TextView alert, jega, group, erroralert, number, badge;
    private TextView ok, statuses, names, degree, des, con, chamber, phone, mail, sms, edit, delete, completion;
    private String username, rate = "", picture = "", interest, counter, pass, id, res, name, contact, gender, email, address, bd, ld, gp, upazila, division, district, subdistrict, bdate, lastdate, phoneno;
    ArrayList<ProductUserEdit> arrayList;
    Typeface fontAwesomeFont;
    ProgressBar progressBar, progressBar1;
    CircleImageView profile_pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.myprofile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Links.EMAIL_SHARED_PREF)) {
            res = (sharedPreferences).getString(Links.EMAIL_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        pass = result[1];
        //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();
        username = result[2];

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        profile_pic = (CircleImageView) findViewById(R.id.image);
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
        ok = (TextView) findViewById(R.id.ok_notok);
        ok.setTypeface(fontAwesomeFont);
        TextView ok = (TextView) findViewById(R.id.ok_notok2);
        ok.setTypeface(fontAwesomeFont);
        TextView notok = (TextView) findViewById(R.id.ok_notok1);
        notok.setTypeface(fontAwesomeFont);

        TextView name = (TextView) findViewById(R.id.name);
        String n = name.getText().toString();
        edit = (TextView) findViewById(R.id.edit);
        edit.setTypeface(fontAwesomeFont);
        delete = (TextView) findViewById(R.id.delete);
        delete.setTypeface(fontAwesomeFont);

        progressBar = (ProgressBar) findViewById(R.id.pgbAwardProgress);
        completion = (TextView) findViewById(R.id.completion);
        statuses = (TextView) findViewById(R.id.statuses);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.VISIBLE);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setVisibility(View.GONE);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/vsdfasisiasdft_repoasdfrt_fosdfasdfr_eddfasdit/923hd?user_id=" + id);
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
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
                /*Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progres.setVisibility(View.GONE);
                alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("user_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductUserEdit(
                            productObject.getString("donors_id"),
                            productObject.getString("donors_name"),
                            productObject.getString("donors_contact"),
                            productObject.getString("donors_gender"),
                            productObject.getString("donors_email"),
                            productObject.getString("donors_division"),
                            productObject.getString("donors_district"),
                            productObject.getString("donors_upazila"),
                            productObject.getString("present_address"),
                            productObject.getString("birth_date"),
                            productObject.getString("last_donation_date"),
                            productObject.getString("main_img"),
                            productObject.getString("interest"),
                            productObject.getString("counter"),
                            productObject.getString("group_code"),
                            productObject.getString("name"),
                            productObject.getString("rating")
                    ));
                    name = arrayList.get(i).getName();
                    contact = arrayList.get(i).getPhn();
                    phoneno = arrayList.get(i).getPhn();
                    gender = arrayList.get(i).getGender();
                    email = arrayList.get(i).getMail();
                    division = arrayList.get(i).getDivid();
                    district = arrayList.get(i).getDisid();
                    subdistrict = arrayList.get(i).getUpaid();
                    address = arrayList.get(i).getAddress();
                    bd = arrayList.get(i).getBirth();
                    bdate = arrayList.get(i).getBirth();
                    ld = arrayList.get(i).getLastDonate();
                    lastdate = arrayList.get(i).getLastDonate();
                    picture = arrayList.get(i).getPic();
                    interest = arrayList.get(i).getInterest();
                    counter = arrayList.get(i).getCounter();
                    gp = arrayList.get(i).getGroup();
                    upazila = arrayList.get(i).getUpazila();
                    rate = arrayList.get(i).getRating();
                }
                if (!picture.equals("")) {
                    progressBar1.setVisibility(View.VISIBLE);
                    //Picasso.with(MyProfile.this).load(picture).into(profile_pic);
                    //Glide.with(MyProfile.this).load(picture).into(profile_pic);
                    Picasso.with(MyProfile.this)
                            .load(picture)
                            .into(new Target() {

                                @Override
                                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                    profile_pic.setImageBitmap(bitmap);
                                    progressBar1.setVisibility(View.GONE);
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                }
                            });
                }
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

                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c1 = Calendar.getInstance();
                try {
                    c1.setTime(df2.parse(ld));
                    c1.add(Calendar.DATE, 90);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView rating = (TextView) findViewById(R.id.txtrate);
                RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBarSmall);
                float ratingbar = Float.parseFloat(rate);
                ratingBar1.setRating(ratingbar);
                rate = rate.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                rating.setText("(" + rate + ")");
                if (rate.equals("০")) {
                    rating.setVisibility(View.GONE);
                }

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
                chamber = (TextView) findViewById(R.id.chamber);
                //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                gp = gp.replace("(", "").replace(")", "");
                des.setText(gp);
                TextView gen = (TextView) findViewById(R.id.gender);
                if (gender.equals("1")) {
                    gen.setText("পুরুষ");
                }
                if (gender.equals("2")) {
                    gen.setText("নারী");
                }
                //id = id.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");

                byte[] b1 = new byte[0];
                try {
                    b1 = email.getBytes("UTF-8");
                    String link = new String(b1, "UTF-8");
                    degree.setText(link);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                byte[] b2 = new byte[0];
                byte[] b3 = new byte[0];
                try {
                    b2 = address.getBytes("UTF-8");
                    String link = new String(b2, "UTF-8");
                    b3 = upazila.getBytes("UTF-8");
                    String link1 = new String(b3, "UTF-8");
                    chamber.setText(link + ", " + link1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //des.setText(name);
                names.setText(name);
                Phones = contact;
                contact = contact.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                con.setText(contact);

                counter = counter.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                int cc = Integer.valueOf(counter);
                //Toast.makeText(MyProfile.this, counter, Toast.LENGTH_SHORT).show();
                TextView total_count = (TextView) findViewById(R.id.t_donation);
                if (counter.equals("০")) {
                    total_count.setText("এখনো রক্তদান করেননি");
                } else {
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

                TextView ok_not = (TextView) findViewById(R.id.ok_not);
                ok_not.setTypeface(fontAwesomeFont);
                if (interest.equals("1")) {
                    ok_not.setText(getResources().getString(R.string.ok));
                    ok_not.setTextColor(Color.parseColor("#006400"));
                } else {
                    ok_not.setText(getResources().getString(R.string.wrong));
                    ok_not.setTextColor(Color.parseColor("#e83d3d"));
                }
                //Toast.makeText(MyProfile.this, picture, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MyProfile.this, ld, Toast.LENGTH_SHORT).show();
                LinearLayout st = (LinearLayout) findViewById(R.id.st);
                if (picture.equals("") && email.equals("") && ld.equals("০০০০-০০-০০")) {
                    progressBar.setProgress(70);
                    completion.setText("৭০%");
                    statuses.setText("ছবি, রক্তদানের তথ্য এবং ইমেইল দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (picture.equals("") && ld.equals("০০০০-০০-০০")) {
                    progressBar.setProgress(80);
                    completion.setText("৮০%");
                    statuses.setText("ছবি এবং রক্তদানের তথ্য দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (picture.equals("") && email.equals("")) {
                    progressBar.setProgress(80);
                    completion.setText("৮০%");
                    statuses.setText("ছবি এবং ইমেইল দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (ld.equals("০০০০-০০-০০") && email.equals("")) {
                    progressBar.setProgress(80);
                    completion.setText("৮০%");
                    statuses.setText("রক্তদানের তথ্য এবং ইমেইল দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (picture.equals("")) {
                    progressBar.setProgress(90);
                    completion.setText("৯০%");
                    statuses.setText("ছবি দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (ld.equals("০০০০-০০-০০")) {
                    progressBar.setProgress(90);
                    completion.setText("৯০%");
                    statuses.setText("রক্তদানের তথ্য দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else if (email.equals("")) {
                    progressBar.setProgress(90);
                    completion.setText("৯০%");
                    statuses.setText("ইমেইল দেওয়া হয়নি");
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.pending));
                    ok.setTextColor(Color.parseColor("#FF8C00"));
                } else {
                    progressBar.setProgress(100);
                    //progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#006400")));
                    completion.setText("১০০%");
                    completion.setTextColor(Color.parseColor("#006400"));
                    statuses.setText("সব তথ্য দেওয়া হয়েছে");
                    st.setVisibility(View.GONE);
                    ok.setVisibility(View.VISIBLE);
                    ok.setText(getResources().getString(R.string.ok));
                    ok.setTextColor(Color.parseColor("#006400"));
                }
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Settings alert1 = new Settings();
                        alert1.showDialog(MyProfile.this, "");
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
        }
    }

    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent1 = new Intent(getBaseContext(), MyDonor.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_notifications:
                    /*Intent intent2 = new Intent(getBaseContext(), MyProfile.class);
                    startActivity(intent2);
                    finish();*/
                    return true;
            }
            return false;
        }

    };

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    public class Settings {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.profile_settings);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView log = (TextView) dialog.findViewById(R.id.log);
            log.setTypeface(fontAwesomeFont);
            LinearLayout l1 = (LinearLayout) dialog.findViewById(R.id.l1);
            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), LogReport.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });

            TextView edit = (TextView) dialog.findViewById(R.id.edit);
            edit.setTypeface(fontAwesomeFont);
            LinearLayout l2 = (LinearLayout) dialog.findViewById(R.id.l2);
            if (names.getText().toString().equals("Name")) {
                l2.setClickable(false);
                l1.setClickable(false);
            } else {
                l2.setClickable(true);
                l1.setClickable(true);
            }
            l2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), ProfileEdit.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("address", address);
                    intent.putExtra("gp", gp);
                    intent.putExtra("ld", lastdate);
                    intent.putExtra("bd", bdate);
                    intent.putExtra("gender", gender);
                    intent.putExtra("division", division);
                    intent.putExtra("district", district);
                    intent.putExtra("subdistrict", subdistrict);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phoneno);
                    intent.putExtra("interest", interest);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            });

            TextView picedit = (TextView) dialog.findViewById(R.id.picedit);
            picedit.setTypeface(fontAwesomeFont);
            LinearLayout l4 = (LinearLayout) dialog.findViewById(R.id.l4);
            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), PicChange.class);
                    intent.putExtra("id", id);
                    intent.putExtra("pic", picture);
                    startActivity(intent);
                }
            });

            TextView passedit = (TextView) dialog.findViewById(R.id.passedit);
            passedit.setTypeface(fontAwesomeFont);
            LinearLayout l5 = (LinearLayout) dialog.findViewById(R.id.l5);
            l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PassChange alert1 = new PassChange();
                    alert1.showDialog(MyProfile.this, "");
                }
            });

            TextView stetho = (TextView) dialog.findViewById(R.id.stetho);
            stetho.setTypeface(fontAwesomeFont);
            LinearLayout l6 = (LinearLayout) dialog.findViewById(R.id.l6);
            l6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), HealthInfo.class);
                    startActivity(intent);
                }
            });

            TextView logout = (TextView) dialog.findViewById(R.id.logout);
            logout.setTypeface(fontAwesomeFont);
            LinearLayout l3 = (LinearLayout) dialog.findViewById(R.id.l3);
            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logout alert1 = new Logout();
                    alert1.showDialog(MyProfile.this, "");
                }
            });

            dialog.show();
        }
    }

    public class PassChange {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.password_change);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final TextInputLayout oldpass = (TextInputLayout) dialog.findViewById(R.id.input_pass);
            final TextInputLayout newpass = (TextInputLayout) dialog.findViewById(R.id.input_pass1);
            final TextInputLayout newconpass = (TextInputLayout) dialog.findViewById(R.id.input_pass2);
            final EditText passold = (EditText) dialog.findViewById(R.id.sign_pass);
            final EditText passnew = (EditText) dialog.findViewById(R.id.sign_pass1);
            final EditText passnewcon = (EditText) dialog.findViewById(R.id.sign_pass2);

            TextView forgot_pass = (TextView) dialog.findViewById(R.id.forgot_pass);
            forgot_pass.setPaintFlags(forgot_pass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            forgot_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PassRecovery alert1 = new PassRecovery();
                    alert1.showDialog(MyProfile.this, "পাসওয়ার্ড পুনরুদ্ধার করুন");
                    dialog.dismiss();
                }
            });

            Button submit = (Button) dialog.findViewById(R.id.member);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String old_pass = passold.getText().toString();
                    String new_pass = passnew.getText().toString();
                    String con_new_pass = passnewcon.getText().toString();

                    if (old_pass.equals("")) {
                        Toast.makeText(MyProfile.this, "আপনার পুরাতন পাসওয়ার্ডটি লিখুন", Toast.LENGTH_SHORT).show();
                    } else if (!old_pass.equals(pass)) {
                        Toast.makeText(MyProfile.this, "আপনার পাসওয়ার্ডটি সঠিক হয়নি", Toast.LENGTH_SHORT).show();
                    } else if (new_pass.equals("")) {
                        Toast.makeText(MyProfile.this, "আপনার নতুন পাসওয়ার্ডটি লিখুন", Toast.LENGTH_SHORT).show();
                    } else if (con_new_pass.equals("")) {
                        Toast.makeText(MyProfile.this, "আপনার পাসওয়ার্ডটি নিশ্চিত করুন", Toast.LENGTH_SHORT).show();
                    } else if (!new_pass.equals(con_new_pass)) {
                        Toast.makeText(MyProfile.this, "আপনার পাসওয়ার্ডটি নিশ্চিত করা যায়নি", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(MyProfile.this, "OK!", Toast.LENGTH_SHORT).show();
                        HttpClient Client = new DefaultHttpClient();

                        String URL = "http://www.bloodband.dhost247.net/direct/cdsgsdfghangedsfg_psdgfasssdfgdfgwordsdfgsd/923hd?user_id=" + id + "&old_pass=" + old_pass + "&new_pass=" + new_pass;
                        //Toast.makeText(this, URL, Toast.LENGTH_LONG).show();
                        String SetServerString = "";

                        HttpGet httpget = new HttpGet(URL);
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        try {
                            SetServerString = Client.execute(httpget, responseHandler);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences preferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Links.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Links.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MyProfile.this, LoginProfile.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                }
            });
            dialog.show();
        }
    }

    public class PassRecovery {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.password_recovery);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText(msg);

            TextInputLayout name = (TextInputLayout) dialog.findViewById(R.id.input_pass);
            TextInputLayout newpass = (TextInputLayout) dialog.findViewById(R.id.input_pass1);
            final EditText username = (EditText) dialog.findViewById(R.id.sign_pass);
            final EditText passnew = (EditText) dialog.findViewById(R.id.sign_pass1);

            Button submit = (Button) dialog.findViewById(R.id.member);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = username.getText().toString();
                    String pass = passnew.getText().toString();
                    HttpClient Client = new DefaultHttpClient();

                    String URL = "http://www.bloodband.dhost247.net/direct/fodfrsdfgesdft_pasdfassasdfwroasdfasdfd/923hd?username=" + user + "&new_pass=" + pass;
                    //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
                    String SetServerString = "";

                    HttpGet httpget = new HttpGet(URL);
                    HttpResponse response = null;
                    try {
                        response = Client.execute(httpget);
                        HttpEntity httpEntity = response.getEntity();
                        String res = EntityUtils.toString(httpEntity);
                        //Toast.makeText(MyProfile.this, res, Toast.LENGTH_SHORT).show();
                        if (res.contains("User Name Not Matched")) {
                            Toast.makeText(MyProfile.this, "ইউজারনেম মিলেনি!", Toast.LENGTH_LONG).show();
                        } else {
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            try {
                                SetServerString = Client.execute(httpget, responseHandler);
                                //Toast.makeText(BeMember.this, SetServerString, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences preferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Getting editor
                            SharedPreferences.Editor editor = preferences.edit();

                            //Puting the value false for loggedin
                            editor.putBoolean(Links.LOGGEDIN_SHARED_PREF, false);

                            //Putting blank value to email
                            editor.putString(Links.EMAIL_SHARED_PREF, "");

                            //Saving the sharedpreferences
                            editor.commit();

                            finish();
                            //Starting login activity
                            Intent intent = new Intent(MyProfile.this, MainActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            dialog.show();
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class Logout {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.logout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button official = (Button) dialog.findViewById(R.id.official);
            official.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //contact.setText(msg3);
            Button personal = (Button) dialog.findViewById(R.id.personal);
            personal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Links.LOGGEDIN_SHARED_PREF, false);

                    //Putting blank value to email
                    editor.putString(Links.EMAIL_SHARED_PREF, "");

                    //Saving the sharedpreferences
                    editor.commit();

                    //Starting login activity
                    Intent intent = new Intent(MyProfile.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
