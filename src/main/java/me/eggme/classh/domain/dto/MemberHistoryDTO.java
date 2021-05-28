package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.CourseHistory;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberHistoryDTO {
    private List<CourseHistory> courseHistories;


    public Long completionCourseCount(){
        return this.getCourseHistories().stream().filter(ch -> ch.getStartTime() >= (ch.getEndTime()-10)).count();
    }
}
