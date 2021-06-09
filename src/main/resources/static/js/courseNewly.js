$(function () {
    $('.title').click(function () {
        $('.newly_write_form_wrapper').css('display', 'block');
    });
    $('.newly_cancel').click(function () {
        $('.newly_write_form_wrapper').css('display', 'none');
    });
    /* 새소식 작성하기에서 데이터를 입력하고 submit 버튼을 누를 호출 호출 */
    $('.newly_submit').click(function () {
        let title = $('.newly_name').val();
        let isPublic = $(':radio[name="isPublic"]:checked').val();
        let notice = tinymce.get('news').getContent();
        let course_id = $('.newly_write_form_wrapper').attr('data-id');
        if (isPublic == 'public') isPublic = true;
        else isPublic = false;

        $.ajax({
            url: "/notice/add",
            method: "post",
            dataType: "json",
            data: {
                'title': title,
                'isPublic': isPublic,
                'notice': notice,
                'course_id': course_id
            }, success: function (result) {
                location.href = "/notice/" + result.id;
            }, error: function (e) {
                console.log(e);
            }
        })
    });

    /* 새소식 수정 버튼을 누를 시 */
    $('.modify').on('click', function () {
        let id = $(this).attr('data-id');
        $.ajax({
            url: "/notice/select",
            method: "post",
            dataType: "json",
            data: {"notice_id": id},
            success: function (result) {
                $('.newly_edit_name').val(result.title);
                tinymce.get('edit_news').setContent(result.notice);

                if (result.public == false) {
                    $('.course_public_edit').prop('checked', true);
                } else {
                    $('.all_public_edit').prop('checked', true);
                }

                $('.newly_edit_form_wrapper').attr('data-id', id);
                $('.newly_edit_form_wrapper').css('display', 'block');
            }, error: function (e) {
                console.log(e);
            }
        });
    });
    /* 새소식 삭제 버튼을 누를 시 */
    $('.delete').on('click', function () {
        $('.newly_delete_form_wrapper').attr('data-id', $(this).attr('data-id'));
        $('.newly_delete_form_wrapper').css('display', 'block');
    });

    /* 새소식 수정 모달에서 저장 누를 시 */
    $('.newly_edit_submit').click(function () {
        let id = $('.newly_edit_form_wrapper').attr('data-id');
        let title = $('.newly_edit_name').val();
        let isPublic = $(':radio[name="isPublicEdit"]:checked').val();
        let notice = tinymce.get('edit_news').getContent();
        if (isPublic == 'public') isPublic = true;
        else isPublic = false;
        $.ajax({
            url: "/notice/edit",
            method: "post",
            dataType: "json",
            data: {
                "id": id,
                "title": title,
                "isPublic": isPublic,
                "notice": notice
            },
            success: function (result) {
                location.href = "/notice/" + result.id;
            }, error: function (e) {
                console.log(e);
            }
        });
    });

    /* 새소식 삭제 모달에서 삭제 누를 시 */
    $('.newly_delete_submit').click(function () {
        let id = $('.newly_delete_form_wrapper').attr('data-id');
        console.log(id);
        $.ajax({
            url: "/notice/delete",
            method: "post",
            dataType: "json",
            data: {"notice_id": id},
            success: function (result) {
                location.href = "/notice/" + result.id;
            }, error: function (e) {
                console.log(e);
            }
        });
    });

    /* 새소식 수정 모달에서 취소 누를 시 */
    $('.newly_edit_cancel').click(function () {
        $('.newly_edit_form_wrapper').css('display', 'none');
    });

    /* 새소식 삭제 모달에서 취소 누를 시 */
    $('.newly_delete_cancel').click(function () {
        $('.newly_delete_form_wrapper').css('display', 'none');
    });

    /* 새소식에 댓글을 작성 */
    $('.comment_write_button').on('click', function () {
        $(this).closest('.notice_comment_form').submit();
    });

    $('.show_comment').click(function () {
        $(this).siblings(".notice_comment_template_area").removeClass("hidden");
    });

    /* 새소식에 댓글 삭제 버튼을 누를 때 호출 */
    $('.notice_profile_delete').on('click', function () {
        let parent = $(this).closest('.notice_comment_template');
        if (confirm("정말 삭제하시겠습니까?") == true) {
            let id = $(this).attr('data-id');
            $.ajax({
                url: "/notice/delete/comment",
                method: "post",
                data: {"comment_id": id},
                success: function (result) {
                    $(parent).remove();
                }, error: function (e) {
                    console.log(e);
                }
            });
        }
    });

    /* 새소식에 댓글 수정을 누를 시 호출 */
    $('.notice_profile_edit').on('click', function () {
        let id = $(this).attr('data-id');
        let text = $(this).closest(".notice_profile_content_wrap").children(".notice_content");
        let parent = $(this).closest(".notice_comment_template");
        $(parent).children(".notice_comment_template_wrap").addClass("hidden");
        $(parent).children(".notice_like_wrap").addClass("hidden");
        let template = "<div class='notice_edit_form'>" +
            "<textarea class='notice_edit_textarea'>" + $(text).text() + "</textarea>" +
            "<div class='notice_edit_button_area'>" +
            "<div class='notice_edit_button_cancel'>취소</div>" +
            "<div class='notice_edit_button_submit' data-id='" + id + "'>확인</div>" +
            "</div>" +
            "</div>";
        $(parent).append(template);
    });
    /* 댓글 수정에서 확인을 눌렀을 떄 */
    $(document).on('click', '.notice_edit_button_submit', function () {
        let id = $(this).attr('data-id');
        let content = $(this).closest('.notice_edit_form').children(".notice_edit_textarea").val();
        let parent = $(this).closest(".notice_comment_template");
        let child = $(parent).children(".notice_comment_template_wrap");
        $.ajax({
            url: "/notice/edit/comment",
            method: "post",
            dataType: "json",
            data: {
                "comment_id": id,
                "commentContent": content
            }, success: function (result) {
                $(child).removeClass("hidden");
                $(child).find(".notice_content").text(result.commentContent);
                $(parent).children(".notice_like_wrap").remove("hidden");
                $(parent).children(".notice_edit_form").remove();
            }, error: function (e) {
                console.log(e);
            }
        });
    });

    /* 댓글 수정에서 취소를 눌렀을 때 */
    $(document).on('click', '.notice_edit_button_cancel', function () {
        let parent = $(this).closest(".notice_comment_template");
        $(parent).children(".notice_comment_template_wrap").removeClass("hidden");
        $(parent).children(".notice_like_wrap").remove("hidden");
        $(parent).children(".notice_edit_form").remove();
    });
});

