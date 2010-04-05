package com.madgag.guardian.contentapi.jaxb;

public class TagBuilder extends Tag {
    Tag tag = new Tag();

    public TagBuilder type(String type) {
        tag.type=type;
        return this;
    }
    
    public TagBuilder id(String id) {
        tag.id=id;
        return this;
    }

    public Tag toTag() {
       return tag;
    }
}
