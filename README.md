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
service = retrofit.create(BingImageService.class);
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

#### OKHttp
##### Download a File
````
public void downloadImage(String url, final String fileName) throws Exception {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {
                    File downloadedFile = new File(getCacheDir(), fileName);
                    BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
                    sink.writeAll(response.body().source());
                    sink.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
````
#### AsyncTask
AysncTask has a bunch of generic types. The three types used by an asynchronous task are the following:
* Params, the type of the parameters sent to the task upon execution.
* Progress, the type of the progress units published during the background computation.
* Result, the type of the result of the background computation.

Using the above the AsyncTask is defined as:
````
class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    protected Long doInBackground(URL... urls) {
    // ...
    }
    protected void onProgressUpdate(Integer... progress) {
    // ...
    }
    protected void onPostExecute(Long result) {
    // ...
    }
}
````

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
It can be executed as follows:
````
new QueryAPITask().execute(str1, str2, str3); // the strings are the params for doInBackground as specified in the first template type.
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

// or use
ExecutorService executor = Executors.newFixedThreadPool(10);

for (int i = 0; i < NUM_JOBS; i++) {
    executor.execute(new Runnable() {
        public void run() {
            // Do some long running operation in background
            // on a worker thread in the thread pool!

            // then update the UI thread. 2 ways
            <!-- 1. invoke runOnUiThread from activity -->
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                // ...
                }
            });

            // or call the handler
            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    // ...
                }
            });
        }
    });
}

executor.shutdown();
````
Executors using Futures:
````
ExecutorService executorService = Executors.newFixedThreadPool(10);
String[] urls = imageUrls();
for (final String url : urls) {
            final Future future = executorService.submit(new Callable<Bitmap>() {
                @Override
                public Bitmap call() throws Exception {
                    Request request = new Request.Builder().url(url).build();
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    InputStream inputStream = response.body().byteStream();
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inSampleSize = 3;
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, o);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = new ImageView(context);
                            imageView.setImageBitmap(bitmap);
                            gridLayout.addView(imageView);
                        }
                    });
                    return bitmap;

                }
            });
            // if you dont want to do runOnUiThread -- you can do a future call
            try {
                Bitmap b = (Bitmap)future.get();
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(b);
                gridLayout.addView(imageView);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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