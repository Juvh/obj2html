package obj2html.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlTable {
    String name() default "";

    HtmlTableHead[] tableHeadSet() default {};

    HtmlAttribute[] attributes() default {};

    HtmlTableRowGroup[] groups();
}
