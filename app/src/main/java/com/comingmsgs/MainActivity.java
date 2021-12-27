package com.comingmsgs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Search:
                Toast.makeText(this, "search selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Groups:
                Toast.makeText(this, "groups selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.Invite:
                Toast.makeText(this, "invite selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.Setting:
                Toast.makeText(this, "setting selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.Logout:
                auth.signOut();//get instance of  firebase auth in onCreate method  to make signout work
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // it make menu visible in main screen
        return super.onCreateOptionsMenu(menu);

    }
}