
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

import piazzoli.kevin.com.firebasechat.Persistencia.UsuarioDAO;
import piazzoli.kevin.com.firebasechat.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView goBackBtn;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView userTextView = (TextView) findViewById(R.id.typeOfUser);
        goBackBtn = findViewById(R.id.goBackBtn);

       goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null)
                {

                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String user = userProfile.user;
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    userTextView.setText(user);

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void returnMenu(){
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UsuarioDAO.getInstancia().isUsuarioLogeado()){
            //el usuario esta logeado y hacemos algo
        }else{
            returnMenu();
        }
    }


}

