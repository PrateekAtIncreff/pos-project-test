
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTIONS

//UI DISPLAY METHODS
function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}
function displayOrderList(data){
    var $tbody = $('#order-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];
    		var buttonHtml = ' <button onclick="displayParticularOrder(' + e.id + ')">View Order</button>'
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + e.date_time[2]+'/'+e.date_time[1]+'/'+e.date_time[0]+',  '
    		         + e.date_time[3]+':'+e.date_time[4] +'</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}



function displayParticularOrder(id){
    var url = getOrderUrl()+"/"+id;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	        $('#view-order-modal').modal('toggle');
    	   		displayOrderItems(data);
    	   },
    	   error: handleAjaxError
    	});
}
function displayOrderItems(data){
    var $tbody = $('#order-view').find('tbody');
    $tbody.empty();
    let sum = 0;
    for(var i in data){
        var e = data[i];
        let amount = parseInt(e.quantity) * parseFloat(e.selling_price);
        var row = '<tr>'
        + '<td>' + e.barcode + '</td>'
        + '<td>' + e.name + '</td>'
        + '<td>' + e.quantity + '</td>'
        + '<td>' + e.selling_price + '</td>'
        + '<td>' + amount + '</td>'
        + '</tr>';
        $tbody.append(row);
        sum = sum+amount;
    }
    var totalAmt = '<td>' + ' Total Price:  RS ' + sum  + '</td>';
    $tbody.append(totalAmt);
}

function refresh(){
    location.reload(true);
}
//INITIALIZATION CODE
function init(){

}

$(document).ready(init);
$(document).ready(getOrderList);


