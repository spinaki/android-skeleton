package xyz.pinaki.androidbasics;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pinaki on 10/1/17.
 */

public class SearchResultsViewAdapter extends RecyclerView.Adapter<SearchResultsViewAdapter.ImageViewHolder> {
    ImageSearchResult imageSearchResult;
    ImageDetailsActivity.ThumbnailClickListener thumbnailClickListener;
    public SearchResultsViewAdapter(ImageSearchResult searchResult, ImageDetailsActivity.ThumbnailClickListener t) {
        imageSearchResult = searchResult;
        thumbnailClickListener = t;
    }
    public void update(ImageSearchResult searchResult) {
        int oldSize = imageSearchResult.size();
        this.imageSearchResult.value.addAll(searchResult.value);
        notifyItemRangeInserted(oldSize, searchResult.value.size());
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbNailView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_thumbnail, null);
        return new ImageViewHolder(thumbNailView, thumbnailClickListener);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(imageSearchResult.value.get(position).thumbnailUrl)
                .placeholder(R.drawable.placeholder).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageSearchResult.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imageView;
        ImageDetailsActivity.ThumbnailClickListener thumbnailClickListener;
        public ImageViewHolder(View itemView, ImageDetailsActivity.ThumbnailClickListener t) {
            super(itemView);
            thumbnailClickListener = t;
            imageView = (ImageView)itemView.findViewById(R.id.thumb_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("PINAKI-CLICK", "thumbnailClickListener");
            thumbnailClickListener.onThumbnailClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            Log.i("PINAKI-CLICK", "thumbnail long ClickListener");
            return true;
        }
    }
}
