package com.blood.band.bloodband;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MyDonor extends AppCompatActivity {
    ArrayList<Product> arrayList;
    ListView lv;
    GridView gridView;
    private SwipeRefreshLayout swipe;
    private String grp, loc, user, photos;
    private ImageView menu, back, loco, error;
    private ProgressBar progres;
    private TextView alert, jega, group, erroralert, number;
    private String answer;
    private Bitmap bitmap;
    private DatabaseHandler db;
    private String f_name, f_add, f_grp, f_email, f_phn;
    private dataAdapter data;
    private Contact dataModel;
    private Bitmap bp;
    private byte[] photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydonor);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = new DatabaseHandler(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddMyDonor.class);
                startActivity(intent);
            }
        });

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        //arrayList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.VISIBLE);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        number = (TextView) findViewById(R.id.number);
        number.setTypeface(fontAwesomeFont);

       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://paper.jhumagolddiamond.com/direct/national_bangla");
            }
        });*/
        ShowRecords();
        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MyDonor.this);
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

    private void ShowRecords() {
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data = new dataAdapter(this, contacts);
        progres.setVisibility(View.GONE);
        alert.setVisibility(View.GONE);
        gridView.setAdapter(data);
        int tot = gridView.getAdapter().getCount();
        String total = String.valueOf(tot);
        total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        number.setText("রক্তদাতার সংখ্যা : " + total + " জন");
        AnimationSet set = new AnimationSet(true);
        Animation animation = AnimationUtils.loadAnimation(MyDonor.this, R.anim.alpha1);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        gridView.setLayoutAnimation(controller);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);
                int idd = dataModel.getID();
                String id1 = String.valueOf(idd);
                //Toast.makeText(MyDonor.this, id1, Toast.LENGTH_SHORT).show();
                String date = dataModel.getDATE();
                //Toast.makeText(MyDonor.this, date, Toast.LENGTH_SHORT).show();
                String name = dataModel.getFName();
                //Toast.makeText(MyDonor.this, name, Toast.LENGTH_SHORT).show();
                String add = dataModel.getFAdd();
                //Toast.makeText(MyDonor.this, add, Toast.LENGTH_SHORT).show();
                String grp = dataModel.getFGrp();
                //Toast.makeText(MyDonor.this, grp, Toast.LENGTH_SHORT).show();
                String email = dataModel.getFEmail();
                //Toast.makeText(MyDonor.this, email, Toast.LENGTH_SHORT).show();
                String phone = dataModel.getFPhone();
                //Toast.makeText(MyDonor.this, phone, Toast.LENGTH_SHORT).show();
                String img = "";
                byte[] pic = dataModel.getImage();
                int l = pic.length;
                String imglength = String.valueOf(l);
                String imageString = Base64.encodeToString(pic, Base64.DEFAULT);
                //Toast.makeText(MyDonor.this, l+"", Toast.LENGTH_LONG).show();
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                Intent intent = new Intent(MyDonor.this, InformationPage.class);
                intent.putExtra("id", id1);
                intent.putExtra("date", date);
                intent.putExtra("name", name);
                intent.putExtra("add", add);
                intent.putExtra("grp", grp);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("img", bitmap);
                intent.putExtra("imglength", imglength);
                startActivity(intent);
                /*db.deleteContact(dataModel.getID());
                startActivity(getIntent());
                finish();*/
                //Toast.makeText(getApplicationContext(), String.valueOf(dataModel.getFName()), Toast.LENGTH_SHORT).show();
            }
        });
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
                        /*Intent intent1 = new Intent(getBaseContext(), MyDonor.class);
                        startActivity(intent1);
                        finish();*/
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
