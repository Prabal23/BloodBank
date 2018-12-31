package com.blood.band.bloodband;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginProfile extends AppCompatActivity {

    Button submit;
    ImageView menu, back;
    private TextInputLayout inputusername, inputpassword;
    private EditText inputuser, inputpass;
    boolean loggedIn = false;
    private String user, password, except_double, res, username, userid, url;
    private ProgressBar progres;
    private TextView alert, jega, group, erroralert, pass;
    private static final String TAG = MainActivity.class.getSimpleName();
    protected static final SharedPreferences settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*url = "http://www.hub.dhost247.net";
        //url = "http://192.168.0.110";
        // Progress dialog
        progres = (ProgressBar) findViewById(R.id.progressBar);
        alert = (TextView) findViewById(R.id.alert);*/

        pass = (TextView)findViewById(R.id.pass);
        pass.setPaintFlags(pass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        inputusername = (TextInputLayout) findViewById(R.id.input_email);
        inputpassword = (TextInputLayout) findViewById(R.id.input_pass);

        inputuser = (EditText) findViewById(R.id.sign_email);
        inputpass = (EditText) findViewById(R.id.sign_pass);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName()) {
                    return;
                }
                if (!validatePass()) {
                    return;
                } else {

                    user = inputuser.getText().toString().trim();
                    password = inputpass.getText().toString().trim();

                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.bloodband.dhost247.net/direct/hasdfkadsfhaSDFadsfADFskhfkshlogsdfinasdASASDAhoaasdf452ho/347789?user=" + user + "&pass=" + password,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    res = response.toString();
                                    //Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                    except_double = res.replace("\"", "").replace("{", "").replace("}", "").replace(":", "").replace("result", "").replace("user_id", "").replace("success", "");
                                    //Toast.makeText(getApplicationContext(), except_double, Toast.LENGTH_LONG).show();
                                    if (except_double.contains("failed") || except_double.contains("fails")) {
                                        Toast.makeText(LoginProfile.this, "ইউজারনেম/পাসওয়ার্ড সঠিক হয়নি", Toast.LENGTH_LONG).show();
                                    } else {
                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = LoginProfile.this.getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        //Adding values to editor
                                        editor.putBoolean(Links.LOGGEDIN_SHARED_PREF, true);
                                        editor.putString(Links.EMAIL_SHARED_PREF, except_double+" "+password+" "+user);
                                        //editor.putString(Config.NAME_SHARED_PREF, email);

                                        //Saving values to editor
                                        editor.commit();

                                        //Starting profile activity
                                        Intent intent = new Intent(LoginProfile.this, MyProfile.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put(Links.KEY_USER, user);
                            params.put(Links.KEY_PASSWORD, password);

                            //returning parameter
                            return params;
                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginProfile.this);
                    requestQueue.add(stringRequest);
                    /*Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);*/
                }
            }
        });

        LinearLayout forgotpass = (LinearLayout)findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassRecovery alert1 = new PassRecovery();
                alert1.showDialog(LoginProfile.this, "পাসওয়ার্ড পুনরুদ্ধার করুন");
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginProfile.this);
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

    public class PassRecovery {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.password_recovery);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText(msg);

            TextInputLayout name = (TextInputLayout) dialog.findViewById(R.id.input_pass);
            TextInputLayout newpass = (TextInputLayout) dialog.findViewById(R.id.input_pass1);
            final EditText username = (EditText) dialog.findViewById(R.id.sign_pass);
            final EditText passnew = (EditText) dialog.findViewById(R.id.sign_pass1);

            Button submit = (Button) dialog.findViewById(R.id.member);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = username.getText().toString();
                    if(user.equals("")){
                        Toast.makeText(LoginProfile.this, "অনুগ্রহ করে আপনার ব্যবাহার্য নামটি লিখুন", Toast.LENGTH_SHORT).show();
                    }
                    if(user.contains(" ")){
                        user = user.replace(" ", "%20");
                    }
                    String pass = passnew.getText().toString();
                    if(pass.equals("")){
                        Toast.makeText(LoginProfile.this, "অনুগ্রহ করে আপনার পাসওয়ার্ডটি লিখুন", Toast.LENGTH_SHORT).show();
                    }
                    if(pass.contains(" ")){
                        pass = pass.replace(" ", "%20");
                    }
                    HttpClient Client = new DefaultHttpClient();

                    String URL = "http://www.bloodband.dhost247.net/direct/fodfrsdfgesdft_pasdfassasdfwroasdfasdfd/923hd?username=" + user + "&new_pass=" + pass;
                    //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
                    String SetServerString = "";

                    HttpGet httpget = new HttpGet(URL);
                    HttpResponse response = null;
                    try {
                        response = Client.execute(httpget);
                        HttpEntity httpEntity = response.getEntity();
                        String res = EntityUtils.toString(httpEntity);
                        //Toast.makeText(MyProfile.this, res, Toast.LENGTH_SHORT).show();
                        if (res.contains("User Name Not Matched")) {
                            Toast.makeText(LoginProfile.this, "ইউজারনেম মিলেনি!", Toast.LENGTH_LONG).show();
                        } else {
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            try {
                                SetServerString = Client.execute(httpget, responseHandler);
                                //Toast.makeText(BeMember.this, SetServerString, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences preferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Getting editor
                            SharedPreferences.Editor editor = preferences.edit();

                            //Puting the value false for loggedin
                            editor.putBoolean(Links.LOGGEDIN_SHARED_PREF, false);

                            //Putting blank value to email
                            editor.putString(Links.EMAIL_SHARED_PREF, "");

                            //Saving the sharedpreferences
                            editor.commit();

                            finish();
                            //Starting login activity
                            Intent intent = new Intent(LoginProfile.this, MainActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Links.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Links.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(LoginProfile.this, MyProfile.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateName() {
        String email = inputuser.getText().toString().trim();
        if (email.isEmpty() /*|| !isValidEmail(email)*/) {
            inputusername.setError("আপনার ইউজারনেম লিখুন");
            requestFocus(inputuser);
            return false;
        } else {
            inputusername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePass() {
        if (inputpass.getText().toString().trim().isEmpty()) {
            inputpassword.setError("আপনার পাসওয়ার্ডটি লিখুন");
            requestFocus(inputpass);
            return false;
        } else {
            inputpassword.setErrorEnabled(false);
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
