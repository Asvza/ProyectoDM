package piazzoli.kevin.com.firebasechat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import piazzoli.kevin.com.firebasechat.R;

/**
 * Created by user on 19/02/2018. 19
 */

public class LoginActivity extends AppCompatActivity {

    Spinner spinner;
    public static final String [] languages = {"Select Language", "English", "French", "Spanish"};

    private EditText txtCorreo,txtContraseña;
    private Button btnLogin,btnRegistro;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedLang = parent.getItemAtPosition(position).toString();

                if (selectedLang.equals("English")){

                    setLocal(LoginActivity.this, "en");
                    finish();
                    startActivity(getIntent());


                }else if (selectedLang.equals("Spanish")){
                    setLocal(LoginActivity.this, "es");
                    finish();
                    startActivity(getIntent());

                }
                else if ((selectedLang.equals("French"))){
                    setLocal(LoginActivity.this, "fr");
                    finish();
                    startActivity(getIntent());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        txtCorreo = (EditText) findViewById(R.id.idCorreoLogin);
        txtContraseña = (EditText) findViewById(R.id.idContraseñaLogin);
        btnLogin = (Button) findViewById(R.id.idLoginLogin);
        btnRegistro = (Button) findViewById(R.id.idRegistroLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = txtCorreo.getText().toString();
                if(isValidEmail(correo) && validarContraseña()){
                    String contraseña = txtContraseña.getText().toString();
                    mAuth.signInWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        /*/ Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "Se logeo correctamente.", Toast.LENGTH_SHORT).show();
                                        nextActivity();*/
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user.isEmailVerified()){
                                            //redirect to user profile
                                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                        }else{
                                            user.sendEmailVerification();
                                            Toast.makeText(LoginActivity.this, "Check ur email to verify account", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Error, credenciales incorrectas.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this, "Validaciones funcionando.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
            }
        });
        //UsuarioDAO.getInstancia().añadirFotoDePerfilALosUsuariosQueNoTienenFoto();

    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña(){
        String contraseña;
        contraseña = txtContraseña.getText().toString();
        if(contraseña.length()>=6 && contraseña.length()<=16){
            return true;
        }else return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Toast.makeText(this, "Usuario logeado.", Toast.LENGTH_SHORT).show();
            nextActivity();
        }
    }

    private void nextActivity(){
        startActivity(new Intent(LoginActivity.this,MenuActivity.class));
        finish();
    }


    //languaje
    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }



}
