package com.justbake.GameEngine.core.scenes.gameObject;

import com.justbake.GameEngine.core.scenes.Scene;
import com.justbake.GameEngine.core.scenes.gameObject.components.GameObjectComponent;
import com.justbake.GameEngine.core.scenes.gameObject.components.TransformComponent;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.RequireComponent;

import java.lang.annotation.Annotation;
import java.util.*;

public class GameObject {

    private final String name;
    private final UUID uuid;
    public boolean isActive = true;
    private final List<GameObject> children;
    private final List<GameObjectComponent> components;

    private Scene scene;

    public GameObject(String name) {
        this.name = name;
        uuid = UUID.randomUUID();
        children = new LinkedList<>();
        components = new ArrayList<>();
    }

    public GameObject addChild(GameObject gameObject) {
        children.add(gameObject);
        return this;
    }

    public GameObject addComponent(GameObjectComponent gameObjectComponent) {
        gameObjectComponent.setGameObject(this);

        List<String> log = new ArrayList<>();
        Boolean hasAllRequiredComponents = true;

        Annotation[] declaredAnnotations = gameObjectComponent.getClass().getDeclaredAnnotations();

        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation instanceof RequireComponent requireComponent) {

                for (Class<? extends GameObjectComponent> component : requireComponent.value()) {
                    if(! hasComponentOfType(component)){
                        hasAllRequiredComponents = false;
                        log.add(component.getSimpleName());
                    }
                }

            }
        }

        if(! hasAllRequiredComponents){
            System.err.println(this.name + " GameObject does not have all required components for component " + gameObjectComponent.getClass().getSimpleName() + "\n" +
                    " Missing components: " + String.join(", ", log));
        }

        components.add(gameObjectComponent);
        return this;
    }

    public GameObject addToScene(Scene scene) {
        scene.addGameObject(this);
        return this;
    }

    public <T extends GameObjectComponent> boolean hasComponentOfType(Class<T> t) {
        return components.stream().anyMatch(gameObjectComponent -> gameObjectComponent.getClass().equals(t));
    }

    public <T extends GameObjectComponent> T getComponent(Class<T> componentClass){

        for (GameObjectComponent component : components) {
            if (component.getClass().equals(componentClass))
                return componentClass.cast(component);
        }

        return null;
    }

    public GameObject setActive(boolean active) {
        isActive = active;
        return this;
    }

    public void init() {
        for (GameObjectComponent goc : components) {
            if(goc.isActive)
                goc.gameObjectInit();
        }

        for (GameObject go : children) {
            if(go.isActive)
                go.init();
        }
    }

    /**
     * will first update all components on this game object then update all child game objects
     */
    public void update()
    {
        for (GameObjectComponent goc : components) {
            if(goc.isActive)
                goc.gameObjectUpdate();
        }

        for (GameObject go : children) {
            if(go.isActive)
                go.update();
        }
    }
    /**
     * will first render all components on this game object then render all child game objects
     */
    public void render()
    {
        for (GameObjectComponent goc : components) {
            if(goc.isActive)
                goc.gameObjectRender();
        }

        for (GameObject go : children) {
            if(go.isActive)
                go.render();
        }
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                ", isActive=" + isActive +
                '}';
    }

    public void dispose() {
        for (GameObjectComponent goc : components) {
                goc.gameObjectDispose();
        }

        for (GameObject go : children) {
                go.dispose();
        }
    }
}
