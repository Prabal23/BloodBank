package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BloodResult extends AppCompatActivity {

    ArrayList<Product> arrayList;
    ArrayList<ProductCache> arrayListcache;
    ArrayList<ProductDist> arrayList1, arrayList2;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, grpid, loc, subloc, user;
    private ImageView menu, back, loco, error;
    private ProgressBar progres;
    private TextView alert, jega, subjega, group, erroralert, number;
    private String answer, jelaa, jela, url, res = "", gurup;
    Typeface fontAwesomeFont;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_result);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        arrayListcache = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.VISIBLE);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        TextView ok = (TextView) findViewById(R.id.ok_notok);
        ok.setTypeface(fontAwesomeFont);
        TextView notok = (TextView) findViewById(R.id.ok_notok1);
        notok.setTypeface(fontAwesomeFont);

        grp = getIntent().getStringExtra("Group");
        grpid = getIntent().getStringExtra("GroupID");
        loc = getIntent().getStringExtra("Location");
        subloc = getIntent().getStringExtra("SubLocation");

        subjega = (TextView) findViewById(R.id.subjega);
        //subjega.setText(subloc);
        jega = (TextView) findViewById(R.id.jega);
        //jega.setText(loc);
        group = (TextView) findViewById(R.id.grouping);
        group.setText(grp);
        number = (TextView) findViewById(R.id.number);
        number.setTypeface(fontAwesomeFont);
        user = getResources().getString(R.string.user);

        //Toast.makeText(this, loc, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, subloc, Toast.LENGTH_SHORT).show();
        if (loc.equals("")) {
            jega.setText("জেলা");
        }
        if (subloc.equals("")) {
            subjega.setText("উপজেলা");
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                answer = "Wifi এর সাথে সংযুক্ত রয়েছে";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc);
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONDistrict().execute("http://www.bloodband.dhost247.net/direct/asdfdasdfadsistridfasdfct_asdfasdfasddflist/293hsdk");
                        //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONSubDistrict().execute("http://www.bloodband.dhost247.net/direct/dsfupaasdfzila_dasfaasdfgainasdfsdfstadsfasdfdisasdftasdfrict/923hd?district_id=" + loc);
                        //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                    }
                });

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();
                                SharedPreferences sharedPreferences = BloodResult.this.getSharedPreferences(Links.SHARED_URL_DONOR, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Links.URL_SHARED_PREF, true);
                                editor.putString(Links.URL_DONOR_SHARED_PREF, result);
                                //editor.putString(Config.NAME_SHARED_PREF, email);

                                //Saving values to editor
                                editor.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(BloodResult.this);
                requestQueue.add(stringRequest);

                //url = "http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc;

                swipe.setOnRefreshListener(
                        new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                error.setVisibility(View.GONE);
                                erroralert.setVisibility(View.GONE);
                                swipe.setRefreshing(true);
                                swipe.setEnabled(true);
                                progres.setVisibility(View.VISIBLE);
                                alert.setVisibility(View.VISIBLE);
                                arrayList = new ArrayList<>();
                                lv = (ListView) findViewById(R.id.listView);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc);
                                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                                    }
                                });
                                if (swipe.isRefreshing()) {
                                    swipe.setRefreshing(false);
                                } else {
                                    swipe.setRefreshing(true);
                                }
                            }
                        }
                );
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                answer = "মোবাইল নেটওয়ার্কের সাথে সংযুক্ত রয়েছে";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc);
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONDistrict().execute("http://www.bloodband.dhost247.net/direct/asdfdasdfadsistridfasdfct_asdfasdfasddflist/293hsdk");
                        //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONSubDistrict().execute("http://www.bloodband.dhost247.net/direct/dsfupaasdfzila_dasfaasdfgainasdfsdfstadsfasdfdisasdftasdfrict/923hd?district_id=" + loc);
                        //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                    }
                });

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                String result = response.toString();
                                SharedPreferences sharedPreferences = BloodResult.this.getSharedPreferences(Links.SHARED_URL_DONOR, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Links.URL_SHARED_PREF, true);
                                editor.putString(Links.URL_DONOR_SHARED_PREF, result);
                                //editor.putString(Config.NAME_SHARED_PREF, email);

                                //Saving values to editor
                                editor.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(BloodResult.this);
                requestQueue.add(stringRequest);

                //url = "http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc;

                swipe.setOnRefreshListener(
                        new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                error.setVisibility(View.GONE);
                                erroralert.setVisibility(View.GONE);
                                swipe.setRefreshing(true);
                                swipe.setEnabled(true);
                                progres.setVisibility(View.VISIBLE);
                                alert.setVisibility(View.VISIBLE);
                                arrayList = new ArrayList<>();
                                lv = (ListView) findViewById(R.id.listView);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + grpid + "&district_id=" + loc + "&upazila_id=" + subloc);
                                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                                    }
                                });
                                if (swipe.isRefreshing()) {
                                    swipe.setRefreshing(false);
                                } else {
                                    swipe.setRefreshing(true);
                                }
                            }
                        }
                );
            }
        } else {
            answer = "ইন্টারনেট সংযোগ বিচ্ছিন্ন রয়েছে।";
            SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_URL_DONOR, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(Links.URL_DONOR_SHARED_PREF)) {
                res = (sharedPreferences).getString(Links.URL_DONOR_SHARED_PREF, "");
                //Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                try {
                    progres.setVisibility(View.GONE);
                    alert.setVisibility(View.GONE);
                    //Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("multi_donor_report");
                    //Toast.makeText(this, "lol1", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Product(
                                productObject.getString("donors_id"),
                                productObject.getString("donors_name"),
                                productObject.getString("donors_contact"),
                                productObject.getString("donors_gender"),
                                productObject.getString("donors_email"),
                                productObject.getString("present_address"),
                                productObject.getString("birth_date"),
                                productObject.getString("last_donation_date"),
                                productObject.getString("interest"),
                                productObject.getString("main_img"),
                                productObject.getString("counter"),
                                productObject.getString("group_code"),
                                productObject.getString("name"),
                                productObject.getString("rating")
                        ));
                        String id = arrayList.get(i).getId();
                        String name = arrayList.get(i).getName();
                        String contact = arrayList.get(i).getPhn();
                        String gender = arrayList.get(i).getGender();
                        String email = arrayList.get(i).getMail();
                        String address = arrayList.get(i).getAddress();
                        String bd = arrayList.get(i).getBirth();
                        String ld = arrayList.get(i).getLastDonate();
                        String interest = arrayList.get(i).getInterest();
                        String count = arrayList.get(i).getCounter();
                        String gp = arrayList.get(i).getGroup();
                        String upazila = arrayList.get(i).getUpazila();
                        String rating = arrayList.get(i).getRating();
                        gurup = arrayList.get(i).getGroup();
                        //Toast.makeText(this, gp, Toast.LENGTH_SHORT).show();
                        /*Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, contact, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, bd, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, ld, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, interest, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, gp, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, upazila, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, rating, Toast.LENGTH_SHORT).show();*/
                    }
                    //Toast.makeText(this, "lol2", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    error.setVisibility(View.VISIBLE);
                    erroralert.setVisibility(View.VISIBLE);
                    //Toast.makeText(BloodResult.this, "Problem", Toast.LENGTH_SHORT).show();
                }

                gurup = gurup.replace("(", "").replace(")","");
                if(gurup.equals(grp)){
                    CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.list_item, arrayList);
                    lv.setVisibility(View.VISIBLE);
                    lv.setAdapter(adapter);
                    int tot = lv.getAdapter().getCount();
                    //Toast.makeText(this, "" + tot, Toast.LENGTH_SHORT).show();
                    String total = String.valueOf(tot);
                    total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    number.setText("রক্তদাতা : " + total + " জন");
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //String quete = arrayList.get(i).getQuete();
                            String id = arrayList.get(i).getId();
                            String name = arrayList.get(i).getName();
                            String contact = arrayList.get(i).getPhn();
                            String gender = arrayList.get(i).getGender();
                            String email = arrayList.get(i).getMail();
                            String address = arrayList.get(i).getAddress();
                            String bd = arrayList.get(i).getBirth();
                            String ld = arrayList.get(i).getLastDonate();
                            String interest = arrayList.get(i).getInterest();
                            String count = arrayList.get(i).getCounter();
                            String pic = arrayList.get(i).getPicture();
                            String gp = arrayList.get(i).getGroup();
                            String upazila = arrayList.get(i).getUpazila();
                            String rating = arrayList.get(i).getRating();
                            Intent intent = new Intent(BloodResult.this, InformationPageUser.class);
                            //intent.putExtra("QUETE", quete);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("contact", contact);
                            intent.putExtra("gender", gender);
                            intent.putExtra("email", email);
                            intent.putExtra("address", address);
                            intent.putExtra("bd", bd);
                            intent.putExtra("ld", ld);
                            intent.putExtra("pic", pic);
                            intent.putExtra("interest", interest);
                            intent.putExtra("count", count);
                            intent.putExtra("gp", gp);
                            intent.putExtra("upazila", upazila);
                            intent.putExtra("rating", rating);
                            startActivity(intent);
                        }
                    });
                }

                swipe.setOnRefreshListener(
                        new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                error.setVisibility(View.GONE);
                                erroralert.setVisibility(View.GONE);
                                swipe.setRefreshing(true);
                                swipe.setEnabled(true);
                                progres.setVisibility(View.VISIBLE);
                                alert.setVisibility(View.VISIBLE);
                                arrayList = new ArrayList<>();
                                lv = (ListView) findViewById(R.id.listView);
                                try {
                                    progres.setVisibility(View.GONE);
                                    alert.setVisibility(View.GONE);
                                    //Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
                                    JSONObject jsonObject = new JSONObject(res);
                                    JSONArray jsonArray = jsonObject.getJSONArray("multi_donor_report");
                                    //Toast.makeText(this, "lol1", Toast.LENGTH_SHORT).show();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject productObject = jsonArray.getJSONObject(i);
                                        arrayList.add(new Product(
                                                productObject.getString("donors_id"),
                                                productObject.getString("donors_name"),
                                                productObject.getString("donors_contact"),
                                                productObject.getString("donors_gender"),
                                                productObject.getString("donors_email"),
                                                productObject.getString("present_address"),
                                                productObject.getString("birth_date"),
                                                productObject.getString("last_donation_date"),
                                                productObject.getString("interest"),
                                                productObject.getString("main_img"),
                                                productObject.getString("counter"),
                                                productObject.getString("group_code"),
                                                productObject.getString("name"),
                                                productObject.getString("rating")
                                        ));
                                        String id = arrayList.get(i).getId();
                                        String name = arrayList.get(i).getName();
                                        String contact = arrayList.get(i).getPhn();
                                        String gender = arrayList.get(i).getGender();
                                        String email = arrayList.get(i).getMail();
                                        String address = arrayList.get(i).getAddress();
                                        String bd = arrayList.get(i).getBirth();
                                        String ld = arrayList.get(i).getLastDonate();
                                        String interest = arrayList.get(i).getInterest();
                                        String count = arrayList.get(i).getCounter();
                                        String gp = arrayList.get(i).getGroup();
                                        String upazila = arrayList.get(i).getUpazila();
                                        String rating = arrayList.get(i).getRating();
                                        gurup = arrayList.get(i).getGroup();
                                        //Toast.makeText(this, gp, Toast.LENGTH_SHORT).show();
                        /*Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, contact, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, bd, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, ld, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, interest, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, gp, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, upazila, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, rating, Toast.LENGTH_SHORT).show();*/
                                    }
                                    //Toast.makeText(this, "lol2", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    error.setVisibility(View.VISIBLE);
                                    erroralert.setVisibility(View.VISIBLE);
                                    //Toast.makeText(BloodResult.this, "Problem", Toast.LENGTH_SHORT).show();
                                }

                                gurup = gurup.replace("(", "").replace(")","");
                                if(gurup.equals(grp)){
                                    CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.list_item, arrayList);
                                    lv.setVisibility(View.VISIBLE);
                                    lv.setAdapter(adapter);
                                    int tot = lv.getAdapter().getCount();
                                    //Toast.makeText(this, "" + tot, Toast.LENGTH_SHORT).show();
                                    String total = String.valueOf(tot);
                                    total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                    number.setText("রক্তদাতা : " + total + " জন");
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            //String quete = arrayList.get(i).getQuete();
                                            String id = arrayList.get(i).getId();
                                            String name = arrayList.get(i).getName();
                                            String contact = arrayList.get(i).getPhn();
                                            String gender = arrayList.get(i).getGender();
                                            String email = arrayList.get(i).getMail();
                                            String address = arrayList.get(i).getAddress();
                                            String bd = arrayList.get(i).getBirth();
                                            String ld = arrayList.get(i).getLastDonate();
                                            String interest = arrayList.get(i).getInterest();
                                            String count = arrayList.get(i).getCounter();
                                            String pic = arrayList.get(i).getPicture();
                                            String gp = arrayList.get(i).getGroup();
                                            String upazila = arrayList.get(i).getUpazila();
                                            String rating = arrayList.get(i).getRating();
                                            Intent intent = new Intent(BloodResult.this, InformationPageUser.class);
                                            //intent.putExtra("QUETE", quete);
                                            intent.putExtra("id", id);
                                            intent.putExtra("name", name);
                                            intent.putExtra("contact", contact);
                                            intent.putExtra("gender", gender);
                                            intent.putExtra("email", email);
                                            intent.putExtra("address", address);
                                            intent.putExtra("bd", bd);
                                            intent.putExtra("ld", ld);
                                            intent.putExtra("pic", pic);
                                            intent.putExtra("interest", interest);
                                            intent.putExtra("count", count);
                                            intent.putExtra("gp", gp);
                                            intent.putExtra("upazila", upazila);
                                            intent.putExtra("rating", rating);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                if (swipe.isRefreshing()) {
                                    swipe.setRefreshing(false);
                                } else {
                                    swipe.setRefreshing(true);
                                }
                            }
                        }
                );
                /*myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("text", res);
                myClipboard.setPrimaryClip(myClip);*/
            }
            if (!sharedPreferences.contains(Links.URL_DONOR_SHARED_PREF)) {
                res = "";
                //Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }
        }
        //Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodResult.this);
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

       /* erroralert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });*/
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
                JSONArray jsonArray = jsonObject.getJSONArray("multi_donor_report");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new Product(
                            productObject.getString("donors_id"),
                            productObject.getString("donors_name"),
                            productObject.getString("donors_contact"),
                            productObject.getString("donors_gender"),
                            productObject.getString("donors_email"),
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.layout.list_item, arrayList
            );
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            number.setText("রক্তদাতা : " + total + " জন");
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(BloodResult.this,
                    R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(
                    set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    String id = arrayList.get(i).getId();
                    String name = arrayList.get(i).getName();
                    String contact = arrayList.get(i).getPhn();
                    String gender = arrayList.get(i).getGender();
                    String email = arrayList.get(i).getMail();
                    String address = arrayList.get(i).getAddress();
                    String bd = arrayList.get(i).getBirth();
                    String ld = arrayList.get(i).getLastDonate();
                    String pic = arrayList.get(i).getPicture();
                    String interest = arrayList.get(i).getInterest();
                    String count = arrayList.get(i).getCounter();
                    String gp = arrayList.get(i).getGroup();
                    String upazila = arrayList.get(i).getUpazila();
                    String rating = arrayList.get(i).getRating();
                    Intent intent = new Intent(BloodResult.this, InformationPageUser.class);
                    //intent.putExtra("QUETE", quete);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("contact", contact);
                    intent.putExtra("gender", gender);
                    intent.putExtra("email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("bd", bd);
                    intent.putExtra("ld", ld);
                    intent.putExtra("pic", pic);
                    intent.putExtra("interest", interest);
                    intent.putExtra("count", count);
                    intent.putExtra("gp", gp);
                    intent.putExtra("upazila", upazila);
                    intent.putExtra("rating", rating);
                    startActivity(intent);
                }
            });
        }
    }

    class ReadJSONDistrict extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jObject = new JSONObject(content);
                JSONArray jsonArray = jObject.getJSONArray("district_list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList1.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    jelaa = arrayList1.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                    if (class_code.equals(loc)) {
                        group.setText(jelaa + " | " + grp);
                        //Toast.makeText(BloodResult.this, jelaa, Toast.LENGTH_SHORT).show();
                        jela = jelaa;
                        //Toast.makeText(BloodResult.this, jela, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONSubDistrict extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jObject = new JSONObject(content);
                JSONArray jsonArray = jObject.getJSONArray("upazila_against_district");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList2.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList2.get(i).getID();
                    //Toast.makeText(BloodResult.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList2.get(i).getName();
                    //Toast.makeText(BloodResult.this, class_name, Toast.LENGTH_SHORT).show();
                    if (class_code.equals(subloc)) {
                        group.setText(jela + " | " + class_name + " | " + grp);
                        //Toast.makeText(BloodResult.this, loc, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
           /*// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BloodResult.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BloodResult.this);

            // set title
            alertDialogBuilder.setTitle("ডাটা লোডজনিত সমস্যা");

            // set dialog message
            alertDialogBuilder
                    .setMessage("ডাটা লোড করা সম্ভব হয়নি।")
                    .setCancelable(false)
                    .setPositiveButton("পুন্রায় চেষ্টা করুন",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(intent);
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();*/
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
