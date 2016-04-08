package com.mobiquity.androidunittests.net.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "queryresult", strict = false)
public class WolframResponse {
    @Attribute private boolean success;
    @Attribute private boolean error;
    @Attribute private int numpods;

    @ElementList(inline = true) List<Pod> pods;

    public List<Pod> getPods() {
        return pods;
    }

    @Root(strict = false)
    public static class Pod implements Comparable<Pod>{
        @Attribute private String title;
        @Attribute private String scanner;
        @Attribute private int position;
        @Attribute(name = "error") private boolean hasError;
        @Attribute private int numsubpods;

        @ElementList(inline = true, entry = "subpod")
        private List<Subpod> subpods;


        public String getTitle() {
            return title;
        }

        public List<Subpod> getSubpods() {
            return subpods;
        }

        @Override
        public int compareTo(Pod another) {
            return position < another.position ? -1 :
                    (position == another.position ? 0 : 1);
        }
    }


    @Root(strict = false)
    public static class Subpod {
        @Element(required = false, name = "plaintext")
        private String text;

        public String getText() {
            return text;
        }
    }

    public int getNumpods() {
        return numpods;
    }
}
