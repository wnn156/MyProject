package com.example.myapplication.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.Data.Post;
import com.example.myapplication.R;

public class PostingDialog extends Dialog implements View.OnClickListener {
    private static final int LAYOUT = R.layout.dialog_posting;

    private MyDialogListener dialogListener;
    public interface MyDialogListener {
        public void onPositiveClicked(Post post);
        public void onNegativeClicked();
    }public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }





    private Context context;

    private EditText titleEdit;
    private EditText authorEdit;
    private EditText bodyEdit;

    private TextView cancelTv;
    private TextView searchTv;


    public PostingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        titleEdit =  findViewById(R.id.titleEdit);
        authorEdit =  findViewById(R.id.authorEdit);
        bodyEdit =  findViewById(R.id.bodyEdit);

        cancelTv = (TextView) findViewById(R.id.findPwDialogCancelTv);
        searchTv = (TextView) findViewById(R.id.findPwDialogFindTv);

        cancelTv.setOnClickListener(this);
        searchTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findPwDialogCancelTv:
                cancel();
                break;
            case R.id.findPwDialogFindTv:

                Post post = new Post();

                post.setTitle(titleEdit.getText().toString());
                post.setAuthor(authorEdit.getText().toString());
                post.setBody(bodyEdit.getText().toString());

                dialogListener.onPositiveClicked(post);
                dismiss();
                break;
        }
    }
}