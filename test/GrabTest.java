import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrabTest {
    WallE robot;
    Action actionToTest;
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
        actionToTest = new Grab();
        extendStub = new ExtendStub();
    }

    @Test
    void testDefaultGrab() throws NoSuchFieldException, IllegalAccessException {
        //Grabbing consists of retracting, opening, extending, and closing
        // Hence need to check the default fields related to these methods -> gripperState and armState

        //Blackbox
        assertEquals(Robot.GripperState.EMPTY, robot.getGripperState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"gripperState");
        assertEquals("gripperState : EMPTY", check1);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    // When the arm is initially retracted
    @Test
    void testValidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.GripperState.HOLDING_OBJECT, robot.getGripperState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"gripperState");
        assertEquals("gripperState : HOLDING_OBJECT", check1);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    // When the arm is initially extended
    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {

        // Pre
        extendStub.execute(robot);

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.GripperState.HOLDING_OBJECT, robot.getGripperState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"gripperState");
        assertEquals("gripperState : HOLDING_OBJECT", check1);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    //Edge case when the robot is already holding an object
    @Test
    void testInvalidExecuteAction() {
        //Act
        try{
            actionToTest.execute(robot);
            actionToTest.execute(robot);
            fail("This should not be reached. Invalid action executed because the robot already holds an item, so it can't grab another");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    @Test
    void testToString(){
        assertEquals( "Grab", actionToTest.toString());
    }
}
