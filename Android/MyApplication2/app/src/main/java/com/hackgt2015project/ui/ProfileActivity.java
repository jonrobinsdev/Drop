package com.hackgt2015project.ui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.hackgt2015project.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private ListView memoryList;
    private CustomListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        profilePicture = (ImageView)findViewById(R.id.profileImage);
        memoryList = (ListView)findViewById(R.id.memoryList);

        List<RowItem> list = new ArrayList<RowItem>();
        RowItem rowItem = new RowItem(R.drawable.treasure_chest);
        list.add(rowItem);
        list.add(rowItem);
        list.add(rowItem);
        list.add(rowItem);
        list.add(rowItem);
        list.add(rowItem);
        list.add(rowItem);
        rowItem = new RowItem("Jonathan Robins");
        list.add(rowItem);
        rowItem = new RowItem(R.drawable.treasure_chest, "Jonathan Robins");
        list.add(rowItem);

        adapter = new CustomListViewAdapter(ProfileActivity.this, R.layout.row_item, list);
        adapter.selectedRowsItems = new int[list.size()];
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
