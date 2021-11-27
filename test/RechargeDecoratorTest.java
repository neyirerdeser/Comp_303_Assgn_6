import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RechargeDecoratorTest {

    WallE robot;
    RechargeDecorator rechargeDecorator;
    Action actionToBeDecorated;
    ComplexActionStub complexActionStub;

    static class ComplexActionStub {

        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All that needs to be done is for the charge to be arbitrarily lower than 95 (i.e. sequencing a few actions).
            // Reflection to force change the private attribute of charge
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot, "charge", 50);
        }
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        robot = new WallE();
        complexActionStub = new ComplexActionStub();
        complexActionStub.executeAction(robot);
        //Arbitrary action that is decorated
        actionToBeDecorated = new Release();
        rechargeDecorator = new RechargeDecorator(actionToBeDecorated);
    }

    @Test
    void testExecuteAction() throws NoSuchFieldException, IllegalAccessException {
        //Act
        rechargeDecorator.execute(robot);

        //Assert
        //Blackbox
        assertTrue(robot.getBatteryCharge() == 100);

        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot, "charge");
        assertTrue("charge : 100".equals(check));
    }

    @Test
    void testToString() {
        assertEquals("Recharge", rechargeDecorator.toString());
    }
}
