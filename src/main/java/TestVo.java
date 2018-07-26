import obj2html.annotations.HtmlAttribute;
import obj2html.annotations.HtmlTable;
import obj2html.annotations.HtmlTableRow;
import obj2html.annotations.HtmlTableRowGroup;

@HtmlTable(attributes = {
        @HtmlAttribute(name = "width", value = "100%"),
        @HtmlAttribute(name = "cellpadding", value = "5"),
        @HtmlAttribute(name = "border", value = "1"),
        @HtmlAttribute(name = "style", value = "border-collapse:collapse; border:1px black solid; padding: 5px; font:'calibri'")
}, groups = {
        @HtmlTableRowGroup(id = "order", name = "Order"),
        @HtmlTableRowGroup(id = "box", name = "Invoices")
})
public class TestVo {

    @HtmlTableRow(name = "AAA", groupId = "order", attributes = {
            @HtmlAttribute(name = "style", value = "text-align: 'center';")
    })
    private String val1;

    @HtmlTableRow(name = "BBB", groupId = "box", attributes = {
            @HtmlAttribute(name = "style", value = "text-align: 'center';")
    })
    private String val2;

    @HtmlTableRow(name = "CCC", groupId = "order", attributes = {
            @HtmlAttribute(name = "style", value = "text-align: 'center';")
    })
    private String val3;

    public TestVo(String val1, String val2, String val3) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }
}