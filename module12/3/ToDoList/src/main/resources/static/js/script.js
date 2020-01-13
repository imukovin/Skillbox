$(function(){
    const appendTask = function(data) {
        if (data.status == true) {
            var task = '<b>' + data.date + ':</b> &nbsp;&nbsp;&nbsp;&nbsp;' + data.tasktext +
                    ' &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="del-link">Удалить</a>';
            $('#task-list-completed').append('<div id="'+data.id + '">' + task + "</div>");
        } else {
            if (new Date() > new Date(data.date)) {
                var task = '<b style="color:red">' + data.date + '(задание не выполнено):</b> &nbsp;&nbsp;&nbsp;&nbsp;' + data.tasktext +
                        ' &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="del-link">Удалить</a>  <a href="#" class="compl-link">Выполнено</a>';
                $('#task-list').append('<div id="'+data.id + '">' + task + "</div>");
            } else {
                var task = '<b>' + data.date + ':</b> &nbsp;&nbsp;&nbsp;&nbsp;' + data.tasktext +
                        ' &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="del-link">Удалить</a>  <a href="#" class="compl-link">Выполнено</a>';
                $('#task-list').append('<div id="'+data.id + '">' + task + "</div>");
            }
        }
    };

    // get new task list
    $.get('/list/', function(response){
        for (i in response) {
            appendTask(response[i]);
        }
    });

    //add task
    $('#add-task').click(function(){
        var data = $('#list-form form').serialize();
        $.ajax({
            method: "POST",
            url: "/list/",
            data: data
        });
        window.location.reload();
    });

    //del task
    $(document).on('click', '.del-link',function(){
        var id = $(this).parent().attr("id");
        $.ajax({
            method: "POST",
            url: "/list/" + id
        });
        window.location.reload();
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