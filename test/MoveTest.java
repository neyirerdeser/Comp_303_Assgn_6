import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {
    WallE robot;
    Move actionToTest;
    VisitorStub visitorStub;
    ExtendStub extendStub;

    static class ExtendStub {
        public void execute(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All this must do is extend the arm
            // Reflection to force change the private attribute of arm state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.EXTENDED);
        }
    }

    static class VisitorStub extends DistanceVisitor{
        private double aDistance = 0;
        
        public double getDistance(){
            return aDistance;
        }

        @Override
        public void visit(Move moveRobot){
            aDistance +=moveRobot.aDistance;
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest = new Move(100);
        extendStub = new ExtendStub();
    }


    @Test
    void testGetDistance() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(100.0, actionToTest.aDistance);
    }

    @Test
    void testDefaultMove() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }


    @Test
    void testValidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }

    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {
        //If the arm is not retracted, it retracts it, and then can move validly
        //First extend
        extendStub.execute(robot);

        //Act
        actionToTest.execute(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }

    @Test
    void testInvalidExecuteAction() {
        //Act
        try{
            Move actionToTestFail = new Move(-1.5);
            actionToTestFail.execute(robot);
            fail("This should not be reached. Invalid action executed because the input distance is negative");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }


    @Test
    void testToString(){
        assertEquals( "Move", actionToTest.toString());
    }


    @Test
    void testAcceptVisitor() {
        visitorStub = new VisitorStub();
        actionToTest.accept(visitorStub);
        assertEquals(100,visitorStub.getDistance());
    }
}
