package com.jccsisc.myuberclone.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.myuberclone.models.Driver;

public class DriverProvider {

    DatabaseReference mDataBase;

    public DriverProvider() {
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }

    //crear el driver ne la db
    public Task<Void> create(Driver driver) {
        return mDataBase.child(driver.getId()).setValue(driver);
    }
}
