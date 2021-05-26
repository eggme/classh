package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
}
