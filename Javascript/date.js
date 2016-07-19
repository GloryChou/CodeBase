// 日期格式化函数
Date.prototype.Format = function(fmt)
{
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时  
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;   
}

// 计算时间差
function dateDiff(date1, date2)
{
    // 传进来的时间格式为：yyyyMMdd
    date1 = date1.substr(0,4) + "/" + date1.substr(4,2) + "/" + date1.substr(6,2);
    date2 = date2.substr(0,4) + "/" + date2.substr(4,2) + "/" + date2.substr(6,2);

    // 转换为时间戳
    var dt1 = new Date(Date.parse(date1));
    var dt2 = new Date(Date.parse(date2));
    
    try
    {
        return Math.abs(Math.round((dt2.getTime() - dt1.getTime()) / (1000*60*60*24)));
    }
    catch (e)
    {
        alert("计算时间差时发生了错误！");
        return -1;
    }
}
