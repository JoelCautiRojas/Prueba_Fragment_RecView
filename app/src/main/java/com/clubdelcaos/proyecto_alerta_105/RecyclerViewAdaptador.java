package com.clubdelcaos.proyecto_alerta_105;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Joel-64 on 07/11/2017.
 */

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView fecha,encabezado,descripcion;
        private ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            encabezado = (TextView) itemView.findViewById(R.id.encabezado);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            imagen = (ImageView) itemView.findViewById(R.id.portada);
        }
    }
    public List<NoticiaModelo> noticiaLista;

    public RecyclerViewAdaptador(List<NoticiaModelo> noticiaLista) {
        this.noticiaLista = noticiaLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_noticia,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        holder.fecha.setText(noticiaLista.get(position).getFecha());
        holder.encabezado.setText(noticiaLista.get(position).getEncabezado());
        holder.descripcion.setText(noticiaLista.get(position).getDescripcion());
        holder.imagen.setImageResource(noticiaLista.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return noticiaLista.size();
    }
}
