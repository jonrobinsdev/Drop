package com.hackgt2015project.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.hackgt2015project.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private ListView memoryList;
    private CustomListViewAdapter adapter;
    private List<RowItem> memoryListContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        profilePicture = (ImageView)findViewById(R.id.profileImage);
        memoryList = (ListView)findViewById(R.id.memoryList);
        memoryListContents = new ArrayList<RowItem>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("treasure");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject object : list) {
                        ArrayList<String> images = (ArrayList<String>) object.get("image");
                        for (String blah : images) {
                            //INSERT CODE TO PULL IMAGES FROM URL ARRAYLIST
                        }
                        ArrayList<String> texts = (ArrayList<String>) object.get("text");
                        for (String text : texts) {
                            //INSERT CODE TO PULL TEXTS
                        }
                    }
                } else {
                }
            }
        });

        RowItem rowItem = new RowItem("Jonathan Robins");
        memoryListContents.add(rowItem);
        memoryListContents.add(rowItem);
        memoryListContents.add(rowItem);
        memoryListContents.add(rowItem);
        adapter = new CustomListViewAdapter(ProfileActivity.this, R.layout.row_item, memoryListContents);
        memoryList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
