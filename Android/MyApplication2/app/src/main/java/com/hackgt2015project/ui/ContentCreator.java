package com.hackgt2015project.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hackgt2015project.R;
import com.hackgt2015project.location.SingleShotLocationProvider;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ContentCreator extends AppCompatActivity {

	ImageButton postButton;
	ImageButton clickButton;
	ImageButton attachButton;

	EditText postContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.activity_content_creator);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);

		postButton = (ImageButton) findViewById(R.id.btn_post);
		clickButton = (ImageButton) findViewById(R.id.btn_click);
		attachButton = (ImageButton) findViewById(R.id.btn_attach);

		postContent = (EditText) findViewById(R.id.post_text);

		postButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				publishPost();
			}
		});
	}

	private void publishPost() {
		if (postContent.getText().toString().isEmpty() || postContent.getText().toString() == null || postContent.getText().toString().equals("")) {
			postContent.setError("Your post cannot be empty");
			return;
		}

		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("Memorising this moment");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();

		SingleShotLocationProvider.requestSingleUpdate(this,
				new SingleShotLocationProvider.LocationCallback() {
					@Override
					public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

						ParseObject postObject = new ParseObject("treasure");
						postObject.add("user", ParseUser.getCurrentUser());
						postObject.add("text", postContent.getText().toString());
						postObject.add("location", new ParseGeoPoint(location.latitude, location.longitude));
						try {
							postObject.save();
							dialog.dismiss();
							finish();
						} catch (ParseException e) {
							e.printStackTrace();
							dialog.dismiss();
							AlertDialog.Builder builder = new AlertDialog.Builder(ContentCreator.this);
							builder.setTitle("Error");
							builder.setMessage("We ran into some issues and couldn't save your memory. Please try again!");
							builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
							builder.show();
						}
					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
