package com.android.nvtrong.myplace.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.adapter.PlaceAdapter;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.android.nvtrong.myplace.extension.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link SaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveFragment extends Fragment {
    public static final String TITLE = "Save";
    @BindView(R.id.listViewSave)
    ListView listView;
    List<Place> listPlace;
    PlaceAdapter placeAdapter;

    public static SaveFragment newInstance() {

        return new SaveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        listPlace = new ArrayList<>();

        listPlace = MyApplication.placeDAO.getAllPlace();
        placeAdapter = new PlaceAdapter(getContext(), listPlace);

        listView.setAdapter(placeAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listPlace = MyApplication.placeDAO.getAllPlace();
        placeAdapter.setList(listPlace);
        placeAdapter.notifyDataSetChanged();
    }

}
