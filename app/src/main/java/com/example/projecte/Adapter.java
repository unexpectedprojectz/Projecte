package com.example.projecte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Fichaje> data;

    Adapter(Context context, ArrayList<Fichaje> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // bind the textview with data received

        Fichaje fichaje = data.get(i);
        String fecha = fichaje.getFecha();
        String hora = fichaje.getHora();
        boolean tipomarcaje = fichaje.isTipomarcaje();
        String tipoincidencia = fichaje.getTipoincidencia();

        viewHolder.fecha.setText(fecha);
        viewHolder.hora.setText(hora);
        if(tipomarcaje==false){
            viewHolder.tipomarcaje.setImageResource(R.drawable.entrada);
            viewHolder.tipo.setText("Entrada");
        }
        else{
            if (tipoincidencia.equals("Salida normal")){
                viewHolder.tipomarcaje.setImageResource(R.drawable.salida);
                viewHolder.tipo.setText("Salida");
            }
            else{
                viewHolder.tipomarcaje.setImageResource(R.drawable.incidencia);
                viewHolder.tipo.setText("Incidencia: " + tipoincidencia);
            }
        }

        // similarly you can set new image for each card and descriptions

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fecha,hora,tipo;
        ImageView tipomarcaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent i = new Intent(v.getContext(),Details.class);
                    //i.putExtra("fecha",data.get(getAdapterPosition()).getFecha());
                    //v.getContext().startActivity(i);
                }
            });
            fecha = itemView.findViewById(R.id.fecha);
            hora = itemView.findViewById(R.id.hora);
            tipomarcaje = itemView.findViewById(R.id.tipomarcaje);
            tipo = itemView.findViewById(R.id.tipo);
        }
    }
}
