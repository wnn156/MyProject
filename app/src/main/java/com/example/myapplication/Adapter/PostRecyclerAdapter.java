package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Post;
import com.example.myapplication.R;

import java.util.ArrayList;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Post> listData;

    public PostRecyclerAdapter(ArrayList<Post> listData){
        this.listData = listData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }


    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Post data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    public void refreshData(ArrayList<Post> posts) {
        listData.clear();
        listData.addAll(posts);
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
        }

        void onBind(Post post) {
            textView1.setText("Title : " + post.getTitle());
            textView2.setText("Author : " + post.getAuthor());
            textView3.setText("Body\n" + post.getBody());
        }
    }
}