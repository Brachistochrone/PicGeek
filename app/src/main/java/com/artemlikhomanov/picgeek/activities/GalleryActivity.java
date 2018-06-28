package com.artemlikhomanov.picgeek.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.artemlikhomanov.picgeek.R;
import com.artemlikhomanov.picgeek.fragments.GalleryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mLayout;

    private GalleryFragment mGalleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        mGalleryFragment = (GalleryFragment) fm.findFragmentById(R.id.fragment_container);

        if (mGalleryFragment == null) {
            mGalleryFragment = (GalleryFragment) createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mGalleryFragment)
                    .commit();
        }
    }

    @NonNull
    private Fragment createFragment() {
        return GalleryFragment.newInstance();
    }
}
