package lambda.part3.exercise;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise3 {

    private static class LazyMapHelper<T, R> {
        private final List<T> source;
        private final Function<T,R> function;

        private LazyMapHelper(List<T> list, Function<T, R> function)
        {
            this.source = list;
            this.function = function;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            // TODO реализация
            return new LazyMapHelper<>(list, Function.identity());
            //throw new UnsupportedOperationException();
        }

        public List<R> force() {
            // TODO реализация
           List<R> list = new ArrayList<>();
           source.forEach(s -> list.add(function.apply(s)));
           return list;
            //throw new UnsupportedOperationException();
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> mapping) {
            return new LazyMapHelper<>(source, function.andThen(mapping));
        }
    }

    @Test
    public void mapEmployeesToLengthOfTheirFullNamesUsingLazyMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> lengths =
        // TODO                 LazyMapHelper.from(employees)
        LazyMapHelper.from(employees)
        // TODO                              .map(Employee -> Person)
        .map(Employee::getPerson)
        // TODO                              .map(Person -> String(full name))
        .map(Person::getFullName)
        // TODO                              .map(String -> Integer(length from string))
        .map(String::length)
        // TODO                              .getMapped();
        .force();
        assertEquals(Arrays.asList(14, 19, 14, 15, 14, 16), lengths);
    }
}
