package hu.edu.greenleaves.bank.model;

import org.junit.Test;

import hu.edu.greenleaves.bank.model.StatusForTransaction;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


public class TestForTransaction {

    @Test
    public void if_Null() {
        StatusForTransaction value = StatusForTransaction.of(null);
        assertEquals(value, null);
    }

    @Test
    public void if_Approved() {
        StatusForTransaction value = StatusForTransaction.of("APPROVED");
        assertEquals(value, StatusForTransaction.APPROVED);
    }

    @Test
    public void if_Declined() {
        StatusForTransaction value = StatusForTransaction.of("DECLINED");
        assertEquals(value, StatusForTransaction.DECLINED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void if_Error() {
        StatusForTransaction value = StatusForTransaction.of("Error");
    }
}