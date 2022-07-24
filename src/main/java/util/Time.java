package util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time
{
    private static double frameStartTime;
    public static double deltaTime = 0.0;

    public static void frameStart() { frameStartTime = glfwGetTime(); }

    public static void frameEnd()
    {
        double frameEndTime = glfwGetTime();
        deltaTime = frameEndTime - frameStartTime;
        frameStartTime = frameEndTime;
    }

    public static double getDeltaTime() { return Time.deltaTime; }

}
