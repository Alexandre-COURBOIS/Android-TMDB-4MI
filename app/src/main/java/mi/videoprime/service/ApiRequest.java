package mi.videoprime.service;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import mi.videoprime.service.TMDBApiClient;

import org.json.JSONObject;

public abstract class ApiRequest<T> {
    private final Class<T> responseType;
    private final Context context;

    public ApiRequest(Context context, Class<T> responseType) {
        this.context = context;
        this.responseType = responseType;
    }

    public void makeRequest(int method, String url) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        T apiResponse = gson.fromJson(response.toString(), responseType);
                        onSuccess(apiResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        });

        TMDBApiClient.getInstance(context).addToRequestQueue(request);
    }

    protected abstract void onSuccess(T response);

    protected abstract void onError(VolleyError error);
}
