public class Frog
{
    private Coord center;
    //size needs to be divisible by 2 at least till we do colliders
    private int size = 40;
    private int moveAmount = 50;
    private Collider collider;
    private Coord upperRightCorner;

    public Frog(Coord center)
    {
        this.center = center;
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
        collider = new Collider(center, size);
    }

    public Coord getCenter() {
        return center;
    }

    public Coord getUpperRightCorner() {
        return upperRightCorner;
    }

    public void addToCenterX(double xInc)
    {
        center.addToX(xInc);
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
        collider = new Collider(center, size);
    }

    public float getSize() {
        return size;
    }

    public Collider getCollider() {
        return collider;
    }

    public void moveFrog(Direction d)
    {
        switch (d)
        {
            case UP -> center.addToY(-moveAmount);
            case DOWN -> center.addToY(moveAmount);
            case LEFT -> center.addToX(-moveAmount);
            case RIGHT -> center.addToX(moveAmount);
        }
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
        collider = new Collider(center, size);
    }
}
