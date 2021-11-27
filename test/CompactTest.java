import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

public class CompactTest {

    WallE robot;
    Action actionToTest;
    GrabStub grabStub;
    VisitorStub visitorStub;

    static class GrabStub{


        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // hold item and retract:
            // Reflection to force change the private attribute of arm state and gripper state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.RETRACTED);
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"gripperState", Robot.GripperState.HOLDING_OBJECT);
        }
    }

    static class VisitorStub extends CompactorVisitor{
        private int aCompacted = 0;

        public int getCompacted(){
            return aCompacted;
        }

        @Override
        public void visit(Compact Compact){
            aCompacted++;
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest = new Compact();
        grabStub = new GrabStub();
    }

    @Test
    void testDefaultCompact() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(0, robot.getCompactorLevel());

        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 0",check1);

        //Blackbox
        assertEquals(Robot.GripperState.EMPTY, robot.getGripperState());

        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"gripperState");
        assertEquals("gripperState : EMPTY",check2);
    }

    @Test
    void testValidExecuteAction() throws NoSuchFieldException, IllegalAccessException {

        //Grab's execute() is tested under different class. Hence, using a stub!
        grabStub.execute(robot);

        //Act
        actionToTest.execute(robot);
        //Blackbox
        assertEquals(1, robot.getCompactorLevel());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"compactedItems");
        assertEquals("compactedItems : 1",check);
    }

    //Edge case when there are in total 10 compacted items
    @Test
    void testInvalidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i< 10; i++){
            grabStub.execute(robot);
            actionToTest.execute(robot);
        }

        //Act
        try{
            grabStub.execute(robot);
            actionToTest.execute(robot);
            fail("This should not be reached. Invalid action executed because there are 10 compacted items, and program tried to compact");
        } catch (Error | NoSuchFieldException | IllegalAccessException e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    //Edge case when the gripper is not holding any object, then it cant compact anything
    @Test
    void testInvalidExecuteAction2() {
        //Act
        try{
            actionToTest.execute(robot);
            fail("This should not be reached. Invalid action executed because nothing is held, and program tried to compact");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }

    @Test
    void testToString(){
        assertEquals( "Compact", actionToTest.toString());
    }


    @Test
    void testAcceptVisitor() {
        visitorStub = new VisitorStub();
        actionToTest.accept(visitorStub);
        assertEquals(1,visitorStub.getCompacted());
    }
}

