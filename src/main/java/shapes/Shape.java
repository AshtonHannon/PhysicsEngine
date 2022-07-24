package shapes;

public abstract class Shape
{
    public abstract void draw();

    public abstract Shape setColor(int r, int g, int b);

    public abstract Shape setColor(int r, int g, int b, int a);
}
