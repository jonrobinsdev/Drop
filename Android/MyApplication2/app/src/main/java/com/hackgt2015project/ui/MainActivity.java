package com.hackgt2015project.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.hackgt2015project.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private Button signInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FacebookSdk.sdkInitialize(getApplicationContext());
		Parse.initialize(this, "WDxhZjVJSBXeBudlLDLU5RutPQnNU1gifhcD4Zuq", "8kg3SvqXKnnfo0PqabwLdv8Nv0iCOgEJ564qlknN");
		ParseFacebookUtils.initialize(getApplicationContext());

		signInButton = (Button) findViewById(R.id.signInButton);
		signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logInWithFaceBook();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
	}

	public void logInWithFaceBook(){
		final List<String> permissions = new ArrayList<String>();
		permissions.add("user_friends");
		//permissions.add("public_profile");
		ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if (user == null) {
					Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("MyApp", "User signed up and logged in through Facebook!");
				} else {
					Log.d("MyApp", "User logged in through Facebook!");
				}
			}
		});
	}
}
