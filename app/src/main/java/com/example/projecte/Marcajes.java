package com.example.projecte;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ncorti.slidetoact.SlideToActView;

import java.util.Calendar;

public class Marcajes extends AppCompatActivity {

    SlideToActView sdEntrada;
    SlideToActView sdSalida;
    TextView tvMantenimiento;

    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;
    int idmarcaje = 0;
    boolean primeravegada = true;

    Spinner spIncidencia;
    Button btAceptar;
    ImageView btCancelar;

    String datos[]= {"Salida normal","Comida", "Descanso", "Urgencia médica", "Horas extras" };

    String incidenciaseleccionada;
    boolean cancelado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcajes);

        sdEntrada = findViewById(R.id.sdEntrada);
        sdSalida = findViewById(R.id.sdSalida);
        tvMantenimiento = findViewById(R.id.tvMantenimiento);

        sdEntrada.setEnabled(false);
        sdSalida.setEnabled(false);
        sdEntrada.setInnerColor(Color.parseColor("#BDBDBD"));
        sdSalida.setInnerColor(Color.parseColor("#BDBDBD"));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/0").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    sdEntrada.setEnabled(true);
                    sdEntrada.setInnerColor(Color.parseColor("#8BC34A"));
                    Animation anim = new AlphaAnimation(0.5f, 1.0f);
                    anim.setDuration(1800); //You can manage the blinking time with this parameter
                    anim.setStartOffset(20);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(Animation.INFINITE);
                    sdEntrada.startAnimation(anim);
                    primeravegada=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        FirebaseDatabase.getInstance().getReference("Marcas/" + uid).orderByChild("idmarcaje").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                idmarcaje = Integer.parseInt(dataSnapshot.getKey());

                if (primeravegada==true) {
                    if (idmarcaje % 2 == 0 && idmarcaje > 0 || idmarcaje == 0) {
                        sdSalida.setEnabled(true);
                        sdSalida.setInnerColor(Color.parseColor("#F44336"));
                        Animation anim = new AlphaAnimation(0.5f, 1.0f);
                        anim.setDuration(1800); //You can manage the blinking time with this parameter
                        anim.setStartOffset(20);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        sdSalida.startAnimation(anim);
                    } else {
                        sdEntrada.setEnabled(true);
                        sdEntrada.setInnerColor(Color.parseColor("#8BC34A"));
                        Animation anim = new AlphaAnimation(0.5f, 1.0f);
                        anim.setDuration(1800); //You can manage the blinking time with this parameter
                        anim.setStartOffset(20);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        sdEntrada.startAnimation(anim);
                    }
                    primeravegada=false;
                }

                idmarcaje = idmarcaje + 1;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        sdEntrada.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {

                sdEntrada.resetSlider();
                sdEntrada.setInnerColor(Color.parseColor("#BDBDBD"));
                sdEntrada.clearAnimation();

                sdSalida.setEnabled(true);
                sdSalida.setInnerColor(Color.parseColor("#F44336"));
                Animation anim = new AlphaAnimation(0.5f, 1.0f);
                anim.setDuration(1800); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                sdSalida.startAnimation(anim);

                Calendar calendario = Calendar.getInstance();
                String horas, minutos, segundos, hora, dia, mes, año, fecha;
                boolean tipomarcaje;

                horas = Integer.toString(calendario.get(Calendar.HOUR_OF_DAY));
                minutos = Integer.toString(calendario.get(Calendar.MINUTE));
                segundos = Integer.toString(calendario.get(Calendar.SECOND));
                dia = Integer.toString(calendario.get(Calendar.DAY_OF_MONTH));
                mes = Integer.toString(calendario.get(Calendar.MONTH)+1);
                año = Integer.toString(calendario.get(Calendar.YEAR));
                hora = horas + " : " + minutos + " : " + segundos;
                fecha = dia + "/" + mes + "/" + año;

                tipomarcaje = false;

                FirebaseDatabase.getInstance().getReference("Marcas").child(uid);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid).child(Integer.toString(idmarcaje));
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/hora").setValue(hora);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/fecha").setValue(fecha);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/tipomarcaje").setValue(tipomarcaje);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/tipoincidencia").setValue("Entrada");

                SuperActivityToast.create(Marcajes.this, new Style(), Style.TYPE_BUTTON)
                        //.setButtonText("UNDO")
                        //.setButtonIconResource(R.drawable.ic_undo)
                        .setProgressBarColor(Color.WHITE)
                        .setText("Entrada realizada correctamente")
                        .setDuration(Style.DURATION_SHORT)
                        .setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(Color.parseColor("#8BC34A"))
                        .setAnimations(Style.ANIMATIONS_POP).show();

            }
        });

        sdSalida.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {

                showDialog();

            }
        });

        sdEntrada.setOnSlideResetListener(new SlideToActView.OnSlideResetListener() {
            @Override
            public void onSlideReset(SlideToActView slideToActView) {
                sdEntrada.setEnabled(false);
            }
        });

        sdSalida.setOnSlideResetListener(new SlideToActView.OnSlideResetListener() {
            @Override
            public void onSlideReset(SlideToActView slideToActView) {
                if (cancelado == false){
                    sdSalida.setEnabled(false);
                }
                else{
                    sdSalida.setEnabled(true);
                    cancelado = false;
                }
            }
        });

        tvMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Marcajes.this, mantenimientoMarcajes.class);
                startActivity(intent);

            }
        });

    }

    private void showDialog() {

        AlertDialog.Builder alert;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }

        else {
            alert = new AlertDialog.Builder(this);
        }

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.seleccionar_incidencia,null);
        spIncidencia = view.findViewById(R.id.spIncidencia);
        btAceptar = view.findViewById(R.id.btAceptar);
        btCancelar = view.findViewById(R.id.btCancelar);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,R.layout.spinner_text_view,datos);
        spIncidencia.setAdapter(adaptador);

        alert.setView(view);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();



        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                incidenciaseleccionada = spIncidencia.getSelectedItem().toString();
                sdSalida.setEnabled(false);
                sdSalida.setInnerColor(Color.parseColor("#BDBDBD"));
                sdSalida.resetSlider();
                sdSalida.clearAnimation();

                sdEntrada.setEnabled(true);
                sdEntrada.setInnerColor(Color.parseColor("#8BC34A"));
                Animation anim = new AlphaAnimation(0.5f, 1.0f);
                anim.setDuration(1800); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                sdEntrada.startAnimation(anim);

                Calendar calendario = Calendar.getInstance();
                String horas, minutos, segundos, hora, dia, mes, año, fecha;
                boolean tipomarcaje;

                horas = Integer.toString(calendario.get(Calendar.HOUR_OF_DAY));
                minutos = Integer.toString(calendario.get(Calendar.MINUTE));
                segundos = Integer.toString(calendario.get(Calendar.SECOND));
                dia = Integer.toString(calendario.get(Calendar.DAY_OF_MONTH));
                mes = Integer.toString(calendario.get(Calendar.MONTH)+1);
                año = Integer.toString(calendario.get(Calendar.YEAR));
                hora = horas + " : " + minutos + " : " + segundos;
                fecha = dia + "/" + mes + "/" + año;

                tipomarcaje = true;

                FirebaseDatabase.getInstance().getReference("Marcas").child(uid);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid).child(Integer.toString(idmarcaje));
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/hora").setValue(hora);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/fecha").setValue(fecha);
                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/tipomarcaje").setValue(tipomarcaje);

                FirebaseDatabase.getInstance().getReference("Marcas/" + uid + "/" + idmarcaje + "/tipoincidencia").setValue(incidenciaseleccionada);

                dialog.cancel();

                if(incidenciaseleccionada.equals("Salida normal")){
                    SuperActivityToast.create(Marcajes.this, new Style(), Style.TYPE_BUTTON)
                            //.setButtonText("UNDO")
                            //.setButtonIconResource(R.drawable.ic_undo)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Salida realizada correctamente")
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(Color.parseColor("#F44336"))
                            .setAnimations(Style.ANIMATIONS_POP).show();
                }

                else{
                    SuperActivityToast.create(Marcajes.this, new Style(), Style.TYPE_BUTTON)
                            //.setButtonText("UNDO")
                            //.setButtonIconResource(R.drawable.ic_undo)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Incidencia marcada correctamente")
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(Color.parseColor("#FFC107"))
                            .setAnimations(Style.ANIMATIONS_POP).show();
                }

            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelado = true;
                sdSalida.resetSlider();
                dialog.cancel();
            }
        });

    }

}
