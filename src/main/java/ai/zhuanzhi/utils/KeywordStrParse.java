package ai.zhuanzhi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordStrParse {
    public static final Pattern VALID_KEYWORD_REGEX =
            Pattern.compile("\\b([\\s|[A-Z._%+-\\\\s]+|[\\u4e00-\\u9fa5]+][[\\u4e00-\\u9fa5]+|A-Z._%+-\\\\s]+)\\b", Pattern.CASE_INSENSITIVE);

    public static String keywordParse(String keywordStr){
        if (keywordStr.contains(",") || keywordStr.contains("，") || keywordStr.contains("<i>") || keywordStr.contains("|")
                || keywordStr.contains("<sub>")) {
            return null;
        }
        Matcher matcher = VALID_KEYWORD_REGEX.matcher(keywordStr.replace(".", ""));
        if(matcher.find()){
            return matcher.group(0);
        }
        return null;
    }
}
