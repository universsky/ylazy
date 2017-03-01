/**
 * Created by jack on 2017/2/24.
 */

function initYlazyReport() {
    //http://localhost:9050/harviewer/index.htm?testTime=2017-02-24%2023%3A21%3A29&testUrl=https://universsky.github.io/&path=http://localhost:9050/result/4
    var url = location.href;

    var pathUrl = url.substring(url.indexOf('path=') + 'path='.length);
    var testTime = url.substring(url.indexOf('testTime=') + 'testTime='.length, url.indexOf('&testUrl='));
    testTime = decodeURIComponent(testTime);
    var testUrl = url.substring(url.indexOf('testUrl=') + 'testUrl='.length, url.indexOf('&path='));

    setReportSummaryHtml(testUrl, testTime);
    setReportDetailHtml(pathUrl);
}


function setReportSummaryHtml(testUrl, testTime) {
    var reportSummaryHtml = "";
    reportSummaryHtml += '<h3>性能测试报告:' + '<a href="' + testUrl + '">' + testUrl + '</a>' + '</h3>';
    reportSummaryHtml += '<table style="font-size: 11px; color: rgba(10,10,10,0.47)">'
    // reportSummaryHtml += '<tr><td>测试url:</td><td>' + testUrl + '</td></tr>';
    reportSummaryHtml += '<tr><td>提测时间:</td><td>' + testTime + '</td></tr>';
    $('#reportSummary').html(reportSummaryHtml);
}

function setReportDetailHtml(pathUrl) {
    $.ajax({
        url: pathUrl,
        type: 'GET',
        success: function (res) {
            var resJSON = JSON.parse(res);
            var entries = resJSON.log.entries;

            var reportDetailHtml = "";
            //请求总数
            var requests = 0;
            //页面加载时间
            var onLoadTime = 0;
            //资源总size
            var totalContentSize = 0;
            //资源总wait时间
            var totalWaitTime = 0;
            //资源总receive时间
            var totalReceiveTime = 0;
            var length = entries.length;
            requests = length;
            //页面加载时间
            onLoadTime = resJSON.log.pages[0].pageTimings.onLoad;
            for (var i = 0; i < length; i++) {
                totalContentSize += entries[i].response.content.size;
            }

            for (var i = 0; i < length; i++) {
                totalWaitTime += entries[i].timings.wait;
            }

            for (var i = 0; i < length; i++) {
                totalReceiveTime += entries[i].timings.receive;
            }

            reportDetailHtml += '</table>';
            reportDetailHtml += '<table class="table table-bordered"><thead><th>请求总数(个）</th><th>页面加载时间(ms)</th><th>资源总Size(B)</th><th>资源总wait(ms)</th><th>资源总receive(ms)</th></thead>' +
                '<tbody><tr>' +
                '<td>' + requests + '</td>' +
                '<td>' + onLoadTime + '</td>' +
                '<td>' + totalContentSize + '</td>' +
                '<td>' + totalWaitTime + '</td>' +
                '<td>' + totalReceiveTime + '</td>' +
                '</tr></tbody></table>';

            $('#reportDetail').html(reportDetailHtml);

        },
        error: function () {

        }
    });
}

$(function () {
    initYlazyReport();
})


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}



