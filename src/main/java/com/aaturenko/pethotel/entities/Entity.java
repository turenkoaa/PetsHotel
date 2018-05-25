package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.repositories.Repository;
import lombok.Getter;

public class Entity {

    @Getter
    protected long id;

    public void update(){
        Repository repository = null;
        repository.update(this);
    }
}
