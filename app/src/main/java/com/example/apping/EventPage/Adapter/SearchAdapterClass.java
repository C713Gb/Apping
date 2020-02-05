package com.example.apping.EventPage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apping.Events.Search;
import com.example.apping.R;

import java.util.ArrayList;

public class SearchAdapterClass extends RecyclerView.Adapter<SearchAdapterClass.MyViewHolder> {

    ArrayList<Search> list;

    public SearchAdapterClass(ArrayList<Search> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.titleId.setText(list.get(position).getTitle());
        holder.descriptionId.setText(list.get(position).getDescription());

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = v.getContext().getSharedPreferences("PREFS",
                        Context.MODE_PRIVATE).edit();
                editor.apply();

                //Toast.makeText(v.getContext(), ""+"Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),   OpenEventsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

         */
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleId;
        TextView descriptionId;
        ImageView starId;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleId = itemView.findViewById(R.id.title_id);
            descriptionId = itemView.findViewById(R.id.description_id);
            starId = itemView.findViewById(R.id.star_icon);

        }
    }
}
