package com.cmontero.tallerdpmjava.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.databinding.FragmentRecyclerviewBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private FragmentRecyclerviewBinding binding;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        List<UsuarioModel> listaUsuarios = new ArrayList<>();
        listaUsuarios = getListaUsuarios();

        RecyclerView.Adapter<UserAdapter.ViewHolder> adapter = new UserAdapter(listaUsuarios);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private List<UsuarioModel> getListaUsuarios(){
        List<UsuarioModel> userModels = new ArrayList<>();
        userModels.add(new UsuarioModel(1,"Juan Lopez","juanlopez@mail.com","Masculino","01/02/1982"));
        userModels.add(new UsuarioModel(2,"Mario Soto","mariosoto@mail.com","Masculino","21/05/1981"));
        userModels.add(new UsuarioModel(3,"Luz Perez","luzperez@mail.com","Femenino","11/06/1983"));
        userModels.add(new UsuarioModel(4,"Teresa Moreno","teresamoreno@mail.com","Femenino","13/02/1980"));
        userModels.add(new UsuarioModel(5,"Omar Quiroz","omarquiroz@mail.com","Masculino","14/04/1981"));
        userModels.add(new UsuarioModel(6,"Pedro Linares","pedrolinares@mail.com","Masculino","12/11/1982"));
        userModels.add(new UsuarioModel(7,"Mateo Guillen","mateoguillen@mail.com","Masculino","03/03/1981"));
        userModels.add(new UsuarioModel(8,"Katy Salazar","katysalazar@mail.com","Femenino","24/10/1980"));
        userModels.add(new UsuarioModel(9,"Alfredo Rios","alfredorios@mail.com","Masculino","02/02/1982"));
        userModels.add(new UsuarioModel(10,"Elena Valdez","elenavaldez@mail.com","Femenino","09/01/1981"));
        return userModels;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}