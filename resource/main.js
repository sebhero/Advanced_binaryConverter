/*
hanterar problemet med att menyn inte stänger sig själv automatiskt eftert klickat ett val
*/
$(function(){ 
     var navMain = $("#nav-main");

     navMain.on("click", "a", null, function () {
         navMain.collapse('hide');
     });
 });