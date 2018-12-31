package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LogReport extends AppCompatActivity {

    ArrayList<ProductBloodLog> arrayList;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, grpid, loc, subloc, user, id;
    private ImageView menu, back, loco, error;
    private ProgressBar progres;
    private TextView alert, jega, subjega, group, erroralert, number;
    private String answer, donid, name, date, date1, unit, place, age, age1, gender = "1", SetServerString;
    Typeface fontAwesomeFont;
    private HttpClient Client;
    HttpGet httpget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.log_report);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

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

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LogEntry.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.VISIBLE);

        number = (TextView) findViewById(R.id.number);
        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/donasdfasdatasdfadsion_asdfasdfreportsd/2183jasgh?user_id=" + id);
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
                                new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/donasdfasdatasdfadsion_asdfasdfreportsd/2183jasgh?user_id=" + id);
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

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(LogReport.this);
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
                JSONArray jsonArray = jsonObject.getJSONArray("user_donation_log_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductBloodLog(
                            productObject.getString("dbdl_id"),
                            productObject.getString("donors_name"),
                            productObject.getString("donation_date"),
                            productObject.getString("donation_issue"),
                            productObject.getString("donation_place"),
                            productObject.getString("donation_age"),
                            productObject.getString("donation_gender")
                    ));
                    String id = arrayList.get(i).getDonId();
                    //Toast.makeText(LogReport.this, id, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapterBloodLog adapter = new CustomListAdapterBloodLog(
                    getApplicationContext(), R.layout.log_list_item1, arrayList
            );
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            number.setText("মোট রক্তদান : " + total + " বার");
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(LogReport.this,
                    R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(
                    set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    donid = arrayList.get(i).getDonId();
                    name = arrayList.get(i).getName();
                    date = arrayList.get(i).getDate();
                    date1 = arrayList.get(i).getDate();
                    unit = arrayList.get(i).getIssue();
                    place = arrayList.get(i).getPlace();
                    age = arrayList.get(i).getAge();
                    age1 = arrayList.get(i).getAge();
                    gender = arrayList.get(i).getGender();
                    //Toast.makeText(LogReport.this, gender, Toast.LENGTH_SHORT).show();

                    Report alert1 = new Report();
                    alert1.showDialog(LogReport.this, "");
                }
            });
        }
    }

    public class Report {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.blood_log_view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView log = (TextView) dialog.findViewById(R.id.log);
            log.setTypeface(fontAwesomeFont);
            TextView edit = (TextView) dialog.findViewById(R.id.edit);
            edit.setTypeface(fontAwesomeFont);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), LogEdit.class);
                    intent.putExtra("id", id);
                    intent.putExtra("donid", donid);
                    intent.putExtra("name", name);
                    intent.putExtra("date", date1);
                    intent.putExtra("unit", unit);
                    intent.putExtra("place", place);
                    intent.putExtra("age", age);
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                    //Toast.makeText(LogReport.this, date1, Toast.LENGTH_SHORT).show();
                }
            });
            TextView logout = (TextView) dialog.findViewById(R.id.logout);
            logout.setTypeface(fontAwesomeFont);
            TextView units = (TextView) dialog.findViewById(R.id.unit);
            units.setTypeface(fontAwesomeFont);
            TextView del = (TextView) dialog.findViewById(R.id.delete);
            del.setTypeface(fontAwesomeFont);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete alert1 = new Delete();
                    alert1.showDialog(LogReport.this, "");
                }
            });
            date = date.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            age1 = age1.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            TextView username = (TextView) dialog.findViewById(R.id.name);
            username.setText(name);
            TextView dondate = (TextView) dialog.findViewById(R.id.dondate);
            dondate.setText(date);
            TextView donunit = (TextView) dialog.findViewById(R.id.donunit);
            donunit.setText("রোগীর সমস্যা - " + unit);
            TextView donplace = (TextView) dialog.findViewById(R.id.donplace);
            donplace.setText(place);
            TextView donage = (TextView) dialog.findViewById(R.id.donage);
            donage.setText("রোগীর বয়স - " + age1);
            TextView dongender = (TextView) dialog.findViewById(R.id.dongender);
            if (gender.equals("1")) {
                dongender.setText("পুরুষ");
            }
            if (gender.equals("2")) {
                dongender.setText("মহিলা");
            }
            TextView age = (TextView) dialog.findViewById(R.id.age);
            age.setTypeface(fontAwesomeFont);
            TextView gender = (TextView) dialog.findViewById(R.id.gender);
            gender.setTypeface(fontAwesomeFont);

            dialog.show();
        }
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
                    Client = new DefaultHttpClient();

                    String URL = "http://www.bloodband.dhost247.net/direct/doasdfasdnatioasdfasdn_deasdfasdletedasf/2183jasgh?donation_id=" + donid;
                    //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
                    SetServerString = "";

                    httpget = new HttpGet(URL);
                    HttpResponse response = null;
                    try {
                        response = Client.execute(httpget);
                        HttpEntity httpEntity = response.getEntity();
                        String res = EntityUtils.toString(httpEntity);
                        //Toast.makeText(BeMember.this, res, Toast.LENGTH_SHORT).show();
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        try {
                            SetServerString = Client.execute(httpget, responseHandler);
                            //Toast.makeText(LogReport.this, SetServerString, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LogReport.this, "সফলভাবে ডিলিট করা হয়েছে", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), LogReport.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
