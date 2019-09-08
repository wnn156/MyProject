package com.example.myapplication.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.Data.Post;
import com.example.myapplication.Data.User;
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
    private User me;
    private EditText titleEdit;
    private EditText bodyEdit;

    private TextView cancelTv;
    private TextView searchTv;


    public PostingDialog(@NonNull Context context, User me) {
        super(context);
        this.context = context;
        this.me = me;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        titleEdit =  findViewById(R.id.titleEdit);
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
                post.setAuthor(me.getName());
                post.setBody(bodyEdit.getText().toString());

                dialogListener.onPositiveClicked(post);
                dismiss();
                break;
        }
    }
}