package com.cmontero.tallerdpmjava.usuarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentUsuariosBinding;
import com.cmontero.tallerdpmjava.utils.Constantes;

import java.util.List;

public class UsuariosFragment extends Fragment {

    private FragmentUsuariosBinding binding;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUsuariosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        navController = Navigation.findNavController(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerUsuarios;

        DAOUsuarios daoUsuarios = new DAOUsuarios(requireContext());
        List<UsuarioModel> listaUsuarios = daoUsuarios.listarUsuarios();

        RecyclerView.Adapter<UserAdapter.ViewHolder> adapter = new UserAdapter(listaUsuarios);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem hide = menu.findItem(R.id.action_addUser);
        hide.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_addUser){
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constantes.MODO_EDICION, false);
            bundle.putParcelable(Constantes.OBJ_USUARIO, null);
            navController.navigate(R.id.newUserFragment, bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}