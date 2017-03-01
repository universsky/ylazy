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

function getHost(url) {
    var host = "null";
    var regex = /.*\:\/\/([^\/]*).*/;
    var match = url.match(regex);
    if(typeof match != "undefined"&& null != match){
        host = match[1];
    }
    return host;
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


            // 资源类型
            var contentTypes=[];
            var htmlType = {type : "html",size : 0,num : 0};
            var imageType = {type : "image",size : 0,num : 0};
            var jsType = {type : "js",size : 0,num : 0};
            var otherType={type : "other ",size : 0,num : 0};

            // dns类型
            var domains = [];
            // 图片
            var imgs = [];

            for(var i = 0;i < length ;i++){
                //响应资源大小
                var responseSize = entries[i].response.bodySize;

                //响应头信息
                var headers = entries[i].response.headers;

                //请求url
                var reqUrl = entries[i].request.url;

                //返回码
                var returnCode = entries[i].response.status;

                //请求总时间
                var resonseTime = entries[i].time;

                //获取压缩类型
                var compress="";
                for(var j = 0;j < headers.length ;j++){
                    if(headers[j].name.indexOf("Content-Encoding") > -1){
                        compress = headers[j].value;
                        break;
                    }
                }

                for(var j = 0;j < headers.length ;j++){
                    var header = headers[j];
                    if(header.name.indexOf("Content-Type") > -1){
                        if(header.value.indexOf("html") >- 1){
                            htmlType.num++;
                            htmlType.size += responseSize;

                        }else if(header.value.indexOf("javascript") >- 1){
                            jsType.num++;
                            jsType.size += responseSize;

                        }else if(header.value.indexOf("image") >- 1){
                            imageType.num++;
                            imageType.size += responseSize;
                            //imgs
                            imgs[imgs.length]={url : reqUrl,size : responseSize,compress : compress,
                                cache : "",time : resonseTime, returnCode:returnCode,type: header.value};


                        }else{
                            otherType.num++;
                            otherType.size += responseSize;

                        }
                        break;
                    }

                }
                //domain
                var reqDomain = getHost(reqUrl);
                var domainExists = false;
                if(domains.length > 0){
                    for(var k = 0 ; k< domains.length;k++){
                        if(domains[k].name.indexOf(reqDomain) > -1 ){
                            domains[k].size += responseSize;
                            domains[k].num++;
                            domainExists = true;
                            break;
                        }
                    }
                }
                if(domains.length==0 || !domainExists){
                    domains[domains.length] = {name : reqDomain,size : responseSize,num : 1};
                }
            }

            contentTypes[0] = htmlType;
            contentTypes[1] = imageType;
            contentTypes[2] = jsType;
            contentTypes[3] = otherType;

            //资源类型表格展示
            reportDetailHtml += '<table class="table table-bordered"><thead><th>资源类型</th><th>大小(KB)</th><th>数量(个)</th></thead>' +
                '<tbody>';
            for(var i =0 ; i<contentTypes.length;i++ ){
                reportDetailHtml  += "<tr><td>" + contentTypes[i].type + "</td><td>" + contentTypes[i].size+"</td><td>" + contentTypes[i].num + "</td></tr>";

            }
            reportDetailHtml += '</tbody></table>';


            //域名资源展示
            reportDetailHtml += '<table class="table table-bordered"><thead><th>域名类型</th><th>大小(KB)</th><th>数量(个)</th></thead>' +
                '<tbody>';
            for(var i =0 ; i<domains.length;i++ ){
                reportDetailHtml  += "<tr><td>" + domains[i].name + "</td><td>" + domains[i].size+"</td><td>" + domains[i].num + "</td></tr>";

            }
            reportDetailHtml += '</tbody></table>';


            //图片资源展示
            reportDetailHtml += '<table class="table table-bordered"><thead><th>图片资源url</th><th>大小</th><th>压缩</th><th>缓存</th><th>耗时(ms)</th><th>返回码</th><th>类型</th></thead>' +
                '<tbody>';
            for(var i =0 ; i<imgs.length;i++ ){
                reportDetailHtml  += "<tr><td style='width: 50%;word-break:break-all'><a target='_blank' href='" + imgs[i].url + "'>"+imgs[i].url+"</a></td><td>"+ imgs[i].size + "</td><td>"+ imgs[i].compress + "</td><td>"+ imgs[i].cache + "</td><td>"+ imgs[i].time + "</td><td>" + imgs[i].returnCode+"</td><td>" + imgs[i].type + "</td></tr>";

            }
            reportDetailHtml += '</tbody></table>';

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


