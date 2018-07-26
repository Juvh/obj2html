package obj2html.annotations;

public @interface HtmlTableHead {

    String name() default "";

    HtmlAttribute[] attributes() default {};
}
