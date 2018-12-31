package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BloodSearch extends AppCompatActivity {

    private TextView t1, t2, t3;
    private ImageView menu, back, loco;
    private Button search;
    private String answer, bg = "", dist = "", subdist = "";
    private GridView dist_list;
    private EditText text;
    ArrayList<ProductDist> arrayList, arrayList1, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_search);

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

        String grp = getIntent().getStringExtra("group");

        t1 = (TextView) findViewById(R.id.blood_grp);
        t1.setText(grp);
        if (grp.equals("A+")) {
            bg = "1";
        }
        if (grp.equals("A-")) {
            bg = "2";
        }
        if (grp.equals("O+")) {
            bg = "3";
        }
        if (grp.equals("O-")) {
            bg = "4";
        }
        if (grp.equals("B+")) {
            bg = "5";
        }
        if (grp.equals("B-")) {
            bg = "6";
        }
        if (grp.equals("AB+")) {
            bg = "7";
        }
        if (grp.equals("AB-")) {
            bg = "8";
        }
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final CharSequence[] items = {
                        "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodSearch.this);
                builder.setTitle(Html.fromHtml("<font color='#e83d3d'>রক্তের গ্রুপ</font>"));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            //Toast.makeText(getBaseContext(),"ডেভোলাপার", Toast.LENGTH_LONG).show();
                            t1.setText("O+");
                            bg = "3";
                        }

                        if (item == 1) {
                            //Toast.makeText(getBaseContext(),"রেটিং দিন", Toast.LENGTH_LONG).show();
                            t1.setText("O-");
                            bg = "4";
                        }

                        if (item == 2) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("A+");
                            bg = "1";
                        }

                        if (item == 3) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("A-");
                            bg = "2";
                        }

                        if (item == 4) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("B+");
                            bg = "5";
                        }

                        if (item == 5) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("B-");
                            bg = "6";
                        }

                        if (item == 6) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("AB+");
                            bg = "7";
                        }

                        if (item == 7) {
                            //Toast.makeText(getBaseContext(),"শেয়ার করুন", Toast.LENGTH_LONG).show();
                            t1.setText("AB-");
                            bg = "8";
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();*/
                LayoutInflater inflater = BloodSearch.this.getLayoutInflater();
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BloodSearch.this);
                final View dialogView = inflater.inflate(R.layout.blood_list, null);
                builder.setView(dialogView);
                final android.app.AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView ap = (TextView) dialogView.findViewById(R.id.ap);
                LinearLayout ll11 = (LinearLayout) findViewById(R.id.l11);
                ap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("A+");
                        bg = "1";
                        dialog.dismiss();
                    }
                });
                TextView an = (TextView) dialogView.findViewById(R.id.an);
                LinearLayout ll12 = (LinearLayout) findViewById(R.id.l12);
                an.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("A-");
                        bg = "2";
                        dialog.dismiss();
                    }
                });
                TextView bp = (TextView) dialogView.findViewById(R.id.bp);
                LinearLayout ll13 = (LinearLayout) findViewById(R.id.l13);
                bp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("B+");
                        bg = "5";
                        dialog.dismiss();
                    }
                });
                TextView bn = (TextView) dialogView.findViewById(R.id.bn);
                LinearLayout ll14 = (LinearLayout) findViewById(R.id.l14);
                bn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("B-");
                        bg = "6";
                        dialog.dismiss();
                    }
                });
                TextView op = (TextView) dialogView.findViewById(R.id.op);
                LinearLayout ll21 = (LinearLayout) findViewById(R.id.l21);
                op.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("O+");
                        bg = "3";
                        dialog.dismiss();
                    }
                });
                TextView on = (TextView) dialogView.findViewById(R.id.on);
                LinearLayout ll22 = (LinearLayout) findViewById(R.id.l22);
                on.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("O-");
                        bg = "4";
                        dialog.dismiss();
                    }
                });
                TextView abp = (TextView) dialogView.findViewById(R.id.abp);
                LinearLayout ll23 = (LinearLayout) findViewById(R.id.l23);
                abp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("AB+");
                        bg = "7";
                        dialog.dismiss();
                    }
                });
                TextView abn = (TextView) dialogView.findViewById(R.id.abn);
                LinearLayout ll24 = (LinearLayout) findViewById(R.id.l24);
                abn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t1.setText("AB-");
                        bg = "8";
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        t2 = (TextView) findViewById(R.id.select);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                District attendance = new District();
                attendance.showdialog(BloodSearch.this, "জেলা বাছাই করুন");
            }
        });

        t3 = (TextView) findViewById(R.id.select1);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubDistrict attendance = new SubDistrict();
                attendance.showdialog(BloodSearch.this, "উপজেলা বাছাই করুন");
            }
        });
        t3.setClickable(false);

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodSearch.this);
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

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = t1.getText().toString();
                String text2 = t2.getText().toString();
                String text3 = t3.getText().toString();

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        answer = "Wifi এর সাথে সংযুক্ত রয়েছে";
                        /*if (text1.equals("রক্তের গ্রুপ") || text2.equals("জেলা বাছাই করুন")) {
                            Toast.makeText(BloodSearch.this, "অনুগ্রহ করে রক্তের গ্রুপ এবং জায়গা বাছাই করুন", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(BloodSearch.this, BloodResult.class);
                            intent.putExtra("Group", text1);
                            intent.putExtra("Location", text2);
                            intent.putExtra("SubLocation", text3);
                            startActivity(intent);
                        }*/
                        searching();
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        answer = "মোবাইল নেটওয়ার্কের সাথে সংযুক্ত রয়েছে";
                        /*if (text1.equals("রক্তের গ্রুপ") || text2.equals("জেলা বাছাই করুন")) {
                            Toast.makeText(BloodSearch.this, "অনুগ্রহ করে রক্তের গ্রুপ এবং জায়গা বাছাই করুন", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(BloodSearch.this, BloodResult.class);
                            intent.putExtra("Group", text1);
                            intent.putExtra("Location", text2);
                            intent.putExtra("SubLocation", text3);
                            startActivity(intent);
                        }*/
                        searching();
                    }
                } else {
                    answer = "ইন্টারনেট সংযোগ বিচ্ছিন্ন রয়েছে।";
                }

                //Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();
                /*if(text1.equals("রক্তের গ্রুপ") || text2.equals("জায়গা বাছাই করুন")){
                    Toast.makeText(BloodSearch.this, "অনুগ্রহ করে রক্তের গ্রুপ এবং জায়গা বাছাই করুন", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(BloodSearch.this, BloodResult.class);
                    intent.putExtra("Group", text1);
                    intent.putExtra("Location", text2);
                    startActivity(intent);
                }*/
            }
        });
    }

    public class District {

        public void showdialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.division_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String formattedDate = df.format(c.getTime());

            TextView title1 = (TextView) dialog.findViewById(R.id.title);
            title1.setText(title);
            text = (EditText) dialog.findViewById(R.id.texting);
            dist_list = (GridView) dialog.findViewById(R.id.gridView);
            dist_list.setTextFilterEnabled(true);
            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSONDistrict().execute("http://www.bloodband.dhost247.net/direct/asdfdasdfadsistridfasdfct_asdfasdfasddflist/293hsdk");
                    //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                }
            });
            dist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    dist = arrayList.get(i).getID();
                    //Toast.makeText(OverallReport.this, cls, Toast.LENGTH_SHORT).show();
                    String clsname = arrayList.get(i).getName();
                    t2.setText(clsname);
                    t3.setClickable(true);
                    dialog.dismiss();
                }
            });

            Button cancel = (Button) dialog.findViewById(R.id.official);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
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
                    arrayList.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final CustomListAdapterDist adapter = new CustomListAdapterDist(
                    getApplicationContext(), R.layout.dist_list_item, arrayList
            );
            dist_list.setAdapter(adapter);
            text.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                    adapter.getFilter().filter(stringVar.toString());
                }
            });
        }
    }

    public class SubDistrict {

        public void showdialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.division_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String formattedDate = df.format(c.getTime());

            TextView title1 = (TextView) dialog.findViewById(R.id.title);
            title1.setText(title);
            dist_list = (GridView) dialog.findViewById(R.id.gridView);
            arrayList1 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSONSubDistrict().execute("http://www.bloodband.dhost247.net/direct/dsfupaasdfzila_dasfaasdfgainasdfsdfstadsfasdfdisasdftasdfrict/923hd?district_id=" + dist);
                    //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                }
            });
            dist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    subdist = arrayList1.get(i).getID();
                    //Toast.makeText(OverallReport.this, cls, Toast.LENGTH_SHORT).show();
                    String clsname = arrayList1.get(i).getName();
                    t3.setText(clsname);
                    dialog.dismiss();
                }
            });

            Button cancel = (Button) dialog.findViewById(R.id.official);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

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
                    arrayList1.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList1.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList1.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final CustomListAdapterDist adapter = new CustomListAdapterDist(
                    getApplicationContext(), R.layout.dist_list_item, arrayList1
            );
            dist_list.setAdapter(adapter);
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

    public void searching() {
        String grp = t1.getText().toString();
        if (grp.equals("রক্তের গ্রুপ")) {
            grp = "";
        }
        Intent intent = new Intent(BloodSearch.this, BloodResult.class);
        intent.putExtra("Group", grp);
        intent.putExtra("GroupID", bg);
        intent.putExtra("Location", dist);
        intent.putExtra("SubLocation", subdist);
        startActivity(intent);
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
