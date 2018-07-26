package obj2html.generator;

import j2html.tags.ContainerTag;
import obj2html.annotations.HtmlAttribute;
import obj2html.annotations.HtmlTable;
import obj2html.annotations.HtmlTableRow;
import obj2html.annotations.HtmlTableRowGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static j2html.TagCreator.*;

public class SimpleVerticalHtmlTableGenerator<T> implements HtmlTableGenerator {

    private Class<T> clazz;
    private Collection<T> dataSeed;
    private Map<String, RowGroupInfo> rowGroupInfos = new HashMap<>();

    public String generate(Map<String, Object> parameters) {
        initialize(parameters);
        HtmlTable htmlTable = clazz.getAnnotation(HtmlTable.class);
        ArrayList<ContainerTag> trList = generateTableRowList(htmlTable);
        return withAttributes(table(), htmlTable.attributes()).with(tbody().with(trList)).renderFormatted();
    }

    ////////////////////////////// Initialize //////////////////////////////
    private void initialize(Map<String, Object> parameters) {
        setClass(parameters);
        setRowGroupInfos();
        setDataSeed(parameters);
    }

    private void setClass(Map<String, Object> parameters) {
        this.clazz = (Class<T>) parameters.get(HtmlTableGenerator.DATA_CLASS_PARAM);
        if (!this.clazz.isAnnotationPresent(HtmlTable.class)) {
            throw new IllegalArgumentException("Target Class is not applicable class : " + clazz.getName());
        }
    }

    private void setRowGroupInfos() {
        Field[] declaredFields = this.clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            HtmlTableRow htmlTableRow = field.getAnnotation(HtmlTableRow.class);
            String groupName = coalesce(htmlTableRow.groupId(), field.getName());
            RowGroupInfo rowGroupInfo = rowGroupInfos.getOrDefault(groupName, new RowGroupInfo(groupName));
            rowGroupInfo.addField(field);
            this.rowGroupInfos.put(groupName, rowGroupInfo);
        }
    }

    private String coalesce(String... values) {
        for (String value : values) {
            if (value != null && value.trim().length() > 0) {
                return value;
            }
        }
        return null;
    }

    private void setDataSeed(Map<String, Object> parameters) {
        this.dataSeed = (Collection<T>) parameters.get(HtmlTableGenerator.DATA_SEED_PARAM);
    }

    ////////////////////////////// Html Generate //////////////////////////////

    private ArrayList<ContainerTag> generateTableRowList(HtmlTable htmlTable) {
        ArrayList<ContainerTag> trList = new ArrayList<>();
        for (HtmlTableRowGroup group : htmlTable.groups()) {
            RowGroupInfo rowGroupInfo = rowGroupInfos.get(group.id());
            ArrayList<Field> fields = rowGroupInfo.getFields();

            for (int idx = 0; idx < rowGroupInfo.getNumberOfFields(); idx++) {
                trList.add(generateTableRow(rowGroupInfo, fields.get(idx), idx));
            }
        }
        return trList;
    }

    private ContainerTag generateTableRow(RowGroupInfo rowGroupInfo, Field field, int idx) {
        field.setAccessible(true);
        HtmlTableRow htmlTableRow = field.getAnnotation(HtmlTableRow.class);

        ContainerTag trTag = withAttributes(tr(), htmlTableRow.attributes());
        setSubjectTableData(rowGroupInfo, idx, field, htmlTableRow, trTag);

        trTag.with(
                each(this.dataSeed, data -> td(getValue(field, data)))
        );
        return trTag;
    }

    private ContainerTag withAttributes(ContainerTag containerTag, HtmlAttribute[] attributes) {
        for (HtmlAttribute attribute : attributes) {
            containerTag = containerTag.attr(attribute.name(), attribute.value());
        }
        return containerTag;
    }

    private void setSubjectTableData(RowGroupInfo rowGroupInfo, int idx, Field field, HtmlTableRow htmlTableRow, ContainerTag trTag) {
        int numberOfFields = rowGroupInfo.getNumberOfFields();
        if (numberOfFields == 1) {
            trTag.with(td(coalesce(htmlTableRow.name(), field.getName())).attr("colspan", "2"));
        } else if (idx == 0) {
            trTag.with(td(rowGroupInfo.getName()).attr("rowSpan", numberOfFields))
                    .with(td(coalesce(htmlTableRow.name(), field.getName())));
        }
        trTag.with(td(coalesce(htmlTableRow.name(), field.getName())));
    }

    private String getValue(Field field, T data) {
        String value;
        try {
            value = String.valueOf(field.get(data));
        } catch (IllegalAccessException e) {
            // log.error("", e);
            value = "";
        }
        return value;
    }
}
