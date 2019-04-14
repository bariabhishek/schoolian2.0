package startup.abhishek.spleshscreen.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Bitmap bitmap;
    ImageView uploadProfile;
    int PICK_IMAGE_REQUEST = 0;
    String encodedImage;

    SessionManger sessionManger;
    TextView username,mobile,numberOfJob;
    ImageView imageView;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_profile, container, false );
 sessionManger=new SessionManger( getActivity() );

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        uploadProfile = view.findViewById( R.id.profileUpload );

        username = view.findViewById( R.id.name );
        mobile = view.findViewById( R.id.mobile );
        numberOfJob = view.findViewById( R.id.numberOfJob );



        HashMap<String,String> user=sessionManger.getUserDetail();
        String name = user.get(sessionManger.NAME);
        String phone = user.get(sessionManger.MOBILE);
        String image = user.get( sessionManger.PROFILE_PIC );

        username.setText( name );
        mobile.setText( phone );

        Glide.with(getActivity())
                .load(image)
                .into(uploadProfile);



        uploadProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
            }
        } );




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath  = data.getData();
            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                getStringImage( bitmap );

                //Setting image to ImageView
                uploadProfile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }



        } else {
            Toast.makeText(getActivity(), "image error", Toast.LENGTH_SHORT).show();
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
