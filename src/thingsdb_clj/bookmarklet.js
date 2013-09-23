javascript:function se(d) {
    var sel = d.selection ? d.selection.createRange().text : d.getSelection();
    if(sel) {
        return sel.toString().replace(/^\s+/,'').replace(/\s$/,'');
    }
}
function add(o) {
    var d=document;
    var s=d.createElement("script");
    var e=encodeURIComponent;
    d.type="text/javascript";
    var u="http://localhost:3000/things/add?title="+e(o.title)+"&url="+e(o.url)+"&tags="+e(o.tags)+"&t="+(new Date().getTime());
    if(o.text) u+="&text="+e(o.text);
    /*alert(u);*/
    s.src=u;
    d.body.appendChild(s);
}
x = {};
s = se(document);
if(s) {x.text = s;}
x.url = document.location.href;
t = document.title;
if(!t) {t = prompt('Title','');}
x.title = t;
x.tags = prompt('Tags','');
add(x);
void(0);