package com.android.nvtrong.myplace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.google.MapsUltis;
import com.android.nvtrong.myplace.data.model.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nvtrong on 4/5/2018.
 */

public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private List<Place> list;

    public PlaceAdapter(Context context, List<Place> list) {
        this.context = context;
        setList(list);
    }

    public void updateListPlace(List<Place> list) {
        setList(list);
        notifyDataSetChanged();
    }

    public void setList(List<Place> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PlaceViewHolder placeViewHolder = new PlaceViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_place, viewGroup, false);
            ButterKnife.bind(placeViewHolder, view);
            view.setTag(placeViewHolder);
        }
        placeViewHolder = (PlaceViewHolder) view.getTag();
        Place place = list.get(i);
//        if(place.getImage()!=null){
//            Bitmap placeBitmap = BitmapFactory.decodeByteArray(place.getImage(),0,place.getImage().length);
//            placeViewHolder.imageViewItem.setImageBitmap(placeBitmap);
//        }
        Picasso.get().load(place.getUrlIcon()).placeholder(R.drawable.logo).error(R.drawable.logo).into(placeViewHolder.imageViewItem);
        placeViewHolder.textViewItemPlaceName.setText(place.getName());
        placeViewHolder.textViewItemDescription.setText(place.getDescription());
        placeViewHolder.textViewItemPlaceAddress.setText(place.getAddress());

        return view;
    }

    class PlaceViewHolder {
        @BindView(R.id.imageItemPlace)
        ImageView imageViewItem;
        @BindView(R.id.textViewItemPlaceName)
        TextView textViewItemPlaceName;
        @BindView(R.id.textViewItemPlaceAddress)
        TextView textViewItemPlaceAddress;
        @BindView(R.id.textViewItemDescription)
        TextView textViewItemDescription;


    }
}
