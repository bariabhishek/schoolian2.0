package com.wikav.voulu.coustomDilogeClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.wikav.voulu.Home;
import com.wikav.voulu.R;

public class CouatomDilogeLastConfirm extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate( R.layout.confirm_popup_layout_coustom,null );
        Button button =view.findViewById(R.id.gotohome);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          getActivity().startActivity(new Intent(getActivity(), Home. class));
                                      }
                                  }
        );
        builder.setView( view )
                .setTitle( "" ).setCancelable(false);
        return builder.create();
    }
}
