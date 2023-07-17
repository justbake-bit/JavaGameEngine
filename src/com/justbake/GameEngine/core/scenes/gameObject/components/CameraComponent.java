package com.justbake.GameEngine.core.scenes.gameObject.components;

import com.justbake.GameEngine.Application;
import com.justbake.GameEngine.core.Window;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.RequireComponent;
import com.justbake.GameEngine.core.scenes.gameObject.components.annotations.init;
import com.justbake.GameEngine.utils.math.Matrix4f;
import com.justbake.GameEngine.utils.math.Vector3f;

@RequireComponent(TransformComponent.class)
public class CameraComponent extends GameObjectComponent {

    public enum ProjectionMode{
        ORTHOGRAPHIC,
        PERSPECTIVE
    }

    private TransformComponent transformComponent;

    private final Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    private final ProjectionMode projectionMode;
    private final float zNear;
    private final float zFar;
    private final float fov;

    public CameraComponent() {
        this(ProjectionMode.ORTHOGRAPHIC,0.01f, 1000f, 90f);
    }

    public CameraComponent(ProjectionMode projectionMode, float zNear, float zFar, float fov) {
        this.projectionMode = projectionMode;
        this.zNear = zNear;
        this.zFar = zFar;
        this.fov = fov;

        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    @init
    void init()
    {
        transformComponent = this.getGameObject().getComponent(TransformComponent.class);
    }

    public final Matrix4f getPerspectiveProjectionMatrix()
    {
        return switch (projectionMode){
            case ORTHOGRAPHIC -> getOrthographicProjection();
            case PERSPECTIVE -> getPerspectiveProjection();
        };
    }

    private final Matrix4f getOrthographicProjection() {
        Window window = this.getGameObject().getScene().getWindow();
        int width = window.getWidth();
        int height = window.getHeight();
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, width, 0.0f, height, zNear, zFar);
        return projectionMatrix;
    }

    public final Matrix4f getPerspectiveProjection() {
        Window window = this.getGameObject().getScene().getWindow();
        int width = window.getWidth();
        int height = window.getHeight();
        float aspectRatio = (float) width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public final Matrix4f getViewMatrix() {
        Vector3f front = transformComponent.front();
        Vector3f up = transformComponent.up();
        viewMatrix.identity();
        viewMatrix = (Matrix4f) viewMatrix.lookAt(transformComponent.getPosition(),
                front.add(transformComponent.getPosition()),
                up);
        return viewMatrix;
    }

}
