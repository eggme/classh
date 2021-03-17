const getJSON = function(url, callback){
    const xhr = new XMLHttpRequest();
    xhr.open('GET',url,true);
    xhr.responseType = 'json';
    xhr.onload = function(){

        const status = xhr.status;
        if(status === 200){
            callback(null,xhr.response);
        }else{
            callback(status,xhr.response);
        }
    };
    xhr.send();
}

getJSON('http://api.openweathermap.org/data/2.5/weather?q=seoul&appid=c52cc813573515081af5b92b6c2e98e5&units=metric',
    function(err,data){
        if(err !== null){
            alert('죄송합니다 오류발생 삐빅..'+ err);
        }else{
            alert('현재 온도는 '+ data.weather[0].main+ '도 입니다.');
        }
    });
