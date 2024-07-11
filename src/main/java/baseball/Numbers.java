package baseball;

import java.util.Arrays;
import java.util.List;

public class Numbers {

    private final int first;
    private final int second;
    private final int third;

    public Numbers(int num) {
        this.first = num / 100;
        this.second = (num % 100) / 10;
        this.third = num % 10;
    }

    public List<Integer> getNumbers() {
        return Arrays.asList(first, second, third);
    }
}
