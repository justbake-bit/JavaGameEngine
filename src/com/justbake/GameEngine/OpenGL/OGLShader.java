package com.justbake.GameEngine.OpenGL;

import com.justbake.GameEngine.core.rendering.Shader;
import com.justbake.GameEngine.core.rendering.ShaderType;
import com.justbake.GameEngine.utils.errors.ShaderCompilationError;
import com.justbake.GameEngine.utils.errors.ShaderLinkingError;
import com.justbake.GameEngine.utils.math.Matrix4f;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.Map;

public class OGLShader implements Shader {
    int shaderProgramId;
    final Map<ShaderType, String> shaderSources = new HashMap<>();
    @Override
    public void compile() throws ShaderCompilationError {

    }

    @Override
    public void link() throws ShaderLinkingError {

    }

    @Override
    public void setShaderSource(ShaderType shaderType, String source) {
        shaderSources.put(shaderType, source);
    }
    @Override
    public String getShaderSource(ShaderType shaderType) {
        return shaderSources.getOrDefault(shaderType, null);
    }

    @Override
    public void uploadMatrix4f(String varName, Matrix4f matrix) {
        int varLocation = GL30.glGetUniformLocation(shaderProgramId, varName);
    }
}
