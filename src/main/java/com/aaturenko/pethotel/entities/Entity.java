package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.repositories.Repository;

public class Entity {

    public void update(){
        Repository repository = null;
        repository.update(this);
    }
}
