package com.crowderia.recyclerviewproject.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.crowderia.recyclerviewproject.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crowderia.recyclerviewproject.model.Repository;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by crowderia on 8/28/17.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repository> mItems;
    private Context context;

    private PostItemListener mItemListener;

    // ========== ViewHolder start ==========
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageView;
        public RelativeLayout linearLayout;
        PostItemListener mItemListener;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (RelativeLayout) itemView.findViewById(R.id.linearLayout);
        }

        @Override
        public void onClick(View view) {
            Repository Repository = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(Repository.getId());
            notifyDataSetChanged();
        }
    }
    // ========== ViewHolder end ==========


    public RepositoryAdapter(Context context, List<Repository> mItems, PostItemListener itemListener) {
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
        final Repository listItem = mItems.get(position);
        holder.textViewHead.setText(listItem.getFullName());
        holder.textViewDesc.setText(listItem.getDescription());

        Picasso.with(context)
                .load(listItem.getOwner().getAvatarUrl())
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


    public void updateProperties(List<Repository> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Repository getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

}
