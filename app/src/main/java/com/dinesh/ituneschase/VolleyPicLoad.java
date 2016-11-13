package com.dinesh.ituneschase;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public class VolleyPicLoad {
    private static VolleyPicLoad mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    /**
     * Constructor which initializes  RequestQueue and ImageLoader
     *
     * @param context
     */
    private VolleyPicLoad(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
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
     * This method is used to get the instance of the singleton VolleyPicLoad
     *
     * @param context
     * @return instance of VolleyPicLoad
     */
    public static synchronized VolleyPicLoad getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyPicLoad(context);
        }
        return mInstance;
    }

    /**
     * This method will create a RequestQueue for Volley
     *
     * @return instance of RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    /**
     * This method Adds request to RequestQueue.
     *
     * @param req these are request to network operations.
     * @param <T> request type.
     */
    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * This method is to load the image from url into the Imageview.
     *
     * @param iv  ImageView
     * @param url url
     */
    public void loadImage(final ImageView iv, String url) {
        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        iv.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        iv.setImageResource(R.drawable.itunes);
                    }
                });
        addToRequestQueue(request);
    }

    /**
     * This method returns the instance of ImageLoader
     *
     * @return instance of ImageLoader
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
