// Generated code from Butter Knife. Do not modify!
package com.android.nvtrong.myplace.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.nvtrong.myplace.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoriesActivity_ViewBinding implements Unbinder {
  private CategoriesActivity target;

  @UiThread
  public CategoriesActivity_ViewBinding(CategoriesActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CategoriesActivity_ViewBinding(CategoriesActivity target, View source) {
    this.target = target;

    target.textViewRestaurant = Utils.findRequiredViewAsType(source, R.id.textViewRestaurant, "field 'textViewRestaurant'", TextView.class);
    target.textViewCinema = Utils.findRequiredViewAsType(source, R.id.textViewCinema, "field 'textViewCinema'", TextView.class);
    target.textViewFashion = Utils.findRequiredViewAsType(source, R.id.textViewFashion, "field 'textViewFashion'", TextView.class);
    target.textViewATM = Utils.findRequiredViewAsType(source, R.id.textViewATM, "field 'textViewATM'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoriesActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewRestaurant = null;
    target.textViewCinema = null;
    target.textViewFashion = null;
    target.textViewATM = null;
  }
}
