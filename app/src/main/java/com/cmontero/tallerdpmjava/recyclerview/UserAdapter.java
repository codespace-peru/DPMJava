package com.cmontero.tallerdpmjava.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cmontero.tallerdpmjava.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<UsuarioModel> listaUsuarios;

    public UserAdapter(List<UsuarioModel> lista) {
        this.listaUsuarios = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_usuarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();
        String nombresUsuario = listaUsuarios.get(position).getNombres();
        String emailUsuario = listaUsuarios.get(position).getEmail();
        String sexoUsuario = listaUsuarios.get(position).getSexo();
        String fechaNac = listaUsuarios.get(position).getFechaNac();

        /*holder.name.setText(nombreUsuario);
        holder.surname.setText(apellidosUsuario);
        int imageid = context.getResources().getIdentifier(foto, "drawable", context.getPackageName());
        holder.img.setImageDrawable(ContextCompat.getDrawable(context,imageid));*/
        holder.name.setText(nombresUsuario);
        holder.email.setText(emailUsuario);
        holder.sexo.setText(sexoUsuario);
        holder.fechaNac.setText(fechaNac);
        if(sexoUsuario.equals("Masculino")){
            holder.img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.user_masculino));
        }
        else if(sexoUsuario.equals("Femenino")){
            holder.img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.user_femenino));
        }


    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView email;
        private final TextView sexo;
        private final TextView fechaNac;
        private final ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtNombres);
            email = itemView.findViewById(R.id.txtEmail);
            sexo = itemView.findViewById(R.id.txtSexo);
            fechaNac = itemView.findViewById(R.id.txtFechaNac);
            img = itemView.findViewById(R.id.imgFoto);
        }
    }
}
