package hu.edu.greenleaves.bank.model;

import org.junit.Test;

import hu.edu.greenleaves.bank.model.ClientAccount;
import hu.edu.greenleaves.bank.model.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TestForClientAccount {

    final ClientAccount clientAccountInfo = new ClientAccount();

    @Test
    public void getUser() throws NoSuchFieldException, IllegalAccessException {
        final Field field = clientAccountInfo.getClass().getDeclaredField("user");
        field.setAccessible(true);
        field.set(clientAccountInfo, new User(13));
        final User result = clientAccountInfo.getUser();
        final int id = result.getId();
        assertEquals("field wasn't retrieved properly", id, 13);

    }

//    @Test
//    public void setUser() throws NoSuchFieldException, IllegalAccessException {
//        //when
//    	clientAccountInfo.setUser(new User(13));
//
//        //then
//        final Field field = clientAccountInfo.getClass().getDeclaredField("user");
//        field.setAccessible(true);
//        
//        assertEquals("Fields didn't match", field.get(clientAccountInfo), 13);
//    }

    @Test
    public void getAmount() throws NoSuchFieldException, IllegalAccessException {
        final Field field = clientAccountInfo.getClass().getDeclaredField("amount");
        field.setAccessible(true);
        field.set(clientAccountInfo, new BigDecimal(30));
        final BigDecimal result = clientAccountInfo.getAmount();
        assertEquals("field wasn't retrieved properly", result, new BigDecimal(30));
    }

    @Test
    public void setAmount() throws NoSuchFieldException, IllegalAccessException {
        //when
    	clientAccountInfo.setAmount(new BigDecimal(30));

        //then
        final Field field = clientAccountInfo.getClass().getDeclaredField("amount");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(clientAccountInfo), new BigDecimal(30));
    }
}