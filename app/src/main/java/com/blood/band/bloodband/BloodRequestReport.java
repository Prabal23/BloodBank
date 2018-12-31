package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class BloodRequestReport extends AppCompatActivity {

    private TextView t1, t2, erroralert;
    private ImageView menu, back, loco, go, error;
    private Button search;
    ArrayList<ProductBloodReqReport> arrayList;
    ListView lv;
    private GridView gridView;
    private SwipeRefreshLayout swipe;
    private String id, grp, loc, answer, provider;
    private ProgressBar progres;
    private TextView alert, jega, group, number;
    private String dialog_title = "#d90000";
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String bg = "0", lattitude, longitude, fulladdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_request_report);

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
            id = (sharedPreferences).getString(Links.EMAIL_SHARED_PREF, "");
        }

        arrayList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.GONE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.GONE);

        number = (TextView) findViewById(R.id.number);
        number.setTypeface(fontAwesomeFont);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BloodRequest.class);
                startActivity(intent);
            }
        });

        TextView logo = (TextView) findViewById(R.id.loco);
        logo.setTypeface(fontAwesomeFont);
        final TextView grp = (TextView) findViewById(R.id.bd_grp);
        grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = BloodRequestReport.this.getLayoutInflater();
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BloodRequestReport.this);
                final View dialogView = inflater.inflate(R.layout.blood_list, null);
                builder.setView(dialogView);
                final android.app.AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView ap = (TextView) dialogView.findViewById(R.id.ap);
                LinearLayout l11 = (LinearLayout) findViewById(R.id.l11);
                ap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("A+");
                        bg = "1";
                        dialog.dismiss();
                    }
                });
                TextView an = (TextView) dialogView.findViewById(R.id.an);
                LinearLayout l12 = (LinearLayout) findViewById(R.id.l12);
                an.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("A-");
                        bg = "2";
                        dialog.dismiss();
                    }
                });
                TextView bp = (TextView) dialogView.findViewById(R.id.bp);
                LinearLayout l13 = (LinearLayout) findViewById(R.id.l13);
                bp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("B+");
                        bg = "5";
                        dialog.dismiss();
                    }
                });
                TextView bn = (TextView) dialogView.findViewById(R.id.bn);
                LinearLayout l14 = (LinearLayout) findViewById(R.id.l14);
                bn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("B-");
                        bg = "6";
                        dialog.dismiss();
                    }
                });
                TextView op = (TextView) dialogView.findViewById(R.id.op);
                LinearLayout l21 = (LinearLayout) findViewById(R.id.l21);
                op.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("O+");
                        bg = "3";
                        dialog.dismiss();
                    }
                });
                TextView on = (TextView) dialogView.findViewById(R.id.on);
                LinearLayout l22 = (LinearLayout) findViewById(R.id.l22);
                on.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("O-");
                        bg = "4";
                        dialog.dismiss();
                    }
                });
                TextView abp = (TextView) dialogView.findViewById(R.id.abp);
                LinearLayout l23 = (LinearLayout) findViewById(R.id.l23);
                abp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("AB+");
                        bg = "7";
                        dialog.dismiss();
                    }
                });
                TextView abn = (TextView) dialogView.findViewById(R.id.abn);
                LinearLayout l24 = (LinearLayout) findViewById(R.id.l24);
                abn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        grp.setText("AB-");
                        bg = "8";
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        ImageView src = (ImageView) findViewById(R.id.src);
        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bg.equals("0")) {
                    Toast.makeText(BloodRequestReport.this, "অনুগ্রহ করে রক্তের গ্রুপ বাছাই করুন", Toast.LENGTH_SHORT).show();
                } else {
                    progres.setVisibility(View.VISIBLE);
                    alert.setVisibility(View.VISIBLE);
                    arrayList = new ArrayList<>();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new ReadJSON().execute("http://www.bloodband.dhost247.net/direct/blasdfasdfoasdfaodasdfasdf_reqasdfasdfueasdfasdfst_rasdfasdfeportadfasdf/q13846?blood_group=" + bg);
                        }
                    });
                }
            }
        });

        t2 = (TextView) findViewById(R.id.select);

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodRequestReport.this);
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
                JSONArray jsonArray = jsonObject.getJSONArray("blood_request");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductBloodReqReport(
                            productObject.getString("request_id"),
                            productObject.getString("amount"),
                            productObject.getString("donation_date"),
                            productObject.getString("request_from"),
                            productObject.getString("password"),
                            productObject.getString("patient_location"),
                            productObject.getString("patient_district"),
                            productObject.getString("contact_number"),
                            productObject.getString("request_comments"),
                            productObject.getString("type"),
                            productObject.getString("group_code"),
                            productObject.getString("name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapterReqReport adapter = new CustomListAdapterReqReport(
                    getApplicationContext(), R.layout.blood_req_list_item, arrayList
            );
            gridView.setAdapter(adapter);
            int tot = gridView.getAdapter().getCount();
            String total = String.valueOf(tot);
            total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            number.setText("রক্তদাতার সংখ্যা : " + total + " জন");
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(BloodRequestReport.this,
                    R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(
                    set, 0.5f);
            gridView.setLayoutAnimation(controller);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    String type = arrayList.get(i).getType();
                    String upazila = arrayList.get(i).getUpazila();
                    String upo = arrayList.get(i).getUpo();
                    Intent intent = new Intent(BloodRequestReport.this, BloodRequestDetails.class);
                    //intent.putExtra("QUETE", quete);
                    intent.putExtra("reqid", id);
                    intent.putExtra("amount", name);
                    intent.putExtra("dondate", contact);
                    intent.putExtra("from", gender);
                    intent.putExtra("pass", email);
                    intent.putExtra("location", address);
                    intent.putExtra("district", bd);
                    intent.putExtra("number", ld);
                    intent.putExtra("message", gp);
                    intent.putExtra("type", type);
                    intent.putExtra("bg", upazila);
                    intent.putExtra("upazila", upo);
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
