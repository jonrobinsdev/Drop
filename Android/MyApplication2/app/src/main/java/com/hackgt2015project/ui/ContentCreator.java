package com.hackgt2015project.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hackgt2015project.R;
import com.hackgt2015project.location.SingleShotLocationProvider;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentCreator extends AppCompatActivity {

	static final int REQUEST_TAKE_PHOTO = 1;
	String mCurrentPhotoPath;

	boolean HAS_PHOTO = false;

	ImageButton postButton;
	ImageButton clickButton;
	ImageButton attachButton;

	TextView attachCount;

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

		attachCount = (TextView) findViewById(R.id.attach_count);

		postContent = (EditText) findViewById(R.id.post_text);

		postButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				publishPost();
			}
		});

		clickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent();
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

						final ParseObject postObject = new ParseObject("treasure");
						postObject.add("user", ParseUser.getCurrentUser());
						postObject.add("text", postContent.getText().toString());
						postObject.add("location", new ParseGeoPoint(location.latitude, location.longitude));
						if (HAS_PHOTO) {
							try {
								File imgFile = new File(mCurrentPhotoPath);
								byte[] imgDataBa = new byte[(int)imgFile.length()];
								DataInputStream dataIs = new DataInputStream(new FileInputStream(imgFile));
								dataIs.readFully(imgDataBa);

								final ParseFile imageFile = new ParseFile(ParseUser.getCurrentUser().getObjectId()+System.currentTimeMillis(), imgDataBa);
								dialog.setTitle("Uploading image");
								imageFile.saveInBackground(new SaveCallback() {
									@Override
									public void done(ParseException e) {
										dialog.setTitle("Memorising this moment");
										try {
											postObject.add("image", imageFile.getUrl());
											postObject.save();
											dialog.dismiss();
											finish();
										} catch (ParseException e2) {
											e.printStackTrace();
											dialog.dismiss();
											AlertDialog.Builder builder =
													new AlertDialog.Builder(ContentCreator.this);
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
								}, new ProgressCallback() {
									@Override
									public void done(Integer integer) {
										dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
										dialog.setMax(100);
										dialog.setProgress(integer.intValue());
									}
								});
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							postObject.save();
							dialog.dismiss();
							finish();
						} catch (ParseException e) {
							e.printStackTrace();
							dialog.dismiss();
							AlertDialog.Builder builder =
									new AlertDialog.Builder(ContentCreator.this);
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

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = /*"file:" + */image.getAbsolutePath();
		return image;
	}

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			HAS_PHOTO = true;
			attachCount.setText("Image Attached");
			galleryAddPic();
		}
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
