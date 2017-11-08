package com.clubdelcaos.proyecto_alerta_105;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel-64 on 07/11/2017.
 */

public class Tab_DirectorioFragment extends Fragment {

    private RecyclerView recyclerViewNoticia;
    private RecyclerViewAdaptador adaptadorNoticia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);
        recyclerViewNoticia = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerViewNoticia.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorNoticia = new RecyclerViewAdaptador(obtenerNoticias());
        recyclerViewNoticia.setAdapter(adaptadorNoticia);
        return view;
    }

    public List<NoticiaModelo> obtenerNoticias(){
        List<NoticiaModelo> noticia = new ArrayList<>();
        noticia.add(new NoticiaModelo("Lunes 06 de Noviembre del 2017","Noticia de primera","Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",R.drawable.imagen1));
        noticia.add(new NoticiaModelo("Martes 07 de Noviembre del 2017","Inicia las nuevas noticias","Cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.",R.drawable.imagen2));
        noticia.add(new NoticiaModelo("Miercoles 08 de Noviembre del 2017","Las mejores noticias para la comunidad","Es un hecho establecido hace demasiado tiempo que un lector se distraerá con el contenido del texto de un sitio mientras que mira su diseño.",R.drawable.imagen3));
        noticia.add(new NoticiaModelo("Jueves 09 de Noviembre del 2017","Noticias y mas noticias","El punto de usar Lorem Ipsum es que tiene una distribución más o menos normal de las letras, al contrario de usar textos como por ejemplo.",R.drawable.imagen4));
        noticia.add(new NoticiaModelo("Viernes 10 de Noviembre del 2017","Mira las noticias","Hay muchas variaciones de los pasajes de Lorem Ipsum disponibles, pero la mayoría sufrió alteraciones en alguna manera, ya sea porque se le agregó humor.",R.drawable.imagen5));
        return  noticia;
    }
}
