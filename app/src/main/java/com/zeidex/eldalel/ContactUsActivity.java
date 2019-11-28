package com.zeidex.eldalel;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends BaseActivity implements OnMapReadyCallback {
    @BindView(R.id.mapView_contactus)
    MapView mapView;

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        mapView.setVisibility(View.GONE);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);

    }

    @OnClick(R.id.item_contact_back)
    public void onBack(){
        onBackPressed();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);

        // Updates the location and zoom of the MapView

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(24.0233598, 40.5805386), 6);
        mMap.animateCamera(cameraUpdate);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(24.0233598, 40.5805386))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(getString(R.string.we_here));

        mMap.addMarker(markerOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mapView.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
//        mapView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mapView.onStop();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();

    }
}
