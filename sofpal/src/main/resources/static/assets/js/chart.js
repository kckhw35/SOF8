window.onload = function() {
	var year = '[[${((map.yearSales*1.0)/map.yearTarget)*100}]]';
	var month = '[[${((map.monthSales*1.0)/map.monthTarget)*100}]]';
	var week = '[[${((map.weekSales*1.0)/map.weekTarget)*100}]]';
	var day = '[[${((map.daySales*1.0)/map.dayTarget)*100}]]';
	$('#easypiechart-blue').data('easyPieChart').update(year);
	$('#easypiechart-red').data('easyPieChart').update(month);
	$('#easypiechart-teal').data('easyPieChart').update(week);
	$('#easypiechart-orange').data('easyPieChart').update(day);

	/* 카테고리별 금월 판매현황
	----------------------------------------*/
	Morris.Donut({
		element: 'category-month',
		data: [
			{ label: "패브릭소파", value: 12 },
			{ label: "가죽소파", value: 30 },
			{ label: "펫소파", value: 20 },
			{ label: "리빙룸", value: 20 }],
		colors: ['#A6A6A6', '#414e63', '#e96562', '#A8E9DC'],
		resize: true
	});

	Morris.Donut({
		element: 'month',
		data: [
			{ label: "패브릭소파", value: 12 },
			{ label: "가죽소파", value: 30 },
			{ label: "펫소파", value: 20 },
			{ label: "리빙룸", value: 20 }],
		colors: ['#A6A6A6', '#414e63', '#e96562', '#A8E9DC'],
		resize: true
	});

	$('.bar-chart').cssCharts({ type: "bar" });
	$('.donut-chart').cssCharts({ type: "donut" }).trigger('show-donut-chart');
	$('.line-chart').cssCharts({ type: "line" });
	$('.pie-thychart').cssCharts({ type: "pie" });
};