package com.cmontero.tallerdpmjava.cocteles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentCoctelesBinding;
import com.android.volley.RequestQueue;
import com.cmontero.tallerdpmjava.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetalleCoctelFragment extends Fragment {

    ListView lvIngredientes;
    TextView tvNombreCoctel;
    TextView tvIngredientes;
    TextView tvTitlePreparacion;
    TextView tvPreparacion;
    ImageView imagenCoctel;
    ProgressBar progressBar;
    RequestQueue cola;
    private String idCoctel;
    private String urlCoctel;
    private FragmentCoctelesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCoctel = getArguments().getString(Constantes.ID_COCTEL);
            urlCoctel = getArguments().getString(Constantes.URL_IMAGEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoctelesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNombreCoctel = view.findViewById(R.id.txtCoctel);
        lvIngredientes = view.findViewById(R.id.lvIngredientes);
        tvIngredientes = view.findViewById(R.id.txtIngredientes);
        tvTitlePreparacion = view.findViewById(R.id.txtTitlePreparacion);
        tvPreparacion = view.findViewById(R.id.txtPreparacion);
        imagenCoctel = view.findViewById(R.id.imagenCoctel);
        progressBar = view.findViewById(R.id.progressBar);
        lvIngredientes.setDivider(null);

        cola = Volley.newRequestQueue(requireContext());

        String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + idCoctel;

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(requireContext())
                .load(urlCoctel)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imagenCoctel);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("drinks");
                        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                        tvNombreCoctel.setText(jsonObject.getString("strDrink"));
                        tvNombreCoctel.setVisibility(View.VISIBLE);
                        tvIngredientes.setVisibility(View.VISIBLE);
                        tvTitlePreparacion.setVisibility(View.VISIBLE);
                        tvPreparacion.setVisibility(View.VISIBLE);
                        tvPreparacion.setText(jsonObject.getString("strInstructions"));
                        progressBar.setVisibility(View.GONE);
                        List<String> listaIngredientes = new ArrayList<>();
                        for(int i=1; i<=15;i++){
                            if(!jsonObject.getString("strIngredient"+i).equals("null"))
                                listaIngredientes.add(jsonObject.getString("strIngredient" + i));
                            else
                                break;
                        }
                        ArrayAdapter adaptador = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listaIngredientes);
                        lvIngredientes.setAdapter(adaptador);

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