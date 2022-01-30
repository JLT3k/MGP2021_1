package com.sdm.mgp2021_1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;

// Created by TanSiewLan2021

public class Mainmenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_start;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL = "email";

    private ShareButton btn_fbShare;

    private ShareDialog share_Dialog;
    private int PICK_IMAGE_REQUEST = 1;

    AccessToken accessToken;

    ProfilePictureView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FacebookSdk.setApplicationId("254725833306068");
        FacebookSdk.isInitialized();

        if (BuildConfig.DEBUG){
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_fbShare = (ShareButton)findViewById(R.id.fb_share_button);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        StateManager.Instance.AddState(new Mainmenu());
    }

    // To share info on FB
    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.yellowcircle);

        if (ShareDialog.canShow(SharePhotoContent.class)){
            System.out.println("photoShown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption("Thank you for playing MGP2020. Your final score is "+ 10)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            btn_fbShare.setShareContent(content);
        }
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_start)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
        }
        if (v == btn_fbShare)
        {
            System.out.println("Share button pressed");
            shareScore();
        }

        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
    }
	
    @Override
    public void OnEnter(SurfaceView _view) {
    }
	
    @Override
    public void OnExit() {
    }
	
    @Override
    public void Update(float _dt) {
    }
	
    @Override
    public String GetName() {
        return "Mainmenu";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        shareScore();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
