/**
 * 
 */

let cart_list = JSON.parse(sessionStorage.getItem("cart_list"));

let item_list = cart_list.items;
$(document).ready(function(){
	//console.log(item_list);
	if(item_list.length == 0){
		success_function();
	}
	
	for(let i = 0; i < item_list.length; i++){

		let tbody_jquery_object = $("div.cart_container tbody");
		tbody_jquery_object.append("<tr></tr>");
		
		let tr_el = $("div.cart_container tbody tr").eq(i);
		
		let checkbox = "".concat('<td><input type="checkbox"></td>');
		tr_el.append(checkbox);
		
		let img = "".concat('<td><img src="',item_list[i].imageUrl.toString(),'">','</td>');
		tr_el.append(img);
		
		let name = "".concat('<td><span>',item_list[i].laptopName,'</span></td>');
		tr_el.append(name);
		
		let unit_price = "".concat('<td><span class="unit_price">',item_list[i].price.toLocaleString(),'</span></td>');
		tr_el.append(unit_price);
		
		let qty = "";
		qty += '<td>';
		qty += '<button class="minus">';
		qty += '<span class="material-symbols-outlined">remove</span>';
		qty += '</button>';
		qty += '<input type="text" class="qty" value="1">';
		qty += '<button class="plus">';
		qty += '<span class="material-symbols-outlined">add</span>';
		qty += '</button>';
		qty += '</td>';
		tr_el.append(qty);
		
		let total = "".concat('<td>',getSubTotal(i).toLocaleString(),'</td>');
		tr_el.append(total);
		
		let remove = "";
		remove += '<td>';
		remove += '<button class="remove_btn">';
		remove += '<span class="material-symbols-outlined">delete</span>';
		remove += '</button>';
		remove += '</td>';
		tr_el.append(remove);
		
	}
	updateTotal();
	$(".minus").on("click",function(){
		let qty = parseInt($(this).siblings("input").val());
		if(qty == 1){
			return;
		}else{
			qty -= 1;
			$(this).siblings("input").val(qty);
			changeTotal(this, qty);
			updateTotal();
		}
	})
	
	$(".plus").on("click",function(){
		let qty = parseInt($(this).siblings("input").val());
		
		if(qty >= 10){
			alert("數量不能超過10個");
		}else{
			qty += 1;
			$(this).siblings("input").val(qty);
			changeTotal(this, qty);
			updateTotal();
		}
	})
	
	$(document).on("click","button.remove_btn",function(){
		let checkbox = $(this).closest("tr").find("td").first().find('input[type="checkbox"]');
		if(checkbox.is(':checked')){
			if(confirm	("是否移除?")){
				let index = $("tbody tr").index($(this).closest("tr"));
				//console.log(index);
				$(this).closest("tr").fadeOut(50,function(){
					item_list.splice(index,1);
					let new_cart_list = {
						"items":item_list
					};
					sessionStorage.setItem("cart_list",JSON.stringify(new_cart_list));
					let myPromise = showCartImg(this);
					myPromise
						.then((res) => {
							success_function();
							updateTotal();
						})
						.catch((res) => {
							updateTotal();
						});

				});
				alert("移除成功");
			}
		}else{
			alert("請選取商品移除");
		}
	})
	$("#check_all").on("click",function(){
		//console.log($('tbody input[type="checkbox"]'));
		if($(this).is(':checked')){			
			$('tbody input[type="checkbox"]').prop("checked",true);
		}else{
			$('tbody input[type="checkbox"]').prop("checked",false);
		}
	})
	
	$('tbody input[type="checkbox"]').on("click",function(){
		let checkbox_qty = $('tbody input[type="checkbox"]').length;
		//console.log(checkbox_qty);
		let checked_qty = 0;
		$('tbody input[type="checkbox"]').each(function(){				
			if($(this).is(":checked")){
				checked_qty++;
				//console.log(checked_qty);
				if(checkbox_qty == checked_qty){
					$("#check_all").prop("checked",true);
				}
			}else{
				$("#check_all").prop("checked",false);
				return;
			}
		})
	})
	
	$("#remove_all_btn").on("click",function(){
		if(confirm("是否全部刪除?")){
			let new_cart_list = {
				"items":[]
			}
			sessionStorage.setItem("cart_list",JSON.stringify(new_cart_list));
			success_function();
			updateTotal();
		}
	})
	
	$(document).on("click","#go_to_homepage",function(){
		window.location.href = "/home";
	})
	
	$("#checkout_btn").on("click",function(){
		let item_list = JSON.parse(sessionStorage.getItem("cart_list")).items;
		/*
		console.log(item_list);
		console.log(JSON.stringify(item_list));
		*/
		$.ajax({
			traditional: true,
			contentType : "application/json;charset=utf-8",
			type : "post",
			url : "/isAuthenticate",
			data : JSON.stringify(item_list),
			success  : function(res){
				console.log(res);
				$("body").html(res);
			},
			error : function(res){
				console.log(res);
				$("body").html(res.responseText);
			}
		})
		//window.location.href="/isAuthenticate";
		
	})
})

function getSubTotal(i){
	/*
	console.log($("span.unit_price").eq(i).text());
	console.log($("input#qty").eq(i).val());
	console.log($("span.unit_price").eq(i).text() * $("input#qty").eq(i).val());
	*/
	
	let total = parseInt($("span.unit_price").eq(i).text().replace(/,/g, ""), 10) * $("input.qty").eq(i).val();
	return total;
}

function changeTotal(this_el, qty){
	let length = $(this_el).closest("tr").find("td").length;
	let subtotal_td = $(this_el).closest("tr").find("td").eq(length - 2);
	let unit_price = parseInt($(this_el).closest("tr").find("span.unit_price").text().replace(/,/g,""),10);
	let new_total = unit_price * qty;
	//console.log(new_total);
	subtotal_td.text(new_total.toLocaleString());
}

function showCartImg(this_el){
	return new Promise((resolve,reject) => {
		let tr_qty = $("tbody tr").length;
		if(tr_qty > 0){			
			//console.log($(this_el).closest("tr"));
			$(this_el).closest("tr").remove();
			tr_qty = tr_qty - 1;
			//console.log(tr_qty);
		}
		
		if(tr_qty == 0){
			resolve('購物車內無商品');
		}else{
			reject('購物車內有商品');
		}

	})
}
function success_function(){
	$("table").remove();
	let div = '<div class="no_item_block"></div>';
	let img = "".concat('<img src="','/images/other/cart.png','">');
	let button = "".concat('<button id="go_to_homepage">繼續瀏覽</button>');
	$(".cart_container").append(div)
	$(".no_item_block").append(img).append(button);
}

function updateTotal(){
	let sum = 0;
	$('tbody tr').each(function(){
		//console.log($(this).children().slice(-2).first());
		sub_total = parseInt($(this).children().slice(-2).first().text().replace(/,/g, ""), 10);
		sum = sum + sub_total;
	})
	sum = sum.toLocaleString();
	//console.log(sum);
	$(".total").get(0).textContent = '總共: $' + sum;
}

function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


