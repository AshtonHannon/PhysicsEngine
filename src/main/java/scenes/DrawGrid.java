package scenes;

public class DrawGrid extends Scene
{
    private static Scene instance;

    public static Scene get()
    {
        if (DrawGrid.instance == null)
            DrawGrid.instance = new DrawGrid();
        return DrawGrid.instance;
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update(double dt)
    {

    }
}
