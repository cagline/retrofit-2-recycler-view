package com.crowderia.recyclerviewproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.crowderia.recyclerviewproject.model.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by crowderia on 8/28/17.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private List<Property> mItems ;
    private Context context;

    public PropertyAdapter(List<Property> mItems, Context context) {
        this.mItems = mItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Property listItem = mItems.get(position);
        holder.textViewHead.setText(listItem.getTitle());
        holder.textViewDesc.setText(listItem.getDescription());

        Picasso.with(context)
                .load(listItem.getImage1())
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateProperties(List<Property> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }
}
