package com.mobiquity.androidunittests.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.fragment.AdditionFragment;

public class MainActivity extends AppCompatActivity
    implements AdditionFragment.AdditionFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onAddClicked(final float augend, final float addend) {
        if (Float.isNaN(addend) || Float.isNaN(addend)) {
            Toast.makeText(this, String.valueOf(Float.NaN), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, String.valueOf(augend + addend), Toast.LENGTH_LONG).show();
        }
    }
}
