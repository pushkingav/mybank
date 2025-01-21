package com.apushkin.model;

import java.util.Objects;
import java.util.StringJoiner;

public class ErrorObject {
    private String fieldName;
    private String rejectedValue;

    public ErrorObject(String fieldName, String rejectedValue) {
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ErrorObject that)) return false;

        return Objects.equals(fieldName, that.fieldName) && Objects.equals(rejectedValue, that.rejectedValue);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(fieldName);
        result = 31 * result + Objects.hashCode(rejectedValue);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorObject.class.getSimpleName() + "[", "]")
                .add("fieldName='" + fieldName + "'")
                .add("rejectedValue='" + rejectedValue + "'")
                .toString();
    }
}
