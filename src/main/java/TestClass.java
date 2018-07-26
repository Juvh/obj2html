import obj2html.annotations.HtmlAttribute;
import obj2html.annotations.HtmlTable;
import obj2html.annotations.HtmlTableRow;
import obj2html.generator.HtmlTableGenerator;
import obj2html.generator.SimpleVerticalHtmlTableGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestClass {

    public static void main(String[] args) {
        SimpleVerticalHtmlTableGenerator generator = new SimpleVerticalHtmlTableGenerator();
        ArrayList<TestVo> dataSeed = new ArrayList<>();
        dataSeed.add(new TestVo(" 1", "11m", "asdfasdf"));
        dataSeed.add(new TestVo(" 2", "11asd", "asdfasdf1"));
        dataSeed.add(new TestVo(" 3", "11dasf", "asdfasdf2"));
        dataSeed.add(new TestVo(" 4", "11dasf", "asdfasdf3"));
        dataSeed.add(new TestVo(" 5", "11ewqr", "asdfasdf4"));
        dataSeed.add(new TestVo(" 6", "11cxzv", "asdfasdf5"));
        dataSeed.add(new TestVo(" 7", "11hjd", "asdfasdf6"));
        dataSeed.add(new TestVo(" 8", "11gsdfh", "asdfasdf7"));
        dataSeed.add(new TestVo(" 9", "11wrg", "asdfasdf8"));
        dataSeed.add(new TestVo(" 10", "11k;lj", "asdfasdf9"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(HtmlTableGenerator.DATA_CLASS_PARAM, TestVo.class);
        parameters.put(HtmlTableGenerator.DATA_SEED_PARAM, dataSeed);

        String html = generator.generate(parameters);

        System.out.println(html);
    }


}
