import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnTest {
    WallE robot;
    Action actionToTest1;
    Action actionToTest2;
    ExtendStub extendStub;

    static class ExtendStub {
        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All this must do is extend the arm
            // Reflection to force change the private attribute of arm state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.EXTENDED);
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest1 = new Turn(Direction.LEFT);
        actionToTest2 = new Turn(Direction.RIGHT);
        extendStub = new ExtendStub();
    }

    @Test
    void testDefaultMoveRobot() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }


    @Test
    void testValidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {
        //Assuming arm is retracted, then it can turn
        //Act
        actionToTest1.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check1);

        //Act
        actionToTest2.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {
        //If the arm is not retracted, it retracts it, and then can move validly

        //First extend
        extendStub.execute(robot);

        //Act
        actionToTest1.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check1);

        //Act
        actionToTest2.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    @Test
    void testToString(){
        assertEquals( "Turn", actionToTest1.toString());
        assertEquals( "Turn", actionToTest2.toString());
    }

}
