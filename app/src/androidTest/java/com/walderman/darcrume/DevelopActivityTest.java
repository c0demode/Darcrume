package com.walderman.darcrume;

import android.app.Instrumentation;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class DevelopActivityTest {

    @Rule //this allows an instance of activity class to be created
    public ActivityTestRule<DevelopActivity> developActivityTestRule = new ActivityTestRule<DevelopActivity>(DevelopActivity.class);

    private DevelopActivity developActivity = null;


    @Before
    public void setUp() throws Exception {
        developActivity = developActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = developActivity.findViewById(R.id.editText_DevMin);
        assertNotNull(view);
    }

    @UiThreadTest
    public void testSetTimer(){
        EditText etMin =     developActivity.findViewById(R.id.editText_DevMin);
        EditText etSec =     developActivity.findViewById(R.id.editText_DevSec);
        TextView countdown = developActivity.findViewById(R.id.textView_Countdown);
        etMin.setText("5");
        etSec.setText("33");

        assertEquals(333000,developActivity.setTimer());
        assertEquals("05:33",developActivity.formatMillisecondsToMinutesSecond(developActivity.setTimer()));
    }

    @After
    public void tearDown() throws Exception {
        developActivity = null;
    }
}