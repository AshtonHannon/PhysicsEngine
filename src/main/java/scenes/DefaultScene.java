package scenes;

import engine.Window;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DefaultScene extends Scene
{
    private static DefaultScene instance = null;
    private static String sceneTitle;
    private int vertexID, fragmentID, shaderProgram;
    private int vaoID, vboID, eboID;

    private final String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private final String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private float[] vertexArray =
            {
                     // position            // color
                     0.5f, -0.5f,  0.0f,    1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
                    -0.5f,  0.5f,  0.0f,    0.0f, 1.0f, 0.0f, 1.0f, // Top left
                     0.5f,  0.5f,  0.0f,    0.0f, 0.0f, 1.0f, 1.0f, // Top right
                    -0.5f, -0.5f,  0.0f,    1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
            };


    // IMPORTANT: Counter-Clockwise order
    private int[] elementArray =
            {
                    2, 1, 0, // Top right triangle
                    0, 1, 3 // Bottom left triangle
            };


    private DefaultScene()
    {
        DefaultScene.sceneTitle = "TestScene1";
    }


    public static Scene get()
    {
        if (DefaultScene.instance == null)
            DefaultScene.instance = new DefaultScene();
        return DefaultScene.instance;
    }


    // Runs once on initialization.
    @Override
    public void init()
    {
        System.out.println("[" + this + "] Initializing!...");
        Window.get().setColor(1.0f, 1.0f, 1.0f, 1.0f);

        // ==================================================
        // Compile and link shaders
        // ==================================================

        /* ####################### */
        /* #### VERTEX SHADER #### */
        /* ####################### */
        // Load and compile vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass the shader src code to GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE)
        {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("[DefaultScene] ERROR: 'defaultShader.glsl' \n\tVertex shader compilation failed!");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }


        /* ######################### */
        /* #### FRAGMENT SHADER #### */
        /* ######################### */
        // Load and compile vertex shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass the shader src code to GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE)
        {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("[DefaultScene] ERROR: 'defaultShader.glsl' \n\tFragment shader compilation failed!");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }


        /* ###################### */
        /* #### LINK SHADERS #### */
        /* ###################### */
        // Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE)
        {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("[DefaultScene] ERROR: 'defaultShader.glsl' \n\tLinking of shaders failed!");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }


        // =========================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ==========================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO & upload vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create indices & upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeInBytes = 4;
        int vertexSizeInBytes = (positionsSize + colorSize) * floatSizeInBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeInBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeInBytes, (positionsSize * floatSizeInBytes));
        glEnableVertexAttribArray(1);
    }


    // Runs every frame.
    @Override
    public void update(double dt)
    {
        // Bind shader program
        glUseProgram(shaderProgram);

        // Bind the VAO
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw elements
        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
    }


    @Override
    public String toString() { return DefaultScene.sceneTitle; }
}
