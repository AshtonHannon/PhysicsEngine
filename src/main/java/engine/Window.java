package engine;

import listeners.KeyListener;
import listeners.MouseListener;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import scenes.TestQuad;
import util.Constants;
import util.Time;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private final int width, height;
    private final String title;
    private long glfwWindow;

    public float r, g, b, a;

    private static Window window = null;

    private static final SceneManager sceneManager = SceneManager.get();


    private Window()
    {
        this.width = Constants.WINDOW_WIDTH;
        this.height = Constants.WINDOW_HEIGHT;
        this.title = Constants.WINDOW_TITLE;
    }


    public static Window get()
    {
        if (Window.window == null)
            Window.window = new Window();
        return Window.window;
    }


    public void setColor(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        glClearColor(this.r, this.g, this.b, this.a);
    }


    public Integer[] getWindowDimensions()
    {
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(glfwWindow, width, height);
        return new Integer[]{width[0], height[0]};
    }


    public int getWindowWidth() { return Window.get().getWindowDimensions()[0]; }


    public int getWindowHeight() { return Window.get().getWindowDimensions()[1]; }


    public void run()
    {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();
        close();

        // Free the memory used for the window.
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback.
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }


    public void init()
    {
        System.out.println("Starting!...");
        // Setup error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW.
        if (!glfwInit()) { throw new IllegalStateException("Unable to initialize GLFW!"); }

        // Configure GLFW.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Makes window not yet VISIBLE (Until after created)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Makes window RESIZABLE
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // Makes window MAXIMIZED

        // Create the window.
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) { throw new IllegalStateException("Failed to create glfwWindow!"); }

        // Set mouse listener callback functions.
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        // Set key listener callback functions.
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current.
        glfwMakeContextCurrent(glfwWindow);

        // Swap intervals
        glfwSwapInterval(1);

        // Make the window visible.
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set initial scene.
        sceneManager.setCurrentScene(TestQuad.get());
    }


    public void loop()
    {
        //Window.get().setWindowOrtho();
        Time.frameStart();

        while (!glfwWindowShouldClose(glfwWindow))
        {
            // Poll events
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            if (Time.getDeltaTime() >= 0)
                sceneManager.getCurrentScene().update(Time.getDeltaTime());

            // Swap buffers (V-Sync)
            glfwSwapBuffers(glfwWindow);

            Time.frameEnd();
        }
    }


    public void close()
    {
        System.out.println("Stopping!...");
    }
}
