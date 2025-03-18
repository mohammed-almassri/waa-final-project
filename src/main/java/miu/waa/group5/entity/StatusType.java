package miu.waa.group5.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum StatusType {
    AVAILABLE, PENDING, CONTINGENT, SOLD;

    public String getReadableName() {
        List<String> subnames = Arrays.asList(this.name().split("_"));
        return subnames.stream().map((subname) -> subname.substring(0, 1).toUpperCase() + subname.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }

    public static Optional<StatusType> getEnumByString(String str) {
        String enumStr = str.toUpperCase().replace(" ", "_");
        List<StatusType> stauses = Arrays.asList(StatusType.values());
        return stauses.stream().filter(status -> status.name().equals(enumStr)).findFirst();

    }
}