package com.cmontero.tallerdpmjava.cocteles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentCoctelesBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CoctelesFragment extends Fragment {

    Spinner spinner;
    RecyclerView recyclerView;
    TextView txtTitleLista;
    Button btnBuscar;
    String ingrediente;
    RequestQueue cola;
    String url;
    private FragmentCoctelesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoctelesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = binding.spinner;
        recyclerView = binding.recyclerCocteles;
        btnBuscar = binding.btnBuscar;
        txtTitleLista = binding.txtLista;

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnBuscar.setOnClickListener(v->{
            buscar();
        });

        List<String> listaIngredientes = new ArrayList<>();
        listaIngredientes.add("Pisco");
        listaIngredientes.add("Rum");
        listaIngredientes.add("Tequila");
        listaIngredientes.add("Vodka");

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, listaIngredientes);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ingrediente = spinner.getSelectedItem().toString();
                url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingrediente;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        } );

        cola = Volley.newRequestQueue(requireContext());
    }

    public void buscar() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        txtTitleLista.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = response.getJSONArray("drinks");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Coctel>>() {}.getType();
                        List<Coctel> listaCocteles = gson.fromJson(String.valueOf(jsonArray), listType);
                        AdapterCoctel miAdapter = new AdapterCoctel(listaCocteles);
                        recyclerView.setAdapter(miAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(requireContext(), "Error al traer los datos: " + error.toString(), Toast.LENGTH_LONG).show()
        );

        cola.add(jsonRequest);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}