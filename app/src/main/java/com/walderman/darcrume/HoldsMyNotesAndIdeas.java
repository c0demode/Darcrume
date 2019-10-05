package com.walderman.darcrume;

public class HoldsMyNotesAndIdeas {

/**
 * simplify develop activity.
 * login activity should check db to make sure a WGU user exists, or else reset db.
 * add interval timer
 * fix start/stop/resume/reset
 * https://youtu.be/ARezg1D9Zd0 floating window
 * https://www.youtube.com/watch?v=0DH2tZjJtm0 hopefully better floating window
 * https://developer.android.com/reference/android/widget/PopupWindow
 * https://www.youtube.com/watch?v=67j1yIFa48s Custom dialog box 2018 - Android Studio UI Tutorial | JayDoesCode
 * https://stackoverflow.com/questions/35834481/how-to-programatically-theme-an-activity-to-be-like-a-dialog
 */

/**
 * Go through and add comments
 * Go through and add try/catch
 * Generally add polish!
 */

/**
 * timer starts
 * 30 seconds go by
 * for a 5 second period, timer flashes and sound plays
 * repeat until timer reaches 10 seconds, then alert user to dump chems
 *
 * startTimer() has a countDownTimer that takes setTime() and a count down interval
 * instead of always passing setTimer to CountDownTimer constructor, there should be logic to determine if the timer is already rolling.
 *
 * private void startTimer() {
 *         countDownTimer = new CountDownTimer(setTimer(), 1000) {
 *         . . . rest of code . . .
 *     }else{
 *         countDownTimer = new CountDownTimer(***REMAINING TIME***, 1000)
 *     }
 * }
 *
 * There should also be a new CountDownTimer that is constructed with (a longer Tick)
 *  1 tick == 1000 == 1 second
 *  should be every thirty seconds so == 30000
 *  onTick(?)
 *      sound alarm
 *      TOAST message
 *
 * onFinish
 *      sound alarm
 *      toast mesage to dump and Rinse
 *
 */

}
