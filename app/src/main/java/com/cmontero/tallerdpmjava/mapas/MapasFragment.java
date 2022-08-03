package com.cmontero.tallerdpmjava.mapas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentBasicosBinding;
import com.cmontero.tallerdpmjava.databinding.FragmentMapasBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapasFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    List<Marker> listaMarcadores = new ArrayList<>();
    private FragmentMapasBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        /*if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);*/
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng sucursal1 = new LatLng(-12.126476, -77.021562);
        LatLng sucursal2 = new LatLng(-12.132766, -76.981192);
        String nombre = "Veterinaria Mis Patitas";
        String sede1 = "Sede Miraflores";
        String sede2 = "Sede Santiago de Surco";

        MarkerOptions markerOptions1 = new MarkerOptions();
        MarkerOptions markerOptions2 = new MarkerOptions();

        markerOptions1.position(sucursal1)
                .title(nombre)
                .snippet(sede1)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        markerOptions2.position(sucursal2)
                .title(nombre)
                .snippet(sede2)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        Marker marcador1 = map.addMarker(markerOptions1);
        Marker marcador2 = map.addMarker(markerOptions2);

        /*map.addMarker(markerOptions1);
        map.addMarker(markerOptions2);*/

        listaMarcadores.add(marcador1);
        listaMarcadores.add(marcador2);

        centrarMarcadores(listaMarcadores);

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sucursal1,13F));

    }

    private void centrarMarcadores(List<Marker> listaMarcadores) {

        LatLngBounds.Builder constructor = new LatLngBounds.Builder();
        for (Marker marker:listaMarcadores) {
            constructor.include(marker.getPosition());
        }

        int ancho = getResources().getDisplayMetrics().widthPixels;
        int alto = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (alto * 0.25);

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(constructor.build(), ancho, alto, padding));

    }
}