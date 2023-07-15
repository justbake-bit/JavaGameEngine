package com.justbake.GameEngine.core;

public interface WindowFactory {

    void init();
    boolean hasOpenWindow();

    Window createWindow();

    Window createWindow(String title);
    Window createWindow(String title, int width, int height);

    boolean destroyWindow(Window window);

    void terminate();

    void pollEvents();
}
