package me.minutz.rwmanager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API {

    public static MutableLiveData<ArrayList<JSONObject>> useri = new MutableLiveData<>();

    public static void updateMembri(final Context ctxt){
        new API.SR(ctxt, new JSONObject(),"get_membri"){
            @Override
            public void raspuns(JSONObject rasp) {
                ArrayList<JSONObject> l = new ArrayList<>();
                useri.postValue(l);
                try {
                    JSONArray jar = rasp.getJSONArray("rasp");
                    for(int i=0;i<jar.length();i++) {

                        JSONObject stringpasser = new JSONObject();
                        final String uiddeadus = jar.getString(i);
                        stringpasser.accumulate("uid",uiddeadus);
                        Log.e("GETTING",uiddeadus);

                        new SR(ctxt, stringpasser,"get_membru") {
                            @Override
                            public void raspuns(JSONObject rasp) {
                                try {
                                    String aproape = rasp.getString("rasp");
                                    rasp = new JSONObject(aproape);
                                    ArrayList<JSONObject> currentArray = API.useri.getValue();
                                    assert currentArray != null;
                                    currentArray.add(rasp);
                                    API.useri.postValue(currentArray);
                                    Log.e("RASP",rasp.toString());
                                    Log.e("GOT",uiddeadus);
                                } catch (JSONException e) {
                                    Log.e("ERROARE",e.toString());
                                }
                            }

                            @Override
                            public void eroare(VolleyError eroare) {
                                Log.e("ERROARE",eroare.toString());
                            }
                        };
                    }

                } catch (JSONException e) {
                    Log.e("ERROARE",e.toString());
                }

            }
            @Override
            public void eroare(VolleyError eroare) {
                Log.e("EROARE",eroare.toString());
            }
        };
    }

    public abstract static class SR{

        public SR(Context ctxt, JSONObject json, String ramura){
            RequestQueue queue = Volley.newRequestQueue(ctxt);
            queue.getCache().clear();
            try {
                json.accumulate("pas","--pas--");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "https://ro049.com/rvw-api/androidapi/";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url +ramura, json, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            raspuns(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            eroare(error);
                        }
                    });
            jsonObjectRequest.setShouldCache(false);
            queue.add(jsonObjectRequest);
        }

        public abstract void raspuns(JSONObject rasp);
        public abstract void eroare(VolleyError eroare);
    }
}
