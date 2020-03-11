

function comment2target(targetId, type, content) {
    if(!content){
        alert("不能回复空内容....");
        return
    }

    $.ajax({
        type:"POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "type": type,
            "content": content,
        }),
       dataType: "json",
        success: function () {
            window.location.reload();
            $("#comment_section").hide();
        },
    });
}

function post() {
    var id = $("#qustion_id").val();
    var qcontent = $("#content").val();
    comment2target(id, 1, qcontent);
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data, function (index, comment) {
                    var media = $("<div/>", {
                        "class": "media"
                    });
                    var mediaLeft = $("<div/>", {
                        "class": "media-left"
                    });
                    var mediaBody = $("<div/>", {
                        "class": "media-body"
                    });
                    var touxiang =  $("<img/>", {
                        "class": "media-object touxiang img-rounded",
                        "src": comment.commentor.avatarUrl
                    });
                    var name = $("<div/>", {
                        "html": comment.commentor.name
                    });
                    var contentandtime = $("<div>");
                    var content = $("<span/>", {
                        "html": comment.comment.content
                    });
                    var time = $("<span/>", {
                        "class": "pull-right text-desc",
                        "html": moment(comment.comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    });
                    var hanghang = $("<br/>");

                    contentandtime.append(content); contentandtime.append(time);
                    mediaBody.append(name); mediaBody.append(contentandtime);
                    mediaLeft.append(touxiang);
                    media.append(mediaLeft); media.append(mediaBody);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 subcomments"
                    })
                    commentElement.append(media); commentElement.append(hanghang);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
// tag
function showSelectTag() {
    debugger;
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
