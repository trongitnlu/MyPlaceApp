// Generated code from Butter Knife. Do not modify!
package com.android.nvtrong.myplace.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.nvtrong.myplace.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ModifyActivity_ViewBinding implements Unbinder {
  private ModifyActivity target;

  @UiThread
  public ModifyActivity_ViewBinding(ModifyActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ModifyActivity_ViewBinding(ModifyActivity target, View source) {
    this.target = target;

    target.editTextPlaceName = Utils.findRequiredViewAsType(source, R.id.editTextPlaceName, "field 'editTextPlaceName'", EditText.class);
    target.editTextPlaceAddress = Utils.findRequiredViewAsType(source, R.id.editTextPlaceAddress, "field 'editTextPlaceAddress'", EditText.class);
    target.editTextDescription = Utils.findRequiredViewAsType(source, R.id.editTextDescription, "field 'editTextDescription'", EditText.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ModifyActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.editTextPlaceName = null;
    target.editTextPlaceAddress = null;
    target.editTextDescription = null;
    target.imageView = null;
  }
}
