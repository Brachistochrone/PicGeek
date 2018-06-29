package com.artemlikhomanov.picgeek.fragments;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemlikhomanov.picgeek.R;
import com.artemlikhomanov.picgeek.application.PicGeekApp;
import com.artemlikhomanov.picgeek.model.Const;
import com.artemlikhomanov.picgeek.model.Photos;
import com.artemlikhomanov.picgeek.model.PhotosResponse;
import com.artemlikhomanov.picgeek.views.CaptionedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends ListAbstractFragment {

    private static final String TAG = "GalleryFragment";

    @Override
    void setupRecyclerView() {

        int columns;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            columns = 2;
        } else {
            columns = 3;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new GalleryAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();

        PicGeekApp.getApi().getRecentPics(Const.FETCH_RECENT_METHOD, Const.API_KEY,
                                        Const.EXTRAS, Const.FORMAT, Const.NOJSONCALLBACK)
                .enqueue(new Callback<PhotosResponse>() {
                    @Override
                    public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                        Log.i(TAG, " " + response.body().getPhotos().getPhotos().get(0).toString());
                    }

                    @Override
                    public void onFailure(Call<PhotosResponse> call, Throwable t) {
                        Log.i(TAG, t.toString());
                    }
                });
    }

    @NonNull
    public static Fragment newInstance() {
        return new GalleryFragment();
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        private CaptionedImageView mThumbnail;

        GalleryViewHolder(View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
        }

        void bindData(String name, int resId) {
            mThumbnail.setText(name);
            mThumbnail.setImageResource(resId);
        }
    }

    private class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int[] mInts = new int[20];

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((GalleryViewHolder)holder).bindData(getString(R.string.temp_name), R.drawable.sorry_closed);
        }

        @Override
        public int getItemCount() {
            return mInts.length;
        }
    }
}
