
package piazzoli.kevin.com.firebasechat.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.util.Objects;

import piazzoli.kevin.com.firebasechat.Persistencia.UsuarioDAO;
import piazzoli.kevin.com.firebasechat.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView goBackBtn;
    private Button goEditProfileBtn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    private TextView retName;
    private TextView retrieveName;
    private TextView retrieveEmail;
    private TextView retrieveGender;
    private TextView retrieveTelephone;
    //private TextView retrieveDoB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");

        retName = findViewById(R.id.usernameTextView);
        retrieveName = findViewById(R.id.fullName);
        retrieveEmail = findViewById(R.id.emailAddress);
        retrieveGender = findViewById(R.id.genderTV);
        retrieveTelephone = findViewById(R.id.telephoneTV);
       // retrieveDoB = findViewById(R.id.age);

        goBackBtn = findViewById(R.id.goBackBtn);
        goEditProfileBtn = findViewById(R.id.btnEditProfile);
        getdata();

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        goEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private void getdata(){
        ValueEventListener addValueEventListener = databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                String email = dataSnapshot.child("correo").getValue(String.class);
                String genero = dataSnapshot.child("genero").getValue(String.class);
                String telefono = dataSnapshot.child("telefono").getValue(String.class);
              //  String dayofbirth = dataSnapshot.child("fechaDeNacimiento").getVaue(long.class);

                retrieveName.setText(nombre);
                retrieveEmail.setText(email);
                retName.setText(nombre);
                retrieveGender.setText(genero);
                retrieveTelephone.setText(telefono);
              //  retrieveDoB.setText(dayofbirth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Fallo en traer información", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

