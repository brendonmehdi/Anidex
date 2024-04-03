package com.example.anidex.HomeFragment;

import android.support.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AnimeFragment();
            case 1:
                return new MangaFragment();
            default:
                return new AnimeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
