$(function(){
	// 読込時
	$(document).ready( function(){
		ajaxSearch();
	});
	$('#search-btn').on('click', function(){
		ajaxSearch();
	});
	$('#check-btn').on('click', function(){
		alertid();
	});
	$('#search-date-from-y, #search-date-from-m').change(function(){
		setDay('from');
	});
	$('#search-date-to-y, #search-date-to-m').change(function(){
		setDay('to');
	});
	$('#chk-darkmode').change(function() {
		changeColorMode($('#chk-darkmode').prop('checked'));
	});
	window.addEventListener("resize", function(event) {
  		changeColorMode($('#chk-darkmode').prop('checked'));
	});
	
});

function changeColorMode(chk){
	// ダークモード
	if(chk==true){
		$("body").css({'color':'#FFFFFF','background-color':'#333333'});
		$("table th,td,tr").css({'border-color':'#FFFFFF'});
		$("table th").css({'color':'#000000'});
		$("table td").css({'color':'#FFFFFF'});
		
		if(window.matchMedia("(max-width: 767px)").matches){
			$("#pricesum th").css({'color':'#000000'});
			$("#expenses td").css({'color':'#000000'});
		}
		else{
			$("#pricesum th").css({'color':'#000000'});
			$("#expenses th").css({'color':'#000000'});
			$("#expenses td").css({'color':'#FFFFFF'});
		}
	}
	// 通常
	else{
		$("body").css({'color':'#000000','background-color':'#FFFFFF'});
		$("table th,td,tr").css({'border-color':'#000000'});
		$("table th").css({'color':'#000000'});
		$("table td").css({'color':'#000000'});
		if(window.matchMedia("(max-width: 767px)").matches){
		}
		else{
			$("#pricesum th").css({'color':'#000000'});
			$("#expenses th").css({'color':'#000000'});
			$("#expenses td").css({'color':'#000000'});	
		}
	};
};

function alertid(){
	result = new Array();
	$('#expenses td').each(function (index, row) {
	    if ($(row).find('input:checkbox').is(':checked')){
	        result.push($(this).nextAll().eq(0).text().replace('ID：',''));
	    }
	});
	alert(result);
};

function setDay(fromOrto){
	var lastday = setLastDay($('#search-date-'+fromOrto+'-y').val(), $('#search-date-'+fromOrto+'-m').val());
	var option = '<option value="" selected="selected"></option>\n';
	for (var i = 1; i <= lastday; i++) {
		option += '<option value="' + i + '">' + i + '</option>\n';
	}
	$('#search-date-'+fromOrto+'-d').html(option);
};

function setLastDay(year, month){
	var lastday = new Array('', 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0){
		lastday[2] = 29;
	}
    return lastday[month];
};

