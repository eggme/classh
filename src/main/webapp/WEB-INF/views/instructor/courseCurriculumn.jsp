<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-23
  Time: 오후 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:csrfMetaTags/>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link href="/css/curriculum.css" rel="stylesheet"/>
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script src="/js/curriculum.js"></script>
<section class="main_session">
    <h5 class="margin_h_tag">강의 제작</h5>
    <h3 class="margin_h_tag margin_bottom">커리큘럼</h3>
    <form>
        <div class="notification_wrap">
            <div class="notification">
                <h3>영상 등록</h3>
                <p>강의의 커리큘럼을 모두 작성한 뒤 수업마다 영상을 연결해 주셨나요?<br/>일부 영상에 재생 에러가 발생할 경우, 파일명을 바꿔서 재업로드해주세요.<br/>강의
                    커리큘럼이 확정되지 않으면 강의를 오픈할 수 없습니다.</p>
                <br/>
                <h3>무료공개 수업 최소 1개 이상!</h3>
                <p>무료공개 수업을 최소 한 개 설정해주세요.<br/>강의를 잘 표현 할 수 있는 좋은 수업을 보여준다면 수강신청률이 올라갑니다.</p>
                <br/>
                <h3>여러개의 섹션으로 나눠주세요</h3>
                <p>수업들을 여러 섹션으로 잘게 묶어 놓으면 학생들이 훨씬 효과적으로 학습할 수 있습니다.<br/>보통 1개 섹션당 4~6개의 수업으로 구성하면 좋아요!</p>
            </div>
        </div>
        <div class="gray_line_divisor"></div>
        <div class="toolbox">
            <c:choose>
                <c:when test="${!course.isReleased()}">
                    <a class="add_section"><i class="fas fa-plus-circle"></i>&nbsp;섹션 추가하기</a>
                </c:when>
            </c:choose>

        </div>
        <div class="section_box">
            <c:forEach var="course_section" items="${course.courseSections}" varStatus="section_status">
                <c:choose>
                    <c:when test="${!course.isReleased()}">
                        <script>
                            createSectionBox('${course_section.name}', '${section_status.index}', '${course_section.id}', false);
                        </script>
                    </c:when>
                    <c:otherwise>
                        <script>
                            createSectionBox('${course_section.name}', '${section_status.index}', '${course_section.id}', true);
                        </script>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="course_class" items="${course_section.courseClasses}" varStatus="class_status">
                    <c:set var="isPreview" value="false"/>
                    <c:set var="inVideo" value="false"/>
                    <c:set var="inFile" value="false"/>
                    <c:if test="${course_class.preview eq true}">
                        <c:set var="isPreview" value="true"/>
                    </c:if>
                    <c:if test="${!empty course_class.mediaPath}">
                        <c:set var="inVideo" value="true"/>
                    </c:if>
                    <c:if test="${!empty course_class.dataPath}">
                        <c:set var="inFile" value="true"/>
                    </c:if>
                    <c:choose>
                        <c:when test="${!course.isReleased()}">
                            <script>
                                createClassBox('${course_class.name}', '${course_section.id}', '${section_status.index}', '${class_status.index}', '${course_class.id}', ${isPreview}, ${inVideo}, ${inFile}, false);
                            </script>
                        </c:when>
                        <c:otherwise>
                            <script>
                                createClassBox('${course_class.name}', '${course_section.id}', '${section_status.index}', '${class_status.index}', '${course_class.id}', ${isPreview}, ${inVideo}, ${inFile}, true);
                            </script>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </c:forEach>
            <%--createSectionBox--%>
        </div>
        <div class="main_center">
            <c:choose>
                <c:when test="${!course.isReleased()}">
                    <div class="save_next_page" onclick="redirectThumbnail('${course.id}')">저장 후 다음이동</div>
                </c:when>
            </c:choose>
        </div>
    </form>

</section>

<div id="add_class_form" class="class_form" tabindex="1" role="dialog" data-id="${course.id}">
    <div class="add_class_content animate">
        <div class="add_class_container">
            <h2>수업 추가</h2>
            <p>제목</p>
            <div class="add_class_input">
                <input type="text" class="gray_input title_class" placeholder="제목을 입력해주세요."/>
            </div>
            <div class="add_class_buttons">
                <div class="submit_button class_submit">저장</div>
                <div class="cancel_button class_cancel">취소</div>
            </div>
        </div>
    </div>
