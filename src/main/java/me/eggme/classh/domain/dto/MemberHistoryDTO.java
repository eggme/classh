package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseHistory;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberHistoryDTO {
    private List<CourseHistory> courseHistories;

    /**
     * 유저의 수업 기록들에서 해당 강의 수업 기록을 리턴함
     * @param course 강의 pk
     * @return
     */
    private List<CourseHistory> findHistories(CourseDTO course){
        List<CourseHistory> courseHistories = this.courseHistories.stream().filter(ch ->
                ch.getCourse().getId() == course.getId()).collect(Collectors.toList());
        if(courseHistories != null) {
            return courseHistories;
        }
        return null;
    }

    /***
     * 유저의 수업 기록들에서 해당 강의의 최종적으로 학습한 개수를 리턴함
      * @param course 강의 pk
     * @return 해당 강의의 최종적으로 학습한 수업 개수
     */
    public Long findHistoriesCompletionCourseCount(CourseDTO course){
        List<CourseHistory> histories = findHistories(course);
        return histories.stream().filter(ch ->
                ch.getStartTime() >= (ch.getEndTime() - 10)).count();
    }

    public Long completionCourseCount(){
        return this.getCourseHistories().stream().filter(ch ->
                ch.getStartTime() >= (ch.getEndTime()-10)).count();
    }
}
