package com.mobiquity.androidunittests.util;

public class HashCodeBuilder {

    int hash;

    public HashCodeBuilder() {
        hash = 3;
    }

    public HashCodeBuilder append(Object item) {
        hash = hash * 31 + (item == null ? 0 : item.hashCode());
        return this;
    }

    public int getHash() {
        return hash;
    }
}
