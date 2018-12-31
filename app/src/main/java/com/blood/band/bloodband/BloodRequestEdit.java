package com.blood.band.bloodband;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BloodRequestEdit extends AppCompatActivity {

    private TextView t1, t2, t3;
    private ImageView menu, back, loco, go, logo;
    private Button member;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, letters, reqid, amt, dondate, from, pass, loc, dis, contact, msg, upo;
    private ProgressBar progres;
    private TextView alert, jega, group, counting, donate_date, zila;
    private EditText inputName, inputEmail, inputPlace, inputAmount, inputPhone, inputGroup, inputPass;
    private TextInputLayout inputLayoutName, inputLayoutMessage, inputLayoutGroup, inputLayoutPhone, inputLayoutPlace, inputLayoutAmount, inputLayoutPass;
    private AutoCompleteTextView text;
    private String answer, date, urgent = "2";
    private String[] languages = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    private int PICK_IMAGE_REQUEST = 1, mYear, mMonth, mDay;
    private Bitmap bitmap;
    private String bg = "", dist = "", subdist = "";
    private GridView dist_list;
    private CheckBox checkBox;
    ArrayList<ProductDist> arrayList, arrayList1, arrayList2, arrayList3, arrayList4, arrayList5, arrayList6, arrayList7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.blood_request_edit);

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

        reqid = getIntent().getStringExtra("reqid");
        amt = getIntent().getStringExtra("amount");
        date = getIntent().getStringExtra("dondate");
        from = getIntent().getStringExtra("from");
        pass = getIntent().getStringExtra("pass");
        loc = getIntent().getStringExtra("location");
        dist = getIntent().getStringExtra("district");
        contact = getIntent().getStringExtra("number");
        msg = getIntent().getStringExtra("message");
        urgent = getIntent().getStringExtra("type");
        bg = getIntent().getStringExtra("bg");
        bg = bg.replace("(", "").replace(")", "");
        upo = getIntent().getStringExtra("upazila");

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_name);
        inputLayoutMessage = (TextInputLayout) findViewById(R.id.input_mail);
        inputLayoutGroup = (TextInputLayout) findViewById(R.id.input_grp);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_phn);
        inputLayoutPlace = (TextInputLayout) findViewById(R.id.input_place);
        inputLayoutAmount = (TextInputLayout) findViewById(R.id.input_amount);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_pass);

        inputName = (EditText) findViewById(R.id.sign_name);
        inputName.setText(from);
        inputEmail = (EditText) findViewById(R.id.sign_mail);
        inputEmail.setText(msg);
        inputPhone = (EditText) findViewById(R.id.sign_phn);
        inputPhone.setText(contact);
        inputAmount = (EditText) findViewById(R.id.sign_amount);
        inputAmount.setText(amt);
        inputPlace = (EditText) findViewById(R.id.sign_place);
        inputPlace.setText(loc);
        inputPass = (EditText) findViewById(R.id.sign_pass);
        inputPass.setText(pass);
        checkBox = (CheckBox) findViewById(R.id.check);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    urgent = "1";
                    //Toast.makeText(BloodRequestEdit.this, urgent, Toast.LENGTH_LONG).show();
                }
            }
        });
        if (urgent.equals("1")) {
            checkBox.setChecked(true);
        }
        counting = (TextView) findViewById(R.id.counting);

        donate_date = (TextView) findViewById(R.id.sign_date);
        donate_date.setText("রক্তদানের তারিখ : "+date);
        donate_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BloodRequestEdit.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                donate_date.setText("রক্তদানের তারিখ : "+year + "-" + d + "-" + day);
                                date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        zila = (TextView) findViewById(R.id.sign_add);
        zila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District attendance = new District();
                attendance.showdialog(BloodRequestEdit.this, "জেলা বাছাই করুন");
            }
        });

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
        text.setText(bg);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, languages);

        text.setAdapter(adapter);
        text.setThreshold(1);

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONDistrict1().execute("http://www.bloodband.dhost247.net/direct/asdfdasdfadsistridfasdfct_asdfasdfasddflist/293hsdk");
                //new ReadJSON().execute("http://192.168.1.7/pathshala/getstd.php");
            }
        });

        member = (Button) findViewById(R.id.member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        Button del = (Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete alert1 = new Delete();
                alert1.showDialog(BloodRequestEdit.this, "");
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodRequestEdit.this);
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
                    HttpClient Client = new DefaultHttpClient();

                    String URL = "http://www.bloodband.dhost247.net/direct/bsdfloosdffsdfd_readsfquasdfeasdfstasdf_dasdfeledsftedfasdf/q13846?request_id=" + reqid;
                    //Toast.makeText(this, URL, Toast.LENGTH_LONG).show();
                    String SetServerString = "";

                    HttpGet httpget = new HttpGet(URL);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    try {
                        SetServerString = Client.execute(httpget, responseHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(BloodRequestEdit.this, "সফলভাবে মুছে ফেলা হয়েছে", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BloodRequestEdit.this, BloodRequestReport.class);
                    startActivity(intent);
                    finish();
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

    public class District {

        public void showdialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.division_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String formattedDate = df.format(c.getTime());

            TextView title1 = (TextView)dialog.findViewById(R.id.title);
            title1.setText(title);
            dist_list = (GridView) dialog.findViewById(R.id.gridView);
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
                    zila.setText(clsname);
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
                    String class_name = arrayList.get(i).getName();
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
                    arrayList.add(new ProductDist(
                            productObject.getString("id"),
                            productObject.getString("name")
                    ));
                    String class_code = arrayList.get(i).getID();
                    String class_name = arrayList.get(i).getName();
                    if (class_code.equals(dist)) {
                        zila.setText(class_name);
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

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateGroup()) {
            return;
        }

        if (!validateAmount()) {
            return;
        }

        if (!validateAddress()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        if (!validatePass()) {
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
                    Intent intent = new Intent(BloodRequestEdit.this, BloodRequestReport.class);
                    startActivity(intent);
                    finish();
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

        if (email.isEmpty()) {
            inputLayoutMessage.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutMessage.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (inputPlace.getText().toString().trim().isEmpty()) {
            inputLayoutPlace.setError(getString(R.string.err_msg_add));
            requestFocus(inputPlace);
            return false;
        } else {
            inputLayoutPlace.setErrorEnabled(false);
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

    private boolean validateAmount() {
        if (inputAmount.getText().toString().trim().isEmpty()) {
            inputLayoutAmount.setError("অনুগ্রহ করে রক্তের পরিমাণ উল্লেখ করুন");
            requestFocus(inputAmount);
            return false;
        } else {
            inputLayoutAmount.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePass() {
        if (inputPass.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError("অনুগ্রহ করে পাসওয়ার্ডটি লিখুন");
            requestFocus(inputPass);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }
        String letter = inputPass.getText().toString();
        if (letters.equals("11") && !letter.equals("")) {

            submitResult();
        } else {
            Toast.makeText(getBaseContext(), "অনুগ্রহপূর্বক মোবাইল নাম্বার সঠিকভাবে লিখুন", Toast.LENGTH_LONG).show();
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
        letters = String.valueOf(length);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void submitResult() {
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

        String name = inputName.getText().toString();
        if(name.contains(" ")){
            name = name.replace(" ", "%20");
        }
        String amount = inputAmount.getText().toString();
        if(amount.contains(" ")){
            amount = amount.replace(" ", "%20");
        }
        String place = inputPlace.getText().toString();
        if(place.contains(" ")){
            place = place.replace(" ", "%20");
        }
        String message = inputEmail.getText().toString();
        if(message.contains(" ")){
            message = message.replace(" ", "%20");
        }
        String phone = inputPhone.getText().toString();
        String pass = inputPass.getText().toString();
        String jela = zila.getText().toString();
        String don_date = donate_date.getText().toString();

        if (name.equals("") || jela.equals("জেলা *") || bgid.equals("") || amount.equals("") || place.equals("") || don_date.equals("রক্তদানের তারিখ *") || message.equals("") || phone.equals("") || pass.equals("")) {
            Toast.makeText(this, "অনুগ্রহ করে সবগুলো তথ্য পূরণ করুন", Toast.LENGTH_SHORT).show();
        } else {
            HttpClient Client = new DefaultHttpClient();

            String URL = "http://www.bloodband.dhost247.net/direct/basdfasdflooasdfasdfd_adsfreqasdfasdfueasdfasdfst_easdfadiasdgasdft/q13846?blood_group=" + bgid + "&blood_amount=" + amount + "&donation_date=" + date + "&your_name=" + name + "&patient_location=" + place + "&patient_district=" + dist + "&message=" + message + "&contact=" + phone + "&password=" + pass + "&request_id=" + reqid + "&type=" + urgent;
            //Toast.makeText(this, URL, Toast.LENGTH_LONG).show();
            String SetServerString = "";

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = Client.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ViewDialog alert = new ViewDialog();
            alert.showDialog(BloodRequestEdit.this, "রক্তদানের অনুরোধ সম্পাদিত হয়েছে।\n\nঅনুগ্রহপূর্বক আপনার পাসওয়ার্দটি সংরক্ষণে রাখুন। এটি পরবর্তিতে প্রয়োজন হতে পারে।");
        }
    }
}
