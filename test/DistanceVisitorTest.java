import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DistanceVisitorTest {

    @Test
    void testGetTotalDistance(){
        //Arrange
        double distance;
        DistanceVisitor distanceVisitor = new DistanceVisitor();
        Move moveRobot = new Move(10);
        //Accepting the visitor 3 times!
        moveRobot.accept(distanceVisitor);
        moveRobot.accept(distanceVisitor);
        moveRobot.accept(distanceVisitor);

        //Act
        distance = distanceVisitor.aDistance;

        //Assert
        assertEquals(30,distance);

    }
}
