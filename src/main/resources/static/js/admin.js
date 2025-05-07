
$(document).ready(function(){
	
	$.ajax({
		url:'/getPageTotal',
		type:'get',
		success:function(res){
			console.log(res);
			let pageItem = $("ul.pagination li.page-item").slice(1,4);
			let pageItemLength = pageItem.length;
//			console.log(pageItem.slice(1,4));
			if(res < pageItemLength){
				for(i = 0; i < pageItemLength - res; i++){
					pageItem.slice(-(pageItemLength - res)).remove();
				}
			}
			if(res >= pageItemLength){
				pageItem.eq(3).on("click",function(){
					let lastPage = $(this).text();
					pageItem.eq(1).attr("href","/admin?offset=" + (lastPage - 1) * 10).text(lastPage);
					pageItem.eq(2).attr("href","/admin?offset=" + (lastPage) * 10).text(lastPage + 1);
					pageItem.eq(3).attr("href","/admin?offset=" + (lastPage + 1) * 10).text(lastPage + 2);
				})
			}
		}
	})
	
	addModal();
		
	const modal = new bootstrap.Modal(document.querySelector(".modal"));
	$(".top_block").on("click",".btn-primary",function(){
		$(".modal").attr("id","new");
		modal.show();
	})


	$(".middle_block").on("click",".btn-success",function(){
		let prodId = $(this).parents("tr").find("td").eq(0).text();
		let prodName = $(this).parents("tr").find("td").eq(2).text();
		let prodBrand = $(this).parents("tr").find("td").eq(3).text();
		let prodPrice = $(this).parents("tr").find("td").eq(4).text();
		let prodSize = $(this).parents("tr").find("td").eq(8).text();
		
	//	console.log($(this).parents("tr").find("td").eq(0).text());
		$("#prodPrice").val(prodPrice);
		$("#prodId").val(prodId);
		$("#prodName").val(prodName);
		$("#prodBrand").val(prodBrand);
		$("#prodSize").val(prodSize);
		
		$(".modal").attr("id","edit");
		modal.show();
	})
	
	$(".btn-close").on("click",function(){
		$("#prodPrice,#prodId,#prodName,#prodBrand,#prodBrand, #prodSize").val("");
		modal.hide();
	})
	
	$('.modal-footer button[type="submit"]').on("click",function(e){
		e.originalEvent.preventDefault();
		
		let id = $(".modal").attr("id");
		
		if(id == 'edit'){
			let msg = checkFormDataValue();
			if(msg == '成功提交表單!'){				
				let prodId = $("#prodId").val();
				$(".modal form").attr("action","".concat("/updateProduct","/" +　prodId))
								.attr("method","post");
				$(".modal form").submit();
			}else{
				alert(msg);
			}
		}
		
		if(id == 'new'){
			let msg = checkFormDataValue();
			
			if(msg == "成功提交表單!"){
				$(".modal form").attr("action","".concat("/createProduct"))
								.attr("method","post");
				$(".modal form").submit();
			}else{
				alert(msg);
			}			
		}
	})
	
	$("tbody").on("click",".btn-danger",function(){
		if(confirm('確認刪除?')){
			let id = $(this).parents("tr").find("td:first-child").text();
			console.log(id);
			
			$(this).parents("tr").fadeOut(1000).remove();
			$.ajax({
				url:"/deleteProduct/" + id,
				type:"get",
				success:function(res){
					alert(res);
				},
				error:function(err){
					alert(err);
				}
				
			})
		}
	})
	const option = {
		root:null,
		rootMargin:"100px 0px 0px 0px",
		threshold:[0],
	}
	
	const observer = new IntersectionObserver(entries => {
		entries.forEach(image => {
			if(image.isIntersecting){
				//console.log(image.target.getAttribute('data-src'));
				// 將data-src 的 屬性 指定給 src屬性
				image.target.setAttribute('src',image.target.getAttribute('data-src'));
				observer.unobserve(image.target);
			}
		})
	},option);
	
	const all_img = $("tbody img");
	all_img.each(function(index,item){
		observer.observe(item);
	})
})

function checkFormDataValue(){
	let msg = "";
	$(".modal form input").each(function(index,item){
		if(item.value == ""){
			let label_content = $(this).prev("label").text();
			msg += label_content + "不能空白!\n";
		} 
	})
	if(msg == ""){
		msg = '成功提交表單!'
	}
	return msg;
}

function addModal(){
	if($(".modal").length == 0){
		let html = `
		<div class="modal fade" tabindex="-1" id="">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<form action="#" method="#" enctype="multipart/form-data">
							<div class="mb-3">
							  <label for="prodId" class="form-label">產品編號</label>
							  <input type="text" class="form-control" id="prodId" value="" name="prodId">
							</div>
							<div class="mb-3">
								<label for="prodName" class="form-label">產品名稱</label>
								<input type="text" class="form-control" id="prodName" value="" name="prodName">
							</div>
							<div class="mb-3">
								<label for="prodBrand" class="form-label">產品品牌</label>
								<input type="text" class="form-control" id="prodBrand" value="" name="prodBrand">
							</div>
							<div class="mb-3">
								<label for="prodPrice" class="form-label">產品價格</label>
								<input type="text" class="form-control" id="prodPrice" value="" name="prodPrice">
							</div>
							<div class="mb-3">
								<label for="prodOS" class="form-label">作業系統</label>
								<select class="form-select" id="prodOS" name="prodOS">
									<option selected value="NONE">請選擇</option>
									<option value="WINDOWS 10">win10</option>
									<option value="WINDOWS 11">win11</option>
								</select>
							</div>
							<div class="mb-3">
								<label for="prodSize" class="form-label">產品尺寸</label>
								<input type="text" class="form-control" id="prodSize" value="" name="prodSize">
							</div>
							<div class="mb-3">
								<label for="prodImg" class="form-label">產品圖片</label>
								<input type="file" class="form-control" id="prodImg" name="prodImg">
								<div class="preview"></div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">送出</button>
					</div>
				</div>
			</div>
		</div>
		`;
		$("nav.page").after(html);
	}
}