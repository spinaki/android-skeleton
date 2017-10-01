package xyz.pinaki.androidbasics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pinaki on 10/1/17.
 */
// the model class
public class ImageSearchResult {
    public List<Image> value;
    public ImageSearchResult() {
        value = new ArrayList<>();
    }

    public void update() {

    }

    public static final class Image {
        String thumbnailUrl;
        String contentUrl;

        @Override
        public String toString() {
            return thumbnailUrl + ", " + contentUrl + "\n";
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
    public int size() {
        return value != null ? value.size() : 0;
    }
}
