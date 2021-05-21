<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 5:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/courseDescription.css" >
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script src="/js/courseDescription.js"></script>

<section class="main_session">
    <h5 class="margin_h_tag">강의 제작</h5>
    <h3 class="margin_h_tag margin_bottom">상세 소개</h3>
    <form class="courseForm" action="/course/${course.id}/save/description" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="course_id" value="" />
        <p class="input_des">강의 두줄 요약<span class="red_text">(강의소개 상단에 보여집니다. 잠재 수강생들이 매력을 느낄만한 글을 짧게 남겨주세요.)</span></p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <textarea class="text_area" name="shortDesc" placeholder="ex) 이 강의를 통해 수강생은 컴퓨터 공학의 기초를 다질 수 있을 것으로 예상합니다."><c:out value="${course.shortDesc}"/></textarea>
            </c:when>
            <c:otherwise>
                <textarea class="text_area" name="shortDesc" placeholder="ex) 이 강의를 통해 수강생은 컴퓨터 공학의 기초를 다질 수 있을 것으로 예상합니다." readonly><c:out value="${course.shortDesc}"/></textarea>
            </c:otherwise>
        </c:choose>
        <br />
        <div class="gray_line_divisor"></div>
        <div class="noti_wrap">
            <div class="notification">
                <h3>소개글을 충실히 작성해 주세요</h3>
                <p>강의 소개글은 강의 수강신청 및 판매율에 결정적인 영향을 끼칩니다.<br/>최소 7줄 이상의 소개를 부탁드려요!!<br/>강의 소개글 분량이 모자란 경우, 제출 후 반려될 수 있습니다.</p>
                <br />
                <h3>매력적인 소개글</h3>
                <p>학생들이 강의 소개를 볼 때 수강하고 싶도록 마음껏 내 강의의 매력을 어필해 보세요.<br/><b>사진, 그래프, 영상</b>등 설득될만한 자료를 꼭 첨부해 주세요!!<br /><br/>혹시 강의 소개 작성이 막막한 분들을 위해<b>가이드</b>를 준비했어요<br/>가이드를 보고 싶은 분들은 <span class="link_text">이곳</span>을 클릭!!!</p>
            </div>
        </div>
        <p class="input_des">강의 상세 내용<span class="red_text">(해당 내용은 강의 소개에서 보여집니다.)</span></p>
        <textarea id="editor" name="longDesc"><c:out value="${course.longDesc}"/></textarea>
        <div class="main_center">
            <c:choose>
                <c:when test="${!course.isReleased()}">
                    <div class="save_next_page">저장 후 다음이동</div>
                </c:when>
            </c:choose>
        </div>
    </form>

</section>
