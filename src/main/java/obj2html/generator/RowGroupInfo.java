package obj2html.generator;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RowGroupInfo {

    private String name;

    private ArrayList<Field> fields = new ArrayList<>();

    public RowGroupInfo() {
    }

    public RowGroupInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public int getNumberOfFields() {
        return this.fields.size();
    }
}
