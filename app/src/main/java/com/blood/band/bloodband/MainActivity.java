package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private TextView t1, t2, op, ap, bp, abp, on, an, bn, abn, g1, g2, g3, g4, g5, g6, g7, g8, total;
    private ImageView menu;
    private String user, answer, apc, anc, bpc, bnc, opc, onc, abpc, abnc, res;
    private int a_p_c, a_n_c, b_p_c, b_n_c, o_p_c, o_n_c, ab_p_c, ab_n_c;
    private float a_p_f, a_n_f, b_p_f, b_n_f, o_p_f, o_n_f, ab_p_f, ab_n_f;
    BarChart chart;
    ArrayList<ProductBloodCount> arrayList, arrayList1, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);*/
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        total = (TextView) findViewById(R.id.total);
        chart = (BarChart) findViewById(R.id.chart1);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        g1 = (TextView) findViewById(R.id.g1);
        g2 = (TextView) findViewById(R.id.g2);
        g3 = (TextView) findViewById(R.id.g3);
        g4 = (TextView) findViewById(R.id.g4);
        g5 = (TextView) findViewById(R.id.g5);
        g6 = (TextView) findViewById(R.id.g6);
        g7 = (TextView) findViewById(R.id.g7);
        g8 = (TextView) findViewById(R.id.g8);

        user = getResources().getString(R.string.user);
        op = (TextView) findViewById(R.id.op);
        op.setText(user);
        op.setTypeface(fontAwesomeFont);

        ap = (TextView) findViewById(R.id.ap);
        ap.setText(user);
        ap.setTypeface(fontAwesomeFont);

        bp = (TextView) findViewById(R.id.bp);
        bp.setText(user);
        bp.setTypeface(fontAwesomeFont);

        abp = (TextView) findViewById(R.id.abp);
        abp.setText(user);
        abp.setTypeface(fontAwesomeFont);

        on = (TextView) findViewById(R.id.on);
        on.setText(user);
        on.setTypeface(fontAwesomeFont);

        an = (TextView) findViewById(R.id.an);
        an.setText(user);
        an.setTypeface(fontAwesomeFont);

        bn = (TextView) findViewById(R.id.bn);
        bn.setText(user);
        bn.setTypeface(fontAwesomeFont);

        abn = (TextView) findViewById(R.id.abn);
        abn.setText(user);
        abn.setTypeface(fontAwesomeFont);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            //Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_DASHBOARD, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(Links.DASHBOARD_SHARED_PREF)) {
                res = (sharedPreferences).getString(Links.DASHBOARD_SHARED_PREF, "");
                //Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }
            if (!sharedPreferences.contains(Links.DASHBOARD_SHARED_PREF)) {
                res = "0 0 0 0 0 0 0 0";
                //Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }
            String[] results = res.split(" ");
            apc = results[0];
            a_p_c = Integer.parseInt(apc);
            a_p_f = Float.parseFloat(apc);
            //Toast.makeText(this, apc, Toast.LENGTH_SHORT).show();
            ap.setText(user + " " + apc);
            anc = results[1];
            a_n_c = Integer.parseInt(anc);
            a_n_f = Float.parseFloat(anc);
            //Toast.makeText(this, anc, Toast.LENGTH_SHORT).show();
            an.setText(user + " " + anc);
            bpc = results[2];
            b_p_c = Integer.parseInt(bpc);
            b_p_f = Float.parseFloat(bpc);
            //Toast.makeText(this, bpc, Toast.LENGTH_SHORT).show();
            bp.setText(user + " " + bpc);
            bnc = results[3];
            b_n_c = Integer.parseInt(bnc);
            b_n_f = Float.parseFloat(bnc);
            //Toast.makeText(this, bnc, Toast.LENGTH_SHORT).show();
            bn.setText(user + " " + bnc);
            opc = results[4];
            o_p_c = Integer.parseInt(opc);
            o_p_f = Float.parseFloat(opc);
            //Toast.makeText(this, opc, Toast.LENGTH_SHORT).show();
            op.setText(user + " " + opc);
            onc = results[5];
            o_n_c = Integer.parseInt(onc);
            o_n_f = Float.parseFloat(onc);
            //Toast.makeText(this, onc, Toast.LENGTH_SHORT).show();
            on.setText(user + " " + onc);
            abpc = results[6];
            ab_p_c = Integer.parseInt(abpc);
            ab_p_f = Float.parseFloat(abpc);
            //Toast.makeText(this, abpc, Toast.LENGTH_SHORT).show();
            abp.setText(user + " " + abpc);
            abnc = results[7];
            ab_n_c = Integer.parseInt(abnc);
            ab_n_f = Float.parseFloat(abnc);
            //Toast.makeText(this, abnc, Toast.LENGTH_SHORT).show();
            abn.setText(user + " " + abnc);
            barChart();
        }
        //Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();

        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();
        arrayList4 = new ArrayList<>();
        arrayList5 = new ArrayList<>();
        arrayList6 = new ArrayList<>();
        arrayList7 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONAP().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONAN().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONBP().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONBN().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONOP().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONON().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONABP().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
                new ReadJSONABN().execute("http://www.bloodband.dhost247.net/direct/tasasdfasdfk_statasdfasdfus_coasdfasdfudfasdsdffnt/293hsdk");
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                alert
                        .show();
            }
        });

        LinearLayout r1 = (LinearLayout) findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BloodSearch.class);
                intent.putExtra("group", "রক্তের গ্রুপ");
                startActivity(intent);
            }
        });

        LinearLayout r2 = (LinearLayout) findViewById(R.id.r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginSearchLocation.class);
                startActivity(intent);
            }
        });

        LinearLayout r4 = (LinearLayout) findViewById(R.id.r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BloodRequestReport.class);
                startActivity(intent);
            }
        });

        LinearLayout r3 = (LinearLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeMember.class);
                startActivity(intent);
            }
        });

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RateDonor.class);
                startActivity(intent);
            }
        });

        LinearLayout b1 = (LinearLayout) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g1.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "3");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b2 = (LinearLayout) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g2.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "4");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b3 = (LinearLayout) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g3.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "1");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b4 = (LinearLayout) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g4.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "2");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b5 = (LinearLayout) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g5.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "5");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b6 = (LinearLayout) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g6.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "6");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b7 = (LinearLayout) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g7.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "7");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout b8 = (LinearLayout) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grp = g8.getText().toString();
                Intent intent = new Intent(getBaseContext(), BloodResult.class);
                intent.putExtra("Group", grp);
                intent.putExtra("GroupID", "8");
                intent.putExtra("Location", "");
                intent.putExtra("SubLocation", "");
                startActivity(intent);
            }
        });

        LinearLayout dev_info = (LinearLayout) findViewById(R.id.dev_info);
        dev_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Developer_Website.class);
                startActivity(intent);
            }
        });

        CircleImageView fb = (CircleImageView) findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Facebook_page.class);
                startActivity(intent);
            }
        });
    }

    public Long getUidRxBytes(int uid) {
        BufferedReader reader;
        Long rxBytes = 0L;
        try {
            reader = new BufferedReader(new FileReader("/proc/uid_stat/" + uid
                    + "/tcp_rcv"));
            rxBytes = Long.parseLong(reader.readLine());
            //Toast.makeText(MainActivity.this, rxBytes+"", Toast.LENGTH_SHORT).show();
            reader.close();
        } catch (FileNotFoundException e) {
            rxBytes = TrafficStats.getUidRxBytes(uid);
            //Toast.makeText(MainActivity.this, rxBytes+"", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rxBytes;
    }

    /**
     * Read UID Tx Bytes
     *
     * @param uid
     * @return txBytes
     */
    public Long getUidTxBytes(int uid) {
        BufferedReader reader;
        Long txBytes = 0L;
        try {
            reader = new BufferedReader(new FileReader("/proc/uid_stat/" + uid
                    + "/tcp_snd"));
            txBytes = Long.parseLong(reader.readLine());
            //Toast.makeText(MainActivity.this, txBytes+"", Toast.LENGTH_SHORT).show();
            reader.close();
        } catch (FileNotFoundException e) {
            txBytes = TrafficStats.getUidTxBytes(uid);
            //Toast.makeText(MainActivity.this, txBytes+"", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txBytes;
    }

    @Override
    public void onBackPressed() {
        ViewDialog alert = new ViewDialog();
        alert.showDialog(MainActivity.this, "এপ থেকে প্রস্থান করতে চান?");
    }

    public class ViewDialog {
        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_exit);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton1 = (Button) dialog.findViewById(R.id.btn_dialog_yes);
            dialogButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_no);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    class ReadJSONAP extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("a_positive_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductBloodCount(
                            productObject.getString("total_a_positive")
                    ));
                    apc = arrayList.get(i).getCount();
                    a_p_c = Integer.parseInt(apc);
                    a_p_f = Float.parseFloat(apc);
                    ap.setText(user + " " + apc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONAN extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("a_negative_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductBloodCount(
                            productObject.getString("total_a_negative")
                    ));
                    anc = arrayList1.get(i).getCount();
                    a_n_c = Integer.parseInt(anc);
                    a_n_f = Float.parseFloat(anc);
                    an.setText(user + " " + anc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONBP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("b_positive_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList2.add(new ProductBloodCount(
                            productObject.getString("total_b_positive")
                    ));
                    bpc = arrayList2.get(i).getCount();
                    b_p_c = Integer.parseInt(bpc);
                    b_p_f = Float.parseFloat(bpc);
                    bp.setText(user + " " + bpc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONBN extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("b_negative_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList3.add(new ProductBloodCount(
                            productObject.getString("total_b_negative")
                    ));
                    bnc = arrayList3.get(i).getCount();
                    b_n_c = Integer.parseInt(bnc);
                    b_n_f = Float.parseFloat(bnc);
                    bn.setText(user + " " + bnc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONOP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("o_positive_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList4.add(new ProductBloodCount(
                            productObject.getString("total_o_positive")
                    ));
                    opc = arrayList4.get(i).getCount();
                    o_p_c = Integer.parseInt(opc);
                    o_p_f = Float.parseFloat(opc);
                    op.setText(user + " " + opc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("o_negative_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList5.add(new ProductBloodCount(
                            productObject.getString("total_o_negative")
                    ));
                    onc = arrayList5.get(i).getCount();
                    o_n_c = Integer.parseInt(onc);
                    o_n_f = Float.parseFloat(onc);
                    on.setText(user + " " + onc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONABP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("ab_positive_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList6.add(new ProductBloodCount(
                            productObject.getString("total_ab_positive")
                    ));
                    abpc = arrayList6.get(i).getCount();
                    ab_p_c = Integer.parseInt(abpc);
                    ab_p_f = Float.parseFloat(abpc);
                    abp.setText(user + " " + abpc);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONABN extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject categoryObject = jsonObject.getJSONObject("status_count");
                JSONArray jsonArray = categoryObject.getJSONArray("ab_negative_count");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList7.add(new ProductBloodCount(
                            productObject.getString("total_ab_negative")
                    ));
                    abnc = arrayList7.get(i).getCount();
                    ab_n_c = Integer.parseInt(abnc);
                    ab_n_f = Float.parseFloat(abnc);
                    abn.setText(user + " " + abnc);
                }
                int t = a_p_c + a_n_c + b_p_c + b_n_c + o_p_c + o_n_c + ab_p_c + ab_n_c;
                String t1 = String.valueOf(t);
                t1 = t1.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                String dashboard = apc + " " + anc + " " + bpc + " " + bnc + " " + opc + " " + onc + " " + abpc + " " + abnc;
                total.setText("মোট রক্তদাতা : " + t1);
                barChart();
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Links.SHARED_DASHBOARD, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(Links.DASH_SHARED_PREF, true);
                editor.putString(Links.DASHBOARD_SHARED_PREF, dashboard);
                //editor.putString(Config.NAME_SHARED_PREF, email);

                //Saving values to editor
                editor.commit();
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
        }
        return content.toString();
    }

    public void barChart() {
        ArrayList<Integer> colors = new ArrayList<>();
        //colors.add(Color.rgb(38, 185, 153));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));
        colors.add(Color.parseColor("#e83d3d"));

        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(o_p_f, 0));
        BARENTRY.add(new BarEntry(o_n_f, 1));
        BARENTRY.add(new BarEntry(a_p_f, 2));
        BARENTRY.add(new BarEntry(a_n_f, 3));
        BARENTRY.add(new BarEntry(b_p_f, 4));
        BARENTRY.add(new BarEntry(b_n_f, 5));
        BARENTRY.add(new BarEntry(ab_p_f, 6));
        BARENTRY.add(new BarEntry(ab_n_f, 7));

        BarEntryLabels = new ArrayList<String>();

        //AddValuesToBARENTRY();

        //AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Blood Groups");
        BarEntryLabels.add("O+");
        BarEntryLabels.add("O-");
        BarEntryLabels.add("A+");
        BarEntryLabels.add("A-");
        BarEntryLabels.add("B+");
        BarEntryLabels.add("B-");
        BarEntryLabels.add("AB+");
        BarEntryLabels.add("AB-");

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(Bardataset);

        BARDATA = new BarData(BarEntryLabels, dataSets);

        Bardataset.setColors(colors);
        chart.getLegend().setEnabled(false);
        chart.setDescription("");
        chart.setData(BARDATA);

        chart.animateY(1500);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    /*Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();*/
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
