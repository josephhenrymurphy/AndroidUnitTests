package com.mobiquity.androidunittests.ui;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiquity.androidunittests.R;

public class DevDrawerViewWrapper implements ViewWrapper<DrawerLayout> {

    @Override
    public DrawerLayout wrap(View view) {
        Context context = view.getContext();
        DrawerLayout drawerLayout = new DrawerLayout(context);
        DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        layoutParams.gravity = GravityCompat.END;
        drawerLayout.setLayoutParams(layoutParams);

        // Add the content view
        drawerLayout.addView(view);

        //Add the dev drawer
        View drawerView = LayoutInflater.from(context).inflate(R.layout.dev_drawer_view, drawerLayout, false);
        drawerLayout.addView(drawerView, layoutParams);
        return drawerLayout;
    }
}
