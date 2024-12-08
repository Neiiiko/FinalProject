import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;

public class Controller
{
    @FXML
    public Canvas drawArea;
    public HBox StartButtonBox;

    private Frog frog = new Frog(new Coord(350,675));
    private CarLane car1 = new CarLane(100, 7, 625, 2, 1);
    private CarLane car2 = new CarLane(100, 2, 575, 2, 1);
    private LogLane log1 = new LogLane(100, 5, 325, 2, 2);

    private Collider pondCollider = new Collider(new Coord(350, 225), 350, 225);
    //private Car car = new Car(new Coord(20, 20), 30);

    private Collider[] winColliders = new Collider[]{new Collider(new Coord(350, 25), 700, 50)};

    private long lastUpdate = 0;

    private boolean isFirstFrame = true;
    private boolean onLog = true;


    private void drawFrog()
    {
        //GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        //drawAreaGC.setFill(Color.GREEN);
        //drawAreaGC.fillRect(frog.getUpperRightCorner().getX(), frog.getUpperRightCorner().getY(), frog.getSize(), frog.getSize());
        GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        // Get frog's position
        double x = frog.getUpperRightCorner().getX();
        double y = frog.getUpperRightCorner().getY();
        double size = frog.getSize();
        // Draw body (ellipse)
        drawAreaGC.setFill(Color.GREEN);
        drawAreaGC.fillOval(x, y, size, size * 0.8);

        // Draw legs (using polygons)
        drawAreaGC.setFill(Color.GREEN);
        drawAreaGC.fillPolygon(
                new double[]{x - size * 0.2, x, x + size * 0.2}, // Left leg
                new double[]{y + size * 0.8, y + size * 0.4, y + size * 0.8},
                3
        );
        drawAreaGC.fillPolygon(
                new double[]{x + size * 0.8, x + size, x + size * 1.2}, // Right leg
                new double[]{y + size * 0.8, y + size * 0.4, y + size * 0.8},
                3
        );
        // Draw mouth (arc)
        drawAreaGC.setStroke(Color.BLACK);
        drawAreaGC.setLineWidth(2);
        drawAreaGC.strokeArc(x + size * 0.2, y + size * 0.4, size * 0.6, size * 0.3, 180, 180, ArcType.OPEN);
    }

    private void drawCar(Car car) {    GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        double x = car.getUpperRightCorner().getX();
        double y = car.getUpperRightCorner().getY();
        double width = car.getSize() * 2;
        double height = car.getSize();
        drawAreaGC.setFill(Color.RED);
        drawAreaGC.fillRoundRect(x, y, width, height, 20, 20);
        drawAreaGC.setFill(Color.LIGHTBLUE);    drawAreaGC.fillRect(x + width * 0.1, y + height * 0.2, width * 0.3, height * 0.6);
        drawAreaGC.fillRect(x + width * 0.6, y + height * 0.2, width * 0.3, height * 0.6);
        drawAreaGC.setFill(Color.BLACK);
        drawAreaGC.fillOval(x + width * 0.1, y - height * 0.2, width * 0.2, height * 0.4);
        drawAreaGC.fillOval(x + width * 0.7, y - height * 0.2, width * 0.2, height * 0.4);
        drawAreaGC.fillOval(x + width * 0.1, y + height * 0.8, width * 0.2, height * 0.4);
        drawAreaGC.fillOval(x + width * 0.7, y + height * 0.8, width * 0.2, height * 0.4);}


    private void drawCars()
    {
        for(Car c: car1.getVisibleCars())
        {
            drawCar(c);
        }
        for(Car c: car2.getVisibleCars())
        {
            drawCar(c);
        }
    }


    private void drawLogs()
    {
        for(Log l: log1.getVisibleLogs())
        {
            drawLog(l);
        }
    }

