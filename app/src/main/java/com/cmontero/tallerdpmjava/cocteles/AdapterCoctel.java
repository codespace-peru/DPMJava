package com.cmontero.tallerdpmjava.cocteles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cmontero.tallerdpmjava.R;
import com.cmontero.tallerdpmjava.utils.Constantes;

import java.util.List;

public class AdapterCoctel extends RecyclerView.Adapter<AdapterCoctel.ViewHolder> {

    private final List<Coctel> listaCocteles;

    public AdapterCoctel(List<Coctel> listaCocteles) {
        this.listaCocteles = listaCocteles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_coctel, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String urlImagen = listaCocteles.get(position).getStrDrinkThumb();
        String nombreImagen = listaCocteles.get(position).getStrDrink();

        holder.nombreCoctel.setText(nombreImagen);

        Glide.with(holder.itemView.getContext())
                .load(urlImagen)
                .centerCrop()
                .placeholder(R.drawable.ic_wine)
                .into(holder.imagenCoctel);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constantes.ID_COCTEL, listaCocteles.get(position).getIdDrink());
            bundle.putString(Constantes.URL_IMAGEN, urlImagen);
            Navigation.findNavController(v).navigate(R.id.detalleCoctelFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaCocteles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagenCoctel;
        private final TextView nombreCoctel;

        public ViewHolder(@NonNull View view) {
            super(view);
            imagenCoctel = view.findViewById(R.id.imagenCoctel);
            nombreCoctel = view.findViewById(R.id.nombreCoctel);
        }
    }
}
