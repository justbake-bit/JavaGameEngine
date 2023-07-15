package com.justbake.GameEngine.GLFW;

import com.justbake.GameEngine.core.Window;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWWindow implements Window {

    private final long id;

    public GLFWWindow(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    /**
     *
     */
    @Override
    public void setVisible(boolean visible) {
        if(visible)
            glfwShowWindow(id);
        else
            glfwHideWindow(id);
    }

    /**
     * @param resizable
     */
    @Override
    public void setResizable(boolean resizable) {
        glfwSetWindowAttrib(id, GLFW_RESIZABLE, resizable?GLFW_TRUE:GLFW_FALSE);
    }

    /**
     * @param decorated
     */
    @Override
    public void setDecorated(boolean decorated) {
        glfwSetWindowAttrib(id, GLFW_DECORATED, decorated?GLFW_TRUE:GLFW_FALSE);
    }
}
