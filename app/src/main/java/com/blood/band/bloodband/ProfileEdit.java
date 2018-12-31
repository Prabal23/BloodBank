package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileEdit extends AppCompatActivity {

    private TextView t1, t2;
    private ImageView menu, back, loco, go, logo;
    private Button member;
    private String answer, bg = "", div = "", dis = "", subdist = "", active;
    private GridView dist_list;
    ArrayList<ProductDist> arrayList, arrayList1, arrayList2, arrayList3, arrayList4, arrayList5;
    ListView lv;
    private HttpClient Client;
    HttpGet httpget;
    private SwipeRefreshLayout swipe;
    private String username, grp, loc, date = "", birth = "", gender = "1", SetServerString;
    private ProgressBar progres;
    private TextView alert, jega, group, counting, division, dist, upazila, donate_date, birth_date;
    private EditText inputName, inputEmail, inputAddress, inputPhone, inputUser, inputPass, inputConPass;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutGroup, inputLayoutPhone, inputLayoutAddress, inputLayoutUser, inputLayoutConpass, inputLayoutPass;
    private AutoCompleteTextView text;
    private String[] languages = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    private int PICK_IMAGE_REQUEST = 1, mYear, mMonth, mDay;
    private Bitmap bitmap;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private String id, name, contact, email, address, bd, ld, gp, divi, district, subdistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.profile_edit);

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

        logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        t2 = (TextView) findViewById(R.id.t2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        contact = getIntent().getStringExtra("phone");
        gender = getIntent().getStringExtra("gender");
        email = getIntent().getStringExtra("email");
        div = getIntent().getStringExtra("division");
        dis = getIntent().getStringExtra("district");
        subdist = getIntent().getStringExtra("subdistrict");
        address = getIntent().getStringExtra("address");
        birth = getIntent().getStringExtra("bd");
        String b = getIntent().getStringExtra("bd");
        date = getIntent().getStringExtra("ld");
        String l = getIntent().getStringExtra("ld");
        gp = getIntent().getStringExtra("gp");
        gp = gp.replace("(", "").replace(")", "");
        active = getIntent().getStringExtra("interest");
        username = getIntent().getStringExtra("username");

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_mail);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_address);
        inputLayoutGroup = (TextInputLayout) findViewById(R.id.input_grp);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_phn);
        inputLayoutUser = (TextInputLayout) findViewById(R.id.input_user);

        inputName = (EditText) findViewById(R.id.sign_name);
        inputName.setText(name);
        inputEmail = (EditText) findViewById(R.id.sign_mail);
        //inputEmail.setClickable(false);
        //inputEmail.setFocusable(false);
        inputEmail.setText(email);
        inputAddress = (EditText) findViewById(R.id.sign_add);
        inputAddress.setText(address);
        inputUser = (EditText) findViewById(R.id.sign_user);
        inputUser.setText(username);
        inputPhone = (EditText) findViewById(R.id.sign_phn);
        inputPhone.setText(contact);
        counting = (TextView) findViewById(R.id.counting);
        final int maxLength = 11;
        inputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // this will show characters remaining
                counting.setText(11 - s.toString().length() + "/11");
                if (s.length() >= maxLength) s.delete(maxLength, s.length());
            }
        });

        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        text.setText(gp);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, languages);

        text.setAdapter(adapter);
        text.setThreshold(1);

        division = (TextView) findViewById(R.id.sign_div);
        //division.setText(div);
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Division attendance = new Division();
                attendance.showdialog(ProfileEdit.this, "বিভাগ বাছাই করুন");
            }
        });

        dist = (TextView) findViewById(R.id.sign_jela);
        //dist.setText(dis);
        dist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District attendance = new District();
                attendance.showdialog(ProfileEdit.this, "জেলা বাছাই করুন");
            }
        });

        upazila = (TextView) findViewById(R.id.sign_upojela);
        //upazila.setText(subdist);
        upazila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubDistrict attendance = new SubDistrict();
                attendance.showdialog(ProfileEdit.this, "উপজেলা বাছাই করুন");
            }
        });

        donate_date = (TextView) findViewById(R.id.sign_donate_date);
        if (date.equals("0000-00-00")) {
            date = "";
            donate_date.setText("সর্বশেষ রক্তদানের তারিখ" + date);
        } else {
            l = l.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            donate_date.setText("সর্বশেষ রক্তদানের তারিখ : " + l);
        }
        donate_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileEdit.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                String y = String.valueOf(year);
                                y = y.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String m = String.valueOf(d);
                                m = m.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String dd = String.valueOf(day);
                                dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                donate_date.setText("সর্বশেষ রক্তদানের তারিখ : " + y + "-" + m + "-" + dd);
                                date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        birth_date = (TextView) findViewById(R.id.sign_date);
        b = b.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
        birth_date.setText("জন্ম তারিখ : " + b);
        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileEdit.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                String y = String.valueOf(year);
                                y = y.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String m = String.valueOf(d);
                                m = m.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String dd = String.valueOf(day);
                                dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                birth_date.setText("জন্ম তারিখ : " + y + "-" + m + "-" + dd);
                                birth = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        RadioButton male = (RadioButton) findViewById(R.id.radioMale);
        RadioButton female = (RadioButton) findViewById(R.id.radioFemale);
        if (gender.equals("1")) {
            radioSexGroup.check(R.id.radioMale);
        }
        if (gender.equals("2")) {
            radioSexGroup.check(R.id.radioFemale);
        }
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioSexButton = (RadioButton) findViewById(checkedId);
                                                         //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                         gender = (String) radioSexButton.getText();
                                                         if (gender.equals("পুরুষ")) {
                                                             gender = "1";
                                                             //Toast.makeText(BeMember.this, gender, Toast.LENGTH_SHORT).show();
                                                         }
                                                         if (gender.equals("মহিলা")) {
                                                             gender = "2";
                                                             //Toast.makeText(BeMember.this, gender, Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 }
        );
        //Toast.makeText(BeMember.this, gender, Toast.LENGTH_SHORT).show();

        Switch interest = (Switch) findViewById(R.id.switchButton);
        if (active.equals("1")) {
            interest.setChecked(true);
        }
        interest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    active = "1";
                    //Toast.makeText(BeMember.this, active, Toast.LENGTH_SHORT).show();
                } else {
                    active = "2";
                    //Toast.makeText(BeMember.this, active, Toast.LENGTH_SHORT).show();
                }
            }
        });

        arrayList3 = new ArrayList<>();
        arrayList4 = new ArrayList<>();
        arrayList5 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONDivision1().execute("http://www.bloodband.dhost247.net/direct/disdfgsdfgsdfgvasdfasdfasdfgsdfision_sdfgsdflisadsfadsfasdfasdft/293hsdk");
                //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONDistrict1().execute("http://www.bloodband.dhost247.net/direct/asdfdasdfadsistridfasdfct_asdfasdfasddflist/293hsdk");
                //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONSubDistrict1().execute("http://www.bloodband.dhost247.net/direct/udfasdfpasdfasdfzila_sdfasdfasdfasdlisdfasdfst/293hsdk");
                //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
            }
        });

        member = (Button) findViewById(R.id.member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (date.equals("") || birth.equals("") || gender.equals("")) {
                    Toast.makeText(ProfileEdit.this, "অনুগ্রহ করে সবগুলো তথ্য পূরণ করুন", Toast.LENGTH_LONG).show();
                } else {
                    submitForm();
                }
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEdit.this);
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

    public class Division {

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
            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSONDivision().execute("http://www.bloodband.dhost247.net/direct/disdfgsdfgsdfgvasdfasdfasdfgsdfision_sdfgsdflisadsfadsfasdfasdft/293hsdk");
                    //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                }
            });
            dist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    div = arrayList.get(i).getID();
                    //Toast.makeText(OverallReport.this, cls, Toast.LENGTH_SHORT).show();
                    String clsname = arrayList.get(i).getName();
                    division.setText(clsname);
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

    class ReadJSONDivision extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jObject = new JSONObject(content);
                JSONArray jsonArray = jObject.getJSONArray("division_list");
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
        }
    }

    class ReadJSONDivision1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jObject = new JSONObject(content);
                JSONArray jsonArray = jObject.getJSONArray("division_list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList3.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList3.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList3.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                    if (class_code.equals(div)) {
                        division.setText(class_name);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            dist_list = (GridView) dialog.findViewById(R.id.gridView);
            arrayList1 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSONDistrict().execute("http://www.bloodband.dhost247.net/direct/dasdfistrsdfictasdf_agasdfaasdfiasdfnst_dsfddafsdfiasdfvsdfisiosdfn/923hd?division_id=" + div);
                    //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                }
            });
            dist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    dis = arrayList1.get(i).getID();
                    //Toast.makeText(OverallReport.this, cls, Toast.LENGTH_SHORT).show();
                    String clsname = arrayList1.get(i).getName();
                    dist.setText(clsname);
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
                JSONArray jsonArray = jObject.getJSONArray("district_against_division");
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

    class ReadJSONDistrict1 extends AsyncTask<String, Integer, String> {

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
                    arrayList4.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList4.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList4.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                    if (class_code.equals(dis)) {
                        dist.setText(class_name);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            arrayList2 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSONSubDistrict().execute("http://www.bloodband.dhost247.net/direct/dsfupaasdfzila_dasfaasdfgainasdfsdfstadsfasdfdisasdftasdfrict/923hd?district_id=" + dis);
                    //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
                }
            });
            dist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    subdist = arrayList2.get(i).getID();
                    //Toast.makeText(OverallReport.this, cls, Toast.LENGTH_SHORT).show();
                    String clsname = arrayList2.get(i).getName();
                    upazila.setText(clsname);
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
                    arrayList2.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList2.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList2.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final CustomListAdapterDist adapter = new CustomListAdapterDist(
                    getApplicationContext(), R.layout.dist_list_item, arrayList2
            );
            dist_list.setAdapter(adapter);
        }
    }

    class ReadJSONSubDistrict1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jObject = new JSONObject(content);
                JSONArray jsonArray = jObject.getJSONArray("upazila_list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList5.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList5.get(i).getID();
                    //Toast.makeText(OverallReport.this, class_code, Toast.LENGTH_SHORT).show();
                    String class_name = arrayList5.get(i).getName();
                    //Toast.makeText(OverallReport.this, class_name, Toast.LENGTH_SHORT).show();
                    if (class_code.equals(subdist)) {
                        upazila.setText(class_name);
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
        }
        return content.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                logo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateAddress()) {
            return;
        }

        if (!validateGroup()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        //Toast.makeText(getApplicationContext(), "ব্লাড ব্যান্ডের সদস্য হওয়ার জন্য ধন্যবাদ!", Toast.LENGTH_SHORT).show();
    }

    public class ViewDialog {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError(getString(R.string.err_msg_add));
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateGroup() {
        if (text.getText().toString().trim().isEmpty()) {
            inputLayoutGroup.setError(getString(R.string.err_msg_grp));
            requestFocus(text);
            return false;
        } else {
            inputLayoutGroup.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_num));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        String letter = inputPhone.getText().toString();
        int length = letter.length();
        String letters = String.valueOf(length);
        if (div.equals("") || dis.equals("") || subdist.equals("")) {
            Toast.makeText(this, "অনুগ্রহ করে সবগুলো তথ্য পূরণ করুন", Toast.LENGTH_LONG).show();
        }
        if (letters.equals("11")) {
            /*Password alert = new Password();
            alert.showDialog(ProfileEdit.this, "আপনার পাসওয়ার্ড প্রদান করুন");*/
            String name = inputName.getText().toString();
            if (name.contains(" ")) {
                name = name.replace(" ", "%20");
            }
            String address = inputAddress.getText().toString();
            if (address.contains(" ")) {
                address = address.replace(" ", "%20");
            }
            String bgid = text.getText().toString();
            if (bgid.equals("A+")) {
                bgid = "1";
            }
            if (bgid.equals("A-")) {
                bgid = "2";
            }
            if (bgid.equals("O+")) {
                bgid = "3";
            }
            if (bgid.equals("O-")) {
                bgid = "4";
            }
            if (bgid.equals("B+")) {
                bgid = "5";
            }
            if (bgid.equals("B-")) {
                bgid = "6";
            }
            if (bgid.equals("AB+")) {
                bgid = "7";
            }
            if (bgid.equals("AB-")) {
                bgid = "8";
            }
            String email = inputEmail.getText().toString();
            String phone = inputPhone.getText().toString();
            String username = inputUser.getText().toString();

            Client = new DefaultHttpClient();

            String URL = "http://www.bloodband.dhost247.net/direct/sdafssdfinglsdafe_sdfdasdfosdfnorasd_asdfiasdfnfoasdfrmatiosdfn_asdfupasdfdasdfateasdf/2183jasgh?name=" + name + "&address=" + address + "&blood_group=" + bgid + "&birth_date=" + birth + "&gender=" + gender + "&division=" + div + "&district=" + dis + "&upazila=" + subdist + "&email=" + email + "&phone=" + phone + "&last_donation=" + date + "&user_id=" + id + "&interest=" + active + "user_name=" + username;
            //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
            SetServerString = "";

            httpget = new HttpGet(URL);
            HttpResponse response = null;
            try {
                response = Client.execute(httpget);
                HttpEntity httpEntity = response.getEntity();
                String res = EntityUtils.toString(httpEntity);
                //Toast.makeText(ProfileEdit.this, res, Toast.LENGTH_LONG).show();
                if (res.contains("Email already exist.")) {
                    Toast.makeText(ProfileEdit.this, "এই ই-মেইল দিয়ে পূর্বেই রেজিস্টার করা হয়েছে!", Toast.LENGTH_LONG).show();
                } else {
                    Insert();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getBaseContext(), "অনুগ্রহপূর্বক মোবাইল নাম্বার সঠিকভাবে লিখুন", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class Password {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.password_confirmation);

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText(msg);

            inputLayoutPass = (TextInputLayout) dialog.findViewById(R.id.input_pass);
            inputLayoutConpass = (TextInputLayout) dialog.findViewById(R.id.input_pass1);

            inputPass = (EditText) dialog.findViewById(R.id.sign_pass);
            inputConPass = (EditText) dialog.findViewById(R.id.sign_pass1);

            Button dialogButton = (Button) dialog.findViewById(R.id.member);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = inputName.getText().toString();
                    String address = inputAddress.getText().toString();
                    String bgid = text.getText().toString();
                    if (bgid.equals("A+")) {
                        bgid = "1";
                    }
                    if (bgid.equals("A-")) {
                        bgid = "2";
                    }
                    if (bgid.equals("O+")) {
                        bgid = "3";
                    }
                    if (bgid.equals("O-")) {
                        bgid = "4";
                    }
                    if (bgid.equals("B+")) {
                        bgid = "5";
                    }
                    if (bgid.equals("B-")) {
                        bgid = "6";
                    }
                    if (bgid.equals("AB+")) {
                        bgid = "7";
                    }
                    if (bgid.equals("AB-")) {
                        bgid = "8";
                    }
                    String email = inputEmail.getText().toString();
                    String phone = inputPhone.getText().toString();
                    String passes = inputPass.getText().toString();
                    String conpasses = inputConPass.getText().toString();
                    if (passes.equals("")) {
                        Toast.makeText(getBaseContext(), "পাসওয়ার্ড দেওয়া হয়নি", Toast.LENGTH_LONG).show();
                    } else if (conpasses.equals("")) {
                        Toast.makeText(getBaseContext(), "পাসওয়ার্ড নিশ্চিত করা হয়নি", Toast.LENGTH_LONG).show();
                    } else if (!passes.equals(conpasses)) {
                        Toast.makeText(getBaseContext(), "পাসওয়ার্ড দুটো সঠিক হয়নি", Toast.LENGTH_LONG).show();
                    } else {
                        Client = new DefaultHttpClient();

                        String URL = "http://www.bloodband.dhost247.net/direct/sdafssdfinglsdafe_sdfdasdfosdfnorasd_asdfiasdfnfoasdfrmatiosdfn_asdfupasdfdasdfateasdf/2183jasgh?name=" + name + "&address=" + address + "&blood_group=" + bgid + "&birth_date=" + birth + "&gender=" + gender + "&division=" + div + "&district=" + dis + "&upazila=" + subdist + "&email=" + email + "&phone=" + phone + "&last_donation=" + date + "&password=" + passes + "&user_id=" + id;
                        //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
                        SetServerString = "";

                        httpget = new HttpGet(URL);
                        HttpResponse response = null;
                        try {
                            response = Client.execute(httpget);
                            HttpEntity httpEntity = response.getEntity();
                            String res = EntityUtils.toString(httpEntity);
                            //Toast.makeText(BeMember.this, res, Toast.LENGTH_SHORT).show();
                            if (res.contains("Email already exist.")) {
                                Toast.makeText(ProfileEdit.this, "এই ই-মেইল দিয়ে পূর্বেই রেজিস্টার করা হয়েছে!", Toast.LENGTH_LONG).show();
                            } else {
                                Insert();
                                dialog.dismiss();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            dialog.show();

        }
    }

    public void Insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            //Toast.makeText(ProfileEdit.this, SetServerString, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "সফলভাবে এডিট করা হয়েছে", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), MyProfile.class);
        startActivity(intent);
        finish();
    }
}
