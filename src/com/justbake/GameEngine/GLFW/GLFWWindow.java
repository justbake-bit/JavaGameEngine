package com.justbake.GameEngine.GLFW;

import com.justbake.GameEngine.core.Window;
import com.justbake.GameEngine.core.scenes.Scene;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class GLFWWindow implements Window {
    private final long id;
    private final List<Scene> sceneList;

    public GLFWWindow(long id, @Nullable Collection<Scene> scenes) {
        this.id = id;
        sceneList = scenes == null ? new ArrayList<>(1) : scenes.stream().toList();
    }

    /**
     *
     */
    @Override
    public void setVisible(boolean visible) {
        if (visible)
            glfwShowWindow(id);
        else
            glfwHideWindow(id);
    }

    /**
     * @param resizable
     */
    @Override
    public void setResizable(boolean resizable) {
        glfwSetWindowAttrib(id, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override
    public void setSize(int width, int height) {
        glfwSetWindowSize(id, width, height);
    }

    /**
     * @param decorated
     */
    @Override
    public void setDecorated(boolean decorated) {
        glfwSetWindowAttrib(id, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override
    public void center() {
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(id, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    id,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
    }

    @Override
    public void loadScene(Scene scene) {
        scene.setWindow(this);
        sceneList.add(scene);
        System.out.println("Loading scene " + scene);
        scene.init();
    }

    @Override
    public void removeScene(Scene scene) {
        sceneList.remove(scene);
        scene.dispose();
    }

    @Override
    public Collection<Scene> getCurrentScenes() {
        return sceneList;
    }

    public long id() {
        return id;
    }

    @Override
    public int getWidth() {
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(id, pWidth, pHeight);

            return  pWidth.get();
        }
    }

    @Override
    public int getHeight() {
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(id, pWidth, pHeight);

            return  pHeight.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GLFWWindow) obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GLFWWindow[" +
                "id=" + id + ']';
    }

}
