package xyz.pinaki.androidbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_page);
        // RecyclerView for images
        // Staggered Grid Layout
        // Adapter with the viewholder pattern
        // retrofit stuff to query the data
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        // should show the views
        RecyclerView.Adapter adapter = new SearchResultsViewAdapter();
        recyclerView.setAdapter(adapter);

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // wait and start a query.
                return false;
            }
        });
        return true;
    }
}
