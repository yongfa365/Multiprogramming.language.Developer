//js操作文件应该不常用，或者在Node.js里才会用到，所以这里就不写了。


//ActiveXObject是微软私有的，各处不推荐用，但像Emeditor的macros可以用，所以也有其应用场景啦，https://developer.mozilla.org/zh-CN/docs/Archive/Web/JavaScript/Microsoft_Extensions/ActiveXObject
var fso = new ActiveXObject("Scripting.FileSystemObject");
var files = [];
var filesHasModified = [];
function showFolderFileList(folderspec) {
    var f = fso.GetFolder(folderspec);
    if (/\\(doc|lib|\.git|\.idea|\.vs|dll)$/gi.test(folderspec)) {
        return;
    }

    // recurse subfolders
    var subfolders = new Enumerator(f.SubFolders);
    for (; !subfolders.atEnd(); subfolders.moveNext()) {
        showFolderFileList((subfolders.item()).path);
    }

    // display all file path names.
    var fc = new Enumerator(f.files);
    for (; !fc.atEnd(); fc.moveNext()) {
        var file = fc.item();
        if (/\.(jmx|config|cs|Config|tt|ttinclude|txt|yml|java|bak|xml|cshtml|sh|yaml|js|json|md|properties)$/gi.test(file)) {
            files.push(file);
        }

    }

}

showFolderFileList('D:\\Sources.git2');

alert("将分析：" + files.length + "个文件")