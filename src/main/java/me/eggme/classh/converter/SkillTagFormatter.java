package me.eggme.classh.converter;

import me.eggme.classh.entity.SkillTag;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class SkillTagFormatter implements Formatter<SkillTag> {

    @Override
    public SkillTag parse(String s, Locale locale) throws ParseException {
        SkillTag skillTag = new SkillTag();
        skillTag.setValue(s);
        return skillTag;
    }

    @Override
    public String print(SkillTag skillTag, Locale locale) {
        return skillTag.getValue();
    }
}
