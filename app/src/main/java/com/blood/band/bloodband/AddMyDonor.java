package com.blood.band.bloodband;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMyDonor extends AppCompatActivity {
    private String grp, loc, user;
    private ImageView menu, back, loco, error, logo;
    private ProgressBar progres;
    private TextView alert, jega, group, erroralert, number;
    private String answer;
    private TextView t1, t2;
    private Button member;
    ArrayList<Product> arrayList;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private TextView counting, last_donate, birth_date;
    private EditText inputName, inputEmail, inputAddress, inputPhone, inputGroup;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutGroup, inputLayoutPhone, inputLayoutAddress;
    private AutoCompleteTextView text;
    private String[] languages = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private DatabaseHandler db;
    private String donate, donate_bng, don_date, b_date_bng, b_date, birth, combine, f_name, f_add, f_grp, f_email, f_phn;
    private dataAdapter data;
    private Contact dataModel;
    private Bitmap bp;
    private byte[] photo;
    private int mYear, mMonth, mDay = 0, count = 0, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmydonor);

        db = new DatabaseHandler(this);

        logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        t2 = (TextView) findViewById(R.id.t2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        last_donate = (TextView) findViewById(R.id.last_donate);
        last_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mDay = 0;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMyDonor.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int d = monthOfYear + 1;
                                day = dayOfMonth;
                                if ((d == 1 || d == 2 || d == 3 || d == 4 || d == 5 || d == 6 || d == 7 || d == 8 || d == 9) || (day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9)) {
                                    String d1 = String.valueOf(d);
                                    d1 = "0" + d1;
                                    int len1 = d1.length();
                                    if (len1 == 3) {
                                        d1 = d1.substring(1);
                                    }
                                    //Toast.makeText(StudentReport.this, d1, Toast.LENGTH_LONG).show();

                                    String day1 = String.valueOf(day);
                                    day1 = "0" + day1;
                                    int len = day1.length();
                                    if (len == 3) {
                                        day1 = day1.substring(1);
                                    }
                                    //donate_bng = day1.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                    don_date = year + "-" + d1 + "-" + day1;
                                    donate_bng = don_date.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                    last_donate.setText("সর্বশেষ রক্তদান : " + donate_bng);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        birth_date = (TextView) findViewById(R.id.birthdate);
        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mDay = 0;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMyDonor.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int d = monthOfYear + 1;
                                day = dayOfMonth;
                                if ((d == 1 || d == 2 || d == 3 || d == 4 || d == 5 || d == 6 || d == 7 || d == 8 || d == 9) || (day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9)) {
                                    String d1 = String.valueOf(d);
                                    d1 = "0" + d1;
                                    int len1 = d1.length();
                                    if (len1 == 3) {
                                        d1 = d1.substring(1);
                                    }
                                    //Toast.makeText(StudentReport.this, d1, Toast.LENGTH_LONG).show();

                                    String day1 = String.valueOf(day);
                                    day1 = "0" + day1;
                                    int len = day1.length();
                                    if (len == 3) {
                                        day1 = day1.substring(1);
                                    }
                                    //donate_bng = day1.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                    b_date = year + "-" + d1 + "-" + day1;
                                    b_date_bng = b_date.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
                                    birth_date.setText("জন্ম তারিখ : " + b_date_bng);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_mail);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_address);
        inputLayoutGroup = (TextInputLayout) findViewById(R.id.input_grp);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_phn);

        inputName = (EditText) findViewById(R.id.sign_name);
        inputEmail = (EditText) findViewById(R.id.sign_mail);
        inputAddress = (EditText) findViewById(R.id.sign_add);
        //inputGroup = (EditText) findViewById(R.id.sign_grp);
        inputPhone = (EditText) findViewById(R.id.sign_phn);
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
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, languages);

        text.setAdapter(adapter);
        text.setThreshold(1);

        member = (Button) findViewById(R.id.member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMyDonor.this);
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

        Uri uri = Uri.parse("android.resource://com.blood.band.bloodband/drawable/profiles");
        if (uri != null) {

            bp = decodeUri(uri, 100);
        }
        //bp = BitmapFactory.decodeResource(getResources(), R.drawable.profiles);
    }

    public void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri choosenImage = data.getData();

                    if (choosenImage != null) {

                        bp = decodeUri(choosenImage, 100);
                        /*try {
                            bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), choosenImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        logo.setImageBitmap(bp);
                    }
                    //super.onActivityResult(requestCode, resultCode, data);
                }
        }
    }

    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    private void getValues() {
        String ld = last_donate.getText().toString();
        if (ld.equals("সর্বশেষ রক্তদানের তারিখ")) {
            donate = "na";
        } else {
            donate = don_date;
        }
        String bd = birth_date.getText().toString();
        if (bd.equals("জন্ম তারিখ")) {
            birth = "na";
        } else {
            birth = b_date;
        }
        combine = donate + " " + birth;
        f_name = inputName.getText().toString();
        if (f_name.contains(" ")) {
            f_name = f_name.replace(" ", "%20");
        }
        f_add = inputAddress.getText().toString();
        if (f_add.contains(" ")) {
            f_add = f_add.replace(" ", "%20");
        }
        f_grp = text.getText().toString();
        f_email = inputEmail.getText().toString();
        f_phn = inputPhone.getText().toString();
        photo = profileImage(bp);
    }

    //Insert data to the database
    private void addContact() {
        getValues();

        db.addContacts(new Contact(combine, f_name, f_add, f_grp, f_email, f_phn, photo));
        Toast.makeText(getApplicationContext(), "সফলভাবে তালিকাভূক্ত করা হয়েছে", Toast.LENGTH_LONG).show();
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

        /*if (!validateEmail()) {
            return;
        }*/

        if (!validatePhone()) {
            return;
        }

        //Toast.makeText(getApplicationContext(), "ব্লাড ব্যান্ডের সদস্য হওয়ার জন্য ধন্যবাদ!", Toast.LENGTH_SHORT).show();
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
        if (letters.equals("11")) {
            addContact();
            finish();
            Intent intent = new Intent(getBaseContext(), MyDonor.class);
            startActivity(intent);
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
}
