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

import notifyme.com.notifyme.R;

import static android.content.ContentValues.TAG;

public class LogInActvity extends Activity {
    //Declare UI Components as variable
    public Button mLoginButton;
    public Button mForgetButton;
    public Button mRegisterButton;
    public EditText mUsernameEditText;
    public EditText mPasswordEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_actvity);
        // Initialize UI components
        mLoginButton = (Button)findViewById(R.id.loginLoginButton);
        mForgetButton = (Button)findViewById(R.id.loginForgetButton);
        mRegisterButton = (Button)findViewById(R.id.loginRegisterButton);
        mUsernameEditText = (EditText)findViewById(R.id.loginUsernameEditText);
        mPasswordEditText = (EditText)findViewById(R.id.loginPasswordEditText);

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

        //Set onclick button for login
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mEmail = mUsernameEditText.getText().toString();
                final String mPassword = mPasswordEditText.getText().toString();

                if (!mEmail.matches(EMAIL_PATTERN)) {

                    Toast.makeText(LogInActvity.this, "Please check your email", Toast.LENGTH_SHORT).show();

                } else if(mPassword.equals("")) {

                    Toast.makeText(LogInActvity.this, "Please check your password", Toast.LENGTH_SHORT).show();

                } else {

                    // [START sign_in_with_email]

                    mAuth.signInWithEmailAndPassword(mEmail, mPassword)

                            .addOnCompleteListener(LogInActvity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    if (task.isSuccessful()) {
                                        Intent takeUserToLogin = new Intent(LogInActvity.this, MainActivity.class);
                                        startActivity(takeUserToLogin);
                                        Toast.makeText(LogInActvity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(LogInActvity.this, "Please check your login credential", Toast.LENGTH_SHORT).show();

                                    }

                                }

                            });
                }

            }
        });
        //Set onclick button for Forget password
        mForgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserForgetPassword = new Intent(LogInActvity.this, PasswordResetActvity.class);
                startActivity(takeUserForgetPassword);

            }
        });
        //Set onclick button for Register
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserToRegister = new Intent(LogInActvity.this, RegisterActivity.class);
                startActivity(takeUserToRegister);
            }
        });

    }
}
