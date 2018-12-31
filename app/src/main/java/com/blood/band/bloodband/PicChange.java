package com.blood.band.bloodband;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PicChange extends AppCompatActivity {
    //public static final String UPLOAD_URL = "http://192.168.0.115/PictureBlood/upload_img.php";
    public static final String UPLOAD_URL = "http://www.bloodband.dhost247.net/direct/sdfgsdfadd_to_madfadsasdfasdain_tasasdfasdfk_co23234mmenasdf1234123t/923hd";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    private TextView imageChoose;
    private Button buttonUpload;
    private ImageView imageView, menu, back;
    private Bitmap bitmap;
    private Uri filePath;
    private String id = "", pic = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_change);

        id = getIntent().getStringExtra("id");
        pic = getIntent().getStringExtra("pic");

        buttonUpload = (Button) findViewById(R.id.submit);
        imageChoose = (TextView) findViewById(R.id.t2);
        imageView = (ImageView) findViewById(R.id.logo);
        if (pic.equals("")) {
            Uri uri = Uri.parse("android.resource://com.blood.band.bloodband/drawable/profiles");
            if (uri != null) {

                bitmap = decodeUri(uri, 70);
            }
        }
        if (!pic.equals("")) {
            Picasso.with(PicChange.this).load(pic).into(imageView);
            //Glide.with(MyProfile.this).load(picture).into(profile_pic);
            try {
                URL url = new URL(pic);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        imageChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "ডেভোলাপার", "রেটিং দিন", "শেয়ার করুন"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PicChange.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    filePath = data.getData();

                    if (filePath != null) {

                        bitmap = decodeUri(filePath, 70);
                        /*try {
                            bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), choosenImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        imageView.setImageBitmap(bitmap);
                    }
                    //super.onActivityResult(requestCode, resultCode, data);
                }
        }
    }

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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadPic() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PicChange.this, "আপলোড হচ্ছে...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                TextView res = (TextView) findViewById(R.id.res);
                if (s.contains("Success") || s.contains("success") || s.contains("ok") || s.contains("OK") || s.contains("Ok")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText("সফলভাবে আপলোড করা হয়েছে");
                    res.setTextColor(Color.parseColor("#006400"));
                    Toast.makeText(PicChange.this, "সফলভাবে আপলোড করা হয়েছে", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getBaseContext(), LoginProfile.class);
                    startActivity(intent);
                }
                /*if (s.contains("সফলভাবে আপলোড করা হয়েছে") || s.contains("সফলভাবে আপডেট করা হয়েছে")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#006400"));
                }*/
                if (s.contains("আপলোড সফল হয়নি")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#e83d3d"));
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                //Toast.makeText(PicChange.this, uploadImage, Toast.LENGTH_SHORT).show();
                String userid = id;

                HashMap<String, String> data = new HashMap<>();

                data.put("images", uploadImage);
                data.put("user_id", userid);
                //data.put("image", uploadImage);
                //data.put("userid", userid);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}
