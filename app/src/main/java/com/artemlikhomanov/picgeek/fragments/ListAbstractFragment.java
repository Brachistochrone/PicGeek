package com.artemlikhomanov.picgeek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemlikhomanov.picgeek.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ListAbstractFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    abstract void setupRecyclerView();

    /*Шаблонный метод*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResId(), container, false);

        initialiseUI(view);

        return view;
    }

    int getLayoutResId() {
        return R.layout.fragment_list;
    }

    void initialiseUI(View view) {
        ButterKnife.bind(this, view);
        setupRecyclerView();
    }
}
