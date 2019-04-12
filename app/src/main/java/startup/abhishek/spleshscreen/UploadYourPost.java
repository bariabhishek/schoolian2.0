package startup.abhishek.spleshscreen;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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

    EditText title,dis,pese;
    ImageView imageView1,imageView2,imageView3;
    String mobilenumber;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 0;
    String encodedImage;
    public String photo;
    String Url="https://voulu.in/api/jobpost.php/";

    Button post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_your_post );

        SessionManger obj = new SessionManger( this );
        HashMap <String,String> hashMap = obj.getUserDetail();
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
            volley();
        }
            }
        } );

    }

     public void volley() {

        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "ap",response );
                try {

                    JSONArray jsonArray = new JSONArray( response );

                        JSONObject jsonObject = jsonArray.getJSONObject( 0);
                        if(jsonObject.getString( "code" ).equalsIgnoreCase( "ok" )){
                            Toast.makeText( getApplicationContext(),"done",Toast.LENGTH_LONG ).show();
                        }


                }catch (JSONException e){
                    Toast.makeText( getApplicationContext(),e.toString(),Toast.LENGTH_LONG ).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG ).show();

            }
        })
        {
            @Override
            protected Map <String, String> getParams() throws AuthFailureError {
                Log.d( "kfjkdfjkdfksdlfsd","shdkas" );
                return super.getParams();
            }
        };
        requestQueue.add( stringRequest );
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
