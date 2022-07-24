package scenes;

import shapes.Rectangle;

import static org.lwjgl.opengl.GL11.glClearColor;

public class TestQuad extends Scene
{
    private static TestQuad instance;
    private static String sceneTitle;


    private TestQuad() { TestQuad.sceneTitle = "TestQuad"; }


    public static Scene get()
    {
        if (TestQuad.instance == null)
            TestQuad.instance = new TestQuad();
        return TestQuad.instance;
    }


    @Override
    public void init()
    {
        this.setBackgroundColor(255, 255, 255, 255);
    }


    @Override
    public void update(double dt)
    {
        Rectangle rec = new Rectangle(-1.0f, 1.0f, 0.25f, 0.25f);
        rec.setColor(255, 0, 0).draw();
    }

    @Override
    public String toString() { return TestQuad.sceneTitle; }

    public void setBackgroundColor(float r, float g, float b, float a)
    {
        glClearColor(r/255, g/255, b/255, a/255);
    }
}
