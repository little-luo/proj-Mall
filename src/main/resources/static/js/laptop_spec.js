/**
 * 
 */

$("select").on("focus", function(){
    this.size = 5;
});

$("select").on("change", function(){
    this.blur(); //js 控制觸發blur事件
});

$("select").on("blur", function(){
    this.size = 1;
    this.blur();
});