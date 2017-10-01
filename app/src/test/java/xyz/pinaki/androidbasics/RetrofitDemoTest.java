package xyz.pinaki.androidbasics;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pinaki on 9/27/17.
 */
public class RetrofitDemoTest {
    @Test
    public void test1() throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitDemo.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        RetrofitDemo.GitHub github = retrofit.create(RetrofitDemo.GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<RetrofitDemo.Contributor>> call = github.contributors("square", "retrofit");
//        Call<List<RetrofitDemo.Contributor>> call = github.contributors("spinaki", "android-camera");

        // Fetch and print a list of the contributors to the library.
        List<RetrofitDemo.Contributor> contributors = call.execute().body();
        for (RetrofitDemo.Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

}