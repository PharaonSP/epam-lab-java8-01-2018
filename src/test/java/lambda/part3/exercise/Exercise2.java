package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions"})
public class Exercise2 {

    private static class MapHelper<T> {

        private final List<T> source;

        private MapHelper(List<T> source) {
            this.source = source;
        }

        public static <T> MapHelper<T> from(List<T> source) {
            return new MapHelper<>(source);
        }

        public List<T> getMapped() {
            return new ArrayList<>(source);
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается с использованием заданной функции.
         * ([T], (T -> R)) -> [R]
         * @param mapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> map(Function<T, R> mapping) {
           List<R> result = new ArrayList<>();

            for (T item : source){
                result.add(mapping.apply(item));
            }

            return new MapHelper<>(result);
            //throw new UnsupportedOperationException();
        }

        /**
         * Создает объект для маппинга, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент преобразовывается в список с использованием заданной функции.
         * ([T], (T -> [R])) -> [R]
         * @param flatMapping Функция преобразования элементов.
         */
        public <R> MapHelper<R> flatMap(Function<T, List<R>> flatMapping) {
            List<R> result = new ArrayList<>();

            for (T item : source){
                result = flatMapping.apply(item);
            }

            return new MapHelper<>(result);
            //throw new UnsupportedOperationException();
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths = MapHelper.from(employees)
        // TODO                 MapHelper.from(employees)
        // TODO                          .map(Employee -> Person)
        .map(Employee::getPerson)
        // TODO                          .map(Person -> String(full name))
        .map(Person::getFullName)
        // TODO                          .map(String -> Integer(length of string))
        .map(String::length)
        // TODO                          .getMapped();
        .getMapped();
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes =
        // TODO               MapHelper.from(employees)
        MapHelper.from(employees)
        // TODO                        .flatMap(Employee -> JobHistoryEntry)
        .flatMap(Employee::getJobHistory)
        // TODO                        .map(JobHistoryEntry -> String(position))
        .map(JobHistoryEntry::getPosition)
        // TODO                        .flatMap(String -> Character(letter))
        .flatMap(s -> {
            List<Character> chars = new ArrayList<>();
            for(Character ch : s.toCharArray()){
                chars.add(ch);
            }
            return chars;
        })
        // TODO                        .map(Character -> Integer(code letter)
        .map(Integer::valueOf)
        // TODO                        .getMapped();
        .getMapped();
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
