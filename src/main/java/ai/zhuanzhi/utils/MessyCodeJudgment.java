package ai.zhuanzhi.utils;


import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessyCodeJudgment {
    /**
     * check if a String contains messy code
     * @param c
     * @return  true of false
     */
    private static boolean isChinese(char c){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isLetterOrDigitOrGreekAlphabet(char c){
        return (c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= 'α' && c <= 'ω');
    }

    public static boolean hasMessyCode(String str){
        Pattern p = Pattern.compile("\\s*|\t*|\n*");
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").replaceAll("\\p{P}","");
        char[] ch = s.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (char c : ch) {
            if(!isLetterOrDigitOrGreekAlphabet(c) && !isChinese(c)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "->";
        System.out.println(MessyCodeJudgment.hasMessyCode(s));
    }
}
