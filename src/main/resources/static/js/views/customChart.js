var context = document.getElementById('line_chart').getContext('2d');
var customChart = new Chart(context, {
    type : 'line',
    data : {
        labels : ['11월 19일', '11월 20일', '11월 21일', '11월 22일', '11월 23일', '11월 24일', '11월 25일'],
        datasets : [{
            label : '페이지뷰',
            data: [7, 9, 6, 8, 1, 5, 12],
            backgroundColor: "rgb(34,219,107, 0.5)",
            fill: true
        },{
            label: '방문자',
            data: [5, 7, 5, 4, 20, 19, 15],
            backgroundColor: "rgb(24,151,73, 0.5)",
            fill: true
        }]
    },options: {
        responsive: true,
        maintainAspectRatio : false,
        plugins:{
            legend:{
                align:'end'
            }
        }
    }
});
