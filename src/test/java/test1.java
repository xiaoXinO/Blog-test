import com.java1234.util.CryptographyUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

/**
 * Created by xin on 2017/2/11.
 */
public class test1 {
    /**
     * ≤‚ ‘  String »•≥˝Html±Í«©
     */
    @Test
    public void test01() {
        String html = "<HTML></HTML>";
        System.out.println(StringEscapeUtils.escapeHtml(html));
    }

    @Test
    public void test02() {
        String str = "123456";
        String s = CryptographyUtil.md5("123456", "xin");
        System.out.println(s);
    }
}
