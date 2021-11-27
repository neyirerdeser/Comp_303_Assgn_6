import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PrivateFieldReflectionAccessor {

    /**
     * Access private field by using field name.
     */
    public static String getStringFieldValue(Robot wallE, String fieldName) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = wallE.getClass().getDeclaredField(fieldName);
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            return (fieldName + " : "+field.get(wallE));
        }
        return ("No private fields of given name in this class!");
    }

    public static String getStringFieldValue(Action action, String fieldName) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = action.getClass().getDeclaredField(fieldName);
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            return (fieldName + " : "+field.get(action));
        }
        return ("No private fields of given name in this class!");
    }

    /**
     *     Access private field by using field name, and change value to desired value
     */
    public static void changeStringFieldValue(Robot wallE, String fieldName, Robot.ArmState stateToChange) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = wallE.getClass().getDeclaredField(fieldName);
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            field.set(wallE,stateToChange);
        }

        else{
            System.out.println("Field not found");
        }

    }

    /**
     *  Access private field by using field name, and change value to desired value
     */
    public static void changeStringFieldValue(Robot wallE, String fieldName, Robot.GripperState stateToChange) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = wallE.getClass().getDeclaredField(fieldName);
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            field.set(wallE,stateToChange);
        }
        else{
            System.out.println("Field not found");
        }

    }

    /**
     *     Access private field by using field name, and change value to desired value
     */
    public static void changeStringFieldValue(Robot wallE, String fieldName, int valueToChange) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = wallE.getClass().getDeclaredField(fieldName);
        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            field.set(wallE,valueToChange);
        }
        else{
            System.out.println("Field not found");
        }

    }

}
