package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyCollectionHelper<T, R> {
        private List<T> source;
        private Function<T,R> function;

        private LazyCollectionHelper(List<T> list, Function<T,R> function){
            this.source = list;
            this.function = function;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Function.identity());
        }


        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            throw new UnsupportedOperationException();
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> flatMapping) {
            return new LazyCollectionHelper<>(source, function.andThen(flatMapping));
        }

        public List<R> force() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = null;
        // TODO              LazyCollectionHelper.from(employees)
        // TODO                                  .flatMap(Employee -> JobHistoryEntry)
        // TODO                                  .map(JobHistoryEntry -> String(position))
        // TODO                                  .flatMap(String -> Character(letter))
        // TODO                                  .map(Character -> Integer(code letter)
        // TODO                                  .getMapped();
        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
