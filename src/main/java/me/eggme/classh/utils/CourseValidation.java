package me.eggme.classh.utils;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.exception.CourseValidationException;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CourseValidation implements Serializable {

    private CourseDTO courseDTO;

    public CourseValidation(CourseDTO courseDTO){
        this.courseDTO = courseDTO;
    }

    public boolean courseInfoValidation(){
        try{
            if(courseDTO == null) log.info("null");
            courseNameValidation();
            skillTagsValidation();
            recommendationsValidation();
            courseCategoryValidation();
            courseLevelValidation();
        }catch(CourseValidationException cve){
            return false;
        }
        return true;
    }

    public boolean courseDetailValidation(){
        try{
            shortDescriptionValidation();
            longDescriptionValidation();
        }catch(CourseValidationException cve){
            return false;
        }
        return true;
    }

    public boolean curriculumValidation(){
        try{
            courseClassValidation();
        }catch(CourseValidationException cve){
            return false;
        }
        return true;
    }

    public boolean coverImageValidation(){
        try{
            defaultCoverImageValidation();
        }catch(CourseValidationException cve){
            return false;
        }
        return true;
    }

    public boolean validation(){
        try{
            if(!courseInfoValidation()){
                throw new CourseValidationException("강의정보에 누락된 정보가 있습니다.");
            }
            if(!courseDetailValidation()){
                throw new CourseValidationException("상세소개에 누락된 정보가 있습니다.");
            }
            if(!curriculumValidation()){
                throw new CourseValidationException("커리큘럼에 누락된 정보가 있습니다.");
            }
            if(!coverImageValidation()){
                throw new CourseValidationException("커버 이미지에 누락된 정보가 있습니다.");
            }
        }catch(CourseValidationException cve){
            return false;
        }
        return true;
    }

    private void courseNameValidation() throws CourseValidationException{
        if(courseDTO.getName() == null ||  courseDTO.getName().length() < 5){
            throw new CourseValidationException("강의 이름은 5글자 이상이어야 합니다.");
        }
    }

    private void priceValidation() throws CourseValidationException{
        if(courseDTO.getPrice() <= 0){
            throw new CourseValidationException("가격은 0이거나 음수일 수 없습니다.");
        }
    }

    private void skillTagsValidation() throws CourseValidationException{
        log.info("스킬태그 개수 : "+courseDTO.getSkillTags().size());
        if(courseDTO.getSkillTags().size() < 2){
            throw new CourseValidationException("스킬태그는 적어도 2개 이상 입력해야합니다.");
        }
    }

    private void recommendationsValidation() throws CourseValidationException{
        log.info("추천인 개수 : "+courseDTO.getRecommendations().size());
        if(courseDTO.getRecommendations().size() < 2){
            throw new CourseValidationException("추천인은 적어도 2개 이상 입력해야합니다.");
        }
    }

    private void courseLevelValidation() throws CourseValidationException{
        if(courseDTO.getCourseLevel() == null){
            throw new CourseValidationException("강의 수준을 선택해야합니다.");
        }
    }

    private void courseCategoryValidation() throws CourseValidationException{
        if(courseDTO.getCourseCategory() == null){
            throw new CourseValidationException("강의 카테고리를 선택해야합니다.");
        }
    }

    private void shortDescriptionValidation() throws CourseValidationException{
        if(courseDTO.getShortDesc() != null && courseDTO.getShortDesc().length() > 0){
            Pattern pattern = Pattern.compile("[가-힣]");
            Matcher matcher = pattern.matcher(courseDTO.getShortDesc());
            int count = 0;
            while(matcher.find()){
                if(count>5) break;
                count++;
            }
            if(count < 5){
                throw new CourseValidationException("강의 두줄 요약은 최소 5자 이상의 한글을 작성해야합니다.");
            }
        }else{
            throw new CourseValidationException("강의 두줄 요약은 최소 5자 이상의 한글을 작성해야합니다.");
        }
    }

    private void longDescriptionValidation() throws CourseValidationException{
        if(courseDTO.getLongDesc() != null && courseDTO.getLongDesc().length() > 0){
            Pattern pattern = Pattern.compile("[가-힣]");
            Matcher matcher = pattern.matcher(courseDTO.getLongDesc());
            int count = 0;
            while(matcher.find()){
                if(count>20) break;
                count++;
            }
            if(count < 20){
                throw new CourseValidationException("강의 상세 내용은 최소 20자 이상의 한글을 작성해야합니다.");
            }
        }else{
            throw new CourseValidationException("강의 상세 내용은 최소 20자 이상의 한글을 작성해야합니다.");
        }
    }

    private void courseClassValidation() throws CourseValidationException{
        // 섹션리스트안에 있는 섹션들을 꺼내서 그 안에있는 클래스들을 꺼낸 다음
        // 한개 이상 미리보기가 설정되어야 하고, 영상이 제공되어야 한다.
        boolean isValidated = courseDTO.getCourseSections().stream()
                .map(cs -> cs.getCourseClasses().stream()
                        .anyMatch(cc -> cc.getMediaPath() != null && cc.isPreview()))
                .anyMatch(b -> b.booleanValue());
        if(!isValidated){
            throw new CourseValidationException("한 개이상의 미리보기 강의에 영상을 포함해야합니다.");
        }
    }

    private void defaultCoverImageValidation() throws CourseValidationException{
        if(courseDTO.getCourseImg().equals("/imgs/default_course_image.png")){
           throw new CourseValidationException("커버 이미지를 등록해야합니다.");
        }
    }
}
