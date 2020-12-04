package systems.enji.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Lambdas {

  private String _suffix;

  public Lambdas(String suffix) {
    _suffix = suffix;
  }

  public static void main(String[] args) {

    Lambdas l = new Lambdas("_suffix");

    process(Lambdas::toUpperCase, "hello");
    process(l::withSuffix, "hello");

    Function<Lambdas, String> f = Lambdas::getSuffix;
    System.out.println(f.apply(l));

  }

  public static void process(Function<String, String> f, String input) {
    System.out.println(f.apply(input));
  }

  public static String toUpperCase(String s) {
    return s.toUpperCase();
  }

  public String withSuffix(String s) {
    return s + _suffix;
  }

  public String getSuffix() {
    return _suffix;
  }

}
