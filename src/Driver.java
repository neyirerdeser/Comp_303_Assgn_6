
public class Driver {
    public static void main(String[]args) {

        WallE robot1 = new WallE();
        WallE robot2 = new WallE();

        Move move = new Move(30);
        Turn turn = new Turn(Direction.LEFT);

        Grab grab = new Grab();
        Compact compact = new Compact();

        ComplexAction move_backwards_30 = new ComplexAction();
        move_backwards_30.addActions(turn,turn,move);
        ComplexAction grab_n_compact_10 = new ComplexAction();

        for(int i=0; i<10; i++) {
            grab_n_compact_10.addActions(grab,compact);
        }   grab_n_compact_10.addActions(new Empty());
        for(int i=0; i<10; i++) {
            grab_n_compact_10.addActions(grab,compact);
        }   grab_n_compact_10.addActions(new Empty());
        for(int i=0; i<10; i++) {
            grab_n_compact_10.addActions(grab,compact);
        }   grab_n_compact_10.addActions(new Empty());


        Program program_with_recharge_action = new Program(new RechargeDecorator(grab_n_compact_10));

        Program move_turn_compact = new Program(move_backwards_30,turn,turn,turn,turn,grab_n_compact_10);

        System.out.println(move_turn_compact.countCompacting());
        System.out.println(move_turn_compact.calculateDistance());
        program_with_recharge_action.execute(robot1);


    }

//    public static void clearLog(Robot pRobot) {
//        Path pathOfLog = Path.of(pRobot.toString()+".txt");
//        Charset charSetOfLog = StandardCharsets.US_ASCII;
//        String stringToWrite = "";
//        try {
//            BufferedWriter bwOfLog = Files.newBufferedWriter(pathOfLog, charSetOfLog);
//            bwOfLog.append(stringToWrite, 0, 0);
//            bwOfLog.newLine();
//            bwOfLog.close();
//        } catch(IOException e){}
//    }
}
