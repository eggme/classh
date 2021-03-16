<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="/js/addCourse.js"></script>
<link rel="stylesheet" href="/css/addCourse.css" >
<section class="main_session">

    <h5 class="margin_h_tag">강의 제작</h5>
    <h3 class="margin_h_tag margin_bottom">강의 정보</h3>
    <form class="course_form" action="/course/create" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <p class="input_des">강의 제목</p>
        <input type="text" name="name" class="gray_input course_title_input" value="${course.name}"/>
        <br/>
        <p class="input_des">가격 설정</p>
        <div class="price_wrap">
            <span class="money_mark">&#8361;</span>
            <input type="number" name="price" class="gray_input price_input course_price_input"
                   placeholder="가격을 설정해 주세요."/>
            <p class="price_description">
                1. 가격 설정 후 제출하신 후에는, 가격 변경이 되지 않아요! 바꾸고 싶은 경우에는 운영팀에 문의해주세요 :)<br/>
                <font color="#fFB6351">2. 입력하신 가격은 부가세 미포함 가격입니다. 실제 수강생에는 부가세 10% 합산된 가격으로 보입니다.</font><br/>
                3. 가격은 무료의 경우 0원으로 유료의 경우 10,000원 이상 1,000원 단위로 설정할 수 있습니다.
            </p>
        </div>
        <br/>
        <p class="input_des">이런 걸 배울 수 있어요</p>
        <input type="text" name="course_tag" class="gray_input course_tag_input" placeholder="ex) Spring framework">
        <div class="course_tag_add">추가하기</div>
        <div class="course_tag_required">두 개 이상 넣어주세요.</div>
        <div class="course_tag_hidden"></div>
        <br/>
        <p class="input_des">이런 분들에게 추천해요</p>
        <input type="text" name="course_recommend" class="gray_input course_recommend_input"
               placeholder="ex) 자바를 공부한 사람">
        <div class="course_recommend_add">추가하기</div>
        <div class="course_tag_required">두 개 이상 넣어주세요.</div>
        <div class="course_recommend_hidden"></div>
        <br/>
        <p class="input_des">카테고리</p>
        <div class="tag_container category">
            <input type="hidden" name="category" class="category_value" value=""/>
            <div class="tag_box category_box">Java</div>
            <div class="tag_box category_box">Spring</div>
            <div class="tag_box category_box">SpringBoot</div>
            <div class="tag_box category_box">Hibernate</div>
            <div class="tag_box category_box">Spring JPA</div>
            <div class="tag_box category_box">Spring Security</div>
            <div class="tag_box category_box">Bootstrap</div>
        </div>
        <br/>
        <p class="input_des">강의 수준</p>
        <div class="tag_container level">
            <input type="hidden" name="level" class="level_value" value=""/>
            <div class="tag_box level_box">초급</div>
            <div class="tag_box level_box">중급</div>
            <div class="tag_box level_box">고급</div>
        </div>
        <div class="main_center">
            <div class="save_next_page">저장 후 다음이동</div>
        </div>
    </form>

</section>
