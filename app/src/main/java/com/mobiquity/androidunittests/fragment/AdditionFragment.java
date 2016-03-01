package com.mobiquity.androidunittests.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobiquity.androidunittests.R;

/**
 * Simple fragment that does addition
 */
public class AdditionFragment extends Fragment {

    private AdditionFragmentListener listener;

    private EditText augend;
    private EditText addend;

    @Override
    public View onCreateView(
        final LayoutInflater inflater,
        final ViewGroup container,
        final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        augend = (EditText) view.findViewById(R.id.augend);
        addend = (EditText) view.findViewById(R.id.addend);
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (listener != null) {
                    listener.onAddClicked(
                        Float.parseFloat(augend.getText().toString()),
                        Float.parseFloat(addend.getText().toString()));
                }
            }
        });
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof AdditionFragmentListener) {
            listener = (AdditionFragmentListener) context;
        } else {
            throw new RuntimeException(
                context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Call back interface for activities that include this fragment
     * must implement.
     */
    public interface AdditionFragmentListener {
        /**
         * Callback for when add is clicked.
         *
         * @param augend addition augend parameter.
         * @param addend addition addend parameter.
         */
        void onAddClicked(final float augend, final float addend);
    }
}
