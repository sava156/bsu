var login;
var message;
var index=0;
var $mb=$("<div class='message1'><p>"+login+"</p><p>"+message+"</p><button class=\"delete1\">Delete</button><button class=\"change\">Change</button></div>");
var $mess;
var $buttons;
var text;
var badLogin=true;
var nickname;
$(document).ready(function(){
	$('.ok').click(function(){
		if(login!=""){
			login=$("input[name=login]").val();
			$(".messageBox").empty();
		}
		if(login!=""){
			$("input[name=login1]").val($("input[name=login]").val());
			$("input[name=login]").val('');
			badLogin=false;
			nickname=login;
			if(localStorage.getItem(login+''))
				$('.messageBox').html(localStorage.getItem(login+''));
		}else{
			badLogin=true;
			alert("Wrong login!");
		}
	})
	$('.send').click(function(){
		if(!badLogin){
			message=$('.area').val();
			if(message!=""){
				$(".messageBox").append($("<div class='message1'><p>"+login+"</p><p class=\"message2\">"+message+"</p><button class=\"delete1\">Delete</button><button class=\"change\">Change</button><textarea class='area1' rows='1' cols='40' placeholder='New Message...'></textarea></div>"));
				localStorage.setItem(login+'', $(".messageBox").html());
			}else{
				alert("Input not empty message!");
			}
		}else{
			alert("Input your username!");
		}
	})
	$(".messageBox").on('click','.delete1',function(){
		$(this).prev().remove();
		$(this).next().remove();
		$(this).next().remove();
		$(this).prev().append($("<p class='message2'>-----DELETED------</p>"));
		$(this).remove();
	})
	$(".messageBox").on('click','.change',function(){
		text=$(this).next().val();
		if(text!=""){
			$(this).prev().prev().remove();
			$(this).prev().prev().append($("<p class='message2'>"+text+"</p>"));
			$(".area1").val("");
		}else{
			alert("Input not empty message!");
		}
	})
});