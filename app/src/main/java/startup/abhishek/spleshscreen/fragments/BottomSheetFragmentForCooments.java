package startup.abhishek.spleshscreen.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import startup.abhishek.spleshscreen.R;

public class BottomSheetFragmentForCooments extends BottomSheetDialogFragment {
TextView test;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.bottom_sheet_layout_for_comments,container,false );
            test=view.findViewById(R.id.idtest);
            String id=getArguments().getString("id");
            test.setText(id);

        return view;
    }




}

