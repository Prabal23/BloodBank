package com.blood.band.bloodband;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BloodLocation extends AppCompatActivity {

    private TextView t1, t2, erroralert;
    private ImageView menu, back, loco, go, error;
    private Button search;
    ArrayList<Product> arrayList;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String id = "", grp, loc, answer, provider;
    private ProgressBar progres;
    private TextView alert, jega, group, number;
    private String dialog_title = "#d90000", res, pass = "";
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String lattitude, longitude, fulladdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_location);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                answer = "Wifi এর সাথে সংযুক্ত রয়েছে";
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                answer = "মোবাইল নেটওয়ার্কের সাথে সংযুক্ত রয়েছে";
            }
        } else {
            answer = "ইন্টারনেট সংযোগ বিচ্ছিন্ন রয়েছে।";
        }
        //Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Links.EMAIL_SHARED_PREF)) {
            res = (sharedPreferences).getString(Links.EMAIL_SHARED_PREF, "");
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            String[] result = res.split(" ");
            id = result[0];
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            pass = result[1];
            //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();
        }
        if (!sharedPreferences.contains(Links.EMAIL_SHARED_PREF)) {
            res = "";
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            id = "";
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            pass = "";
            //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();
        }

        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.GONE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.GONE);

        number = (TextView) findViewById(R.id.number);
        number.setTypeface(fontAwesomeFont);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/singladsfasdfeasdfaasdfs_uasdfsasdferasdfasd_donsfdgorsdfg_asdfgrea_sdfglidsfgsdfgsdfgsdfgstsdgs/923hd?user_id="+id);
            }
        });

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
                                new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/singladsfasdfeasdfaasdfs_uasdfsasdferasdfasd_donsfdgorsdfg_asdfgrea_sdfglidsfgsdfgsdfgsdfgstsdgs/923hd?user_id="+id);
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

        t2 = (TextView) findViewById(R.id.select);

        progres.setVisibility(View.VISIBLE);
        alert.setVisibility(View.VISIBLE);

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodLocation.this);
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
                JSONArray jsonArray = jsonObject.getJSONArray("single_donor_report");

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
            number.setText("রক্তদাতার সংখ্যা : "+total+" জন");
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(BloodLocation.this,
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
                    String gp = arrayList.get(i).getGroup();
                    String upazila = arrayList.get(i).getUpazila();
                    Intent intent = new Intent(BloodLocation.this, InformationPageUser.class);
                    //intent.putExtra("QUETE", quete);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("contact", contact);
                    intent.putExtra("gender", gender);
                    intent.putExtra("email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("bd", bd);
                    intent.putExtra("ld", ld);
                    intent.putExtra("gp", gp);
                    intent.putExtra("upazila", upazila);
                    startActivity(intent);
                }
            });
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
