import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static boolean test = false;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) throws InterruptedException {
        System.out.println(ANSI_PURPLE + "Программа работы по методике Pomodoro" + ANSI_RESET);

        do {
            promptOutput();
            String[] cmd = new Scanner(System.in).nextLine().split(" ");
            boolean commandsValid = false;
            String validCommands = "-w|-b|-count|[0-9]{1,}|-h|-exit|-t";
            for (int i = 0; i < cmd.length; i++) {
                Pattern pattern = Pattern.compile(validCommands);
                Matcher matcher = pattern.matcher(cmd[i]);
                if (matcher.find() && (cmd.length != 1) && (cmd.length % 2 == 0)) {
                    commandsValid = true;
                } else if (cmd[0].equals("-exit") || cmd[0].equals("-h")) {
                    commandsValid = true;
                } else commandsValid = false;
            }
            if (!commandsValid) {
                System.out.println("Неверная комманда или синтаксис! Помощь: -h");
                continue;
            }

            int work = 25;
            int breake = 5;
            int sizeBreak = 30;
            int sizeWork = 30;
            int help = 0;
            int count = 1;
            boolean exit = false;

            for (int i = 0; i < cmd.length; i++) {
                switch (cmd[i]) {
                    case "-h" -> help = getHelp();
                    case "-w" -> work = Integer.parseInt(cmd[++i]);
                    case "-b" -> breake = Integer.parseInt(cmd[++i]);
                    case "-count" -> count = Integer.parseInt(cmd[++i]);
                    case "-t" -> test = true;
                    case "-exit" -> {
                        exit = true;
                        System.out.println("Выход");
                    }
                }

            }
            if (exit) break;
            if (help == 0) {
                long startTime = System.currentTimeMillis();
                for (int i = 1; i <= count; i++) {
                    timer(work, breake, sizeBreak, sizeWork);
                }
                long endTime = System.currentTimeMillis();
                System.out.println(ANSI_RED + "Pomodoro таймер истек: " + ANSI_GREEN + (endTime - startTime) / (1000 * 60) + ANSI_RED + " min" + ANSI_RESET);
            }
            test = false;
        } while (true);

    }

    private static void promptOutput() {
        System.out.println(ANSI_CYAN + "Введи команду: \n-w время работы в минутах, " +
                "\n-b время перерыва в минутах " +
                "\n-count количество итераций. " +
                "\nЗначения по умолчанию: -w 25 -b 5 -count 1" +
                "\n-h - помощь, -exit - выход" + ANSI_RESET);
    }

    private static int getHelp() {
        int help;
        System.out.println(
                "\n\nPomodoro - сделай свое время более эффективным");
        System.out.println(
                "	-w <time>: время работы, сколько хочешь работать.");
        System.out.println(
                "	-b <time>: время отдыха, сколько хочешь отдыхать.");
        System.out.println(
                "	-count <count>: количество итераций.");
        System.out.println(
                "	-help: меню помощи.");
        help = 1;
        return help;
    }

    public static void timer(int work, int breake, int sizebreak, int sizework) throws InterruptedException {


        printProgress("Work Progress: ", work, sizework);

        printProgress("Break Progress: ", breake, sizebreak);
    }

    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        length = 60 * time / size;
        rep = 60 * time / length;
        double stretchb = size / (3.0 * time);
        for (int i = 1; i <= rep; i++) {
            double x = i;
            x = 1.0 / 3.0 * x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time * stretchb;
            double percent = (x / w) * 1000;
            x /= stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;

            System.out.print(new StringBuilder().append(ANSI_CYAN).append(process)
                    .append(percent).append("% ").append(ANSI_RESET).append((" ")
                            .repeat(5 - (String.valueOf(percent).length())))
                    .append(ANSI_YELLOW + "[" + ANSI_RESET).append((ANSI_RED + "#" + ANSI_RESET).repeat(i))
                    .append((ANSI_GREEN + "#" + ANSI_RESET).repeat(rep - i)).append(ANSI_YELLOW).append("]")
                    .append(ANSI_RESET).append(ANSI_CYAN).append("( ").append(x).append("min / ").append(time)
                    .append("min )").append(ANSI_RESET).append("\r"));
//            System.out.print(process + percent+"% " + (" ").repeat(22 - (String.valueOf(percent).length()))
//                    +"[" + ("#").repeat(i) + ("-").repeat(rep - i)+"]    ( " + x +"min / " + time +"min )"+  "\r");
            if (!test) {
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();

    }
}

