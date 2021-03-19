package me.eggme.classh.converter;

import me.eggme.classh.entity.Recommendation;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class RecommendationFormatter implements Formatter<Recommendation> {

    @Override
    public Recommendation parse(String s, Locale locale) throws ParseException {
        Recommendation recommendation = new Recommendation();
        recommendation.setValue(s);
        return recommendation;
    }

    @Override
    public String print(Recommendation recommendation, Locale locale) {
        return recommendation.getValue();
    }
}
