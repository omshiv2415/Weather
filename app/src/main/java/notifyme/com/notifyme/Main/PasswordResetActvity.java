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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import notifyme.com.notifyme.R;

import static android.content.ContentValues.TAG;

public class PasswordResetActvity extends Activity {

    public Button mResetButton;
    public Button mLoginButton;
    public EditText mEmailEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_actvity);

        mLoginButton = (Button) findViewById(R.id.resetlLoginButton);
        mResetButton = (Button) findViewById(R.id.resetResetButton);
        mEmailEditText = (EditText) findViewById(R.id.resetEmailEditText);
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
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserForgetPassword = new Intent(PasswordResetActvity.this, LogInActvity.class);
                startActivity(takeUserForgetPassword);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmailReset = mEmailEditText.getText().toString();
                if (!mEmailReset.matches(EMAIL_PATTERN)) {

                    Toast.makeText(PasswordResetActvity.this, "Please check your email", Toast.LENGTH_SHORT).show();

                } else {


                    mAuth.sendPasswordResetEmail(mEmailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(PasswordResetActvity.this, "Please check your email", Toast.LENGTH_SHORT).show();

                            Intent takeUserForgetPassword = new Intent(PasswordResetActvity.this, LogInActvity.class);
                            startActivity(takeUserForgetPassword);

                        }
                    });


                }

            }
        });
    }
}
