// Generated code from Butter Knife. Do not modify!
package com.android.nvtrong.myplace;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    this.target = target;

    target.layout = Utils.findRequiredViewAsType(source, R.id.layoutMain, "field 'layout'", RelativeLayout.class);
    target.imageLogo = Utils.findRequiredViewAsType(source, R.id.imageLogo, "field 'imageLogo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layout = null;
    target.imageLogo = null;
  }
}
