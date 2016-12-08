package notifyme.com.notifyme.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import notifyme.com.notifyme.DatebaseSupport.User;
import notifyme.com.notifyme.R;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends Activity {


    public Button mRegisterButton;
    public Button mLoginButton;
    public EditText mNameEditText;
    public EditText mEmailEditText;
    public EditText mPasswordEditText;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static  String CHECK_PASSWORD = ("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{7,21})");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterButton = (Button) findViewById(R.id.registerRegisterButton);
        mLoginButton = (Button) findViewById(R.id.registerLoginButton);
        mNameEditText = (EditText) findViewById(R.id.registerNameEditText);
        mEmailEditText = (EditText) findViewById(R.id.registerEmailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.registerPasswordEditText);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    // User is signed in

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {

                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }

            }

        };


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mEmail = mEmailEditText.getText().toString();
                final String mPassword = mPasswordEditText.getText().toString();
                final String mFullName = mNameEditText.getText().toString();


                if(mFullName.equals("")){

                    Toast.makeText(RegisterActivity.this, "Please check your Name", Toast.LENGTH_SHORT).show();

                }else if(!mEmail.matches(EMAIL_PATTERN)){

                    Toast.makeText(RegisterActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();

                }else if((!mPassword.matches(CHECK_PASSWORD))){

                    Toast.makeText(RegisterActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();

                } else {
                    mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {


                                writeNewUser(mFullName);
                                Intent takeUserToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(takeUserToLogin);
                                Toast.makeText(RegisterActivity.this, "successfully", Toast.LENGTH_SHORT).show();


                            }


                        }

                    });

                }

            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserToLogin = new Intent(RegisterActivity.this, LogInActvity.class);
                startActivity(takeUserToLogin);

            }
        });
    }


    private void writeNewUser(String FullName) {


        User user = new User(FullName);

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);

    }


}
