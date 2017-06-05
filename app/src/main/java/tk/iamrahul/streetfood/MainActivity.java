package tk.iamrahul.streetfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    TextView txt;
    String firstName;
    Profile profile;
    LoginButton loginButton;
    ImageView imageview;
    Context context = MainActivity.this;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        profile = Profile.getCurrentProfile();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
        txt = (TextView)findViewById(R.id.text_fb);
        imageview = (ImageView)findViewById(R.id.image_profile);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String user_id = loginResult.getAccessToken().getUserId();
                txt.setText("Fb token"+loginResult.getAccessToken().getUserId());
                Picasso.with(context)
                        .load("https://graph.facebook.com/" + user_id+ "/picture?type=large")
                        .into(imageview);

            }

            @Override
            public void onCancel() {

                txt.setText("cancle user login");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}
