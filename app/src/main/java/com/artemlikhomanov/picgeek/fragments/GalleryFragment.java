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
import com.artemlikhomanov.picgeek.fragments.listeners.OnBottomReachedListener;
import com.artemlikhomanov.picgeek.model.Const;
import com.artemlikhomanov.picgeek.model.Photo;
import com.artemlikhomanov.picgeek.model.Photos;
import com.artemlikhomanov.picgeek.model.PhotosResponse;
import com.artemlikhomanov.picgeek.views.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends ListAbstractFragment {

    private static final String TAG = "GalleryFragment";

    private List<Photo> mPhotos;
    private Photos mPhotosMeta;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchPics();
    }

    private void fetchPics() {

        if (mPhotosMeta == null) {
            PicGeekApp.getApi().getPics(Const.FETCH_RECENT_METHOD, Const.API_KEY,
                                        Const.EXTRAS, Const.FORMAT, Const.NOJSONCALLBACK)
                    .enqueue(new Callback<PhotosResponse>() {
                        @Override
                        public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                            if (response.body() != null) {
                                mPhotosMeta = response.body().getPhotos();
                                mPhotos = mPhotosMeta.getPhotos();

                                mRecyclerView.setAdapter(new GalleryAdapter(mPhotos, new OnBottomReachedListener() {
                                    @Override
                                    public void onBottomReached() {
                                        if (!mPhotosMeta.isLastPage()) {
                                            fetchPics();
                                        }
                                    }
                                }));

                            } else {
                                Log.i(TAG, "response.body() == null");
                            }
                        }

                        @Override
                        public void onFailure(Call<PhotosResponse> call, Throwable t) {
                            Log.i(TAG, t.toString());
                        }
                    });
        } else {
            PicGeekApp.getApi().getPics(Const.FETCH_RECENT_METHOD, Const.API_KEY,
                                        Const.EXTRAS, mPhotosMeta.nextPage(),
                                        Const.FORMAT, Const.NOJSONCALLBACK)
                    .enqueue(new Callback<PhotosResponse>() {
                        @Override
                        public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                            if (response.body() != null) {
                                mPhotosMeta = response.body().getPhotos();
                                mPhotos.addAll(mPhotosMeta.getPhotos());
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            } else {
                                Log.i(TAG, "response.body() == null");
                            }
                        }

                        @Override
                        public void onFailure(Call<PhotosResponse> call, Throwable t) {
                            Log.i(TAG, t.toString());
                        }
                    });
        }
    }

    @NonNull
    public static Fragment newInstance() {
        return new GalleryFragment();
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        private SquareImageView mThumbnail;
        private Photo mPhoto;

        GalleryViewHolder(View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
        }

        void bindData(Photo photo) {
            if (photo != null) {
                mPhoto = photo;
                Picasso.get()
                        .load(photo.getUrl_q())
                        .placeholder(R.drawable.image_placeholder)
                        .into(mThumbnail);
            } else {
                mThumbnail.setImageResource(R.drawable.image_placeholder);
            }
        }
    }

    private class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Photo> mPhotos;

        private OnBottomReachedListener mListener;

        GalleryAdapter(List<Photo> photos, OnBottomReachedListener listener) {
            mPhotos = photos;
            mListener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (mPhotos != null) {
                ((GalleryViewHolder)holder).bindData(mPhotos.get(position));

                if (position == mPhotos.size() - 1) {
                    mListener.onBottomReached();
                }

            } else {
                ((GalleryViewHolder)holder).bindData(null);
            }
        }

        @Override
        public int getItemCount() {
            if (mPhotos != null) {
                return mPhotos.size();
            } else {
                return 20;
            }
        }
    }
}
