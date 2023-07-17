package com.justbake.GameEngine.core.rendering;

import com.justbake.GameEngine.utils.errors.ShaderCompilationError;
import com.justbake.GameEngine.utils.errors.ShaderLinkingError;
import com.justbake.GameEngine.utils.math.Matrix4f;

public interface Shader {
    /**
     * @throws ShaderCompilationError will be thrown if the compilation fails
     */
    void compile() throws ShaderCompilationError;


    /**
     * @throws ShaderLinkingError will be thrown if the link fails
     */
    void link() throws ShaderLinkingError;

    /**
     * @param shaderType the type of shader the source represents
     * @param source the source code of the shader
     */
    void setShaderSource(ShaderType shaderType, String source);

    /**
     * @param shaderType the type of shader we want to get the source from
     * @return the source of the shader as a String
     */
    String getShaderSource(ShaderType shaderType);

    void uploadMatrix4f(String varName, Matrix4f matrix);
}