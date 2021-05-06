var chartType = Object.freeze({
    TYPE_DOUGHNUT : 'doughnut',
    TYPE_LINE : 'line',
    TYPE_BAR : 'bar'
});

function drawingBarChart(id){
    var context = document.getElementById(id).getContext('2d');
    var barChart = new Chart(context, {
        type : 'bar',
        data : {
            labels: ['이승준', '홍수진', '김수민', '김진아', '이무진', '진성민', '전나은'],
            datasets: [{
                label: '수익',
                data: [750000, 950000, 650000, 850000, 150000, 550000, 970000],
                backgroundColor: ["rgb(34,219,107, 0.5)",
                    "rgba(196,253,217,0.5)",
                    "rgb(231,155,89)",
                    "rgb(88,91,224)",
                    "rgb(255,117,173)",
                    "rgb(255,233,117)",
                    "rgb(55,238,255)"
                ],
                borderWidth:1
            }]
        },options: {
            responsive: true,
            maintainAspectRatio : false,
            plugins:{
                legend:{
                    align:'end'
                }
            },
            scale:{
                y:{
                    beforeAtZero: true
                }
            }
        }
    });

}

function drawingLineChart(id){
    var context = document.getElementById(id).getContext('2d');
    var lineChart = new Chart(context, {
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
}

function drawingDoughnutChart(id){
    var context = document.getElementById(id).getContext('2d');
    var doughnutChart = new Chart(context, {
        type : 'doughnut',
        data : {
            labels : ['Java', 'Spring', 'SpringBoot', 'Hibernate', 'JPA', 'Security', 'etc'],
            datasets : [{
                label : 'category',
                data: [200, 386, 222, 97, 111, 25, 379],
                backgroundColor: ['rgb(34,219,107, 0.5)',
                    'rgb(24,151,73, 0.5)',
                    'rgb(203,255,150)',
                    'rgb(24,121,151)',
                    'rgb(24,52,151)',
                    'rgb(122,90,238)',
                    'rgb(253,196,113)'
                ],
                hoverOffset: 4
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
}