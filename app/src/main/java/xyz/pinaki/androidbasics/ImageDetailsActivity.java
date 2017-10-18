package xyz.pinaki.androidbasics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by pinaki on 10/17/17.
 */

public class ImageDetailsActivity extends AppCompatActivity {
    static final String IMAGE_POSITION = "image_position";
    static final String IMAGE_URLS = "image_urls";
    ViewPager pager;
    FragmentStatePagerAdapter adapter;

//    @Nullable

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details_pager);
        int position = getIntent().getIntExtra(IMAGE_POSITION, -1);
        String[] urls = getIntent().getStringArrayExtra(IMAGE_URLS);
        adapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        pager = (ViewPager) findViewById(R.id.details_pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pager.setCurrentItem(position);

    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        String[] urls;

        public ImagePagerAdapter(FragmentManager fm, String[] urls) {
            super(fm);
            this.urls = urls;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(urls[position]);
        }

        @Override
        public int getCount() {
            return urls.length;
        }
    }
    public interface ThumbnailClickListener {
        void onThumbnailClick(int position);
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.image_details_pager, container, false);
//        pager = (ViewPager) v.findViewById(R.id.details_pager);
//        return v;
//    }

}
