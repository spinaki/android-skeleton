package xyz.pinaki.androidbasics;

import java.util.List;

/**
 * Created by pinaki on 10/1/17.
 */
// the model class
public class ImageSearchResult {
    public List<Image> value;

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
}
