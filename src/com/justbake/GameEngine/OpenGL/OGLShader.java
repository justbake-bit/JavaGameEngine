package com.justbake.GameEngine.OpenGL;

import com.justbake.GameEngine.core.rendering.Shader;
import com.justbake.GameEngine.core.rendering.ShaderType;
import com.justbake.GameEngine.utils.errors.ShaderCompilationError;
import com.justbake.GameEngine.utils.errors.ShaderLinkingError;

public class OGLShader implements Shader {
    @Override
    public void compile() throws ShaderCompilationError {

    }

    @Override
    public void link() throws ShaderLinkingError {

    }

    @Override
    public void setShaderSource(ShaderType shaderType, String source) {

    }
    @Override
    public String getShaderSource(ShaderType shaderType) {
        return null;
    }
}
