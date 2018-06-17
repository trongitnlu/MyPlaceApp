package com.android.nvtrong.myplace.dialog;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.extension.SharePreferences;

public class SettingFragment extends DialogFragment {

        public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Get the layout inflater
        View view = inflater.inflate(R.layout.dialog_setting, container, false);
        Button button = view.findViewById(R.id.btn_setting);
        EditText editText = view.findViewById(R.id.edit_radius);
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SharePreferences.setPreferencesRadius(getContext(), Integer.parseInt(editText.getText().toString()));
                int a = SharePreferences.getPreferencesRadius(getContext());
                Toast.makeText(getContext(), "Radius search: "+a, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
        view.setLayoutParams(layoutParams);
        return view;

    }
}
