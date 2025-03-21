/**
 * 
 */

$("form").on("submit",function(e){
	//console.log(e);
	e.preventDefault();
	let pw = $(this).parent().find("input");
	
	let pw1 = pw.eq(0);
	let pw2 = pw.eq(1);
	
	$("div.form_block form p").remove();
	
	if(pw1.val() == ""){
		$(this).prepend('<p style="color:red; text-align:center; position:relative; top:-10px;">請輸入新密碼!</p>');
		return;
	}
	if(pw2.val() == ""){
		$(this).prepend('<p style="color:red; text-align:center; position:relative; top:-10px;">請再輸入一次新密碼!</p>');
		return;
	}
	const reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
	console.log(pw1.val());
	if(reg.test(pw1.val())){		
		// 驗證密碼是否一致
		if(pw1.val() == pw2.val()){
			$(this).get(0).submit();
		}else{
			$(this).prepend('<p style="color:red; text-align:center; position:relative; top:-10px;">您輸入的密碼不一致，請再重新輸入!</p>');
		}
	}else{
		$(this).prepend('<p style="color:red; text-align:center; position:relative; top:-10px;">密碼必須少8位數，且至少有任一大小字母與數字</p>')
	}
})


