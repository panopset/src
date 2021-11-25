
var static_host = "./";
var scz="screen";
var path = window.location.pathname;
var pgnm = '';

function initPage() {
 pgnm = getRawPathName(path);
 $('#pagetitle').text(pgnm);
 if (pgnm == '' || pgnm == 'home') {
  pgnm = 'index';
 }
 var elems = document.getElementsByClassName(pgnm);
 for (var i = 0; i < elems.length; i++) {
  if (scz == "screen") {
   elems[i].style.setProperty("background-color", "#000");
   elems[i].style.setProperty("color", "#ff0");
  } else {
   elems[i].style.setProperty("background-color", "#fff");
   elems[i].style.setProperty("color", "#000");
  }
 }
}

function toggleScreens() {
 $('#ptoggle').text(scz);
 if (scz == "screen") {
  setPrinter();
 } else {
  setScreen();
 }
 initPage();
}

function setScreen() {
 scz = "screen"
 document.documentElement.style.setProperty('--bgd', '#000');
 document.documentElement.style.setProperty('--fgd', '#afa');
 document.documentElement.style.setProperty('--hdr', '#3f3');
 document.documentElement.style.setProperty('--mbg', '#040');
 document.documentElement.style.setProperty('--mfg', '#ccf');
 document.documentElement.style.setProperty('--panbgtxt', '#333');
 document.documentElement.style.setProperty('--panfgtxt', '#dff');
 document.documentElement.style.setProperty('--panpage', '#ff0');
 document.documentElement.style.setProperty('--border', '#000');
 document.documentElement.style.setProperty('--panborder', '#44c');
 document.documentElement.style.setProperty('--panhltfg', '#6f6');
 document.documentElement.style.setProperty('--panhltbg', '#333');
}

function setPrinter() {
 scz = "printer"
 document.documentElement.style.setProperty('--bgd', '#fff');
 document.documentElement.style.setProperty('--fgd', '#000');
 document.documentElement.style.setProperty('--hdr', '#000');
 document.documentElement.style.setProperty('--mbg', '#ddd');
 document.documentElement.style.setProperty('--mfg', '#000');
 document.documentElement.style.setProperty('--panbgtxt', '#fff');
 document.documentElement.style.setProperty('--panfgtxt', '#000');
 document.documentElement.style.setProperty('--panpage', '#000');
 document.documentElement.style.setProperty('--border', '#fff');
 document.documentElement.style.setProperty('--panborder', '#ccc');
 document.documentElement.style.setProperty('--panhltfg', '#000');
 document.documentElement.style.setProperty('--panhltbg', '#fff');
}

function getRawPathName(path) {
 return path.replace('/', '').replace('.html', '');
}

function formatErrors(j, e) {
  return 'Code: ' + j.status + ' Response: ' + j.responseText + ' Status: ' + j.statusText;
}
