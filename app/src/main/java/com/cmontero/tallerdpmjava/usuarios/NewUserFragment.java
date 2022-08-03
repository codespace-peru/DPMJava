package com.cmontero.tallerdpmjava.usuarios;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentNewuserBinding;
import com.cmontero.tallerdpmjava.utils.Constantes;
import com.cmontero.tallerdpmjava.utils.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class NewUserFragment extends Fragment {

    private boolean mModoEdicion;
    private UsuarioModel mUsuario;
    EditText edtFechaNacUsuario;
    NavController navController;
    private FragmentNewuserBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mModoEdicion = getArguments().getBoolean(Constantes.MODO_EDICION);
            mUsuario = getArguments().getParcelable(Constantes.OBJ_USUARIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewuserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(binding.getRoot());
        TextView txtTitulo = binding.txtMiTitulo;
        EditText edtNombresUsuario = binding.edtNombreUsuario;
        EditText edtEmailUsuario = binding.edtEmailUsuario;
        Spinner spSexoUsuario = binding.spSexoUsuario;
        edtFechaNacUsuario = binding.edtFechaNacUsuario;
        Button btnGuardarUsuario = binding.btnGuardarUsuario;
        Button btnEditarUsuario = binding.btnEditarUsuario;

        DAOUsuarios daoUsuarios = new DAOUsuarios(requireContext());



        List<String> sexos = new ArrayList<>();
        sexos.add("Masculino");
        sexos.add("Femenino");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, sexos);
        spSexoUsuario.setAdapter(spinnerAdapter);


        edtFechaNacUsuario.setOnClickListener(v->{
            showDatePickerDialog();
        });

        if(mModoEdicion){//Editar usuario
            txtTitulo.setText("Editar Usuario");
            btnGuardarUsuario.setVisibility(View.GONE);
            btnEditarUsuario.setVisibility(View.VISIBLE);
            edtNombresUsuario.setText(mUsuario.getNombres());
            edtEmailUsuario.setText(mUsuario.getEmail());
            spSexoUsuario.setSelection(spinnerAdapter.getPosition(mUsuario.getSexo()));
            edtFechaNacUsuario.setText(mUsuario.getFechaNac());
        }
        else{ // Agregar usuario
            btnGuardarUsuario.setVisibility(View.VISIBLE);
            btnEditarUsuario.setVisibility(View.GONE);
        }

        btnGuardarUsuario.setOnClickListener(v->{
            try{
                String nombres = edtNombresUsuario.getText().toString();
                String email = edtEmailUsuario.getText().toString();
                String fechaNac = edtFechaNacUsuario.getText().toString();
                String sexo = spSexoUsuario.getSelectedItem().toString();
                UsuarioModel usuarioModel = new UsuarioModel(nombres,email,fechaNac,sexo);
                long x = daoUsuarios.registrarUsuario(usuarioModel);
                if(x > 0){
                    Toast.makeText(requireContext(), "Se registró correctamente", Toast.LENGTH_LONG).show();
                    navController.popBackStack();
                }
                else {
                    Toast.makeText(requireContext(), "Ocurrió un error al registrar", Toast.LENGTH_LONG).show();
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }

        });

        btnEditarUsuario.setOnClickListener(v->{
            try{
                String nombres = edtNombresUsuario.getText().toString();
                String email = edtEmailUsuario.getText().toString();
                String fechaNac = edtFechaNacUsuario.getText().toString();
                String sexo = spSexoUsuario.getSelectedItem().toString();
                UsuarioModel usuarioModel = new UsuarioModel(mUsuario.getId(),nombres,email,fechaNac,sexo);
                int x = daoUsuarios.editarUsuario(usuarioModel);
                if(x > 0){
                    Toast.makeText(requireContext(), "Se editó correctamente", Toast.LENGTH_LONG).show();
                    navController.popBackStack();
                }
                else {
                    Toast.makeText(requireContext(), "Ocurrió un error al editar", Toast.LENGTH_LONG).show();
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }

        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, mes, dia) -> {
            final String fecha = dia + "/" + (mes+1) + "/" + year;// 18/06/2022
            edtFechaNacUsuario.setText(fecha);
        });
        newFragment.show(requireActivity().getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}