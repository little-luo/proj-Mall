/**
 * 
 */

$("select").on("focus", function(){
    this.size = 5;
});
let reg = /\B(?=(?:\d{3})+(?!\d))/g;
let unit_price = $(".price span").text();
$(".price span").text(unit_price.replace(reg,","));
$("select").on("change", function(){
	let amount = $(this).val();
	let new_price =  (unit_price * amount).toString().replace(reg,",");
	$(".price span").text(new_price);
    this.blur(); //js 控制觸發blur事件
});

$("select").on("blur", function(){
    this.size = 1;
});
$(document).ready(function(){
	let cart_list = JSON.parse(sessionStorage.getItem("cart_list"));
	let item_list;
	if(cart_list == null){		
		item_list = [];
	}else{
		item_list = cart_list.items;
		$("div.cart_block").append("".concat('<span class="item_qty" style="line-height:0px;">',item_list.length.toString(),'</span>'));
	}
	$(".bottom_block form").on("click","button",function(e){
		e.preventDefault();
		
		let laptopId = $(".id").text();
		if($(this).get(0) == $("#add_to_cart").get(0)){
			$.ajax({
				url: '/laptops/' + laptopId,
			    type: 'GET',
			    contentType: 'application/json',
			    success: function (response) {
			        //console.log("回應成功：", response);
					item = response;
					if(item_list.length == 0){
						alert("此商品已加入至購物車");	
						item_list.push(item);
					}else{
						for(let i = 0; i < item_list.length; i++){
							if(item_list[i].laptopId == item.laptopId){
								alert("商品已存在於購物車內");
								break;
							}else{
								if(i == item_list.length - 1){
									alert("此商品已加入至購物車");							
									item_list.push(item);
									break;
								}else{
									continue;
								}
							}
						}
					}
					cart_list = {
						"items":item_list
					};
					sessionStorage.setItem("cart_list",JSON.stringify(cart_list));
					if($(".cart_block > span").length == 0){					
						$("div.cart_block").append("".concat('<span class="item_qty" style="line-height:0px;">',item_list.length.toString(),'</span>'));			
					}else{
						$(".cart_block > span").text(item_list.length.toString());
					}	
				},
				error: function(error){
					alert(error);
				}
			})	
		}
		if($(this).get(0) == $("#buy_btn").get(0)){
			$.ajax({
				type:"post",
				url:"/isAuthenticate",
				data:JSON.stringify(item_list),
				contentType:"application/json;charset=utf-8",
				success:function(res){
					$("body").html(res);
				},
				error:function(err){
					$("body").html(err.responseText);
				}
			})
		}
		
	})
})
