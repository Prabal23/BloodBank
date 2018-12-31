package com.blood.band.bloodband;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HealthInfo extends AppCompatActivity {
    String rate, dn_date, check;
    private int PICK_IMAGE_REQUEST = 1, mYear, mMonth, mDay;
    ImageView menu, back;
    CheckBox c, c1, c2, c3, c4, c5, c6, c7, c8, c9;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_info);

        TextView title = (TextView)findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        final TextView res = (TextView)findViewById(R.id.result);

        c = (CheckBox) findViewById(R.id.check9);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c1 = (CheckBox) findViewById(R.id.check8);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c2 = (CheckBox) findViewById(R.id.check);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c3 = (CheckBox) findViewById(R.id.check1);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c4 = (CheckBox) findViewById(R.id.check2);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c5 = (CheckBox) findViewById(R.id.check3);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c6 = (CheckBox) findViewById(R.id.check4);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c7 = (CheckBox) findViewById(R.id.check5);
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c8 = (CheckBox) findViewById(R.id.check6);
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }
            }
        });
        c9 = (CheckBox) findViewById(R.id.check7);
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    check = "10";
                    total+=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
                }else{
                    total-=10;
                    String t = String.valueOf(total);
                    t = t.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                    res.setText("সর্বমোট : "+t+"/১০০");
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

                AlertDialog.Builder builder = new AlertDialog.Builder(HealthInfo.this);
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
}
