package hu.edu.greenleaves.bank.commons;


import org.junit.Test;

import hu.edu.greenleaves.bank.commons.Helper;

import static org.junit.Assert.*;

public class HelperTest {

    @Test
    public void inputNormalizer() {
        String input = "glöb";
        String output = Helper.inputNormalizer(input);
        assertEquals(output, "glb");
    }

    @Test
    public void xssMatcher() {
        assertTrue(Helper.xssMatcher("<script>test</script>"));
        assertFalse(Helper.xssMatcher("astring"));
    }

}