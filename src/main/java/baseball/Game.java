package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private static final int DIGITS = 3;
    private Numbers targetNumbers;
    private boolean gameOver;

    public void play() {
        System.out.println("숫자 야구 게임을 시작합니다.");

        do {
            startNewGame();
            while (!gameOver) {
                System.out.println("숫자를 입력해주세요 : ");
                String input = Console.readLine();
                try {
                    int guess = validateInput(input);
                    if (guess != -1) {
                        processGuess(guess);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (askForRestart());
    }

    private void startNewGame() {
        this.targetNumbers = generateTargetNumbers();
        this.gameOver = false;
    }

    private Numbers generateTargetNumbers() {
        Set<Integer> uniqueDigits = new HashSet<>();
        while (uniqueDigits.size() < DIGITS) {
            int randomDigit = Randoms.pickNumberInRange(1, 9);
            uniqueDigits.add(randomDigit);
        }
        int num = 0;
        for (int digit : uniqueDigits) {
            num = num * 10 + digit;
        }
        return new Numbers(num);
    }

    private int validateInput(String input) {
        if (!input.matches("\\d{3}")) {
            throw new IllegalArgumentException("세 자리 숫자를 입력해야 합니다.");
        }
        return Integer.parseInt(input);
    }

    private void processGuess(int guess) {
        Numbers guessedNumbers = new Numbers(guess);
        List<Integer> guessList = guessedNumbers.getNumbers();

        int strikes = countStrikes(guessList);
        int balls = countBalls(guessList);

        if (strikes == DIGITS) {
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            gameOver = true;
        } else if (balls == 0 && strikes == 0) {
            System.out.println("낫싱");
        } else {
            System.out.println(strikes + "스트라이크 " + balls + "볼");
        }
    }

    private int countStrikes(List<Integer> guessList) {
        List<Integer> targetList = targetNumbers.getNumbers();
        int strikes = 0;
        for (int i = 0; i < DIGITS; i++) {
            if (guessList.get(i).equals(targetList.get(i))) {
                strikes++;
            }
        }
        return strikes;
    }

    private int countBalls(List<Integer> guessList) {
        List<Integer> targetList = targetNumbers.getNumbers();
        int balls = 0;
        for (int i = 0; i < DIGITS; i++) {
            if (guessList.contains(targetList.get(i)) && !guessList.get(i).equals(targetList.get(i))) {
                balls++;
            }
        }
        return balls;
    }

    private boolean askForRestart() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요. ");
        String input = Console.readLine();
        while (!input.equals("1") && !input.equals("2")) {
            System.out.println("올바른 값을 입력하세요. 게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요. ");
            input = Console.readLine();
        }
        return input.equals("1");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
