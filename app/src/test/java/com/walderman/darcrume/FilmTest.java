package com.walderman.darcrume;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilmTest {
    Film input = new Film(777,"Tester Brand", "Testable Film Object", "BW", 36, 800);

    @Test
    public void testGetId() {
        int output;
        int expected = 777;

        output = input.getFilm_id();

        assertEquals(expected, output);
    }


    @Test
    public void testGetBrand() {
        String output;
        String expected = "Tester Brand";

        output = input.getBrand();

        assertEquals(expected, output);
    }

    @Test
    public void testGetName() {
        String output;
        String expected = "Testable Film Object";

        output = input.getName();

        assertEquals(expected, output);
    }

    @Test
    public void testGetType() {
        String output;
        String expected = "BW";

        output = input.getType();

        assertEquals(expected, output);
    }

    @Test
    public void testGetExp() {
        int output;
        int expected = 36;

        output = input.getExp();

        assertEquals(expected, output);
    }

    @Test
    public void testGetISO() {
        int output;
        int expected = 800;

        output = input.getIso();

        assertEquals(expected, output);
    }

}