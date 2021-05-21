<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-25
  Time: 오후 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/courseImage.css">
<script src="/js/courseImage.js"></script>

<section class="main_session">
    <h5 class="margin_h_tag">강의 제작</h5>
    <h3 class="margin_h_tag margin_bottom">커버 이미지</h3>
    <form>
        <div class="noti_wrap">
            <div class="notification">
                <h3>커버 이미지 - 썸네일 및 홍보영상 등</h3>
                <p>강의 커버 이미지(썸네일)를 직접 제작하실 경우, 이미지 규정에 맞춰 주세요.<br/>이미지가 규정에 맞지 않을 경우, 운영팀 판단하에 임의로 변경될 수 있습니다.<br/>홍보동영상
                    등록에 홍보동영상이나 강의 첫번째 영상(무료공개)을 등록해주세요.</p>
            </div>
        </div>
        <div class="gray_line_divisor"></div>
        <div class="section_box">
            <div class="upload_box">
                <div class="img_box">
                    <img class="course_cover_image" src="${course.courseImg}"/>
                </div>
                <div class="upload_text">
                    <h3>강의를 대표하는 이미지</h3>
                    <p>이미지 크기 : 768 x 500(px), 확장자 : jpg. jpeg, png, <span style="background-color: #FFFF77;">이미지에 한글을 포함할 수 없습니다.</span>
                    </p>
                    <div class="upload_item_box">
                        <c:choose>
                            <c:when test="${!course.isReleased()}">
                                <div class="file_box">
                                    <input class="upload-name" value="커버 이미지를 선택해주세요." disabled="disabled">
                                    <label for="ex_filename">파일 선택</label>
                                    <input class="upload-hidden" id="ex_filename" name="file" type="file"
                                           accept=".jpg, .jpeg, .png" onchange="changeImage(this, '${course.id}')">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="file_box">
                                    <input class="upload-name" value="커버 이미지를 선택해주세요." disabled="disabled" readonly>
                                    <label for="ex_filename_otherwise" style="cursor: not-allowed !important;">파일 선택</label>
                                    <input class="upload-hidden" id="ex_filename_otherwise" name="file" type="hidden"
                                           accept=".jpg, .jpeg, .png" readonly>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </form>

</section>
