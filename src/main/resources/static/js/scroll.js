

export default function scrollToTop(){
	window.scrollTo({
	  top:0,
	  behavior:'smooth',
	})
}

export const switchScrollBtn = () => {
	$(window).on("scroll",function(){
		//console.log(window.scrollY);
		if(window.scrollY > 50){
			$(".scroll").css("display","block");
		}else{
			$(".scroll").css("display","none");
		}
	})
}