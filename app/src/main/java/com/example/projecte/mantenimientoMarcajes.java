package com.example.projecte;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class mantenimientoMarcajes extends AppCompatActivity {

    DatabaseReference dbReference;

    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Fichaje> marcajes = new ArrayList<Fichaje>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_marcajes);

        recyclerView = findViewById(R.id.recyclerView);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        dbReference = FirebaseDatabase.getInstance().getReference();

        dbReference.child("Marcas/" + uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Fichaje fichaje = snapshot.getValue(Fichaje.class);
                    String fecha = fichaje.getFecha();
                    String hora = fichaje.getHora();
                    boolean tipomarcaje = fichaje.isTipomarcaje();
                    String tipoincidencia = fichaje.getTipoincidencia();

                    marcajes.add(new Fichaje(fecha, hora, tipomarcaje, tipoincidencia));
                }

                Collections.reverse(marcajes);
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, marcajes);
        recyclerView.setAdapter(adapter);

    }
}
