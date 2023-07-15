package com.justbake.GameEngine.core.scenes.gameObject.components;

import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.RequireComponent;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@RequireComponent(TransformComponent.class)
public class CameraComponent extends GameObjectComponent{

    private Matrix4f projectionMatrix, viewMatrix;
    private Vector3f position;

    public CameraComponent(){
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

}