</div>
<div id="add_section_form" class="section_form" tabindex="1" role="dialog" data-id="${course.id}">
    <div class="add_section_content animate">
        <div class="add_section_container">
            <h2>섹션 추가</h2>
            <p>제목</p>
            <div class="add_section_input">
                <input type="text" class="gray_input title_section" placeholder="제목을 입력해주세요."/>
            </div>
            <p>학습 목표</p>
            <div class="add_section_input">
                <input type="text" class="gray_input goal_section" placeholder="이 섹션의 학습 목표를 적어주세요."/>
            </div>
            <div class="add_section_buttons">
                <div class="submit_button section_submit">저장</div>
                <div class="cancel_button section_cancel">취소</div>
            </div>
        </div>
    </div>
</div>
<div class="edit_section_form" tabindex="1" role="dialog" data-id="${course.id}">
    <div class="edit_section_content animate">
        <div class="edit_section_container">
            <h2>섹션 변경</h2>
            <p>제목</p>
            <div class="edit_section_input">
                <input type="text" class="gray_input title_edit_section" placeholder="제목을 입력해주세요."/>
            </div>
            <p>학습 목표</p>
            <div class="edit_section_input">
                <input type="text" class="gray_input goal_edit_section" placeholder="이 섹션의 학습 목표를 적어주세요."/>
            </div>
            <div class="edit_section_buttons">
                <div class="submit_button edit_section_submit">저장</div>
                <div class="cancel_button edit_section_cancel">취소</div>
            </div>
        </div>
    </div>
</div>
<div id="add_class_description_form" class="class_description_form" tabindex="1" role="dialog" data-id="${course.id}">
    <input type="hidden" class="section_number" name="section_number" value="0"/>
    <input type="hidden" class="section_id" name="section_id" value="0"/>
    <input type="hidden" class="class_number" name="class_number" value="0"/>
    <input type="hidden" class="class_id" name="class_id" value="0"/>
    <form action="/course/edit/class/info" method="post" class="editClassForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" class="id" name="id" value="0"/>
        <input type="hidden" class="mediaPath" name="mediaPath" value="0"/>
        <input type="hidden" class="dataPath" name="dataPath" value="0"/>
        <div class="add_class_description_content animate">
            <div class="add_class_description_container">
                <div class="absolute_top">
                    <div class="add_section_input">
                        <p class="class_edit_title">제목</p>
                        <input type="text" class="gray_input title_class_description" name="name"
                               placeholder="제목을 입력해주세요."/>
                    </div>
                </div>
                <div class="scroll_container">
                    <div class="add_section_input">
                        <p class="edit_title">영상 업로드</p>
                        <div class="upload_item_box">
                            <div class="file_box">
                                <input class="upload-name" value="수업 영상 파일을 선택해주세요." disabled="disabled">
                                <label for="ex_filename">파일 선택</label>
                                <input class="upload-hidden file_upload" id="ex_filename" type="file"
                                       accept=".mp4, .mvg, .mv4" onchange="fileUpload(this)">
                            </div>
                        </div>
                    </div>
                    <div class="isPublic_wrap">
                        <div class="isPublic_title">무료공개 여부 선택</div>
                        <div class="isPublic_toggle">
                            <input type="checkbox" id="switch" name="preview" class="isPublic_toggle_input"
                                   value="true"/>
                            <label for="switch" class="isPublic_toggle_button"></label>
                            <span class="on_off toggle_off">OFF</span>
                        </div>
                    </div>
                    <div class="study_note_wrap">
                        <div class="edit_title margin_bottom">수업노트 작성</div>
                        <textarea id="instructorNote" name="instructorMemo"></textarea>
                    </div>
                    <div class="study_file_warp">
                        <p class="edit_title">자료파일 업로드</p>
                        <div class="upload_item_box">
                            <div class="file_box">
                                <input class="fileUpload-name" value="수업과 연결될 파일을 첨부해주세요." disabled="disabled">
                                <label for="study_filename">파일 선택</label>
                                <input class="upload-hidden study_file_upload" id="study_filename"
                                       type="file"
                                       accept=".pdf" onchange="StudyFileUpload(this)">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="add_class_description_buttons">
                    <div class="submit_button class_description_submit">저장</div>
                    <div class="cancel_button class_description_cancel">취소</div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>