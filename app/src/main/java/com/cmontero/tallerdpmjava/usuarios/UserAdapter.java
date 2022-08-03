package com.cmontero.tallerdpmjava.usuarios;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.utils.Constantes;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<UsuarioModel> listaUsuarios;
    public UserAdapter(List<UsuarioModel> lista) {
        this.listaUsuarios = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_usuarios,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Context context = holder.names.getContext();

        String nombresUsuario = listaUsuarios.get(position).getNombres();
        String emailusuario = listaUsuarios.get(position).getEmail();
        String fechaNacUsuario = listaUsuarios.get(position).getFechaNac();
        String sexoUsuario = listaUsuarios.get(position).getSexo();

        holder.names.setText(nombresUsuario);
        holder.email.setText(emailusuario);
        holder.fechaNac.setText(fechaNacUsuario);
        holder.sexo.setText(sexoUsuario);

        if(sexoUsuario.equals("Masculino"))
            holder.img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.user_masculino));
        else if(sexoUsuario.equals("Femenino"))
            holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_femenino));

        holder.imgEditar.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constantes.MODO_EDICION, true);
            bundle.putParcelable(Constantes.OBJ_USUARIO, listaUsuarios.get(position));
            Navigation.findNavController(holder.itemView).navigate(R.id.newUserFragment, bundle);

        });

        holder.imgDelete.setOnClickListener(v->{
            DAOUsuarios daoUsuarios = new DAOUsuarios(context);
            int result = daoUsuarios.eliminarUsuario(listaUsuarios.get(position).getId());
            if(result<=0){
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Se eliminó correctamente", Toast.LENGTH_SHORT).show();
                listaUsuarios.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
            }
        });

        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constantes.OBJ_USUARIO, listaUsuarios.get(position));
            Navigation.findNavController(holder.itemView).navigate(R.id.detalleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView names;
        private final TextView email;
        private final TextView fechaNac, sexo;
        private final ImageView img, imgEditar, imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.txtNombres);
            email = itemView.findViewById(R.id.txtEmail);
            fechaNac = itemView.findViewById(R.id.txtFechaNac);
            sexo = itemView.findViewById(R.id.txtSexo);
            img = itemView.findViewById(R.id.imgFoto);
            imgEditar = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
