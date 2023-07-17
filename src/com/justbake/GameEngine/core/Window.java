package com.justbake.GameEngine.core;

import com.justbake.GameEngine.core.scenes.Scene;

import java.util.Collection;

public interface Window {

    void setResizable(boolean resizable);
    void setSize(int width, int height);

    int getWidth();
    int getHeight();

    void setVisible(boolean visible);
    void setDecorated(boolean decorated);
    void center();

    void loadScene(Scene scene);
    void removeScene(Scene scene);

    Collection<Scene> getCurrentScenes();
}
