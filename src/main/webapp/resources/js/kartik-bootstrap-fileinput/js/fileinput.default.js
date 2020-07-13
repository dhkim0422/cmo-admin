var previewFileIconSettings = {
    'doc': '<i class="fas fa-file-word text-primary"></i>',
    'xls': '<i class="fas fa-file-excel text-success"></i>',
    'ppt': '<i class="fas fa-file-powerpoint text-danger"></i>',
    'pdf': '<i class="fas fa-file-pdf text-danger"></i>',
    'zip': '<i class="fas fa-file-archive text-muted"></i>',
    'htm': '<i class="fas fa-file-code text-info"></i>',
    'txt': '<i class="fas fa-file-alt text-info"></i>',
    'mov': '<i class="fas fa-file-video text-warning"></i>',
    'mp3': '<i class="fas fa-file-audio text-warning"></i>',
    'jpg': '<i class="fas fa-file-image text-danger"></i>',
    'gif': '<i class="fas fa-file-image text-muted"></i>',
    'png': '<i class="fas fa-file-image text-primary"></i>'
};
var previewFileExtSettings = { // configure the logic for determining icon file extensions
    'doc': function(ext) {
        return ext.match(/(doc|docx)$/i);
    },
    'xls': function(ext) {
        return ext.match(/(xls|xlsx)$/i);
    },
    'ppt': function(ext) {
        return ext.match(/(ppt|pptx)$/i);
    },
    'zip': function(ext) {
        return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
    },
    'htm': function(ext) {
        return ext.match(/(htm|html)$/i);
    },
    'txt': function(ext) {
        return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
    },
    'mov': function(ext) {
        return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
    },
    'mp3': function(ext) {
        return ext.match(/(mp3|wav)$/i);
    }
};
$.fn.fileinput = $.fn.fileinput || {};
$.fn.fileinput.defaults = $.fn.fileinput.defaults || {};
$.fn.fileinput.defaults.showClose = false;
$.fn.fileinput.defaults.showConsoleLogs = false;
$.fn.fileinput.defaults.initialPreviewAsData = true;
$.fn.fileinput.defaults.overwriteInitial = false;
$.fn.fileinput.defaults.previewFileType = 'any';
$.fn.fileinput.defaults.preferIconicPreview = true;
$.fn.fileinput.defaults.previewFileIconSettings = previewFileIconSettings;
$.fn.fileinput.defaults.previewFileExtSettings = previewFileExtSettings;
$.fn.fileinput.defaults.browseClass = 'btn waves-effect waves-light btn-outline-primary font-weight-bold';
$.fn.fileinput.defaults.removeClass = 'btn waves-effect waves-light btn-outline-primary font-weight-bold';
$.fn.fileinput.defaults.cancelClass = 'btn waves-effect waves-light btn-outline-primary font-weight-bold';
$.fn.fileinput.defaults.pauseClass = 'btn waves-effect waves-light btn-outline-primary font-weight-bold';
$.fn.fileinput.defaults.uploadClass = 'btn waves-effect waves-light btn-outline-primary font-weight-bold';
$.fn.fileinput.defaults.uploadAsync = false;
$.fn.fileinput.defaults.uploadParamNames = $.fn.fileinput.defaults.uploadParamNames || {};
$.fn.fileinput.defaults.uploadParamNames.fileId = $.fn.fileinput.defaults.uploadParamNames.fileId || 'fileIdNm';

function fnGetExtIcon(ext){
	var out;
	if (previewFileIconSettings) {
        out = previewFileIconSettings[ext] || previewFileIconSettings[ext.toLowerCase()] || null;
    }
	$.each(previewFileExtSettings, function (key, func) {
        if (previewFileIconSettings[key] && func(ext)) {
            out = previewFileIconSettings[key];
            return;
        }
    });
	return out || '<i class="fas fa-file"></i>';
}

function fnGetFileSize(size){
	if (size === 0) {
        return '0.00 B';
    } else {
        var i = Math.floor(Math.log(size) / Math.log(1024));
        var sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + sizes[i];
    }
}

function fnGetDownloadBtn(row, url){
	url = url+'?fileId='+row.fileId+"&fileSeq="+row.fileSeq;
	var downloadBtn = '<a class="kv-file-download btn btn-sm btn-kv btn-default btn-outline-secondary" title="파일 다운로드" href="'+url+'" target="_blank"><i class="fas fa-download"></i></a>'
	return downloadBtn;
}
