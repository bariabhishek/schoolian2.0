package startup.abhishek.spleshscreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {

    private EditText name;
    private EditText number;
    private ExampleDilogeListner exampleDilogeListner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate( R.layout.aleart_builder_layout,null );

        name = view.findViewById( R.id.et1name );
        number =view.findViewById( R.id.et1number );


        builder.setView( view )
                .setTitle( "OTP" )
                .setNegativeButton( "cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                } ).setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String username = name.getText().toString();
                String nmber = number.getText().toString();
                exampleDilogeListner.applyTexts( username,nmber );

            }
        } );


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );

        try {

        exampleDilogeListner = (ExampleDilogeListner) context;
    }catch (ClassCastException e){
            throw new ClassCastException( context.toString()+"must impliment ExampleDilogeListner" );
        }
    }

    public interface ExampleDilogeListner{
        void applyTexts(String username, String number);
    }
}
