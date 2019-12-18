package com.example.imagegalleryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.imagegalleryapp.model.ImageData;
import com.example.imagegalleryapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ImageData> dataAdapters;
    private ImageLoader imageLoader;
    private RecyclerItemClickListener.OnItemClickListener mItemClickListener;

    public RecyclerViewAdapter(List<ImageData> getDataAdapter, Context context) {
        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    public RecyclerItemClickListener.OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(RecyclerItemClickListener.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        ImageData dataAdapterOBJ = dataAdapters.get(position);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        imageLoader.get(dataAdapterOBJ.getThImageURL(),
                ImageLoader.getImageListener(
                        viewholder.thumbnailImageView,
                        R.mipmap.ic_launcher,
                        android.R.drawable.ic_dialog_alert
                )
        );

        viewholder.thumbnailImageView.setImageUrl(dataAdapterOBJ.getThImageURL(), imageLoader);
        viewholder.titleTextView.setText(dataAdapterOBJ.getImageTitle());
        viewholder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public NetworkImageView thumbnailImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            thumbnailImageView = (NetworkImageView) itemView.findViewById(R.id.thumbnail_image_view);
            onItemClickEvent(itemView);

            thumbnailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }

        private void onItemClickEvent(final View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }
}
