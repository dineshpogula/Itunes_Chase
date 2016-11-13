package com.dinesh.ituneschase;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public class VolleySearch implements Response.Listener<String>, Response.ErrorListener {

    private static volatile VolleySearch volleySearch;
    private Context context;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    /**
     * Constructor of VolleySearch and initializes the  RequestQueue and ImageLoader
     *
     * @param context
     */
    private VolleySearch(Context context) {
        this.context = context;
        mRequestQueue = getRequestQueue();
        imageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * This method initializes the RequestQueue with HurlStack.
     *
     * @return instance of RequestQueue.
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context, new HurlStack() {
                @Override
                protected HttpURLConnection createConnection(URL url) throws IOException {
                    HttpURLConnection connection = super.createConnection(url);
                    connection.setInstanceFollowRedirects(false);
                    return connection;
                }
            });
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    /**
     * This mehod is used to get the instance of single class of VolleySearch.
     *
     * @param context
     * @return instance of VolleySearch
     */
    public static VolleySearch getInstance(Context context) {
        if (volleySearch == null)
            volleySearch = new VolleySearch(context);
        return volleySearch;
    }

    /**
     * This method is used to search the tracks based in search text and entity.
     *
     * @param search         search item.
     * @param entity         entity
     * @param resultListener ResultListener for results.
     */
    public void search(String search, Entity entity, ResultListener resultListener) {
        this.resultListener = resultListener;
        search = search.trim().toLowerCase();
        search = search.replaceAll(" ", "+");
        String url = "https://itunes.apple.com/search?term=" + search + "&entity=" + entity.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this);
        mRequestQueue.add(stringRequest);
    }

    /**
     * This method will parse the JSON string form url and give Tracks.
     *
     * @param json
     * @return instace of Tracks.
     */
    public Tracks getTracks(String json) {
        Gson gson = new Gson();
        Tracks tracks = gson.fromJson(json, Tracks.class);
        return tracks;
    }

    /**
     * This method will give the ImageLoader.
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * This Interface is used to subscribe for the results.
     */
    public interface ResultListener {
        void onResults(Track[] tracks);

        void onError(VolleyError error);
    }

    private ResultListener resultListener;

    @Override
    public void onResponse(String response) {
        Log.d("Response is ", response);
        Track[] results = getTracks(response).results;
        Log.d("Response is ", results.length + "");
        if (resultListener != null) resultListener.onResults(results);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        if (resultListener != null) resultListener.onError(error);
    }
}
