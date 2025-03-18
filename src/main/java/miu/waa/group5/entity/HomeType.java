package miu.waa.group5.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum HomeType {
    HOUSE, TOWN_HOME, CONDO, APARTMENT;

    public String getReadableName() {
        List<String> subnames = Arrays.asList(this.name().split("_"));
        return subnames.stream().map((subname) -> subname.substring(0, 1).toUpperCase() + subname.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }

    public static Optional<HomeType> getEnumByString(String str) {
        String enumStr = str.toUpperCase().replace(" ", "_");
        List<HomeType> homes = Arrays.asList(HomeType.values());
        return homes.stream().filter(home -> home.name().equals(enumStr)).findFirst();

    }
}



