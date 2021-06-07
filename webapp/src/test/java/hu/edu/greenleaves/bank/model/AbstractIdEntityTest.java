package hu.edu.greenleaves.bank.model;

import org.junit.Before;
import org.junit.Test;

import hu.edu.greenleaves.bank.model.AbsEntity;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class AbstractIdEntityTest {

    final AbsEntity abstractIdEntity = new AbsEntity();


    @Test
    public void getId() throws NoSuchFieldException, IllegalAccessException {
        final Field field = abstractIdEntity.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(abstractIdEntity, 12);
        final int result = abstractIdEntity.getId();
        assertEquals("field wasn't retrieved properly", result, 12);

    }

    @Test
    public void setId() throws NoSuchFieldException, IllegalAccessException {
        //when
        abstractIdEntity.setId(12);

        //then
        final Field field = abstractIdEntity.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(abstractIdEntity), 12);



    }
}