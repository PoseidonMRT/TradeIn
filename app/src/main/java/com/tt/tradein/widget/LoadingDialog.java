package com.tt.tradein.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tt.tradein.R;

/**
 * Created by tuozhaobing on 16-5-15.
 */
public class LoadingDialog extends DialogFragment{
    public LoadingDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.loading_img);
        imageView.setImageDrawable(new LoadingDrawable(new SwapLoadingRenderer(getActivity())));
        builder.setView(view);
        return builder.create();
    }
}
