package com.cmontero.tallerdpmjava.usuarios;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentDetalleuserBinding;
import com.cmontero.tallerdpmjava.utils.Constantes;

public class DetalleUserFragment extends Fragment {

    private UsuarioModel mUsuario;
    private FragmentDetalleuserBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsuario = getArguments().getParcelable(Constantes.OBJ_USUARIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleuserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgFotoUsuario = binding.imgFotoUsuario;
        TextView txtNombresUsuario = binding.txtNombresUsuario;
        TextView txtEmailUsuario = binding.txtEmailUsuario;
        TextView txtFechaNacUsuario = binding.txtFechaNacUsuario;
        TextView txtSexoUsuario = binding.txtSexoUsuario;

        if(mUsuario.getSexo().equals("Masculino"))
            imgFotoUsuario.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.user_masculino));
        else
            imgFotoUsuario.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.user_femenino));

        txtNombresUsuario.setText(mUsuario.getNombres());
        txtEmailUsuario.setText(mUsuario.getEmail());
        txtFechaNacUsuario.setText(mUsuario.getFechaNac());
        txtSexoUsuario.setText(mUsuario.getSexo());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}