import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyTest {

    WallE robot;
    Action actionToTest;
    GrabMoveStub grabMoveStub;
    CompactMoveStub compactMoveStub;


    static class GrabMoveStub{


        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // Stub only needs to simulate grab by holding an item and having its arm retracted

            // Reflection to force change the private attribute of arm state and gripper state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.RETRACTED);
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"gripperState", Robot.GripperState.HOLDING_OBJECT);
        }
    }

    static class CompactMoveStub{

        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All that the compact object stub must do is the gripper should be open and compacted items needs to increment by 1
            // Assume when robot is empty, and grabs an object to compact it. It's compacted items is fixed as 1!
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"gripperState", Robot.GripperState.OPEN);
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"compactedItems",1);
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest = new Empty();
        grabMoveStub = new GrabMoveStub();
        compactMoveStub = new CompactMoveStub();
    }

    @Test
    void testDefaultEmptyCompactor() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(0, robot.getCompactorLevel());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 0", check);
    }

    @Test
    void testValidExecuteAction() throws NoSuchFieldException, IllegalAccessException {
        // Need compactor level > 0 to empty, so we compact one first
        // First grab object with stub
        grabMoveStub.execute(robot);
        // Then compact object with stub
        compactMoveStub.execute(robot);

        //Act
        actionToTest.execute(robot);
        //Blackbox
        assertEquals(0, robot.getCompactorLevel());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 0",check);
    }

    //Edge case when there are in total 0 compacted items
    @Test
    void testInvalidExecuteAction() {
        //Act
        try{
            actionToTest.execute(robot);
            fail("This should not be reached. Invalid action executed because there are 0 compacted items, and program tried to empty");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    @Test
    void testToString(){
        assertEquals( "Empty compactor", actionToTest.toString());
    }

}
