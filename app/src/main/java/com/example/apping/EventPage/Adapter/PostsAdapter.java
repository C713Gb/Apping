package com.example.apping.EventPage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apping.EventPage.TabFragmentsPage.OpenEventsActivity;
import com.example.apping.Events.Posts;
import com.example.apping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context mContext;
    private List<Posts> mPost;

    public PostsAdapter(Context mContext, List<Posts> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Posts post = mPost.get(position);

        Glide.with(mContext).load(post.getPostImage()).into(holder.event_image);

        holder.event_title.setVisibility(View.VISIBLE);
        holder.event_title.setText(post.getTitle());


        holder.event_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTitle = post.getTitle();
                String mDescription = post.getDescription();
                String mOrganisers = post.getOrganisers();
                String mDate = post.getEventDate();
                String mLat = post.getEventLocationLatitude();
                String mLng = post.getEventLocationLongitude();


                Intent intent = new Intent(mContext, OpenEventsActivity.class);
                intent.putExtra("Title:",mTitle);
                intent.putExtra("Description:",mDescription);
                intent.putExtra("Organisers:",mOrganisers);
                intent.putExtra("Date:",mDate);
                intent.putExtra("Lat:",mLat);
                intent.putExtra("Lng:",mLng);
                mContext.startActivity(intent);

            }
        });

        holder.event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTitle = post.getTitle();
                String mDescription = post.getDescription();
                String mOrganisers = post.getOrganisers();
                String mDate = post.getEventDate();

                Intent intent = new Intent(mContext, OpenEventsActivity.class);
                intent.putExtra("Title:",mTitle);
                intent.putExtra("Description:",mDescription);
                intent.putExtra("Organisers:",mOrganisers);
                intent.putExtra("Date:",mDate);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView event_image;
        public TextView event_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            event_image = itemView.findViewById(R.id.event_image);
            event_title = itemView.findViewById(R.id.event_title);
        }

    }


}
