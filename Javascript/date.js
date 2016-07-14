// ���ڸ�ʽ������
Date.prototype.Format = function(fmt)
{
	var o = {
		"M+" : this.getMonth()+1,                 //�·�
		"d+" : this.getDate(),                    //��
		"h+" : this.getHours(),                   //Сʱ  
		"m+" : this.getMinutes(),                 //��
		"s+" : this.getSeconds(),                 //��
		"q+" : Math.floor((this.getMonth()+3)/3), //����
		"S"  : this.getMilliseconds()             //����
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

// ����ʱ���
function dateDiff(date1, date2)
{
	// ��������ʱ���ʽΪ��yyyyMMdd
	date1 = date1.substr(0,4) + "/" + date1.substr(4,2) + "/" + date1.substr(6,2);
	date2 = date2.substr(0,4) + "/" + date2.substr(4,2) + "/" + date2.substr(6,2);

	// ת��Ϊʱ���
	var dt1 = new Date(Date.parse(date1));
    var dt2 = new Date(Date.parse(date2));
    
    try
    {
        return Math.abs(Math.round((dt2.getTime() - dt1.getTime()) / (1000*60*60*24)));
    }
    catch (e)
    {
 	    alert("����ʱ���ʱ�����˴���");
        return -1;
    }
}