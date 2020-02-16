$(function(){
    //add task
    $('#add-task').click(function(){
        var data = $('#list-form form').serialize();
        $.ajax({
            method: "POST",
            url: "/addtask",
            data: data
        });
        window.location.reload();
    });

    //del task
    $(document).on('click', '.del-link',function(){
        var id = $(this).parent().attr("id");
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', 'http://localhost:8080/list/' + id, false);
        xhr.send();
        /*$.ajax({
            //method: "GET",
            url: "/list/" + id,
            type : "delete"
        });*/
        //window.location.reload();
    });

    //mark as completed
    $(document).on('click', '.compl-link',function(){
        var id = $(this).parent().attr("id");
        $.ajax({
            method: "POST",
            url: "/listCompl/" + id
        });
        window.location.reload();
    });
});