function ajaxSearch(){
	loadingIn();
	
	var id,datefrom,dateto,yobi,use,categ,name,price,rate,paystatus,remarks;
	
	id=$('#search-id').val();
	
	if($('#search-date-from-y').val()!='' && $('#search-date-from-m').val()!='' && $('#search-date-from-d').val()!=''){
		datefrom=$('#search-date-from-y').val()+'-'+$('#search-date-from-m').val()+'-'+$('#search-date-from-d').val();	
	}
	else if($('#search-date-from-y').val()!='' && $('#search-date-from-m').val()!='' && $('#search-date-from-d').val()==''){
		datefrom=$('#search-date-from-y').val()+'-'+$('#search-date-from-m').val()+'-1';
	}
	else{
		datefrom='';
	};
	
	if($('#search-date-to-y').val()!='' && $('#search-date-to-m').val()!='' && $('#search-date-to-d').val()!=''){
		dateto=$('#search-date-to-y').val()+'-'+$('#search-date-to-m').val()+'-'+$('#search-date-to-d').val();	
	}
	else if($('#search-date-to-y').val()!='' && $('#search-date-to-m').val()!='' && $('#search-date-to-d').val()==''){
		dateto=$('#search-date-to-y').val()+'-'+$('#search-date-to-m').val()+'-' + setLastDay($('#search-date-to-y').val(), $('#search-date-to-m').val());
	}
	else{
		dateto='';
	};
	
	yobi=$('#search-yobi').val();
	use=$('#search-use').val();
	categ=$('#search-categ').val();
	name=$('#search-name').val();
	price=$('#search-price').val();
	rate=$('#search-rate').val();
	paystatus=$('#search-paystatus').val();
	remarks=$('#search-remarks').val();
	
	$.ajax({
		url:'expenses_db/Search',
		type:'POST',
		cache:false,
		data: {
			id: id,
			datefrom: datefrom,
			dateto: dateto,
			yobi: yobi,
			use: use,
			categ: categ,
			name: name,
			price: price,
			rate: rate,
			paystatus: paystatus,
			remarks: remarks
			}
	})
	.done(function(data){
		$('#search-condition').empty();
		if(id!=''){$('#search-condition').append('<tr><th>ID</th><td>'+id+'</td></tr>')};
		if(datefrom!=''){$('#search-condition').append('<tr><th>日付(From)</th><td>'+datefrom+'</td></tr>')};
		if(dateto!=''){$('#search-condition').append('<tr><th>日付(To)</th><td>'+dateto+'</td></tr>')};
		if(yobi!=''){$('#search-condition').append('<tr><th>曜日</th><td>'+yobi+'</td></tr>')};
		if(use!=''){$('#search-condition').append('<tr><th>使途</th><td>'+use+'</td></tr>')};
		if(categ!=''){$('#search-condition').append('<tr><th>カテゴリ</th><td>'+categ+'</td></tr>')};
		if(name!=''){$('#search-condition').append('<tr><th>名称</th><td>'+name+'</td></tr>')};
		if(price!=''){$('#search-condition').append('<tr><th>金額</th><td>'+price+'</td></tr>')};
		if(rate!=''){$('#search-condition').append('<tr><th>比率</th><td>'+rate+'</td></tr>')};
		if(paystatus!=''){$('#search-condition').append('<tr><th>精算</th><td>'+paystatus+'</td></tr>')};
		if(remarks!=''){$('#search-condition').append('<tr><th>備考</th><td>'+remarks+'</td></tr>')};
		loadData(data,$('#chk-darkmode').prop('checked'));
		
	})
	.fail(function(textStatus, errorThrown){
		alert('検索に失敗しました' + '\n' + 'status:' + textStatus + '\n' +'errorThrown:' + errorThrown);
	});
	
	loadingOut();
};

function loadData(data,colorflg){
	$('#expenses').empty();
	$('#pricesum').empty();
	
	// データ用
	var row_data;

	// 行のループ  ( <tr></tr> )
	$.each(data,function(index,value){
		// 合計テーブル
		if (index == 0){
			$.each(value,function(key,val){
				$('#pricesum').append('<th>'+key+'</th>');
			});
			// テーブルに行を追加	
			row_data = $('#pricesum').append('<tr></tr>');

			// 列のループ ( <td></td> )
			$.each(value,function(key,val){
				// 行に列を追加
				row_data.append('<td>'+val+'</td>');
			});
		};
		
		//一覧
		// 初回はタイトル作成
		if (index == 1){
			//　選択チェックボックス列のタイトルを追加
			$('#expenses').append('<th class = th-select-chk>選択</th>');
			// 列のループ ( <th></th> )
			$.each(value,function(key,val){
				$('#expenses').append('<th class = th-' + key +'>'+key+'</th>');
			});
		};
		
		if (index >= 1){
			// テーブルに行を追加
			row_data = $('#expenses').append('<tr></tr>');
			
			//　選択チェックボックス列を追加
			row_data.append('<td class = td-select-chk><span>選択</span><input type="checkbox"></td>');
			
			// 列のループ ( <td></td> )
			$.each(value,function(key,val){
				// 行に列を追加
				row_data.append('<td class = td-' + key +'><span>'+key+'</span>'+val+'</td>');
			});
		};
	});
	changeColorMode(colorflg)
};

function loadingIn(){
	$(document).ajaxSend(function() {$("#loading-lay").fadeIn(500);});
};

function loadingOut(){
	setTimeout(function(){$("#loading-lay").fadeOut(500);},1000);
};

