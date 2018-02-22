package dcube.com.salonseek;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.MobDelAdapter;
import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class MyInfoActivity extends Activity {

    TextView save,tv_full_name,tv_email,tv_user_name,tv_change_pwd;
    MyApplication global;
    Context myContext;

    EditText ed_pwd;

    LinearLayout lin_add_num;
    ListView lv_del_mob;
  //  EditText ed_add_num;

    public static boolean is_pic_changed;

    WebServicesHandler ws;
   // ImageView profilepic;
    CircleImageView profilepic;


    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;

   public static HashMap<String,String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_info);

        tv_change_pwd = (TextView) findViewById(R.id.tv_change_pwd);
        save = (TextView) findViewById(R.id.save);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_full_name = (TextView) findViewById(R.id.tv_full_name);
        profilepic = (CircleImageView) findViewById(R.id.profilepic);
        lv_del_mob = (ListView) findViewById(R.id.lv_del_mob);
  //      tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        lin_add_num = (LinearLayout)findViewById(R.id.lin_add_num);

        ed_pwd = (EditText)findViewById(R.id.ed_pwd);

   //     ed_add_num = (EditText)findViewById(R.id.ed_add_num);

        myContext = this;

        global = (MyApplication) getApplicationContext();

        global.setAdd_number("");

        new GetProfile().execute();

        //Requesting storage permission
      //  requestStoragePermission();

        is_pic_changed = false;

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        lin_add_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

      //          ed_add_num.setVisibility(View.VISIBLE);

                CustomDialog cs = new CustomDialog(MyInfoActivity.this);
                cs.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //       global.setAdd_number(ed_add_num.getText().toString());

          //      uploadImage();

                sendImage(bitmap);

//                if (is_pic_changed)
//                {
//                    uploadMultipart();
//                }
                new UpdateProfile().execute();

             //   finish();

            }
        });


        tv_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setPassword(ed_pwd.getText().toString());

                Log.e("Pwd","Pwd "+ed_pwd.getText().toString());

            }
        });

    }



    public  void sendImage(Bitmap... params)
    {
        Bitmap bitmap = params[0];

        if (is_pic_changed)
        {
            String uploadImage = getStringImage(bitmap);

            data = new HashMap<>();

            data.put(UPLOAD_KEY, uploadImage);
        }
    }




    public void uploadMultipart() {

        //getting name for the image
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString()+".jpeg";
      // String name = editText.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {

            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, GlobalConstants.SALON_LIST_ON_MAP_URL)
                    .addFileToUpload(path, GlobalConstants.PROFILE_IMAGE) //Adding file
                    .addParameter("name", ts) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc)
        {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /*

    //Requesting permission
    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }

*/


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

    }

    //method to get the file path from uri
    public String getPath(Uri uri)
    {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);

            if (cursor != null)
            {
                cursor.moveToFirst();
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();

                return path;
            }

        }

        return "error";

    }



    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public class UpdateProfile extends AsyncTask<String,String,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(myContext);
            progressDialog.setMessage("Updating Profile....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ws.AddNumberToUser(myContext);

            ws.UpdateProfile(myContext);

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            if (progressDialog.isShowing())
            {
                progressDialog.cancel();
            }

            finish();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilepic.setImageBitmap(bitmap);

                is_pic_changed = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public class GetProfile extends AsyncTask<String,String,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(myContext);
            progressDialog.setMessage("Loading Details....");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            ws.GetProfileService(myContext);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (progressDialog.isShowing())
            {
                progressDialog.cancel();
            }

            tv_full_name.setText(global.getProfileDetails().get(GlobalConstants.FULL_NAME));
            tv_email.setText(global.getProfileDetails().get(GlobalConstants.LOGIN_EMAIL));
        //    tv_user_name.setText(global.getProfileDetails().get(GlobalConstants.USER_NAME));

            String user_img = global.getProfileDetails().get(GlobalConstants.USER_IMG);

            Picasso.with(myContext)
                    .load(GlobalConstants.SALON_IMAGES_URL+user_img )
                    .into(profilepic);

            lv_del_mob.setAdapter(new MobDelAdapter(myContext));
        }

    }


    public class CustomDialog extends Dialog
    {

        TextView tv_ok;
        EditText ed_number;

        public CustomDialog(Context context) {
            super(context);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.add_number_dialog);

            ed_number = (EditText)findViewById(R.id.ed_number);
            tv_ok = (TextView)findViewById(R.id.tv_ok);

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ed_number.getText().toString().length() == 10)
                    {
                        global.setAdd_number(ed_number.getText().toString());
                        cancel();
                    }
                    else
                    {
                        Toast.makeText(MyInfoActivity.this, "Number is not of 10 digit", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }


    }

    /*

    private void uploadImage()
    {

        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyInfoActivity.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }


        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

*/

}
