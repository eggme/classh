var context = document.getElementsByClassName('doughnut_chart').getContext('2d');
var customChart = new Chart(context, {
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
