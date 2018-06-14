// Generated code from Butter Knife. Do not modify!
package com.android.nvtrong.myplace.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.nvtrong.myplace.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlaceAdapter$PlaceViewHolder_ViewBinding implements Unbinder {
  private PlaceAdapter.PlaceViewHolder target;

  @UiThread
  public PlaceAdapter$PlaceViewHolder_ViewBinding(PlaceAdapter.PlaceViewHolder target,
      View source) {
    this.target = target;

    target.imageViewItem = Utils.findRequiredViewAsType(source, R.id.imageItemPlace, "field 'imageViewItem'", ImageView.class);
    target.textViewItemPlaceName = Utils.findRequiredViewAsType(source, R.id.textViewItemPlaceName, "field 'textViewItemPlaceName'", TextView.class);
    target.textViewItemPlaceAddress = Utils.findRequiredViewAsType(source, R.id.textViewItemPlaceAddress, "field 'textViewItemPlaceAddress'", TextView.class);
    target.textViewItemDescription = Utils.findRequiredViewAsType(source, R.id.textViewItemDescription, "field 'textViewItemDescription'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlaceAdapter.PlaceViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageViewItem = null;
    target.textViewItemPlaceName = null;
    target.textViewItemPlaceAddress = null;
    target.textViewItemDescription = null;
  }
}
