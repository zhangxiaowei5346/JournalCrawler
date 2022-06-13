package ai.zhuanzhi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstitutionStrParse {
    public static final Pattern VALID_INSTITUTION_REGEX =
            Pattern.compile("(?i)\\b([\\u4e00-\\u9fa5]+[[大学]|院|所])[\\s|,]([\\u4e00-\\u9fa5]+[院|系|所|实验室|中心])\\b", Pattern.CASE_INSENSITIVE);

    public static List<String> institutionParse(String institutionStr) {
        Matcher matcher = VALID_INSTITUTION_REGEX.matcher(institutionStr);
        List<String> institution = new ArrayList<String>();
        if (matcher.find()) {
            institution.add(matcher.group(1));
            institution.add(matcher.group(2));
            return institution;
        }
        return null;
    }
}
