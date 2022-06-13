package ai.zhuanzhi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailStrParse {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,8}\\b", Pattern.CASE_INSENSITIVE);

    public static String emailParse(String emailStr){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
