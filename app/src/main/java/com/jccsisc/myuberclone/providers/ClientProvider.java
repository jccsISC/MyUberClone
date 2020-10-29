package com.jccsisc.myuberclone.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.myuberclone.models.Client;

public class ClientProvider {
    DatabaseReference mDataBase;

    public ClientProvider() {
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    //crear el cliente ne la db en el nodo Clients
    public Task<Void> create(Client client) {
        return mDataBase.child(client.getId()).setValue(client);
    }

}
