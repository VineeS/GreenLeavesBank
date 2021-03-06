package hu.edu.greenleaves.bank.commons;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static final long serialVersionUID = 1L;

    private Helper() {}

    public static String inputNormalizer(String input) {
        input = Normalizer.normalize(input, Normalizer.Form.NFKC);
        return input.replaceAll("[^\\p{ASCII}]", "");
    }

    public static Boolean xssMatcher(String input) {
        Pattern pattern = Pattern.compile("<script>");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }






}
