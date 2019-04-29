package startup.abhishek.spleshscreen.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.Registration;
import startup.abhishek.spleshscreen.SessionManger;

import static android.app.Activity.RESULT_OK;

public class FullScreenDialogForEditProfile extends DialogFragment {

    private Toolbar toolbar;
    private EditText name,email,phone,location;
    private TextView saveBtn;
    private ImageView editImage,imageUploadBtn;
    private String sessionName,sessionImage,sessionPhone,sessionEmail,sessionLocation;
    private SessionManger sessionManger;
    boolean isNewImageSet = false;
    Bitmap newImage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        sessionManger = new SessionManger(getContext());
        HashMap<String,String> user=sessionManger.getUserDetail();
         sessionName = user.get(sessionManger.NAME);
        sessionPhone = user.get(sessionManger.MOBILE);
        sessionImage = user.get( sessionManger.PROFILE_PIC );
        sessionEmail = user.get( sessionManger.EMAIL );
        sessionLocation = user.get( sessionManger.LOCATION );
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_edit_profile, container, false);
        toolbar=view.findViewById(R.id.toolbarEdit);
        setToolbar(view);
        name=view.findViewById(R.id.nameUser);
        email=view.findViewById(R.id.emailEdit);
        phone=view.findViewById(R.id.mobileNumber);
        location=view.findViewById(R.id.location);
        saveBtn=view.findViewById(R.id.saveButton);
        editImage=view.findViewById(R.id.imageviewedit);
        imageUploadBtn=view.findViewById(R.id.imageUploadBtn);
        setAllFileds();



        return view;
    }

    private void setAllFileds() {
        name.setText(sessionName);
        email.setText(sessionEmail);
        phone.setText(sessionPhone);
        location.setText(sessionLocation);
        Glide.with(getContext()).load(sessionImage).into(editImage);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckData();
            }
        });

        imageUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "calll", Toast.LENGTH_SHORT).show();
                Uri resultUri = result.getUri();
                try {

                    newImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    editImage.setImageBitmap(newImage);
                    isNewImageSet=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("Photo",error.toString());
            }
        }
    }
    private void CheckData() {
       String Name= name.getText().toString();
       String Email= email.getText().toString();
       String Phone= phone.getText().toString();
       String Location= location.getText().toString();
        if(Name.equals("")|| Email.equals("")|| Phone.equals(""))
        {
            Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (isNewImageSet)
            {
                uplaodData(Name,Email,Phone,Location,getStringImage(newImage));
            }
            else
            {
                uplaodData(Name,Email,Phone,Location,sessionImage);

            }

        }
    }



    private void uplaodData(String name, String email, String phone, String location, String stringImage) {
    }

    private void setToolbar(View view) {
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setTitle("EditProfile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }



}