    private void drawLog(Log log) {
        GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        double x = log.getUpperRightCorner().getX();
        double y = log.getUpperRightCorner().getY();
        double width = log.getSize() * 3;
        double height = log.getSize() * 0.5;
        drawAreaGC.setFill(Color.BROWN);
        drawAreaGC.fillRoundRect(x, y, width, height, 20, 20);
        drawAreaGC.setStroke(Color.web("80471C"));
        drawAreaGC.setLineWidth(2);
        drawAreaGC.strokeLine(x + width * 0.1, y + height * 0.2, x + width * 0.9, y + height * 0.2);
        drawAreaGC.strokeLine(x + width * 0.1, y + height * 0.5, x + width * 0.8, y + height * 0.5);
        drawAreaGC.strokeLine(x + width * 0.2, y + height * 0.8, x + width * 0.9, y + height * 0.8);
        drawAreaGC.setFill(Color.BROWN);
        drawAreaGC.fillOval(x - height * .2, y, height, height);
        drawAreaGC.fillOval(x + width - height * 1, y, height, height);
        drawAreaGC.setFill(Color.web("E6C5AD"));
        drawAreaGC.fillOval(x - height * 0.1, y + height * 0.1, height * 0.8, height * 0.8);
        drawAreaGC.fillOval(x + width - height * 1, y + height * 0.1, height * 0.8, height * 0.8);
    }

    private void drawBackground()
    {
        GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        drawAreaGC.setFill(Color.BLACK);
        drawAreaGC.fillRect(0, 400, 700, 250);
        drawAreaGC.setFill(Color.BLUE);
        drawAreaGC.fillRect(0, 100, 700, 250);
    }

    private void checkCarCollisions()
    {
        for(Car c: car1.getVisibleCars())
        {
            if(c.isCollidingWith(frog))
            {
                stopGame();
            }
        }
    }

    private void checkLogCollisions()
    {
        for(Log l: log1.getVisibleLogs())
        {
            if(l.isCollidingWith(frog))
            {
                onLog = true;
                //System.out.println("here");
                frog.addToCenterX(l.getLastAmountMoved());
            }
        }
    }

    private void checkPondCollision()
    {
        if(pondCollider.isCollidingWith(frog) && !onLog)
        {
            stopGame();
        }
    }



    private void checkWinCollsions()
    {
        for(Collider c: winColliders)
        {
            if(c.isCollidingWith(frog))
            {
                stopGame();
            }
        }
    }

    public void changeDirection(KeyEvent event)
    {

        if(event.getCode() == KeyCode.UP)
        {
            frog.moveFrog(Direction.UP);
        }
        if(event.getCode() == KeyCode.DOWN)
        {
            frog.moveFrog(Direction.DOWN);
        }
        if(event.getCode() == KeyCode.RIGHT)
        {
            frog.moveFrog(Direction.RIGHT);
        }
        if(event.getCode() == KeyCode.LEFT)
        {
            frog.moveFrog(Direction.LEFT);
        }

    }

    public void startGame()
    {
        StartButtonBox.setVisible(false);
        drawArea.requestFocus();
        clockTimer.start();
    }

    public void stopGame()
    {
        System.out.println("You Died");
        frog = new Frog(new Coord(350,675));
        StartButtonBox.setVisible(true);
        clockTimer.stop();
    }

    private void drawFrame(double dTime, long elapsedNanoTime)
    {
        clear();
        drawBackground();
        car1.updateLane(dTime, elapsedNanoTime, (int)drawArea.getWidth());
        car2.updateLane(dTime, elapsedNanoTime, (int)drawArea.getWidth());
        System.out.println(car2.getVisibleCars().get(0).getCenter());
        drawCars();
        log1.updateLogLane(dTime, elapsedNanoTime, (int)drawArea.getWidth());
        drawLogs();
        checkLogCollisions();
        drawFrog();
        checkCarCollisions();
        checkPondCollision();
        onLog = false;
    }

    private void clear()
    {
        GraphicsContext drawAreaGC = drawArea.getGraphicsContext2D();
        drawAreaGC.setFill(Color.WHITE);
        drawAreaGC.fillRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
    }

    public AnimationTimer clockTimer = new AnimationTimer()
    {
        @Override
        public void handle(long now)
        {
            if(isFirstFrame)
            {
                lastUpdate = now;
                isFirstFrame = false;
            }
            else
            {
                long elapseNanoTime = now - lastUpdate;
                double elapsedSeconds = elapseNanoTime / 1000000000.0;
                drawFrame(elapsedSeconds, elapseNanoTime);
                lastUpdate = now;
            }

        }
    };
}
