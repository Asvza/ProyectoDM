package piazzoli.kevin.com.firebasechat.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import piazzoli.kevin.com.firebasechat.R;

public class EditProfileActivity extends AppCompatActivity {

    private Button goToSaveProfileBtn;
    private ImageView goBackBtn;

    private EditText retrieveName;
    private EditText retrieveEmail;
    private EditText retrieveGender;
    private EditText retrieveTelephone;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuarios");

        goToSaveProfileBtn = findViewById(R.id.btnSaveProfile);
        goBackBtn = findViewById(R.id.goBackBtn);
        retrieveName = findViewById(R.id.editTextTextPersonName);
        retrieveGender = findViewById(R.id.editTextGender);
        retrieveTelephone = findViewById(R.id.editTextPhoneNumber);

        getdata();


        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        goToSaveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editName = retrieveName.getText().toString();
                String editGender = retrieveGender.getText().toString();
                String editPhoneNumber = retrieveTelephone.getText().toString();

                updatedata(editName,editGender,editPhoneNumber);

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
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
                String genero = dataSnapshot.child("genero").getValue(String.class);
                String telefono = dataSnapshot.child("telefono").getValue(String.class);
                //  String dayofbirth = dataSnapshot.child("fechaDeNacimiento").getVaue(long.class);

                retrieveName.setText(nombre);
                retrieveGender.setText(genero);
                retrieveTelephone.setText(telefono);
                //  retrieveDoB.setText(dayofbirth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Fallo en traer información", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updatedata(String editName, String editGender, String editPhoneNumber){

        HashMap<String, Object> Usuario = new HashMap<String,Object>();
        Usuario.put("nombre",editName);
        Usuario.put("genero",editGender);
        Usuario.put("telefono",editPhoneNumber);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(userID).updateChildren(Usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    retrieveName.setText("");
                    retrieveGender.setText("");
                    retrieveTelephone.setText("");
                    Toast.makeText(EditProfileActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(EditProfileActivity.this, "Fallo en actualizar información", Toast.LENGTH_SHORT).show();

                }

            }
        });{


        }



    }


}