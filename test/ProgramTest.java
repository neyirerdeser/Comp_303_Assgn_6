import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {
    WallE robot;
    ManyActionStub manyActionStub;
    Program programToTest;

    static class ManyActionStub {

        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All that needs to be done is for the charge to be arbitrarily lower than 5 (i.e. sequencing a few actions).
            // Reflection to force change the private attribute of charge
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot, "charge", 3);
        }
    }

    @BeforeEach
    void setUp() {
        robot = new WallE();
        manyActionStub = new ManyActionStub();
        programToTest = new Program();
    }

    @Test
    void testValidExecuteProgram1() throws NoSuchFieldException, IllegalAccessException, IOException {
        //All the basic actions have been unit tested. Thus, the only thing to test is whether..
        // 1. Battery level recharges if less than 5, 2. Updates battery level after each basic action, 3. Logs action
        manyActionStub.executeAction(robot);

        //Action
        programToTest.addActions(new Release());
        programToTest.execute(robot);

        //Assert
        //Blackbox
        assertTrue(robot.getBatteryCharge() < 100 && robot.getBatteryCharge() >= 95);

        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "charge");
        assertTrue("charge : 99".equals(check) || "charge : 98".equals(check) || "charge : 97".equals(check) || "charge : 96".equals(check) || "charge : 95".equals(check));

        //The above tests checks that it has indeed recharged if less than 5 charge, AND it updated the battery level.

        Path pathOfLog = Path.of(robot.toString() + ".txt");
        String strCurrentLine;
        String actualLog = null;

        BufferedReader objReader = new BufferedReader(new FileReader(String.valueOf(pathOfLog)));
        while ((strCurrentLine = objReader.readLine()) != null) {
            actualLog = strCurrentLine;
        }

        //Assert
        assertTrue(actualLog != null);
        //The above test checks that it has indeed logged the action.
    }

    @Test
    void testValidExecuteProgram2() throws NoSuchFieldException, IllegalAccessException, IOException {
        //Now, let us have a program that executes with not just basic actions, but with basic actions, complex actions, and
        // decorated (basic & complex) actions

        //Arrange
        ComplexAction complexAction = new ComplexAction();
        complexAction.addActions(new Move(10));
        complexAction.addActions(new Turn(Direction.LEFT));
        complexAction.addActions(new Move(10));

        RechargeDecorator decoratedAction1 = new RechargeDecorator(complexAction);
        RechargeDecorator decoratedAction2 = new RechargeDecorator(new Release());


        programToTest.addActions(new Release());
        programToTest.addActions(complexAction);
        programToTest.addActions(decoratedAction1);
        programToTest.addActions(decoratedAction2);

        //Action
        programToTest.execute(robot);

        //Assert -> Still asserting the 3 things a program does (1. recharge if lower than 5, 2. update, 3. log)
        //Blackbox
        assertTrue(robot.getBatteryCharge() < 100 && robot.getBatteryCharge() >= 95);

        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "charge");
        assertTrue("charge : 99".equals(check) || "charge : 98".equals(check) || "charge : 97".equals(check) || "charge : 96".equals(check) || "charge : 95".equals(check));

        //The above tests checks that it has indeed recharged if less than 5 charge, AND it updated the battery level.

        Path pathOfLog = Path.of(robot.toString() + ".txt");
        String strCurrentLine;
        String actualLog = null;

        BufferedReader objReader = new BufferedReader(new FileReader(String.valueOf(pathOfLog)));
        while ((strCurrentLine = objReader.readLine()) != null) {
            actualLog = strCurrentLine;
        }

        //Assert
        assertTrue(actualLog != null);
        //The above test checks that it has indeed logged the action.
    }

    @Test
    void testGetCompactComputation(){
        //Arrange
        CompactorVisitor visitor = new CompactorVisitor();
        programToTest.addActions(new Grab());
        programToTest.addActions(new Move(10));
        programToTest.addActions(new Move(20));
        programToTest.addActions(new Compact());

        //Act
        int result;
        result = programToTest.countCompacting();

        //Assert
        assertEquals(1,result);
    }

    @Test
    void testGetTotalDistance(){
        //Arrange
        CompactorVisitor visitor = new CompactorVisitor();
        programToTest.addActions(new Grab());
        programToTest.addActions(new Move(10));
        programToTest.addActions(new Move(20));
        programToTest.addActions(new Compact());

        //Act
        double result;
        result = programToTest.calculateDistance();

        //Assert
        assertEquals(30,result);
    }

    @Test
    void testAcceptVisitor(){
        //Action
        programToTest.addActions(new Grab());
        programToTest.addActions(new Move(10));
        programToTest.addActions(new Move(20));
        programToTest.addActions(new Compact());
        programToTest.addActions(new Grab());
        programToTest.addActions(new Compact());

        DistanceVisitor visitor1 = new DistanceVisitor();
        CompactorVisitor visitor2 = new CompactorVisitor();

        programToTest.accept(visitor1);
        programToTest.accept(visitor2);

        //Assert
        assertEquals(30,visitor1.aDistance);
        assertEquals(2,visitor2.aCompacted);
    }
}
