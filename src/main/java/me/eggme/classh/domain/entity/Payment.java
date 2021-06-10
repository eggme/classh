package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"member", "course"})
@ToString(exclude = {"member", "course"})
public class Payment extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;
    // 결제 방법
    private String method;

    // 카드이름
    private String cardName;

    // 카드 번호
    private String cardNumber;

    // 제품 코드
    private String imp_uid;

    // 상품 코드
    private String merchantId;

    // 상품 명
    private String courseName;

    // 상품 가격
    private int coursePrice;

    // 구매 상태
    public String purchaseStatus;

    // 구매 결과
    public boolean purchaseResult;

    @JsonBackReference
    @ManyToOne
    private Member member;

    @JsonManagedReference
    @OneToMany
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private Set<Course> courseSet = new HashSet<>();

    public void addCourse(Course course){
        this.getCourseSet().add(course);
    }
}
