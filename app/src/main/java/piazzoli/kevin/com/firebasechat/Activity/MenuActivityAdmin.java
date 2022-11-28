package piazzoli.kevin.com.firebasechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import piazzoli.kevin.com.firebasechat.R;

public class MenuActivityAdmin extends AppCompatActivity {

    private CardView cardCompras;
    private CardView cardVideollamada;
    private CardView cardPerfil;
    private CardView cardAjustes;
    private CardView cardCerrarSesion;


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        cardCompras = findViewById(R.id.cardCompras);
        cardVideollamada = findViewById(R.id.cardVideollamada);
        cardPerfil = findViewById(R.id.cardPerfil);
        cardAjustes = findViewById(R.id.cardAjustes);
        cardCerrarSesion = findViewById(R.id.cardCerrarsesion);


        cardCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityAdmin.this, MainActivity.class);
                startActivity(intent);
            }
        });


        cardVideollamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivityAdmin.this, VerUsuariosActivity.class);
                startActivity(intent);
            }
        });

        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityAdmin.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityAdmin.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        cardCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MenuActivityAdmin.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        userID = user.getUid();
    }

}
