package com.justbake.GameEngine;

import com.justbake.GameEngine.GLFW.GLFWWindowFactory;
import com.justbake.GameEngine.OpenGL.OGLShaderFactory;
import com.justbake.GameEngine.core.WindowFactory;
import com.justbake.GameEngine.core.rendering.ShaderFactory;
import com.justbake.GameEngine.utils.Time;


/**
 * A singleton class for all out processes to run on
 */
public abstract class Application implements Runnable {

    private static ShaderFactory shaderFactory;
    private static WindowFactory windowFactory;

    public abstract void init();

    /**
     *
     */
    @Override
    public final void run() {
        WindowFactory windowFactory = getWindowFactory();
        ShaderFactory shaderFactory = getShaderFactory();

        windowFactory.init();
        this.init();

        while(windowFactory.hasOpenWindow()) {
            //TODO: lock updates to x times a second
            windowFactory.updateWindows();

            windowFactory.renderWindows();
            Time.endFrame();
        }

        shaderFactory.dispose();
        windowFactory.terminate();
    }

    public static ShaderFactory getShaderFactory() {
        if(shaderFactory == null) shaderFactory = new OGLShaderFactory();
        return shaderFactory;
    }

    public static void setShaderFactory(ShaderFactory shaderFactory) {
        Application.shaderFactory = shaderFactory;
    }

    public static WindowFactory getWindowFactory() {
        if(windowFactory == null) windowFactory = new GLFWWindowFactory();
        return windowFactory;
    }

    public static void setWindowFactory(WindowFactory windowFactory) {
        Application.windowFactory = windowFactory;
    }
}
