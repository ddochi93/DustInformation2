package com.ddochi.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ddochi.R;

public class AddLocationDialogFragment extends DialogFragment {
    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onOkclicked(String city);
    }

    public void setmOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private EditText mCityEditText;

    public static AddLocationDialogFragment newInstance(OnClickListener listener) {
        Bundle args = new Bundle();

        AddLocationDialogFragment fragment = new AddLocationDialogFragment();
        fragment.setmOnClickListener(listener);
        fragment.setArguments(args);
        return fragment;
    }

    public AddLocationDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_location,null,false); //new LayoutInflater와의 차이?

        mCityEditText = ((View) view).findViewById(R.id.city_edit);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 추가");
        builder.setView(view);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String city = mCityEditText.getText().toString();
                mOnClickListener.onOkclicked(city);
            }
        });
        builder.setNegativeButton("취소",null);
        return builder.create();
    }
}
