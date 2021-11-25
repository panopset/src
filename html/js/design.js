function init() {
 //var c = document.getElementById("cvsbgd");
 //var ctx = c.getContext("2d");
 var ctx = $("#cvsbgd").get(0).getContext("2d");
 ctx.fillStyle = "#6f6";
 ctx.fillRect(0,0,10,10);
 $("#cvsbgd").mousedown(handleMouseDown);
 $("#cvsbgd").mousemove(handleMouseMove);
 $("#cvsbgd").mouseup(handleMouseUp);
 $("#cvsbgd").mouseout(handleMouseOut);
 initPage();
}

function handleMouseDown(e) {
}

function handleMouseMove(e) {
}

function handleMouseUp(e) {
}

function handleMouseOut(e) {
}


