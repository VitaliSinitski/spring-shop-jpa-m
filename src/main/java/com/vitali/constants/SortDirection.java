package com.vitali.constants;

public enum SortDirection {
    ASC,
    DESC;

    private static SortDirection sortDirection;
    public static SortDirection getSortDirection() {
        return sortDirection;
    }
    public static void setSortDirection(SortDirection sortDirection) {
        SortDirection.sortDirection = sortDirection;
    }
    public static boolean isSortedAscending(SortDirection getSortDirection) {
        return SortDirection.ASC.equals(getSortDirection);
    }

}
