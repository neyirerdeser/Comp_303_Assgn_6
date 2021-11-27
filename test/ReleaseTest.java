import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReleaseTest {
    WallE robot;
    Action actionToTest;
    ExtendStub extendStub;

    static class ExtendStub {
        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All this must do is extend the arm

            // Reflection to force change the private attribute of arm state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot, "armState", Robot.ArmState.EXTENDED);
        }
    }

    @BeforeEach
    void setUp() {
        //Arrange
        robot = new WallE();
        actionToTest = new Release();
        extendStub = new ExtendStub();
    }

    @Test
    void testDefaultRelease() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(Robot.GripperState.EMPTY, robot.getGripperState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "gripperState");
        assertEquals("gripperState : EMPTY", check1);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "armState");
        assertEquals("armState : RETRACTED", check2);
    }

    @Test
    void testValidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {
        //If the object was already retracted and just had to open to release

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.GripperState.OPEN, robot.getGripperState());
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());

        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "gripperState");
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "armState");
        assertEquals("gripperState : OPEN", check1);
        assertEquals("armState : RETRACTED", check2);


    }

    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {
        //If the arm is not retracted, it retracts it, and then can release

        //First extend
        extendStub.execute(robot);

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.GripperState.OPEN, robot.getGripperState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "gripperState");
        assertEquals("gripperState : OPEN", check1);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "armState");
        assertEquals("armState : RETRACTED", check2);
    }


    @Test
    void testToString() {
        assertEquals("Release", actionToTest.toString());
    }

}
