import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    WallE robot;
    Grab grab;
    Action passedAction;

    @Test
    void testLogger() throws IOException {
        //Arrange
        robot = new WallE();
        grab = new Grab();
        Path pathOfLog = Path.of(robot.toString() + ".txt");
        String strCurrentLine;
        String actualLog = "NA";

        //grabbing object -> This is the action that will be logged
        grab.execute(robot);
        passedAction = grab;

        //Act
        Logger.logToFile(passedAction, robot);
        BufferedReader objReader = new BufferedReader(new FileReader(String.valueOf(pathOfLog)));
        while ((strCurrentLine = objReader.readLine()) != null) {
            actualLog = strCurrentLine;
        }

        //Assert
        assertEquals("Grab action performed, battery level is 100", actualLog);
    }
}
