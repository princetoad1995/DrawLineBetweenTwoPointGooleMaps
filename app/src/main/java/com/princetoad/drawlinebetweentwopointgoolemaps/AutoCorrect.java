package com.princetoad.drawlinebetweentwopointgoolemaps;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by PRINCE D. TOAD on 12/13/2016.
 */
public class AutoCorrect {

    private PlacesAutoCompleteAdapter mPlacesAdapter;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private GoogleApiClient mGoogleApiClient;
    private PlacesAutoCompleteAdapter.PlaceAutocomplete item;

    public PlacesAutoCompleteAdapter getmPlacesAdapter() {
        return mPlacesAdapter;
    }

    public AutoCorrect(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context).addApi(Places.GEO_DATA_API).build();
        mPlacesAdapter = new PlacesAutoCompleteAdapter(context, android.R.layout.simple_list_item_1, mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
    }


    public void start() {
        mGoogleApiClient.connect();
    }

    public void stop() {
        mGoogleApiClient.disconnect();
    }

    public void autoCorrect(int position) {
        item = mPlacesAdapter.getItem(position);
        final String placeId = String.valueOf(item.placeId);
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e("place", "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }
                final Place place = places.get(0);
            }
        });
    }
}
