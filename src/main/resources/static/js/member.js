
$(document).ready(function(){
	$.ajax({	
		url:'/users/羅章峻',
		type:'get',
		success:function(res){
			console.log(res);
			$(".full_name").text(res.full_name);
			$(".gender").text(res.gender);
			$(".county").text(res.county);
			$(".district").text(res.district);
			$(".address").text(res.address);
			$(".profile").attr("src","data:image/*;base64," + res.img_base64)
			$("#user_id").attr("value",res.id);
		},
		error:function(res){
			console.log(res);
		}
	}
	)
	$(".edit_btn").on("click",function(){
		$(".light_box").css("display","block");
		$("#full_name").val($("span.full_name").text());
		$("#gender").val($("span.gender").text());
		$("#address").val($("span.county").text() + $("span.district").text() + $("span.address").text());
	})
	
	$(".preview").on("click",function(e){
		e.originalEvent.stopPropagation();
		$('input[type="file"').click();
	})
	
	$('label[for="profile"]').on("click",function(e){
		e.originalEvent.stopPropagation();
	})
	
	$(".red_cross").on("click",function(){
		$(".light_box").css("display","none");
		$(".preview").empty()
					 .append('<label for="profile">上傳您的大頭貼</label>');
		 $('label[for="profile"]').on("click",function(e){
		 	e.originalEvent.stopPropagation();
		 })
	})
	let img;
	$("#profile").on("change",function(e){
		console.log(e);
		let dataTransfer = new DataTransfer();
		let file;
		//console.log(img);
		if(img !== undefined){
			file = img;
			// 實作檔案拖曳 成功後 改變傳遞給 後端的圖片資料
			dataTransfer.items.add(file);
			$("#profile").get(0).files = dataTransfer.files;
			$(".light_box form button").click();			
			//console.log($("#profile").get(0).files);
			//console.log(dataTransfer.files);
		}else{			
			file = e.target.files[0];
		}
		let reader = new FileReader();
		reader.addEventListener("load",function(e){
			let base64 = e.target.result;
			//console.log(base64);
			$(".preview").empty()
						 .append('<img src="' + base64 + '">');
		})
		reader.readAsDataURL(file);
	})
	
	$(".profile_block").on("dragover",function(e){
		e.originalEvent.preventDefault();
	})

	$(".profile_block").on("drop",function(e){
		e.originalEvent.preventDefault();
		img = e.originalEvent.dataTransfer.files[0];
		//console.log(img);
		
		let reader = new FileReader();
		
		reader.addEventListener("load",function(e){
			let base64 = e.target.result;
			let new_img = new Image();
			new_img.src = base64;
			$(".profile_block").empty()
							   .css("border","5px dotted black")
							   .css("background-color","#ccc")
							   .append(new_img);
			$(".profile_block img").css("width","100%")
							 .css("height","100%")
							 .css("object-fit","contain");
			$('.light_box form input[type="file"]').change();	
		})
		reader.readAsDataURL(img);
	})
	
	$(".profile_block").on("dragenter",function(e){
		$(this).css("border","5px dotted green")
			   .css("background-color","rgba(0,0,0,0.3)");   
	})
	
	$(".profile_block").on("dragleave",function(){
		$(this).css("border","5px dotted black")
			   .css("background-color","#ccc");
	})
})