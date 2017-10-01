package xyz.pinaki.androidbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private static final String BING_API_URL= "https://api.cognitive.microsoft.com/";
    private BingImageService service;
    SearchResultsViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_page);
        // RecyclerView for images
        // Staggered Grid Layout
        // Adapter with the viewholder pattern
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, 1));
        // should show the views
        ImageSearchResult searchResult = new ImageSearchResult();
        adapter = new SearchResultsViewAdapter(searchResult);
        recyclerView.setAdapter(adapter);

        // retrofit stuff to query the data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BING_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(BingImageService.class);

        // A separate activity for showing the bitmaps
        // with a viewpager to show the images.
        // fragmentstatepager adapter to provide the data
        // this activity may be started by an Intent

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // start a query by posting to the APIThread
                int offset = 0;
                Call<ImageSearchResult> call = service.search(query, 100, offset, "en-us", "Moderate");
                call.enqueue(new Callback<ImageSearchResult>() {
                    @Override
                    public void onResponse(Call<ImageSearchResult> call, Response<ImageSearchResult> response) {
                        ImageSearchResult imageSearchResult = response.body();
                        Log.i("ImageSearchResult", imageSearchResult.toString());
                        adapter.update(imageSearchResult);
                    }

                    @Override
                    public void onFailure(Call<ImageSearchResult> call, Throwable t) {
                        Log.i("ImageSearchResult Fail", t.toString());
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // wait and start a query.
                return false;
            }
        });
        return true;
    }

    private interface BingImageService {
        @Headers({"Ocp-Apim-Subscription-Key: 5db7a77b19d943f2a4cce83ce6b59502"})
        @GET("/bing/v5.0/images/search")
        Call<ImageSearchResult> search(@Query("q") String query, @Query("count") Integer count,
                                       @Query("offset") Integer offset, @Query("mkt") String market,
                                       @Query("safeSearch") String safeSearch);
    }
}
