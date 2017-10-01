### Networking
#### Retrofit
* [retrofit cliffnotes](https://guides.codepath.com/android/Consuming-APIs-with-Retrofit)
* [github.io](http://square.github.io/retrofit/)
````
compile 'com.squareup.retrofit2:retrofit:2.3.0'
compile 'com.squareup.retrofit2:converter-gson:2.3.0' // gson
compile 'com.squareup.picasso:picasso:2.5.2' // picasso
````
Initialize
````
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
````
Define the interface for various requests
##### Get
````
private interface BingImageService {
        @Headers({"Key: Value"})
        @GET("/bing/v5.0/images/search")
        Call<ImageSearchResult> search(@Query("q") String query,
            @Query("count") Integer count);
    }

````
Instantiate the class as below:
````
retrofit.create(BingImageService.class);
````

##### Execute A Call
The model class is `ImageSearchResult`.
````
Call<ImageSearchResult> call = service.search(query, count, offset, "en-us", "Moderate");
````
* Synchronous
````
ImageSearchResult imageSearchResult = call.execute().body();
````
* Asynchronous
````
call.enqueue(new Callback<ImageSearchResult>() {
    @Override
    public void onResponse(Call<ImageSearchResult> call, Response<ImageSearchResult> response) {
        int statusCode = response.code();
        ImageSearchResult result = response.body();
    }

    @Override
    public void onFailure(Call<ImageSearchResult> call, Throwable t) {
        // Log error here since request failed
    }
});

````
##### Post
* post a json
````
public class User {
    int id;
    String name;
}
````
The endpoint in interface might look like this:
````
@POST("/users/new")
Call<User> createUser(@Body User user);
````
Invoke the API as such:

#### AsyncTask
````
private class QueryAPITask extends AsyncTask<String, Integer, ImageSearchResult> {
        protected ImageSearchResult doInBackground(String... queryParams) {
            try {
                return queryRetrofit("snowboarding", count, offset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ImageSearchResult result) {
            int c = recyclerViewAdapter.getItemCount();
            recyclerViewAdapter.update(result);
            recyclerViewAdapter.notifyItemRangeInserted(c, result.size());
        }
    }
````
#### ExecutorService
Define a bunch of threads and execute them.
````
int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
// Construct thread pool passing in configuration options
// int minPoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit,
// BlockingQueue<Runnable> workQueue
ThreadPoolExecutor executor = new ThreadPoolExecutor(NUMBER_OF_CORES*2,
    NUMBER_OF_CORES * 2, 60L, TimeUnit.SECONDS,
    new LinkedBlockingQueue<Runnable>()
);
executor.execute(new Runnable() {
    public void run() {
        // Do some long running operation in background
        // on a worker thread in the thread pool!
    }
});
executor.shutdown();
````
#### HandlerThread

### Views
### Recycler View
* [RecyclerView Cliffnotes](https://guides.codepath.com/android/using-the-recyclerview)
* [Tutorial](https://www.raywenderlich.com/126528/android-recyclerview-tutorial)

### View Pager
* [FragmentPageAdapter](https://guides.codepath.com/android/ViewPager-with-FragmentPagerAdapter)

#### Gravity vs Layout_Gravity
* Gravity tells a view how it should align / layout its children.
* Layout_Gravity:
* https://www.youtube.com/watch?v=FIg0X7mU06Y
* https://stackoverflow.com/questions/26583716/why-the-right-button-cant-on-the-right-of-parent-like-left-button-on-the-left-o
##### Layout_Gravity for LinearLayout
* For a horizontal Linear Layout the following values make sense:
top
center
bottom
That is because the children of a horizontal Linear Layout are layed out
horizontally one after the other. Only thing can be controlled using the
android:layout_gravity is how a child view is positioned vertically.
* For a vertical Linear Layout the following values make sense:
left
center
right
That is because the children of a vertical Linear Layout are layed out vertically one below the other. Only thing can be controlled using the android:layout_gravity is how a child view is positioned horizontally.
* See details [here](http://sandipchitale.blogspot.in/2010/05/linearlayout-gravity-and-layoutgravity.html).
* More on Layouts: https://guides.codepath.com/android/Constructing-View-Layouts#optimizing-layout-performance
* Layout Weight: https://developer.android.com/guide/topics/ui/layout/linear.html#Weight

* Import Constraint Layout
````
compile 'com.android.support:recyclerview-v7:25.3.1'
````
### Service

### Intent

### Database