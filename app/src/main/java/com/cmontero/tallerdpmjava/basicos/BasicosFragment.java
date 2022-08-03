package com.cmontero.tallerdpmjava.basicos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentBasicosBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BasicosFragment extends Fragment {

    private FragmentBasicosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBasicosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtTitulo = binding.txtMiTitulo;
        EditText edtNombres = binding.edtNombres;
        Button btnBoton = binding.btnMiBoton;
        ImageView imgImagen = binding.imgImagen;
        SwitchMaterial switchMaterial = binding.switch1;
        ProgressBar progressBar = binding.progressBar;
        RadioGroup rgSexo = binding.rgSexo;
        RadioButton rbMasculino = binding.rbMasculino;
        RadioButton rbFemenino = binding.rbFemenino;

        txtTitulo.setText("Controles Basicos");

        btnBoton.setOnClickListener(v -> {
            String nombres = edtNombres.getText().toString();
            Toast.makeText(requireContext(),"Hola "+nombres,Toast.LENGTH_LONG).show();
        });

        imgImagen.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_andy));
        imgImagen.setBackgroundColor(getResources().getColor(R.color.background));

        switchMaterial.setOnClickListener(v->{
            if(switchMaterial.isChecked()){
                switchMaterial.setText("ON");
                progressBar.setVisibility(View.VISIBLE);
            }
            else{
                switchMaterial.setText("OFF");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        rgSexo.setOnCheckedChangeListener((radioGroup, i) -> {
            if(rbMasculino.isChecked()){
                Toast.makeText(requireContext(), "Sexo Masculino", Toast.LENGTH_SHORT).show();
            }
            else if(rbFemenino.isChecked()){
                Toast.makeText(requireContext(), "Sexo Femenino", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}