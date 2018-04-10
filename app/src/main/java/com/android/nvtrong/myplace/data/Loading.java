package com.android.nvtrong.myplace.data;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.nvtrong.myplace.R;

/**
 * Created by nvtrong on 4/10/2018.
 */

public class Loading extends ProgressDialog {
    public static Loading loading;

    public Loading(Context context) {
        super(context);
        setMessage(getContext().getResources().getString(R.string.text_progress_load));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public static Loading create(Context context) {
        loading = new Loading(context);
        return loading;
    }
}
