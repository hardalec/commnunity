function post() {
    var id = $("#qustion_id").val();
    var qcontent = $("#content").val();
    if(!content){
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type:"POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": id,
            "type": 1,
            "content": qcontent,
        }),
       dataType: "json",
        success: function () {
            window.location.reload();
            $("#comment_section").hide();
        },
    });
}
