function rn(){
        return (Math.floor( Math.random()* (1+40-20) ) ) + 20;
}
$(function () {

    var data = [[1,rn()],[2,rn()],[3,rn()],[4,rn()],[5,rn()],[6,rn()],[7,rn()],[8,rn()],[9,rn()],[10,rn()],[11,rn()],[12,rn()]];
    $(".sparkline").sparkline(data,{
        width: '100%',
        height: 50,
        fillColor: 'transparent',
    });

    i=1;
    for (i=1; i<=3; i++) {
        data = [rn(),rn(),rn(),rn(),rn(),rn(),rn(),rn(),rn(),rn(),rn()];
        $(".stat-monthly-"+i).sparkline(data,{
            width: '100%',
            height: 50,
            fillColor: '#efefef',
            spotRadius: 2
        });
    } 

    $(".stat-bullet").sparkline([225,130,250,175,150,50], {
        type: 'bullet',
        width: '100%',
        height: 70,
        targetColor: '#468847',
        performanceColor: '#b94a48',
        rangeColors: ['#dff0d8','#fcf8e3','#f2dede','#b94a48']
    });
    
});