var context = document.getElementById('bar_chart').getContext('2d');
var customChart = new Chart(context, {
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
