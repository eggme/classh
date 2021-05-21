package me.eggme.classh.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.utils.CourseValidation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CourseDTO implements Serializable {
    private Long id;
    private String name;
    private int price;
    private String url;
    private CourseState courseState;
    private List<SignUpCourse> signUpCourses;
    private Instructor instructor;
    private List<CourseSection> courseSections;
    private List<SkillTag> skillTags;
    private List<Recommendation> recommendations;
    private CourseLevel courseLevel;
    private CourseCategory courseCategory;
    private String shortDesc;
    private String longDesc;
    private String courseImg;
    private List<CourseReview> courseReviews;
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();
    private String nickName;
    @JsonIgnore
    private CourseValidation courseValidation;

    // 총 강의 시간
    public int getTotalTime(){
        int totalTime = courseSections.stream().mapToInt(s ->
                s.getCourseClasses().stream().mapToInt(c ->
                        c.getSeconds())
                        .sum())
                .sum();
        return totalTime;
    }

    // 총 강의 수
    public int getTotalClassCount(){
        int totalClassCount = courseSections.stream().mapToInt(s -> s.getCourseClasses().size()).sum();
        return totalClassCount;
    }

    // '제출' 상태 이면 true 반환
    public boolean isSubmitted(){
        if(getCourseState().getValue().equals(CourseState.SUBMIT.getValue()) ||
                getCourseState().getValue().equals(CourseState.RELEASE.getValue())){
            return true;
        }
        return false;
    }

    // '승인완료' 상태이면 true 반환
    public boolean isReleased(){
        if(getCourseState().getValue().equals(CourseState.RELEASE.getValue())){
            return true;
        }
        return false;
    }

    // 리뷰 평균 점수
    public String getReviewAvg(){
        if(courseReviews == null) return "0";
        double average = courseReviews.stream().mapToInt(cr -> cr.getRate()).average().orElseGet(() -> 0);
        return String.format("%.1f", average);
    }

    // 리뷰 총 개수
    public int getReviewCount(){
        if(courseReviews == null) return 0;
        return courseReviews.size();
    }

    public String getRatePercent() throws JsonProcessingException {
        Map<String, Double> map = new HashMap<>();
        map.put("rateFive", getRatePercentConversion(getReviewScoreCountByFive()));
        map.put("rateFour", getRatePercentConversion(getReviewScoreCountByFour()));
        map.put("rateThree", getRatePercentConversion(getReviewScoreCountByThree()));
        map.put("rateTwo", getRatePercentConversion(getReviewScoreCountByTwo()));
        map.put("rateOne", getRatePercentConversion(getReviewScoreCountByOne()));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    private double getRatePercentConversion(int count){
        return (count / ((double)this.getReviewCount())) * 100;
    }

    // 리뷰 5점 개수
    public int getReviewScoreCountByFive(){
        if(courseReviews == null) return 0;
        List<CourseReview> reviewRateByFive = courseReviews.stream().filter(cr -> cr.getRate() == 5).collect(Collectors.toList());
        return reviewRateByFive.size();
    }

    // 리뷰 4점 개수
    public int getReviewScoreCountByFour(){
        if(courseReviews == null) return 0;
        List<CourseReview> reviewRateByFour = courseReviews.stream().filter(cr -> cr.getRate() == 4).collect(Collectors.toList());
        return reviewRateByFour.size();
    }

    // 리뷰 3점 개수
    public int getReviewScoreCountByThree(){
        if(courseReviews == null) return 0;
        List<CourseReview> reviewRateByThree = courseReviews.stream().filter(cr -> cr.getRate() == 3).collect(Collectors.toList());
        return reviewRateByThree.size();
    }

    // 리뷰 2점 개수
    public int getReviewScoreCountByTwo(){
        if(courseReviews == null) return 0;
        List<CourseReview> reviewRateByTwo = courseReviews.stream().filter(cr -> cr.getRate() == 2).collect(Collectors.toList());
        return reviewRateByTwo.size();
    }

    // 리뷰 1점 개수
    public int getReviewScoreCountByOne(){
        if(courseReviews == null) return 0;
        List<CourseReview> reviewRateByOne = courseReviews.stream().filter(cr -> cr.getRate() == 1).collect(Collectors.toList());
        return reviewRateByOne.size();
    }

    // 해당강의에 수강평을 썼는지 검증
    public boolean isWroteReview(Member member){
        for(CourseReview review : courseReviews){
            if(review.getMember().getId() == member.getId()){
                return true;
            }
        }
        return false;
    }
}
