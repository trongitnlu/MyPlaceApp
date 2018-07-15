package com.android.nvtrong.myplace.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.activity.DetailActivity;
import com.android.nvtrong.myplace.data.google.MapsUltis;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.android.nvtrong.myplace.extension.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private List<Place> list;
    private boolean saveFragment;
    public PlaceAdapter(Context context, List<Place> list) {
        this.context = context;
        setList(list);
    }
    public PlaceAdapter(Context context, List<Place> list, boolean save) {
        this.context = context;
        this.saveFragment = save;
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
        Picasso.get().load(place.getUrlIcon()).placeholder(R.drawable.logo).error(R.drawable.logo).into(placeViewHolder.imageViewItem);
        placeViewHolder.textViewItemPlaceName.setText(place.getName());
        placeViewHolder.textViewItemDescription.setText(place.getDescription());
        placeViewHolder.textViewItemPlaceAddress.setText(place.getAddress());
        if(saveFragment){
            PlaceDAO placeDAO = MyApplication.placeDAO;
            placeViewHolder.btn_removePlace.setVisibility(View.VISIBLE);
            placeViewHolder.btn_removePlace.setOnClickListener(view1->{
                placeDAO.delete(place.getId());
                updateListPlace(placeDAO.getAllPlace());
            });
        }
        view.setOnClickListener( view1 -> {
                Intent intent = new Intent(context, DetailActivity.class);
                Log.d("place ok","ne: " + place.toString() );
                intent.putExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA, place);
                context.startActivity(intent);
        });
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
        @BindView(R.id.btn_removePlace)
        ImageButton btn_removePlace;

    }
}