tinymce.init({
    mode: 'textareas',
    selector: '#news',
    height: 300,
    plugins: 'image code media image',
    language_url: '/js/ko_KR.js',
    toolbar: 'undo redo | link image | code | media ',
    media_live_embeds: true,
    image_title: true,
    automatic_uploads: true,
    file_picker_types: 'image',
    video_template_callback: function (data) {
        return '<video width="' + data.width + '" height="' + data.height + '"' + (data.poster ? ' poster="' + data.poster + '"' : '') + ' controls="controls">\n' + '<source src="' + data.source1 + '"' + (data.source1mime ? ' type="' + data.source1mime + '"' : '') + ' />\n' + (data.source2 ? '<source src="' + data.source2 + '"' + (data.source2mime ? ' type="' + data.source2mime + '"' : '') + ' />\n' : '') + '</video>';
    },
    images_upload_handler: image_upload_handler,
    content_style: '//www.tinymce.com/css/codepen.min.css'
});

tinymce.init({
    mode: 'textareas',
    selector: '#edit_news',
    height: 300,
    plugins: 'image code media image',
    language_url: '/js/ko_KR.js',
    toolbar: 'undo redo | link image | code | media ',
    media_live_embeds: true,
    image_title: true,
    automatic_uploads: true,
    file_picker_types: 'image',
    video_template_callback: function (data) {
        return '<video width="' + data.width + '" height="' + data.height + '"' + (data.poster ? ' poster="' + data.poster + '"' : '') + ' controls="controls">\n' + '<source src="' + data.source1 + '"' + (data.source1mime ? ' type="' + data.source1mime + '"' : '') + ' />\n' + (data.source2 ? '<source src="' + data.source2 + '"' + (data.source2mime ? ' type="' + data.source2mime + '"' : '') + ' />\n' : '') + '</video>';
    },
    images_upload_handler: image_upload_handler,
    content_style: '//www.tinymce.com/css/codepen.min.css'
});