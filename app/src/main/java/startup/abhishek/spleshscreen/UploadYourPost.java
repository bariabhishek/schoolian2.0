package startup.abhishek.spleshscreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadYourPost extends AppCompatActivity {


    //new post
    AlertDialog alertDialog;

    EditText title,dis,pese;
    ImageView imageView1,imageView2,imageView3;
    String mobilenumber;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 0;
    String encodedImage;
    public String photo;
    SessionManger sessionManger;
    String Url="https://voulu.in/api/jobpost.php";

    Button post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_your_post );

        sessionManger = new SessionManger( this );
        HashMap <String,String> hashMap = sessionManger.getUserDetail();
        mobilenumber= hashMap.get( SessionManger.MOBILE );

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initilisation();
        imageView1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
            }
        } );



        condition();


    }

    private void condition() {
        post.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {



        if(title.getText().toString().isEmpty()){
            title.setError( "fill blanck" );
        }else if (dis.getText().toString().isEmpty()){
            dis.setError( "fill blanck"   );
        }else if(pese.getText().toString().isEmpty()){
            pese.setError(  "fill blanck"  );
        }else {
                String titles=title.getText().toString();
                String diss=dis.getText().toString();
                String paise=pese.getText().toString();

            volley(mobilenumber,titles,diss,paise,getStringImage(bitmap));
        }
            }
        } );

    }

     public void volley(final String mobilenumber, final String titles, final String  dis, final String paise, final String stringImage) {

         final ProgressDialog progressDialog = new ProgressDialog(this);
         progressDialog.setMessage("Uploading...");
         progressDialog.setCancelable(false);
         progressDialog.show();
         UploadYourPost.this.setFinishOnTouchOutside(false);

         StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         try {
                             JSONObject jsonObject = new JSONObject(response);
                             String success = jsonObject.getString("success");

                             if (success.equals("1")){
                                 Toast.makeText(UploadYourPost.this, "Success!", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                                 Intent views = new Intent(UploadYourPost.this,Home. class);
                                 startActivity(views);
                                 finish();

                             }
                             else
                             {
                                 Toast.makeText(UploadYourPost.this, "Not upload", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                             }

                         } catch (JSONException e) {
                             e.printStackTrace();
                             progressDialog.dismiss();
                             Toast.makeText(UploadYourPost.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                         }

                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         progressDialog.dismiss();
                         Toast.makeText(UploadYourPost.this, "Try Again!"+error, Toast.LENGTH_SHORT).show();
                     }
                 })
        {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("title", titles);
                    params.put("mobile", mobilenumber);
                    params.put("des", dis);
                    params.put("pic", stringImage);
                    params.put("rate", paise);
                    return params;

            }
        };
         RequestQueue requestQueue = Volley.newRequestQueue( this );
         requestQueue.add( stringRequest );
         requestQueue.getCache().clear();
    }

    private void initilisation() {

        post = findViewById( R.id.post );
        title = findViewById( R.id.title );
        dis = findViewById( R.id.discription );
        pese = findViewById( R.id.dealpese );
        imageView1= findViewById( R.id.iv1);
        imageView2= findViewById( R.id.iv2 );
        imageView3= findViewById( R.id.iv3 );
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath  = data.getData();
            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);

              photo =  getStringImage( bitmap );

                //Setting image to ImageView
                imageView1.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }



        } else {
            Toast.makeText(this, "image error", Toast.LENGTH_SHORT).show();
        }
    }


    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}
