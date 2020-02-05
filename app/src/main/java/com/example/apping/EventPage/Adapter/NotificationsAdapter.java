package com.example.apping.EventPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apping.EventPage.AddEventActivity;
import com.example.apping.Events.Notifications;
import com.example.apping.Events.Posts;
import com.example.apping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context mContext;
    private List<Notifications> mNotifications;
    private String whole;

    public NotificationsAdapter(Context mContext, List<Notifications> mNotifications) {
        this.mContext = mContext;
        this.mNotifications = mNotifications;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Notifications notifications = mNotifications.get(position);

        holder.notificationText.setVisibility(View.VISIBLE);
        String str = "A New event got organised.";
        holder.notificationText.setText(str);
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notificationText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notification_text);

        }
    }

}
