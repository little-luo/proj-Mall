/**
 * 
 */

$(document).ready(function(){
	$(".container form").on("submit",function(e){
		e.preventDefault();
		$("span.error").remove();
		
		let email_val = $(".email_block input").val();
		if(email_val == ""){
			$(".email_block label").append('<span class="error">email不能空白</span>');
		}
		//console.log(email_val);
		let password_val = $(".password_block input").val();
		const reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
		if(password_val == ""){
			$(".password_block label").append('<span class="error">密碼不能空白</span>');
		}else{
			if(!reg.test(password_val)){
				$(".password_block label").append('<span class="error">密碼必須少8位數，且至少有任一大小字母與數字</span>');
			}
		}
		//console.log(password_val);
		let gender_val = $('.gender_block input:checked').val();
		//console.log(gender_val);
		let full_name_val = $(".fullname_block input").val();
		if(full_name_val == ""){
			$(".fullname_block label").append('<span class="error">姓名不能空白</span>');
		}
		//console.log(full_name_val);
		let county_val = $(".my_city_selector select:first-child").val();
		if(county_val == ""){
			$("form > div:last-child label").append('<span class="error">縣市不能空白</span>');
		}
		//console.log(county_val);
		let district_val = $(".my_city_selector select:last-child").val();
		if(district_val == ""){
			$("form > div:last-child label").append('<span class="error">區域不能空白</span>');
		}
		//console.log(district_val);
		let address_val = $(".city_block input").val();
		if(address_val == ""){
			$("form > div:last-child label").append('<span class="error">地址不能空白</span>');	
		}
		//console.log(address_val);
		if($("span.error").length == 0){
			// 取消form表單的submit事件的綁定	
			$(this).unbind(); 
			$(this).submit();		
		}
	})
})
