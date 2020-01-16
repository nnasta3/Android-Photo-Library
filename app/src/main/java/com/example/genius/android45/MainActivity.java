package com.example.genius.android45;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Album> listAdapter;
    private ArrayList<Album> albums = new ArrayList<>();
    private Album selectedAlbum = null;
    static public Album currAlbum = null;

    Button saveButton;
    EditText editAlbum;
    Button cancelEdit;
    ImageButton searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIds();
        hideEditButtons();
        Toast.makeText(MainActivity.this, R.string.clickandhold, Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.mainListView);
        Album[] albumList = new Album[] {new Album("stock1"),
                new Album("stock2"),
                new Album("stock3")};
        albums.addAll(Arrays.asList(albumList));

        listAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, albums);

        listAdapter.add(new Album("stock4"));
        listAdapter.add(new Album("stock5"));

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album selectedItem = (Album) parent.getItemAtPosition(position);
                selectedAlbum = selectedItem;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Album selectedItem = (Album) parent.getItemAtPosition(position);
                selectedAlbum = selectedItem;
                openAlbum();
                return true;
            }
        });


//        listView.setAdapter(
//                new ArrayAdapter<>(this, R.layout.activity_main, albums)
//        );
     //   albums = getResources().getStringArray(R.array.)
    }

    public void addAlbum(View view) {
        hideEditButtons();
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View addAlbumView = getLayoutInflater().inflate(R.layout.add_album, null);
        final EditText addAlbum = addAlbumView.findViewById(R.id.albumName);

        final Button submitAlbum = addAlbumView.findViewById(R.id.submitAlbum);
        System.out.println("check1");
        submitAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumName = null;
                if(!addAlbum.getText().toString().isEmpty()){
                    albumName = addAlbum.getText().toString();
                    for (Album a : albums){
                        if(albumName.equals(a.getName())){
                            Toast.makeText(MainActivity.this, R.string.error_duplicate_msg, Toast.LENGTH_SHORT).show();
                            return;
                            //selectedAlbum = null;
                        }
                    }
                    Album newAlbum = new Album(albumName);
                    albums.add(newAlbum);
                    //listAdapter.add(albumName);
                    listView.setAdapter(listAdapter);
                    selectedAlbum = null;
                    addAlbum.setText("");
                    Toast.makeText(MainActivity.this, "Album Successfully Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, R.string.empty_field, Toast.LENGTH_SHORT).show();
                    System.out.println("EMPTY FIELD");
                }
                // hides soft keyboard after submit is pressed
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(submitAlbum.getWindowToken(), 0);
            }
        });
        addAlbumView.setVisibility(View.VISIBLE);
        mBuilder.setView(addAlbumView);
        mBuilder.show();
        selectedAlbum = null;
//        dialog = mBuilder.create();
//        dialog.show();

    }

    public void delAlbum(View view) {
        hideEditButtons();
        if(selectedAlbum == null){
            Toast.makeText(MainActivity.this, R.string.noSelection, Toast.LENGTH_SHORT).show();
            return;
        }
        albums.remove(selectedAlbum);
        listView.setAdapter(listAdapter);
        selectedAlbum = null;
    }

    public void editAlbum(View view) {
        editAlbum = findViewById(R.id.editAlbumName);
        if(selectedAlbum == null){
            Toast.makeText(MainActivity.this, R.string.noSelection, Toast.LENGTH_SHORT).show();
            return;
        }
        if(!selectedAlbum.getName().isEmpty()){
            editAlbum.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            cancelEdit.setVisibility(View.VISIBLE);
            editAlbum.setText(selectedAlbum.getName());
            final Button saveButton = findViewById(R.id.saveAlbum);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editAlbum.getText().toString().equals(selectedAlbum.getName())){
                        Toast.makeText(MainActivity.this, R.string.error_duplicate_msg, Toast.LENGTH_SHORT).show();
                        selectedAlbum = null;
                        return;
                    }
                    int index = albums.indexOf(selectedAlbum);
                    Album selectedAlbum = albums.get(index);
                    String newAlbum = editAlbum.getText().toString();
                    for(Album a : albums){
                        if(newAlbum.equals(a.getName())){
                            selectedAlbum = null;
                            Toast.makeText(MainActivity.this, R.string.error_duplicate_msg, Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                    selectedAlbum.setName(newAlbum);
                    listView.setAdapter(listAdapter);
                    hideEditButtons();
                    Toast.makeText(MainActivity.this, R.string.renamesuccess, Toast.LENGTH_SHORT).show();
                    selectedAlbum = null;
                    // hides soft keyboard after saved item
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(editAlbum.getWindowToken(), 0);
                }
            });
            cancelEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideEditButtons();
                    selectedAlbum = null;
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm.hideSoftInputFromWindow(cancelEdit.getWindowToken(), 0);
                }
            });

        }
        else{
            Toast.makeText(MainActivity.this, R.string.noSelection, Toast.LENGTH_SHORT).show();
        }
    }

    public void openAlbum( ) {
        System.out.println("OPEN ALBUM");
        if(selectedAlbum == null){
            Toast.makeText(MainActivity.this, R.string.noSelection, Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedAlbum.getName().isEmpty()){
            Toast.makeText(MainActivity.this, R.string.noSelection, Toast.LENGTH_SHORT).show();
            return;
        }
        currAlbum = selectedAlbum;
        Intent i = new Intent(MainActivity.this, PhotosActivity.class);
        startActivity(i);
    }

    public void searchAlbum(View view) {
        selectedAlbum = null;

        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

    private void getIds() {
        editAlbum = findViewById(R.id.editAlbumName);
        saveButton = findViewById(R.id.saveAlbum);
        cancelEdit = findViewById(R.id.cancelEdit);
        searchButton = findViewById(R.id.search);
    }

    public void hideEditButtons(){
        saveButton.setVisibility(View.INVISIBLE);
        editAlbum.setVisibility(View.INVISIBLE);
        cancelEdit.setVisibility(View.INVISIBLE);
        editAlbum.setText("");
    }

}
