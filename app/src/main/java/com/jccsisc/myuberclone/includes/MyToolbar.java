package com.jccsisc.myuberclone.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jccsisc.myuberclone.R;

public class MyToolbar {

    //metodo para utilizar el toolbar donde querramos
    public static void showToolbar(AppCompatActivity activity, String title, boolean upButton) {
        //paa que el toolbar funcione tenemos que irnos al manifest y ponerle una propiedad parentActivityName a la actividad
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
