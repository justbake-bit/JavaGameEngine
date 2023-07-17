package com.justbake.GameEngine.core.scenes.gameObject.components;

import com.justbake.GameEngine.utils.math.Quaternionf;
import com.justbake.GameEngine.utils.math.Vector3f;

public class TransformComponent extends GameObjectComponent {

    private Vector3f position;
    private Vector3f size;
    private Quaternionf rotation;

    public Vector3f up(){
        return (Vector3f) new Vector3f(0.0f, -1.0f, 0.0f).rotate(rotation);
    }

    public Vector3f front(){
        return (Vector3f) new Vector3f(0.0f, 0.0f, -1.0f).rotate(rotation);
    }


    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getSize() {
        return size;
    }

    public Quaternionf getRotation() {
        return rotation;
    }
}
