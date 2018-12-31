package com.blood.band.bloodband;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pranto on 8/24/2017.
 */

public class BloodRequestDetails extends AppCompatActivity {

    private TextView names, degree, des, con, chamber, mail, sms, edit, delete, grp, name, place, district, message, phone, amount, date;
    private String pic, url, address, ticket, phn, bd, reqid, amt, dondate, from, pass, loc, dis, contact, msg, bg, upo;
    private CircleImageView image;
    private ImageView menu, back, loco;
    Context cntx;
    private DatabaseHandler db;
    private String f_name, f_add, f_grp, f_email, f_phn, urgent;
    private dataAdapter data;
    private Contact dataModel;
    private TextInputLayout inputLayoutPass;
    private EditText inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_req_details);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        grp = (TextView) findViewById(R.id.grp);
        amount = (TextView) findViewById(R.id.amount);
        date = (TextView) findViewById(R.id.date);
        name = (TextView) findViewById(R.id.name);
        place = (TextView) findViewById(R.id.place);
        district = (TextView) findViewById(R.id.district);
        message = (TextView) findViewById(R.id.message);
        phone = (TextView) findViewById(R.id.phone);

        reqid = getIntent().getStringExtra("reqid");
        amt = getIntent().getStringExtra("amount");
        amount.setText(amt);
        dondate = getIntent().getStringExtra("dondate");
        date.setText(dondate);
        from = getIntent().getStringExtra("from");
        name.setText(from);
        pass = getIntent().getStringExtra("pass");
        loc = getIntent().getStringExtra("location");
        place.setText(loc);
        dis = getIntent().getStringExtra("district");
        contact = getIntent().getStringExtra("number");
        phone.setText(contact);
        msg = getIntent().getStringExtra("message");
        message.setText(msg);
        urgent = getIntent().getStringExtra("type");
        TextView urg = (TextView) findViewById(R.id.urgent);
        if (urgent.equals("2")) {
            urg.setVisibility(View.GONE);
        }
        if (urgent.equals("1")) {
            urg.setVisibility(View.VISIBLE);
        }

        bg = getIntent().getStringExtra("bg");
        bg = bg.replace("(", "").replace(")", "");
        grp.setText(bg);
        upo = getIntent().getStringExtra("upazila");
        district.setText(upo);

        delete = (TextView) findViewById(R.id.delete);
        delete.setTypeface(fontAwesomeFont);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Password alert = new Password();
                alert.showDialog(BloodRequestDetails.this, "আপনার পাসওয়ার্ড প্রদান করুন");
            }
        });
        TextView call = (TextView) findViewById(R.id.call);
        call.setTypeface(fontAwesomeFont);
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialContactPhone(contact);
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodRequestDetails.this);
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

    public class Password {

        public void showDialog(AppCompatActivity activity, String msge) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.password_confirmation_request);

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText(msge);

            inputLayoutPass = (TextInputLayout) dialog.findViewById(R.id.input_pass);

            inputPass = (EditText) dialog.findViewById(R.id.sign_pass);

            Button dialogButton = (Button) dialog.findViewById(R.id.member);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passes = inputPass.getText().toString();
                    if (passes.equals("")) {
                        Toast.makeText(getBaseContext(), "পাসওয়ার্ড দেওয়া হয়নি", Toast.LENGTH_LONG).show();
                    } else if (!passes.equals(pass)) {
                        Toast.makeText(getBaseContext(), "পাসওয়ার্ড সঠিক হয়নি", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(BloodRequestDetails.this, BloodRequestEdit.class);
                        //intent.putExtra("QUETE", quete);
                        intent.putExtra("reqid", reqid);
                        intent.putExtra("amount", amt);
                        intent.putExtra("dondate", dondate);
                        intent.putExtra("from", from);
                        intent.putExtra("pass", pass);
                        intent.putExtra("location", loc);
                        intent.putExtra("district", dis);
                        intent.putExtra("number", contact);
                        intent.putExtra("message", msg);
                        intent.putExtra("type", urgent);
                        intent.putExtra("bg", bg);
                        intent.putExtra("upazila", upo);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
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
                    Intent intent2 = new Intent(getBaseContext(), MyProfile.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };
}