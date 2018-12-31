package com.blood.band.bloodband;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.droidsonroids.gif.GifImageView;

public class RatingbyMe extends AppCompatActivity {
    String rate = "0", dn_date = "", res, id, pass, name, address, email, phoneno, phonebd, bg = "", userid = "", username, f3, f4, f5, f6, f7, f8, r1, r2, r3, r4, r5;
    private int PICK_IMAGE_REQUEST = 1, mYear, mMonth, mDay;
    ImageView menu, back, error;
    ArrayList<ProductUserEdit> arrayList;
    ProgressBar progressBar, progres;
    TextView alert, erroralert, count;
    TextInputLayout nameInput, addInput, relInput, emailInput, phoneInput;
    EditText nameText, addressText, relationText, phoneText, emailText;
    TextView donorname;
    GridView gridView;
    ArrayList<Product> arrayList1;
    GifImageView loader;
    CheckBox c, c1, c2, c3, c4, c5;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_donor_me);

        SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Links.EMAIL_SHARED_PREF)) {
            res = (sharedPreferences).getString(Links.EMAIL_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        pass = result[1];
        //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();

        donorname = (TextView) findViewById(R.id.name);
        donorname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Donor attendance = new Donor();
                attendance.showdialog(RatingbyMe.this, "");
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView title = (TextView) findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setPaintFlags(subtitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        nameInput = (TextInputLayout) findViewById(R.id.input_name);
        addInput = (TextInputLayout) findViewById(R.id.input_address);
        relInput = (TextInputLayout) findViewById(R.id.input_relation);
        emailInput = (TextInputLayout) findViewById(R.id.input_email);
        phoneInput = (TextInputLayout) findViewById(R.id.input_phone);

        nameText = (EditText) findViewById(R.id.sign_name);
        addressText = (EditText) findViewById(R.id.sign_address);
        relationText = (EditText) findViewById(R.id.sign_relation);
        emailText = (EditText) findViewById(R.id.sign_email);
        phoneText = (EditText) findViewById(R.id.sign_phone);

        final TextView date = (TextView) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RatingbyMe.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                String y = String.valueOf(year);
                                y = y.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String m = String.valueOf(d);
                                m = m.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                String dd = String.valueOf(day);
                                dd = dd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                date.setText("রক্তদানের তারিখ : " + y + "-" + m + "-" + dd);
                                dn_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        c = (CheckBox) findViewById(R.id.check);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f3 = "1";
                } else {
                    f3 = "0";
                }
            }
        });
        c1 = (CheckBox) findViewById(R.id.check1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f4 = "1";
                } else {
                    f4 = "0";
                }
            }
        });
        c2 = (CheckBox) findViewById(R.id.check2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f5 = "1";
                } else {
                    f5 = "1";
                }
            }
        });
        c3 = (CheckBox) findViewById(R.id.check3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f6 = "1";
                } else {
                    f6 = "1";
                }
            }
        });
        c4 = (CheckBox) findViewById(R.id.check4);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f7 = "1";
                } else {
                    f7 = "0";
                }
            }
        });
        c5 = (CheckBox) findViewById(R.id.check5);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    f8 = "1";
                } else {
                    f8 = "0";
                }
            }
        });

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBarSmall);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rate = String.valueOf(rating);
                //Toast.makeText(getApplicationContext(), "Rating : " + rate, Toast.LENGTH_LONG).show();

            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitResult();
            }
        });

        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        alert.setVisibility(View.VISIBLE);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(RatingbyMe.this);
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
                    address = arrayList.get(i).getAddress();
                    email = arrayList.get(i).getMail();
                    phoneno = arrayList.get(i).getPhn();
                    phonebd = arrayList.get(i).getPhn();
                    phonebd = phonebd.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                }
                nameText.setText(name);
                addressText.setText(address);
                emailText.setText(email);
                phoneText.setText(phonebd);
                nameText.setClickable(false);
                nameText.setFocusable(false);
                addressText.setClickable(false);
                addressText.setFocusable(false);
                emailText.setClickable(false);
                emailText.setFocusable(false);
                phoneText.setClickable(false);
                phoneText.setFocusable(false);

            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
        }
    }

    public class Donor {

        public void showdialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.donor_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            count = (TextView) dialog.findViewById(R.id.count);
            gridView = (GridView) dialog.findViewById(R.id.gridView);
            loader = (GifImageView) dialog.findViewById(R.id.loader);
            final TextView group = (TextView) dialog.findViewById(R.id.group);
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = RatingbyMe.this.getLayoutInflater();
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RatingbyMe.this);
                    final View dialogView = inflater.inflate(R.layout.blood_list, null);
                    builder.setView(dialogView);
                    final android.app.AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    TextView ap = (TextView) dialogView.findViewById(R.id.ap);
                    LinearLayout ll11 = (LinearLayout) dialogView.findViewById(R.id.l11);
                    ll11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("A+");
                            bg = "1";
                            dialog.dismiss();
                        }
                    });
                    TextView an = (TextView) dialogView.findViewById(R.id.an);
                    LinearLayout ll12 = (LinearLayout) dialogView.findViewById(R.id.l12);
                    ll12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("A-");
                            bg = "2";
                            dialog.dismiss();
                        }
                    });
                    TextView bp = (TextView) dialogView.findViewById(R.id.bp);
                    LinearLayout ll13 = (LinearLayout) dialogView.findViewById(R.id.l13);
                    ll13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("B+");
                            bg = "5";
                            dialog.dismiss();
                        }
                    });
                    TextView bn = (TextView) dialogView.findViewById(R.id.bn);
                    LinearLayout ll14 = (LinearLayout) dialogView.findViewById(R.id.l14);
                    ll14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("B-");
                            bg = "6";
                            dialog.dismiss();
                        }
                    });
                    TextView op = (TextView) dialogView.findViewById(R.id.op);
                    LinearLayout ll21 = (LinearLayout) dialogView.findViewById(R.id.l21);
                    ll21.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("O+");
                            bg = "3";
                            dialog.dismiss();
                        }
                    });
                    TextView on = (TextView) dialogView.findViewById(R.id.on);
                    LinearLayout ll22 = (LinearLayout) dialogView.findViewById(R.id.l22);
                    ll22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("O-");
                            bg = "4";
                            dialog.dismiss();
                        }
                    });
                    TextView abp = (TextView) dialogView.findViewById(R.id.abp);
                    LinearLayout ll23 = (LinearLayout) dialogView.findViewById(R.id.l23);
                    ll23.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("AB+");
                            bg = "7";
                            dialog.dismiss();
                        }
                    });
                    TextView abn = (TextView) dialogView.findViewById(R.id.abn);
                    LinearLayout ll24 = (LinearLayout) dialogView.findViewById(R.id.l24);
                    ll24.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            group.setText("AB-");
                            bg = "8";
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

            if (bg.equals("")) {
                group.setText("রক্তের গ্রুপ বাছাই করুন");
            }
            if (bg.equals("1")) {
                group.setText("A+");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("2")) {
                group.setText("A-");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("3")) {
                group.setText("O+");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("4")) {
                group.setText("O-");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("5")) {
                group.setText("B+");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("6")) {
                group.setText("B-");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("7")) {
                group.setText("AB+");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }
            if (bg.equals("8")) {
                group.setText("AB-");
                loader.setVisibility(View.VISIBLE);
                arrayList1 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                        //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                    }
                });
            }

            ImageView search = (ImageView) dialog.findViewById(R.id.search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loader.setVisibility(View.VISIBLE);
                    arrayList1 = new ArrayList<>();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new ReadJSON1().execute("http://www.bloodband.dhost247.net/direct/fghjfghvigdfghdfgsrtweritadfas_asdfasdfasdfrepasdfasdfasdfortdfasdfasdf/923hd?blood_group=" + bg + "&district_id=&upazila_id=");
                            //new ReadJSON1().execute("http://paper.jhumagolddiamond.com/paper/direct/local_english");
                        }
                    });
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    userid = arrayList1.get(i).getId();
                    username = arrayList1.get(i).getName();
                    //Toast.makeText(RateDonor.this, userid, Toast.LENGTH_SHORT).show();
                    if(id.equals(userid)){
                        Toast.makeText(RatingbyMe.this, "নিজের মূল্যায়ন করতে পারবেন না", Toast.LENGTH_SHORT).show();
                    }else{
                        donorname.setText(username);
                        dialog.dismiss();
                    }
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

    class ReadJSON1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                loader.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("multi_donor_report");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new Product(
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
            }
            CustomListAdapterDonor adapter = new CustomListAdapterDonor(
                    getApplicationContext(), R.layout.user_list_item, arrayList1
            );
            gridView.setAdapter(adapter);
            int tot = gridView.getAdapter().getCount();
            String total = String.valueOf(tot);
            total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            count.setText("রক্তদাতার সংখ্যা - " + total);
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(RatingbyMe.this,
                    R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(
                    set, 0.5f);
            gridView.setLayoutAnimation(controller);
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

    public void submitResult() {
        String name = nameText.getText().toString();
        String add = addressText.getText().toString();
        String rel = relationText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();

        if (userid.equals("")) {
            Toast.makeText(this, "রক্তদাতা বাছাই করুন", Toast.LENGTH_LONG).show();
        }
        else if (dn_date.equals("")) {
            Toast.makeText(this, "রক্রদানের তারিখ বাছাই করুন", Toast.LENGTH_LONG).show();
        }
        else if (rate.equals("0") || rate.equals("0.0")) {
            Toast.makeText(this, "রক্তদাতাকে রেটিং দিন", Toast.LENGTH_LONG).show();
        }
        else if (name.equals("")) {
            Toast.makeText(this, "আপনার নাম লিখুন", Toast.LENGTH_LONG).show();
        }
        else if (add.equals("")) {
            Toast.makeText(this, "আপনার ঠিকানা লিখুন", Toast.LENGTH_LONG).show();
        }
        else if (rel.equals("")) {
            Toast.makeText(this, "রোগীর সাথে আপনার সম্পর্কটি লিখুন", Toast.LENGTH_LONG).show();
        }
        else if (phone.equals("")) {
            Toast.makeText(this, "আপনার ফোন নম্বারটি লিখুন", Toast.LENGTH_LONG).show();
        } else {
            HttpClient Client = new DefaultHttpClient();

            String URL = "http://www.bloodband.dhost247.net/direct/fasdfeedsdfasdbsdfacasdfk_easdfasdntasdfry/923hd?f1=" + userid + "&f2=" + dn_date + "&f3=" + f3 + "&f4=" + f4 + "&f5=" + f5 + "&f6=" + f6 + "&f7=" + f7 + "&f8=" + f8 + "&f9=" + rate + "&r1=" + name + "&r2=" + add + "&r3=" + rel + "&r4=" + email + "&r5=" + phone;
            //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
            String SetServerString = "";

            HttpGet httpget = new HttpGet(URL);
            HttpResponse response = null;
            try {
                response = Client.execute(httpget);
                HttpEntity httpEntity = response.getEntity();
                String res = EntityUtils.toString(httpEntity);
                //Toast.makeText(BeMember.this, res, Toast.LENGTH_SHORT).show();
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    SetServerString = Client.execute(httpget, responseHandler);
                    //Toast.makeText(LogEntry.this, SetServerString, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "সফলভাবে রক্তদাতার মূল্যায়ন করা হয়েছে", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                    Intent intent2 = new Intent(getBaseContext(), LoginProfile.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };

}
