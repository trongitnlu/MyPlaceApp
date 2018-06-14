// Generated code from Butter Knife. Do not modify!
package com.android.nvtrong.myplace.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.nvtrong.myplace.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlaceActivity_ViewBinding implements Unbinder {
  private PlaceActivity target;

  @UiThread
  public PlaceActivity_ViewBinding(PlaceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PlaceActivity_ViewBinding(PlaceActivity target, View source) {
    this.target = target;

    target.listViewPlace = Utils.findRequiredViewAsType(source, R.id.listViewPlace, "field 'listViewPlace'", ListView.class);
    target.textViewNoData = Utils.findRequiredViewAsType(source, R.id.textViewNodata, "field 'textViewNoData'", TextView.class);
    target.loadingPanel = Utils.findRequiredViewAsType(source, R.id.loadingPanel, "field 'loadingPanel'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlaceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listViewPlace = null;
    target.textViewNoData = null;
    target.loadingPanel = null;
  }
}
