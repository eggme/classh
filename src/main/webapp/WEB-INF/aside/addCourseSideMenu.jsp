<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 3:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/aside/addCourseSideMenu.css" >
<script src="/js/aside/addCourseSideMenu.js"></script>
<script type="text/javascript">
    function activeButtonSet(value){
        $('.items').children().removeClass('active');
        $('.'+value).addClass('active');
    }
</script>
<aside class="main_aside">
    <h2 class="item_title">강의 제작</h2>
    <ul class="items">
        <li class="course_info active">
            <a href="/course/${course.id}/edit/course_info">
                <i class="fas fa-check-circle check"></i>&nbsp;&nbsp;강의정보
            </a>
        </li>
        <li class="description">
            <a href="/course/${course.id}/description">
                <i class="fas fa-check-circle check"></i>&nbsp;&nbsp;상세소개
            </a>
        </li>
        <li class="curriculum">
            <a href="/course/${course.id}/curriculum">
                <i class="fas fa-check-circle check"></i>&nbsp;&nbsp;커리큘럼
            </a>
        </li>
        <li class="thumbnail_ ">
            <a href="/course/${course.id}/thumbnail">
                <i class="fas fa-check-circle check"></i>&nbsp;&nbsp;커버 이미지
            </a>
        </li>
        <c:set var="info" value="${course.courseValidation.courseInfoValidation()}" />
        <c:set var="description" value="${course.courseValidation.courseDetailValidation()}" />
        <c:set var="curriculum" value="${course.courseValidation.curriculumValidation()}" />
        <c:set var="coverImage" value="${course.courseValidation.coverImageValidation()}" />
        <script>
            activeButtonSet('${category}');
            validationMenu(${info}, ${description}, ${curriculum}, ${coverImage});
        </script>
    </ul>
    <br/>
    <br/>
    <c:set var="isSubmitted" value="${course.isSubmitted()}" />
    <c:if test="${!isSubmitted}">
        <div class="relativity">
            <div class="final_submit">제출하기</div>
            <div class="guide_wrap animate hidden">
                <div class="guide_title">체크를 확인해주세요!</div>
                <div class="guide_body">
                    <div class="guide_img_wrap">
                        <img src="/imgs/checklist.jpg" class="guide_img"/>
                    </div>
                    <div class="guide_subText">모든 체크리스트가 활성화 되었는지 확인해주세요</div>
                </div>
            </div>
            <c:set var="check" value="${course.courseValidation.validation()}"/>
            <script>
                validation(${check});
            </script>
        </div>
    </c:if>
</aside>

<div class="course_submit_form" data-id="${course.id}">
    <div class="course_submit_content animate">
        <div class="course_submit_container">
            <div class="course_submit_icon">
                <i class="far fa-check-circle"></i>
            </div>
            <div class="course_submit_text_title">
                정말 제출하시겠어요?
            </div>
            <div class="course_submit_text_description">
                <div>모든 항목 작성이 완료되었나요?</div>
                <div>강의가격, 커리큘럼, 소개 등을 한번 더 체크해 주세요.</div>
                <div>강의 준비에 미흡한 사항이 있을 경우,</div>
                <div>담당MD 피드백과 함께 강의 상태가</div>
                <div>미제출(임시저장 상태)로 변경될 수 있습니다.</div>
                <br />
                <div>* 제출 확인 후, 강의 오픈 준비에 영업일 기준 최대 1주일까지 소요될 수 있습니다.</div>
                <br />
                <div>*오픈 준비가 완료되면 담당 MD가 최종 확인 메일을 보내드립니다.</div>
                <div>꼭 회신 부탁드려요.</div>
                <br />
                <div>* 모든 확인이 끝나면 계약서를 발송해드리고(유료 강의 한정) 강의를 오픈합니다.</div>
            </div>
            <div class="course_submit_button_area">
                <div class="submit_button course_submit">확인</div>
                <div class="cancel_button course_cancel">취소</div>
            </div>
        </div>
    </div>
</div>

<div class="course_submit_success_form" >
    <div class="course_submit_success_content animate">
        <div class="course_submit_success_container">
            <div class="course_submit_success_icon">
                <i class="far fa-check-circle"></i>
            </div>
            <div class="course_submit_success_text_title">
                강의가 제출되었습니다.😊
            </div>
            <div class="course_submit_success_text_description">
                <div style="padding: 0px 14px;">소중한 지식 컨텐츠를 인프런에서 공유해 주셔서 감사합니다.</div>
                <div style="padding: 0 8px;">좋은 지식인 만큼 열심히 소개하고 알려 많은 사람들이 나눌 수 있도록 노력하겠습니다.</div>
                <div style="padding: 0 10px;">강의는 검토후에 하루 또는 이틀 내에 공개될 예정입니다.</div>
            </div>
            <div class="course_submit_success_button_area">
                <div class="course_success_submit">확인</div>
            </div>
        </div>
    </div>
</div>