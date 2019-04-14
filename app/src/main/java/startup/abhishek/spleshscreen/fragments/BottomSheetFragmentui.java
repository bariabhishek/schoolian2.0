package startup.abhishek.spleshscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import startup.abhishek.spleshscreen.R;

public class BottomSheetFragmentui extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    ImageView call,message ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.bottom_sheet_layout,container,false );

        call = view.findViewById( R.id.civcall );
        message = view.findViewById( R.id.civmessage );




        return view;
    }

    private void click() {

        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( getContext(),"call",Toast.LENGTH_LONG ).show();
                mListener.onButtonClicked("Button 2 clicked");
                dismiss();
            }
        } );
        message.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onButtonClicked("Button 2 clicked");
                dismiss();
                Toast.makeText( getContext(),"message",Toast.LENGTH_LONG ).show();
            }
        } );
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}

