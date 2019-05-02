package startup.abhishek.spleshscreen.fragments;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Base64;
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

import startup.abhishek.spleshscreen.EditProfile;
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
    Button editProfile;

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
        sessionManger=new SessionManger( getContext() );

        uploadProfile = view.findViewById( R.id.profileUpload );

        username = view.findViewById( R.id.name );
        mobile = view.findViewById( R.id.mobile );
        numberOfJob = view.findViewById( R.id.numberOfJob );
        editProfile = view.findViewById( R.id.editProfile );

editProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
            full.show(getFragmentManager(),"TAG");*/
       Intent intent = new Intent(getActivity(), EditProfile.class);
       startActivity(intent);

    }
});

        HashMap<String,String> user=sessionManger.getUserDetail();
        String name = user.get(sessionManger.NAME);
        String phone = user.get(sessionManger.MOBILE);
        String image = user.get( sessionManger.PROFILE_PIC );

        username.setText( name );
        mobile.setText( phone );

        Glide.with(getActivity())
                .load(image)
                .into(uploadProfile);







        return view;
    }




    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}
