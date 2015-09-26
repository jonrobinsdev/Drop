package com.hackgt2015project.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hackgt2015project.R;

public class CreatorActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creator);

		FloatingActionButton createButton = (FloatingActionButton) findViewById(R.id.new_post_button);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreatorActivity.this, ContentCreator.class);
				startActivity(intent);
			}
		});
	}

}
