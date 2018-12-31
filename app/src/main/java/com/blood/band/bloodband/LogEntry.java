package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class LogEntry extends AppCompatActivity {

    private TextView t1, t2;
    private ImageView menu, back, loco, go, logo;
    private Button member;
    private String answer, bg = "", div = "", dis = "", subdist = "", URL;
    private ListView dist_list;
    ArrayList<ProductDist> arrayList, arrayList1, arrayList2;
    ListView lv;
    private HttpClient Client;
    HttpGet httpget;
    private SwipeRefreshLayout swipe;
    private String id, grp, loc, date = "", birth = "", gender = "1", SetServerString, name;
    private ProgressBar progres;
    private TextView alert, jega, group, counting, division, dist, upazila, donate_date, birth_date;
    private EditText inputName, inputEmail, inputAddress, inputPhone, inputAge, inputPass, inputConPass;
    private TextInputLayout inputLayoutName, inputLayoutUnit, inputLayoutAge, inputLayoutPhone, inputLayoutAddress, inputLayoutPass, inputLayoutConpass;
    private AutoCompleteTextView text;
    private String[] languages = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    private int PICK_IMAGE_REQUEST = 1, mYear, mMonth, mDay;
    private Bitmap bitmap;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.log_entry_edit);

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

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_name);
        inputLayoutUnit = (TextInputLayout) findViewById(R.id.input_unit);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_address);
        inputLayoutAge = (TextInputLayout) findViewById(R.id.input_age);

        inputName = (EditText) findViewById(R.id.sign_name);
        inputName.setText(name);
        inputName.setFocusable(false);
        inputName.setClickable(false);
        inputEmail = (EditText) findViewById(R.id.sign_unit);
        inputAddress = (EditText) findViewById(R.id.sign_add);
        inputAge = (EditText) findViewById(R.id.sign_age);

        donate_date = (TextView) findViewById(R.id.sign_date);
        donate_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LogEntry.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                donate_date.setText(year + "-" + d + "-" + day);
                                date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioSexButton = (RadioButton) findViewById(checkedId);
                                                         //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                         gender = (String) radioSexButton.getText();
                                                         if (gender.equals("পুরুষ")) {
                                                             gender = "1";
                                                         }
                                                         if (gender.equals("মহিলা")) {
                                                             gender = "2";
                                                         }
                                                     }
                                                 }
        );

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream is = null;
                if (!validateName()) {
                    return;
                }
                if (!validateAddress()) {
                    return;
                }
                if (!validateEmail()) {
                    return;
                } else {
                    String name = inputName.getText().toString();
                    if(name.contains(" ")){
                        name = name.replace(" ", "%20");
                    }
                    String unit = inputEmail.getText().toString();
                    String add = inputAddress.getText().toString();
                    String age = inputAge.getText().toString();
                    String gender1 = gender;
                    if(add.contains(" ")){
                        add = add.replace(" ", "%20");
                    }
                    if (date.equals("রক্তদানের তারিখ *")) {
                        Toast.makeText(LogEntry.this, "রক্তদানের তারিখ দেওয়া হয়নি", Toast.LENGTH_LONG).show();
                    } else {
                        HttpClient Client = new DefaultHttpClient();

                        String URL = "http://www.bloodband.dhost247.net/direct/dodfnatsdfion_log_edsdffntsdfasdfry/2183jasgh?user_id=" + id + "&donation_date=" + date + "&donation_issue=" + unit + "&donation_place=" + add + "&donation_age=" + age + "&donation_gender=" + gender1;
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
                            Toast.makeText(LogEntry.this, "সফলভাবে তালিকাভূক্ত করা হয়েছে", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), LogReport.class);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            startActivity(intent);
                            finish();
        /*TextView result = (TextView) findViewById(R.id.result);
        result.setText(SetServerString + "\n" + URL);*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

                AlertDialog.Builder builder = new AlertDialog.Builder(LogEntry.this);
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

        if (email.isEmpty()) {
            inputLayoutUnit.setError("অনুগ্রহ করে রোগীর সমস্যাটি লিখুন");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutUnit.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError("অনুগ্রহ করে রক্তদানের ঠিকানা লিখুন");
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
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
}
