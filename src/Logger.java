import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Logger {
    public static void logToFile(Action pAction, Robot pRobot) {
        Path pathOfLog = Path.of(pRobot.toString()+".txt");
        Charset charSetOfLog = StandardCharsets.US_ASCII;
        String stringToWrite = pAction.toString()+" action performed, battery level is "+pRobot.getBatteryCharge();
        try {
            BufferedWriter bwOfLog = Files.newBufferedWriter(pathOfLog, charSetOfLog, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bwOfLog.append(stringToWrite, 0, stringToWrite.length());
            bwOfLog.newLine();
            bwOfLog.close();
        } catch(IOException e){
            System.out.println("Failed to log action");
        }
    }

    public static void printToConsole(Action pAction, Robot pRobot) {
        System.out.println(pAction.toString()+" action performed, battery level is "+pRobot.getBatteryCharge());
    }
}
