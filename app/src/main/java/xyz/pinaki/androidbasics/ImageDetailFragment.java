package xyz.pinaki.androidbasics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pinaki on 10/17/17.
 */

public class ImageDetailFragment extends Fragment {
    private ImageView imageView;
    private static final String IMAGE_DATA_EXTRA = "image_data_extra";
    private String imageUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    public static ImageDetailFragment newInstance(String url) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString(IMAGE_DATA_EXTRA, url);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        imageView = (ImageView) v.findViewById(R.id.large_image);
        Picasso.with(getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(imageView);
        return v;
    }

}
