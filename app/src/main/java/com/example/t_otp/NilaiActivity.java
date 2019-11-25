package com.example.t_otp;

import android.content.Context;
import android.os.Bundle;

import com.example.t_otp.fragments.NilaiFragment;
import com.example.t_otp.helpers.ToaLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

public class NilaiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String kdKelas = "NONE";

        if (bundle != null){
            kdKelas = bundle.getString(MainActivity.EXTRA_KD_KELAS_KEY);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, NilaiFragment.newInstance(kdKelas))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return false;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
