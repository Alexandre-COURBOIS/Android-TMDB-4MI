package mi.videoprime.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class TMDBApiClient {
    private static TMDBApiClient instance;
    private static Context ctx;
    private RequestQueue requestQueue;

    private TMDBApiClient(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized TMDBApiClient getInstance(Context context) {
        if (instance == null) {
            instance = new TMDBApiClient(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
