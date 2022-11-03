package piazzoli.kevin.com.firebasechat.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import piazzoli.kevin.com.firebasechat.Persistencia.UsuarioDAO;
import piazzoli.kevin.com.firebasechat.R;

public class MenuActivity extends AppCompatActivity {

    private CardView cardCompras;
    private CardView cardVideollamada;
    private CardView cardPerfil;
    private CardView cardAjustes;
    private CardView cardCerrarSesion;

    private Button btnVerUsuarios;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cardCompras = findViewById(R.id.cardCompras);
        cardVideollamada = findViewById(R.id.cardVideollamada);
        cardPerfil = findViewById(R.id.cardPerfil);
        cardAjustes = findViewById(R.id.cardAjustes);
        cardCerrarSesion = findViewById(R.id.cardCerrarsesion);
        btnVerUsuarios = findViewById(R.id.btnVerUsuarios);

        cardCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnVerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,VerUsuariosActivity.class);
                startActivity(intent);
            }
        });


        cardVideollamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, VerUsuariosActivity.class);
                startActivity(intent);
            }
        });

        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        cardCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        userID = user.getUid();
    }

}
