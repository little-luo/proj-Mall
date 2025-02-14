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
