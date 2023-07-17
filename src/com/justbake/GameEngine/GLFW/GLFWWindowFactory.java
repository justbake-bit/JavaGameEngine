package com.justbake.GameEngine.GLFW;

import com.justbake.GameEngine.core.Window;
import com.justbake.GameEngine.core.WindowFactory;
import com.justbake.GameEngine.core.scenes.Scene;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLFWWindowFactory implements WindowFactory {

    private static final List<GLFWWindow> windows = new ArrayList<>(1);

    /**
     * initializes glfw and sets error callback and set default window hints
     */
    @Override
    public void init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
    }

    /**
     *
     */
    @Override
    public void updateWindows() {
        glfwPollEvents();

        for (GLFWWindow window : windows) {
            for(Scene scene : window.getCurrentScenes()){
                scene.update();
            }
        }
    }

    @Override
    public void renderWindows() {
        for (GLFWWindow window : windows) {
            glfwSwapBuffers(window.id());
            for(Scene scene : window.getCurrentScenes()){
                scene.render();
            }
        }
    }

    /**
     * loops over all current GLFWWindows in the windows list.
     * if all windows should close then return false.
     * if a window should close, destroy the window.
     * @return true if all windows should close. false otherwise
     */
    @Override
    public boolean hasOpenWindow() {
        boolean allWindowsShouldClose = true;

        Iterator<GLFWWindow> iterator = windows.iterator();

        while(iterator.hasNext()){
            GLFWWindow next = iterator.next();
            long id = next.id();
            if(! glfwWindowShouldClose(id)) {
                allWindowsShouldClose = false;
            }else{
                glfwDestroyWindow(id);
                iterator.remove();
            }
        }

        return ! allWindowsShouldClose;
    }

    /**
     * @return
     */
    @Override
    public Window createWindow() {
        // Create the window
        return createWindow("GLFW Window");
    }

    /**
     * @param title
     * @return
     */
    @Override
    public Window createWindow(String title) {
        // Create the window
        return createWindow(title, 800, 600);
    }

    /**
     * @param title
     * @param width
     * @param height
     * @return
     */
    @Override
    public Window createWindow(String title, int width, int height) {
        glfwDefaultWindowHints();
        // Create the window
        long windowId = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( windowId == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWWindow glfwWindow = new GLFWWindow(windowId, null);

        glfwSetWindowFocusCallback(windowId, this::windowFocusCallback);

        if(glfwGetWindowAttrib(windowId, GLFW_FOCUSED) == GLFW_TRUE){
            setWindowInput(windowId);
        }

        windows.add(glfwWindow);

        return glfwWindow;
    }

    public boolean destroyWindow(Window window)
    {
        if(window instanceof GLFWWindow glfwWindow && windows.contains(glfwWindow)) {
            windows.remove(glfwWindow);
            glfwSetWindowFocusCallback(glfwWindow.id(), null);
            glfwDestroyWindow(glfwWindow.id());
            return true;
        }
        return false;
    }

    /**
     *
     */
    @Override
    public void terminate() {
        for(int i = 0; i < windows.size(); i++){
            if (destroyWindow(windows.get(i)))
                i--;
        }

        glfwTerminate();
    }

    private void windowFocusCallback(long windowId, boolean focused) {
        if(focused){
            setWindowInput(windowId);
        }
    }

    private void setWindowInput(long windowId){
        for (GLFWWindow window : windows) {
            long windowRemoveId = window.id();

            glfwSetKeyCallback(windowRemoveId, null);
            glfwSetCursorPosCallback(windowRemoveId, null);
            glfwSetMouseButtonCallback(windowRemoveId, null);
            glfwSetScrollCallback(windowRemoveId, null);
        }

        glfwSetKeyCallback(windowId, GLFWInputHandler::keyCallback);
        glfwSetCursorPosCallback(windowId, GLFWInputHandler::cursorPositionCallback);
        glfwSetMouseButtonCallback(windowId, GLFWInputHandler::mouseButtonCallback);
        glfwSetScrollCallback(windowId, GLFWInputHandler::scrollCallback);
    }
}
