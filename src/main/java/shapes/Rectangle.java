package shapes;

import util.Constants;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends Shape
{
    private float x, y, width, height;
    private float r, g, b, a;


    public Rectangle(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.a = 255;
    }


    @Override
    public void draw()
    {
        glColor4f(r/255, g/255, b/255, a/255);
        glBegin(GL_QUADS);

        glVertex2f(x, y);
        glVertex2f(width, y);
        glVertex2f(width, -height);
        glVertex2f(x, -height);

        glEnd();
    }

    @Override
    public Rectangle setColor(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    @Override
    public Rectangle setColor(int r, int g, int b, int a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }
}
