package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import lombok.Data;

import java.util.Objects;

@Data
public abstract class Entity {

    protected long id;

    public void update(){
        getMapper().update(this);
    }

    public void delete(){
        getMapper().delete(this);
    }

    public Entity save(){
        return getMapper().save(this);
    }

    public Entity(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract DataMapper getMapper();
}
