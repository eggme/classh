package me.eggme.classh.converter;

import me.eggme.classh.entity.Tag;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class TagFormatter implements Formatter<Tag> {

    @Override
    public Tag parse(String s, Locale locale) throws ParseException {
        Tag tag = new Tag();
        tag.setValue(s);
        return tag;
    }

    @Override
    public String print(Tag tag, Locale locale) {
        return tag.getValue();
    }
}
