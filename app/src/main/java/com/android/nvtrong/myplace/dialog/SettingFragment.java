package com.android.nvtrong.myplace.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.extension.MyApplication;
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
        editText.setText(SharePreferences.getPreferencesRadius(MyApplication.context)+"");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferences.setPreferencesRadius(MyApplication.context, Integer.parseInt(editText.getText().toString()));
                int a = SharePreferences.getPreferencesRadius(MyApplication.context);
                Toast.makeText(MyApplication.context, "Radius search: "+a, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
        view.setLayoutParams(layoutParams);
        return view;

    }
}
