/**
 * 
 */

//console.log("ttt");
//console.log(jQuery);
/*
// 核取方塊 寫法一

$(document).on("click","div.brand_option input",function(e){
	let this_el = this;
	let brand_option = $("div.brand_option");
	check_only_one(brand_option,this_el);
})


$(document).on("click","div.os_option input",function(e){
	let this_el = this;
	let os_option = $("div.os_option");
	check_only_one(os_option,this_el);
})

$(document).on("click","div.size_option input",function(){
	let this_el = this;
	let size_option = $("div.size_option");
	check_only_one(size_option,this_el);
})

$(document).on("click","div.budget_option input",function(){
	let this_el = this;
	let budget_option = $("div.budget_option");
	check_only_one(budget_option,this_el);
})

function check_only_one(option,this_el){
	//console.log(brand_option);
	option.each(function(index,item){
		//console.log(item);
		let check_el = item.querySelector("input");
		if(this_el == check_el){
			check_el.checked = true;
		}else{			
			check_el.checked = false;
		}
	})
}
*/
// 核取方塊 寫法二
$(document).on("click","input[type='checkbox']",function(e){
	let this_el = this;
	let other_option = $(this).parent().siblings();
	check_only_one(other_option,this_el);
	//console.log(other_option);
})

function check_only_one(other_option,this_el){
	this_el.checked = true;
	
	other_option.each(function(index,item){
		item.querySelector("input").checked = false;
	})
}

// 送出表單
$("div.info a").on("click",function(e){
	e.preventDefault();
	$(this).closest("form").get(0).submit();
})

//購物車
let item = null;
let item_list = [];
let span_el = null;
$(".card button").on("click",function(e){
 	let laptopId = $(this).parent().find('input[type="hidden"]').val();
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
			let cart_list = {
				"items" : item_list
			};
			// JSON格式的物件字串化存放到sessionStorage;
			sessionStorage.setItem("cart_list",JSON.stringify(cart_list));
			//將JSON格式的字串轉換成JSON物件
			//console.log(JSON.parse(sessionStorage.getItem("cart_list")));
			if(span_el == null){
				span_el = "".concat('<span class="item_qty">',item_list.length.toString(),"</span>");
				$("#cart").parent().append(span_el);
			}else{
				$("span.item_qty").text(item_list.length.toString());				
			}
	    },
	    error: function (xhr, status, error) {
	        console.error("錯誤發生：", error);
	        console.error("狀態碼：", xhr.status);
	        console.error("回應文字：", xhr.responseText); // 查看伺服器實際回傳的內容
	    }
	});
})

$(".cart_block #cart").on("click",function(e){
	$(this).parent().get(0).submit();
})

