package com.justbake.GameEngine.core.scenes.gameObject.components.annotations;

import com.justbake.GameEngine.core.scenes.gameObject.components.GameObjectComponent;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireComponent {
    Class<? extends GameObjectComponent>[] value();
}

