package com.jccsisc.myuberclone.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.myuberclone.models.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {
    DatabaseReference mDataBase;

    public ClientProvider() {
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    //crear el cliente ne la db en el nodo Clients
    public Task<Void> create(Client client) {

        //si quisieramos que excluyera el campo id haremos lo siguiente
        Map<String, Object> map = new HashMap<>();
        map.put("name", client.getName());
        map.put("email", client.getEmail());

        //ahora aqui ya no le pasamos el objeto client sino el objeto map
        return mDataBase.child(client.getId()).setValue(map);
    }

}
