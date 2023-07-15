package com.justbake.GameEngine.utils;

public class Time {

    public static final float timeStarted = System.nanoTime();

    private static float frameDeltaTime = 0;
    private static float frameStartTime = timeStarted;

    public static void endFrame()
    {
        float now = getTime();
        frameDeltaTime = now - frameStartTime;
        frameStartTime = now;
    }

    public static float getTime()
    {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }

    public static float getDeltaTime()
    {
        return frameDeltaTime;
    }
}
