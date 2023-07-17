package com.justbake.GameEngine.core.scenes.gameObject.components;

import com.justbake.GameEngine.core.scenes.gameObject.GameObject;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.dispose;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.init;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.render;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class GameObjectComponent {

    public boolean isActive = true;

    private final List<Method> initMethods;
    private final List<Method> updateMethods;
    private final List<Method> renderMethods;
    private final List<Method> disposeMethods;

    private final Object instance;

    private GameObject gameObject;

    public GameObjectComponent() {
        instance = this;
        this.initMethods = new ArrayList<>();
        this.updateMethods = new ArrayList<>();
        this.renderMethods = new ArrayList<>();
        this.disposeMethods = new ArrayList<>();

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(init.class)) {
                if(isValidMethod(method))
                    initMethods.add(method);
            }

            if(method.isAnnotationPresent(update.class)) {
                if(isValidMethod(method))
                    updateMethods.add(method);
            }

            if(method.isAnnotationPresent(render.class)) {
                if(isValidMethod(method))
                    renderMethods.add(method);
            }

            if(method.isAnnotationPresent(dispose.class)) {
                if(isValidMethod(method))
                    disposeMethods.add(method);
            }
        }
    }

    private boolean isValidMethod(Method method){
        if(method.getParameterCount() != 0) {
            System.err.println("Update method must have 0 parameters");
            return false;
        }

        Type returnType = method.getReturnType();
        if(returnType != void.class && returnType != Void.class) {
            System.err.println("Update method return type must be Void not " + returnType);
            return false;
        }

        return true;
    }

    public final void gameObjectInit(){
        invokeMethods(initMethods);
    }

    public final void gameObjectUpdate(){

        invokeMethods(updateMethods);
    }

    public final void gameObjectRender(){

        invokeMethods(renderMethods);
    }

    public final void gameObjectDispose() {
        invokeMethods(disposeMethods);
    }

    public void invokeMethods(List<Method> methodList){
        methodList.forEach(method -> {
            try {
                method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
