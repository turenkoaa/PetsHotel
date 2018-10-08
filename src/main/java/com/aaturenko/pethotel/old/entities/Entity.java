package com.aaturenko.pethotel.old.entities;

import com.aaturenko.pethotel.old.dao.DataMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
public abstract class Entity implements Serializable {

    protected long id;

    public void delete(){
        dataMapper().delete(this);
    }

    public Entity save(){
        return dataMapper().save(this);
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

    public abstract DataMapper dataMapper();
}
