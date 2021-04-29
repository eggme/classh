package me.eggme.classh.domain.dto;

import lombok.Getter;

public enum CourseCategory {

    PROGRAMMING("개발·프로그래밍"),
    SECURITY("보안·네트워크"),
    DATA_SCIENCE("데이터 사이언스"),
    CREATIVE("크리에이티브"),
    SKILL("업무 스킬"),
    FOREIGN_LANGUAGE("학문·외국어"),
    CAREER("커리어"),
    CULTURE("교양"),
    ECT("그 외");

    @Getter
    private String value;

    CourseCategory(String value){
        this.value = value;
    }

    public static CourseCategory findCategory(String category){
        for(CourseCategory c : CourseCategory.values()){
            if(c.getValue().equals(category)){
                return c;
            }
        }
        return null;
    }
}
