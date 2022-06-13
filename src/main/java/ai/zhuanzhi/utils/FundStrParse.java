package ai.zhuanzhi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FundStrParse {
    public static final Pattern VALID_FUND_REGEX =
            Pattern.compile("\\b[Nos]{0,3}([A-Z0-9-]+)\\b", Pattern.CASE_INSENSITIVE);

    public static List<String> fundParse(String fundStr) {
        String[] funds = fundStr.replace(".", "")
                .replace("。", "")
                .split(";|；");
        List<String> fundsParsed = new ArrayList<String>();
        for (String fund : funds) {
            Matcher matcher = VALID_FUND_REGEX.matcher(fund);
            if (matcher.find() && matcher.group().length() > 5) {
                fundsParsed.add(matcher.group());
            }
        }
        return fundsParsed;
    }

}
